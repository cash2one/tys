package com.tys.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class MUtil {

	/**
	 * 发送HTTP GET请求,返回字符结果
	 * 
	 * @param url
	 * @return
	 */
	public static String getStringByUrl(String url) {
		return getStringByUrl(url, null);
	}

	private static RequestConfig getHttpClientConfig(){
		final RequestConfig config = RequestConfig.custom()
				  .setSocketTimeout(5000)
				  .setConnectTimeout(5000)
				  .setConnectionRequestTimeout(10000)
				  .setStaleConnectionCheckEnabled(true)
				  .build();
		return config;
	}
	
	/**
	 * 发送HTTP GET请求,带上cookie,返回字符结果
	 * @param url
	 * @param cookie
	 * @return
	 */
	public static String getStringByUrl(String url, String cookie) {
//		MUtil.log("getStringByUrl=" + url);
		String ret = "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(getHttpClientConfig());
			if (cookie != null){
				MUtil.log("cookie=" + cookie);
				httpget.addHeader(new BasicHeader("Cookie", cookie));
			}
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			try {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					ret = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}

//		MUtil.log("getStringByUrl ret=" + ret);
		return ret;
	}

	/**
	 * 发送HTTP POST请求,返回字符结果,HttpEntity示例如下 List<NameValuePair> nvps = new
	 * ArrayList<NameValuePair>(); nvps.add(new BasicNameValuePair("username",
	 * "vip")); nvps.add(new BasicNameValuePair("password", "secret")); new
	 * UrlEncodedFormEntity(nvps)
	 * 
	 * @param url
	 * @return
	 */
	public static String postStringByUrl(String url, HttpEntity entity) {
		MUtil.log("postStringByUrl=" + url);
		String ret = "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setConfig(getHttpClientConfig());
			httppost.setEntity(entity);

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					ret = EntityUtils.toString(response.getEntity(), "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}

		MUtil.log("postStringByUrl ret=" + ret);
		return ret;
	}
	
	
	
	
	/**
	 * 上传文件
	 * 
	 * @param targetURL
	 * @param filename
	 * @return
	 */
	public static String postFileByUrl(String targetURL, String filename) {
		String ret = "";
		MUtil.log("postFileByUrl=" + targetURL);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(targetURL);
			httppost.setConfig(getHttpClientConfig());
			FileBody filebody = new FileBody(new File(filename));
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(Charset.forName("UTF-8"));
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
			builder.addPart("file", filebody);
			
			HttpEntity reqEntity = builder.build();

			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					ret = EntityUtils.toString(entity, "UTF-8");
					
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}

		// File targetFile = new File(filename);
		// PostMethod filePost = new PostMethod(targetURL);
		// try {
		// // 通过以下方法可以模拟页面参数提交
		// // filePost.setParameter("name", "中文");
		// // filePost.setParameter("pass", "1234");
		// Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
		// filePost.setRequestEntity(new MultipartRequestEntity(parts,
		// filePost.getParams()));
		// HttpClient client = new HttpClient();
		// client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		// int status = client.executeMethod(filePost);
		// if (status == HttpStatus.SC_OK) {
		// // 上传成功
		// ret = filePost.getResponseBodyAsString();
		// } else {
		// // 上传失败
		// }
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// } finally {
		// filePost.releaseConnection();
		// }
		MUtil.log("postFileByUrl ret=" + ret);
		return ret;
	}
	
	/**
	 * 修改文件
	 * 
	 * @param targetURL
	 * @param filename
	 * @return
	 */
	public static String postFileByUrl(String targetURL, String filename,String id) {
		String ret = "";
		MUtil.log("postFileByUrl=" + targetURL);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(targetURL);
			httppost.setConfig(getHttpClientConfig());
			FileBody filebody = new FileBody(new File(filename));
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(Charset.forName("UTF-8"));
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
			builder.addPart("file", filebody);
			builder.addTextBody("id", id);
			HttpEntity reqEntity = builder.build();
			
			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					ret = EntityUtils.toString(entity, "UTF-8");
					
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}
		MUtil.log("postFileByUrl ret=" + ret);
		return ret;
	}

	/**
	 * 回复HTTP请求方法
	 * 
	 * @param response
	 *            Servlet参数
	 * @param text
	 */
	public static void outPrint(HttpServletResponse response, String text) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(text);
			out.flush();
			MUtil.log("outPrint=" + text);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 回复HTTP请求方法,不自动关闭输出流
	 * 
	 * @param response
	 *            Servlet参数
	 * @param text
	 */
	public static void outPrintEx(HttpServletResponse response, String text) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(text);
			out.flush();
			MUtil.log("outPrint=" + text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹出提示框
	 * 
	 * @param response
	 * @param text
	 */
	public static void alertDialog(HttpServletResponse response, String text) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("window.parent.parent.frames.main.location.reload();alert('" + text + "');");
		// sb.append("window.showModalDialog('NewBox.jsp',window,'status:no;scroll:no;dialogWidth:235px;dialogHeight:100px');");
		sb.append("</script>");
		outPrintEx(response, sb.toString());
	}

	/**
	 * 弹出提示框，确定后返回
	 * 
	 * @param response
	 * @param text
	 */
	public static void alertAndBack(HttpServletResponse response, String text) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('" + text + "');");
		// sb.append("location.replace(document.referrer);");
		sb.append("history.go(-1);");
		// sb.append("location.reload()");
		sb.append("</script>");
		outPrintEx(response, sb.toString());
	}

	/**
	 * 根据时区获取指定时间字串
	 * 
	 * @param timezone
	 * @return
	 */
	public static String convertRtcTime(String timezone) {
		final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 默认时区
		float tz = 8.0f;
		try {
			tz = Float.parseFloat(timezone);
		} catch (NumberFormatException e) {
		}

		String times;
		if (tz < 0) {
			int intmin = (int) ((-1 * tz % 1) * 60);
			String strmin = intmin + "";
			String min = strmin;
			if (strmin.length() <= 1) {
				min = "0" + strmin;
			}
			times = "GMT" + ((int) tz) + ":" + min;
		} else {
			int intmin = (int) ((tz % 1) * 60);
			String strmin = intmin + "";
			String min = strmin;
			if (strmin.length() <= 1) {
				min = "0" + strmin;
			}
			times = "GMT+" + ((int) tz) + ":" + min;
		}

		sdf.setTimeZone(TimeZone.getTimeZone(times));
		return sdf.format(System.currentTimeMillis());
	}

	/**
	 * 保存文件
	 * 
	 * @param filePath
	 * @param is
	 */
	public static void saveFile(String filePath, InputStream is) {
		MUtil.log("saveFile path="+filePath);
		FileOutputStream fos = null;
		try {
			File path = new File(filePath);
			path.getParentFile().mkdirs();

			fos = new FileOutputStream(filePath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
			// 刷新此缓冲的输出流
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
	}
	
	
	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static boolean delFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	

	/**
	 * 获取webapps路径
	 * 
	 * @return
	 */
	public static String getWebappsPath() {
		String tempdir = getTomcatPath() + "webapps" + File.separator;
		return tempdir;
	}

	/**
	 * 获取tomcat路径
	 * 
	 * @return
	 */
	public static String getTomcatPath() {
		return System.getProperty("catalina.home") + File.separator;// Tomcat目录
	}
	
	/**
	 * 获取项目class 路径路径
	 * 
	 * @return
	 */
	public static String getPrjClassPath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();// Tomcat目录
	}

	/**
	 * 获取tomcat log文件
	 * 
	 * @return
	 */
	public static File getLogsFile() {
		String logPath = MUtil.getTomcatPath() + "logs" + File.separator + "catalina.out";
		File logfile = new File(logPath);
		return logfile;
	}

	/**
	 * 获取自定义 cws.log文件
	 * 
	 * @return
	 */
	public static File getCustomLogsFile() {
		String logPath = MUtil.getTomcatPath() + "logs" + File.separator + "cws.log";
		File logfile = new File(logPath);
		return logfile;
	}

	private final static Logger log = LoggerFactory.getLogger(MUtil.class);

	/**
	 * 打印LOG方法,info等级
	 * 
	 * @param text
	 */
	public static void log(String text) {
		log.info(text);
//		 System.out.println(text);
	}

	/**
	 * 打印LOG方法,error等级
	 * 
	 * @param text
	 */
	public static void err(String text) {
		log.error(text);
	}

}
