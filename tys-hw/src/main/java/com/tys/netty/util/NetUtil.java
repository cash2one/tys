package com.tys.netty.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtil {

	private static Logger logger = LoggerFactory.getLogger(NetUtil.class);

	private static String localIP;

	/**
	 * 获取本机IP地址，并自动区分Windows还是Linux操作系统
	 * 
	 * @return String
	 */
	public static String getLocalIP() {
		if (localIP != null) {
			return localIP;
		}

		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			String os = System.getProperty("os.name");  
			if (os.toLowerCase().startsWith("win")) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.�?��的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("Get Host Ip Fail.", e);
		}

		if (null != ip) {
			localIP = ip.getHostAddress();
		}
		return localIP;
	}

	
	
	/**
	 * 判断str是否合法ip
	 * @param str
	 * @return
	 */
	public static boolean isValidIP(String str) {
		Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
				+ "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
		return pattern.matcher(str).matches();
	}

}
