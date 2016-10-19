package com.tys.spi;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class VirtualDetector extends IoHandlerAdapter {

	private NioSocketConnector mConnector;
	private String mImei;
	private InetSocketAddress curAddr;
	private IoSession mSession;

	private boolean firstConn = true;

	public static void main(String[] arg) throws Exception {
//		new VirtualDetector("localhost", 7087, "669897719778992");
		new VirtualDetector("112.126.65.114", 7087, "669897719778992");
	}

	public VirtualDetector(String ip, int port, String imei) {
		curAddr = new InetSocketAddress(ip, port);
		mConnector = new NioSocketConnector();
		mConnector.setDefaultRemoteAddress(curAddr);
		DefaultIoFilterChainBuilder chain = mConnector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ProtocolCodecFactory() {

			private ProtocolEncoder encoder = new ProtocolEncoderAdapter() {
				@Override
				public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
					String value = (String) message;
					IoBuffer buffer = IoBuffer.allocate(value.length()).setAutoExpand(true);
					buffer.putString(value, Charset.defaultCharset().newEncoder());
					buffer.flip();
					out.write(buffer);
				}
			};

			private ProtocolDecoder decoder = new CumulativeProtocolDecoder() {
				@Override
				protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
					out.write(in.getString(Charset.defaultCharset().newDecoder()));
					return false;
				}
			};

			@Override
			public ProtocolEncoder getEncoder(IoSession session) throws Exception {
				return encoder;
			}

			@Override
			public ProtocolDecoder getDecoder(IoSession session) throws Exception {
				return decoder;
			}
		}));
		SocketSessionConfig dcfg = (SocketSessionConfig) mConnector.getSessionConfig();
		dcfg.setReaderIdleTime(180);// 3分钟心跳包
		mConnector.setConnectTimeoutMillis(30000);
		mConnector.setHandler(this);
		this.mImei = imei;

		// 开始连接
		connect(imei);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		printfLog("sessionOpened");
		mSession = session;
		if (firstConn) {
			firstConn = false;
			sendRegist(session);
		} else {
			sendHeartBeat(session);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		printfLog("sessionClosed");
		new Thread() {
			@Override
			public void run() {
				int count = 1;
				while (true) {
					try {
						Thread.sleep(5000 + (count * 1000));// 5S后重连,失败一次增加1S
					} catch (Exception e) {
					}
					count++;
					if (connect(mImei)) {
						break;
					}
				}
			}
		}.start();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		messageReceiveEx(session, (String) message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		printfLog("send to \t\t" + message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if (status.equals(IdleStatus.READER_IDLE)) {
			// 20S空闲时间到，判断心跳是否回复
			if (session.containsAttribute("t")) {
				// 回复超时,关闭连接
				// 20S心跳超时
				session.removeAttribute("t");
				session.close(true);
				printfLog(mImei + " 心跳超时");
			}
		} else if (status.equals(IdleStatus.WRITER_IDLE)) {
			// 30S空闲时间到，发送心跳包
			sendHeartBeat(session);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		printfLog("exception " + cause);
		session.close(true);
	}

	private int sn = 0;

	private void sendHeartBeat(IoSession session) {
		long time = System.currentTimeMillis();
		session.setAttribute("h", time);// 计算下次发送心跳时间

		session.write(createSendData("05", mImei + mImei + "30"));
		session.getConfig().setWriterIdleTime(30);// 30S发送下一条心跳
	}

	private void sendRegist(IoSession session) {
		long time = System.currentTimeMillis();
		session.setAttribute("t", time);// 用于计算等待回复超时
		session.write(createSendData("10", mImei + mImei));
		session.getConfig().setReaderIdleTime(20);// 20S等待回复超时
		session.getConfig().setWriterIdleTime(30);// 30S发送下一条心跳
		
	}
	
	private void uploadAttendance(IoSession session) {
		StringBuffer sb = new StringBuffer();
		sb.append(mImei);
		sb.append(13);
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		int imeilast = 330017000;//取imei后9位，转成16进制
		for(int i=0; i<13; i++){
			String hex = String.format("%09X", imeilast+i);
			sb.append(hex.substring(hex.length() - 8));
			sb.append(dateFormat.format(new Date()));
			sb.append(i%4);//0-进校 1-离校 2-进校（卡电量低）3-离校（卡电量低）4-单读头模式 5-单读头模式(电量低)
		}
		
		long time = System.currentTimeMillis();
		session.setAttribute("t", time);// 用于计算等待回复超时
		session.write(createSendData("09", sb.toString()));
		session.getConfig().setReaderIdleTime(20);// 20S等待回复超时
		session.getConfig().setWriterIdleTime(30);// 30S发送下一条心跳
	}

	private String createSendData(String cmd, String pack) {

		int packLen = 0;
		sn++;
		if (sn > 9999) {
			sn = 0;
		}
		final DecimalFormat df = new DecimalFormat("0000");

		packLen = pack.length() + 12;
		StringBuffer sb = new StringBuffer();
		sb.append("DIRM01");
		sb.append(df.format(packLen));// LEN
		sb.append(cmd);// CMD
		sb.append(df.format(sn));// SN
		sb.append(pack);// PACK

		sb.append(String.format("%02X", checkSum(sb.subSequence(6, sb.length()).toString().getBytes())));// 除去数据头所有数据进行计算校验

		return sb.toString();

	}

	private int checkSum(byte[] checkBytes) {
		int checkSum = 0;

		for (int i = 0; i < checkBytes.length; i++) {
			checkSum += checkBytes[i] & 0xff;
		}

		checkSum = checkSum & 0xff;

		return checkSum;
	}

	public void messageReceiveEx(IoSession session, String message) {
		printfLog("receive \t\t" + message);

		String cmd = message.substring(10, 12);
		String pack = message.substring(16, message.length() - 2);
		String checksum = message.substring(message.length() - 2);
		printfLog("cmd=" + cmd + " pack=" + pack + " checksum=" + checksum);
		if (cmd.equals("10")) {
			if(pack.charAt(0) == '1'){
				printfLog("验证成功");
				uploadAttendance(session);
			} else if(pack.charAt(0) == '2'){
				printfLog("未注册探头ID");
			} else {
				printfLog("验证失败");
			}
		}

		if (session.containsAttribute("t")) {
			// 更新心跳包回复时间
			Long time = (Long) session.getAttribute("t");
			int last = (int) (System.currentTimeMillis() - time);
			if (last < 20000) {// 30S心跳发送到时
				session.getConfig().setReaderIdleTime(last / 1000);// 心跳包剩余时间
			}
		}

		session.getConfig().setWriterIdleTime(30);// 30S后发送心跳
	}

	

	public boolean connect(String boxCode2) {
		boolean isOk = false;
		printfLog("Start connect... " + curAddr);

		ConnectFuture connectFuture = mConnector.connect();
		connectFuture.awaitUninterruptibly();
		if (connectFuture.isConnected()) {
			isOk = true;
			printfLog("connect done");
		}
		return isOk;
	}

	public void close() {
		mConnector.dispose();
		printfLog("close...");
	}

	public void sendText(long id, String text) {
		IoSession session = getSession(id);
		session.write(text);
	}

	public IoSession getSession(long id) {
		return mConnector.getManagedSessions().get(id);
	}

	private void printfLog(String text) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sf.format(new Date()) + " " + text);
	}

}
