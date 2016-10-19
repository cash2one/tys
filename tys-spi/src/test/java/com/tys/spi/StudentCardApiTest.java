package com.tys.spi;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class StudentCardApiTest {

//	private String host = "localhost:8080/studentcard-web";
	private String host = "112.126.65.114:7090/studentcard-web";

	private BasicCookieStore cookieStore = new BasicCookieStore();
	private CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

	private String mImei = "668801151100360";

	public static void main(String[] arg) throws Exception {
		final StudentCardApiTest test = new StudentCardApiTest();
//		test.autoRun();
		test.setAlarmTel();
//		test.getOncePosition();
//		test.listen();
	}

	public void autoRun() {
		try {
			VirtualStudentCard vsc = new VirtualStudentCard(host.split(":")[0], 7089, mImei);
			Thread.sleep(5000);
			
			setElectronicFence();
			setAlarmTel();
			issuingNotice();
			setClassMode();
			setTiming();
			setContinuationUpload();
			getOncePosition();
			setTimeZone();
			closeLongConnection();
			switchGPS();
			switchMsgMode();
			restoreFactoryVal();
			sendAttendanceInfo();
			sendWorkInfo();
			
			
			
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

	// 设置电子围栏
	public void setElectronicFence() throws Exception {
		System.out.println("******************test=setElectronicFence");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/setElectronicFence");
		httppost.setHeader("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("switchOrder", 0);
		json.put("latitude", 114.087345);
		json.put("longitude", 22.5479);
		json.put("roundNo", 1);
		json.put("radius", 300);
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void setAlarmTel() throws Exception {
		System.out.println("******************test=setAlarmTel");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/setAlarmTel");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("tel2", "1008611");
		json.put("tel3", "1008612");
		json.put("tel4", "1008613");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void issuingNotice() throws Exception {
		System.out.println("******************test=issuingNotice");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/issuingNotice");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("noticeContent", "你好。1234ADSF");
		json.put("tel3", "1008612");
		json.put("tel4", "1008613");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void setClassMode() throws Exception {
		System.out.println("******************test=setClassMode");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/setClassMode");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("weekSets", "1111100");
		json.put("morningPeriod", "08:30-11:30");
		json.put("afternoonPeriod", "14:30-17:30");
		json.put("nightPeriod", "18:30-21:30");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void setTiming() throws Exception {
		System.out.println("******************test=setTiming");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/setTiming");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("weekSets", "1111100");
		json.put("morningTime", "08:30");
		json.put("afternoonTime", "14:30");
		json.put("nightTime", "17:45");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void setContinuationUpload() throws Exception {
		System.out.println("******************test=setContinuationUpload");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/setContinuationUpload");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("number", "50");
		json.put("interval", "30");
		json.put("isActive", "1");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void getOncePosition() throws Exception {
		System.out.println("******************test=getOncePosition");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/getOncePosition/" + mImei);
		httppost.setHeader("Content-Type", "application/json");

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void setTimeZone() throws Exception {
		System.out.println("******************test=setTimeZone");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/setTimeZone");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("timeZone", "+8");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void closeLongConnection() throws Exception {
		System.out.println("******************test=closeLongConnection");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/closeLongConnection/" + mImei);
		httppost.setHeader("Content-Type", "application/json");

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void switchGPS() throws Exception {
		System.out.println("******************test=switchGPS");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/switchGPS");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("switchType", "0");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void switchMsgMode() throws Exception {
		System.out.println("******************test=switchMsgMode");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/switchMsgMode");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("switchType", "0");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void restoreFactoryVal() throws Exception {
		System.out.println("******************test=restoreFactoryVal");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/restoreFactoryVal/" + mImei);
		httppost.setHeader("Content-Type", "application/json");

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void sendAttendanceInfo() throws Exception {
		System.out.println("******************test=sendAttendanceInfo");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/sendAttendanceInfo");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("noticeContent", "测试考勤");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	public void sendWorkInfo() throws Exception {
		System.out.println("******************test=sendWorkInfo");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/sendWorkInfo");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("imeiNo", mImei);
		json.put("noticeContent", "测试作业");
		httppost.setEntity(new StringEntity(json.toString()));

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}
	
	public void listen() throws Exception {
		System.out.println("******************test=listen");
		HttpPost httppost = new HttpPost("http://" + host + "/webservice/listen/" + mImei);
		httppost.setHeader("Content-Type", "application/json");

		HttpClientContext context = HttpClientContext.create();

		CloseableHttpResponse response = httpclient.execute(httppost, context);

		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 打印返回数据头
	private void printHeaders(CloseableHttpResponse response) {
		Header[] headers = response.getAllHeaders();
		for (Header tmp : headers) {
			System.out.println(tmp.toString());
		}
	}

}
