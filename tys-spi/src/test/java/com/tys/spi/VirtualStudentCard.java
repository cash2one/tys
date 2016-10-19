package com.tys.spi;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class VirtualStudentCard extends IoHandlerAdapter {

	private NioSocketConnector mConnector;
	private String mImei;
	private InetSocketAddress curAddr;
	private IoSession mSession;

	private boolean firstConn = true;

	public static void main(String[] arg) throws Exception {
		new VirtualStudentCard("localhost", 7089, "669897330017000");
//		new VirtualStudentCard("112.126.65.114", 7089, "669897330017000");//太阳升
		try {
			VirtualStudentCard vsc = new VirtualStudentCard("localhost", 7089, "669897330017000");
//			VirtualStudentCard vsc = new VirtualStudentCard("112.126.65.114", 7089, "669897330017000");
			Thread.sleep(5000);
			
			vsc.testLowBattery();
			vsc.testOutfence1();
			vsc.testInfence1();
			vsc.testSMS();
			vsc.testSOS();
			vsc.testAGPS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

	public VirtualStudentCard(String ip, int port, String boxCode) {
		curAddr = new InetSocketAddress(ip, port);
		mConnector = new NioSocketConnector();
		mConnector.setDefaultRemoteAddress(curAddr);
		DefaultIoFilterChainBuilder chain = mConnector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), ";", ";")));
		SocketSessionConfig dcfg = (SocketSessionConfig) mConnector.getSessionConfig();
		dcfg.setReaderIdleTime(30);// 30S心跳包
		mConnector.setConnectTimeoutMillis(30000);
		mConnector.setHandler(this);
		this.mImei = boxCode;

		// 开始连接
		connect(boxCode);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		printfLog("sessionOpened");
		mSession = session;
		if (firstConn) {
			firstConn = false;
			sendTimeSync(session);
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

	private void sendHeartBeat(IoSession session) {
		long time = System.currentTimeMillis();
		session.setAttribute("t", time);// 计算心跳回复超时
		session.write("ii,limei:" + mImei + ",A,SC1.1,1.0");
		session.getConfig().setReaderIdleTime(20);// 20S等心跳回复超时
		session.getConfig().setWriterIdleTime(30);// 30S发送下一条心跳
	}

	private void sendTimeSync(IoSession session) {
		session.write("ii,limei:" + mImei + ",B,");
		session.getConfig().setReaderIdleTime(20);// 20S等待回复超时
		session.getConfig().setWriterIdleTime(30);// 30S发送下一条心跳
	}

	private void uploadData(IoSession session, String cmd, boolean isGps) {
		String data;
		if (isGps) {
			// 注意与学生卡标准协议有差别，多了几个逗号
			data = "limei:" + mImei + "," + cmd
					+ ",A,80,19,16,165,130130230533,2232.7807,N,11404.2924,E,1.74,,,,,,,,,,,,,,,,8,256,";
		} else {
			data = "limei:" + mImei + "," + cmd
					+ ",,77,18,0,,,,,,,,460,0,2633,f7a,20,460,0,2633,f71,b,460,0,2633,fa3,a,8,256,";
		}
		session.write(data);
	}

	public void messageReceiveEx(IoSession session, String message) {
		printfLog("receive \t\t" + message);

		if (message.equals("Ibaby OK")) {// 心跳包回复OK
			session.removeAttribute("t");
			return;
		}
		

		String cmd = "";
		String imei = "";
		String[] paras;

		String[] messageStrings = message.split(":");

		if (messageStrings.length == 2) {
			paras = messageStrings[1].split(",");

			if (paras.length > 1) {
				imei = paras[0];
				cmd = paras[1];
			}

			if (cmd.equals("B")) {// 时间同步

			} else if (cmd.equals("P")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + ",OK");

			} else if (cmd.equals("T")) {
				for (int i = 2; i < 5; i++) {
					if (paras[i].equals("0")) {
						paras[i] = "";
					}
				}
				session.write("ii,limei:" + mImei + "," + cmd + ",," + paras[2] + "," + paras[3] + "," + paras[4]
						+ ",OK");

			} else if (cmd.equals("G")) {
				session.write("ii,limei:" + mImei + "," + cmd + ",OK");

			} else if (cmd.equals("M")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + "," + paras[3] + "," + paras[4] + ","
						+ paras[5] + ",OK");

			} else if (cmd.equals("E")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + "," + paras[3] + "," + paras[4] + ","
						+ paras[5] + ",OK");

				uploadData(session, "tracker", true);

			} else if (cmd.equals("C")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + "," + paras[3] + "," + paras[4]
						+ ",OK");

			} else if (cmd.equals("D")) {
				if ((System.currentTimeMillis() & 0x01) == 1) {
					uploadData(session, "tracker", true);
				} else {
					uploadData(session, "tracker", false);
				}

			} else if (cmd.equals("I")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + ",OK");

			} else if (cmd.equals("N")) {
				session.write("ii,limei:" + mImei + "," + cmd + ",OK");

			} else if (cmd.equals("K")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + ",OK");

			} else if (cmd.equals("Q")) {
				if ((System.currentTimeMillis() & 0x01) == 1) {
					uploadData(session, "Q01", true);
				} else {
					uploadData(session, "Q01", false);
				}
			} else if (cmd.equals("U")) {
				session.write("ii,limei:" + mImei + "," + cmd + ",OK");
				// 更改IP
//				curAddr = new InetSocketAddress(paras[2], Integer.parseInt(paras[3]));
//				session.close(true);
			} else if (cmd.equals("R")) {
				session.write("ii,limei:" + mImei + "," + cmd + ",OK");

			} else if (cmd.equals("KQ")) {
				session.write("ii,limei:" + mImei + "," + cmd + ",OK");

			} else if (cmd.equals("HW")) {
				session.write("ii,limei:" + mImei + "," + cmd + ",OK");

			} else if (cmd.equals("W")) {
				session.write("ii,limei:" + mImei + "," + cmd + "," + paras[2] + ",OK");
			} else if (cmd.equals("AGPS")) {
			} else {
				printfLog("not parse cmd...");
				return;
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

	public void testLowBattery() {
		try {
			uploadData(mSession, "lowbattery", true);
			//Thread.sleep(3000);

			uploadData(mSession, "lowbattery", false);
			//Thread.sleep(3000);

		} catch (Exception e) {
		}
	}

	public void testOutfence1() {
		try {
			uploadData(mSession, "outfence1", true);
			//Thread.sleep(3000);

			uploadData(mSession, "outfence1", false);
			//Thread.sleep(3000);

		} catch (Exception e) {
		}

	}

	public void testInfence1() {
		try {
			uploadData(mSession, "infence1", true);
			//Thread.sleep(3000);

			uploadData(mSession, "infence1", false);
			//Thread.sleep(3000);

		} catch (Exception e) {
		}
	}

	public void testSMS() {
		try {
			uploadData(mSession, "SMS", true);
			//Thread.sleep(3000);

			uploadData(mSession, "SMS", false);
			//Thread.sleep(3000);

		} catch (Exception e) {
		}
	}

	public void testSOS() {
		try {
			uploadData(mSession, "SOS", true);
			//Thread.sleep(3000);

			uploadData(mSession, "SOS", false);
			//Thread.sleep(3000);

		} catch (Exception e) {
		}
	}

	public void testAGPS() {
		try {
			uploadData(mSession, "AGPS", false);
			//Thread.sleep(3000);
		} catch (Exception e) {
		}
	}

	private void printfLog(String text) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sf.format(new Date()) + " " + text);
	}

}
