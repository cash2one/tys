package com.tys.spi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.tys.util.MDateUtil;

public class SpiTest {

	private String host = "localhost:8080/tys-web/spi/app/";
//	 private String host = "112.126.65.114:7091/spi/app/";

	// private CloseableHttpClient httpclient = HttpClients.createDefault();
	private BasicCookieStore cookieStore = new BasicCookieStore();
	private CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

	private String imei = "669897418589525";
	private String phone = "15889762406";

	public static void main(String[] arg) throws Exception {
		final SpiTest test = new SpiTest();
		// test.smsCode(0);
		// test.userRegist("340521");
		// test.smsCode(1);
		// test.resetPw(code);
		// test.smsCode(2);//变更手机

		 test.autoRun();

//		test.newVersion(null, "3");
		
		
//		test.login();
//		test.queryAd();
		
//		String versionStr = "1.0.1";
//		String[] tmp = versionStr.split("\\.");
//		 System.out.println(tmp[0]);
		
	}

	private void autoRun() throws Exception {
		// smsCode(0);
		// userRegist("605312");

		newVersion("tys_1.0", "3");
		// resetPw(String);
		queryAd();

		login();
		checkIn();
		updatePushId();
		setting();
		changePw();
		queryPoints();
		pointsList();

		querySchool("440300", null, null, null, null);// 通过城市代码
		querySchool(null, "22.59672333", "113.84486767", null, null);// 公司附近
		querySchool(null, null, null, "实验", null);// 名称模糊匹配
		querySchool(null, null, null, null, "1");// 班级id

		queryClass("1", null);// 学校id
		queryClass(null, "126681005");// 用户id

		// addChildren();//暂时不用
		childList();
		settingChildren();
		feedback();
		queryCourse();

		bind();
		online();
		queryServer();

		jxzxList(null, null, "0");// 首次加载
		jxzxList(null, MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", "0");// 加载历史
		jxzxList(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", null, "0");// 加载最新
		jxzxList(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-03-01 00:00:00").getTime() + "",
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", "0");// 加载剩余
		jxzxInfo();
		jxzxLike();
		jxzxComment();

		chatClassList();
		chatClassMember();
		userInfo();
		classNotice();

		questionList(null, null, null, null, null);// 首次加载
		questionList(null, MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "",
				null, null, null);// 加载历史
		questionList(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", null,
				null, null, null);// 加载最新
		questionList(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-03-01 00:00:00").getTime() + "",
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", null, null,
				null);// 加载剩余问题

		questionList(null, null, "440300", null, null);// 首次加载，深圳
		questionList(null, MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "",
				"440300", null, null);// 加载历史
		questionList(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", null,
				"440300", null, null);// 加载最新
		questionList(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-03-01 00:00:00").getTime() + "",
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "", "440300",
				null, null);// 加载剩余问题

		questionList(null, null, "440300",
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-03-01 00:00:00").getTime() + "", null);// 首次加载，深圳，最后更新

		questionList(null, null, null, null, "126681005");// 用户问题列表

		commitQuestion(null);// 添加
		commitQuestion("1");// 修改

		deleteQuestion();

		questionInfo();
		answerList();
		acceptAnswer();

		locQuery();
		deviceLocation("0");
		deviceLocation("1");
		deviceMonitor();

		homework();
		attendance();
		attendanceHistory("7",
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "");

		settingHomework();
		mySettingHomework(null, null);// 首次加载
		mySettingHomework(null,
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "");// 加载历史
		mySettingHomework(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "",
				null);
		mySettingHomework(MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-03-01 00:00:00").getTime() + "",
				MDateUtil.parseDateStr(MDateUtil.DATETIME_PATTERN, "2016-04-01 00:00:00").getTime() + "");// 加载最新

		classAttendance("" + System.currentTimeMillis());// 当天

		userLogout();

	}

	// 获取验证码，0：注册验证码，1：重置密码验证码，2：变更手机验证码
	private void smsCode(int type) throws Exception {
		System.out.println("test=smsCode");
		HttpPost httppost = new HttpPost("http://" + host + "n/smsCode");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("phone", phone));
		nvps.add(new BasicNameValuePair("type", "" + type));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

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

	// 用户注册
	private void userRegist(String code) throws Exception {
		System.out.println("test=userRegist");
		HttpPost httppost = new HttpPost("http://" + host + "n/userRegist");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("acct", phone));
		nvps.add(new BasicNameValuePair("pw", "123456"));
		nvps.add(new BasicNameValuePair("code", code));
		nvps.add(new BasicNameValuePair("sex", "0"));
		nvps.add(new BasicNameValuePair("cityCode", "110100"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 登录
	private void login() throws Exception {
		System.out.println("test=login");
		HttpPost httppost = new HttpPost("http://" + host + "s/userLogin");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("acct", phone));
		nvps.add(new BasicNameValuePair("pw", "123456"));
		nvps.add(new BasicNameValuePair("imei", "865072025427421"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			// List<Cookie> list = cookieStore.getCookies();
			// BasicClientCookie coo = new BasicClientCookie("JSESSIONID",
			// list.get(0).getValue());
			// coo.setDomain("112.74.81.68");
			// coo.setPath("/bslnew/");
			// cookieStore.addCookie(coo);
			// list = cookieStore.getCookies();

			// System.out.println(list.size());
			// if (list.isEmpty()) {
			// System.out.println("不存在cookie");
			// } else {
			// for (int i = 0; i < list.size(); i++) {
			// System.out.println("---" + list.get(i).toString());
			// }
			// }

			// Header[] header = response.getAllHeaders();
			// for (Header tmp : header) {
			// System.out.println(tmp.getName() + " : " + tmp.getValue());
			// }

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}

	}

	// 退出登录
	private void userLogout() throws Exception {
		System.out.println("test=userLogout");
		HttpPost httppost = new HttpPost("http://" + host + "s/userLogout");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 新版本监测
	private void newVersion(String companyId, String type) throws Exception {
		System.out.println("test=newVersion");
		HttpPost httppost = new HttpPost("http://" + host + "n/newVersion");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("version", "0.0.1"));
		if(type != null)
			nvps.add(new BasicNameValuePair("type", type));
		// 可选参数
		if (companyId != null)
			nvps.add(new BasicNameValuePair("companyId", companyId));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.3 重置密码
	private void resetPw(String code) throws Exception {
		System.out.println("test=resetPw");
		HttpPost httppost = new HttpPost("http://" + host + "n/resetPw");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("phone", phone));
		nvps.add(new BasicNameValuePair("code", code));
		nvps.add(new BasicNameValuePair("newPw", "123456"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.3 重置密码
	private void online() throws Exception {
		System.out.println("test=online");
		HttpPost httppost = new HttpPost("http://" + host + "s/online");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("imei", imei));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.9 查询挂载服务器
	private void queryServer() throws Exception {
		System.out.println("test=queryServer");
		HttpPost httppost = new HttpPost("http://" + host + "s/queryServer");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("imei", imei));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.10 查询地区广告
	private void queryAd() throws Exception {
		System.out.println("test=queryAd");
		HttpPost httppost = new HttpPost("http://" + host + "n/queryAd");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cityCode", "110100"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.10 查询地区广告
	private void updatePushId() throws Exception {
		System.out.println("test=updatePushId");
		HttpPost httppost = new HttpPost("http://" + host + "s/updatePushId");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userId", "1234"));
		nvps.add(new BasicNameValuePair("channelId", "5678"));
		nvps.add(new BasicNameValuePair("deviceType", "3"));
		nvps.add(new BasicNameValuePair("companyId", "tys_1.0"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.12 查询学校
	private void querySchool(String cityCode, String latitude, String longitude, String name, String classId)
			throws Exception {
		System.out.println("test=querySchool");
		HttpPost httppost = new HttpPost("http://" + host + "s/querySchool");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (cityCode != null)
			nvps.add(new BasicNameValuePair("cityCode", cityCode));

		if (latitude != null && longitude != null) {
			nvps.add(new BasicNameValuePair("latitude", ""));
			nvps.add(new BasicNameValuePair("longitude", ""));
		}

		if (name != null)
			nvps.add(new BasicNameValuePair("name", "实验小学"));

		if (classId != null)
			nvps.add(new BasicNameValuePair("classId", classId));

		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.13 查询班级
	private void queryClass(String schoolId, String teacherId) throws Exception {
		System.out.println("test=queryClass");
		HttpPost httppost = new HttpPost("http://" + host + "s/queryClass");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (schoolId != null)
			nvps.add(new BasicNameValuePair("schoolId", schoolId));
		if (teacherId != null)
			nvps.add(new BasicNameValuePair("teacherId", teacherId));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.1 设置个人信息
	private void setting() throws Exception {
		System.out.println("test=setting");
		HttpPost httppost = new HttpPost("http://" + host + "s/setting");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", "ABC"));
		nvps.add(new BasicNameValuePair("sex", "1"));
		nvps.add(new BasicNameValuePair("birthday", "2010-01-01"));
		nvps.add(new BasicNameValuePair("custAcct", "abc123456"));
		nvps.add(new BasicNameValuePair("cityCode", "110100"));
		// nvps.add(new BasicNameValuePair("imgId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.2 密码修改
	private void changePw() throws Exception {
		System.out.println("test=changePw");
		HttpPost httppost = new HttpPost("http://" + host + "s/changePw");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("oldPw", "123456"));
		nvps.add(new BasicNameValuePair("newPw", "123456"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.3 孩子列表
	private void childList() throws Exception {
		System.out.println("test=childList");
		HttpPost httppost = new HttpPost("http://" + host + "s/childList");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.4 添加孩子
	private void addChildren() throws Exception {
		System.out.println("test=addChildren");
		HttpPost httppost = new HttpPost("http://" + host + "s/addChildren");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", "大大"));
		nvps.add(new BasicNameValuePair("sex", "1"));
		nvps.add(new BasicNameValuePair("classId", "1"));
		nvps.add(new BasicNameValuePair("birthday", "" + System.currentTimeMillis()));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.5 扫码绑定
	private void bind() throws Exception {
		System.out.println("test=bind");
		HttpPost httppost = new HttpPost("http://" + host + "s/bind");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("imei", imei));
		nvps.add(new BasicNameValuePair("childId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.6 设置孩子信息
	private void settingChildren() throws Exception {
		System.out.println("test=settingChildren");
		HttpPost httppost = new HttpPost("http://" + host + "s/settingChildren");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("childId", "1"));
		nvps.add(new BasicNameValuePair("phone0", phone));
		nvps.add(new BasicNameValuePair("phone1", "13800138001"));
		nvps.add(new BasicNameValuePair("phone2", "13800138002"));
		nvps.add(new BasicNameValuePair("phone3", "13800138003"));
		nvps.add(new BasicNameValuePair("phone4", "13800138004"));
		nvps.add(new BasicNameValuePair("phone5", "13800138005"));
		nvps.add(new BasicNameValuePair("phone6", "13800138006"));
		nvps.add(new BasicNameValuePair("phone7", "13800138007"));
		nvps.add(new BasicNameValuePair("phone8", "13800138008"));
		nvps.add(new BasicNameValuePair("phone9", "13800138009"));
		nvps.add(new BasicNameValuePair("name", "小小" + (System.currentTimeMillis() % 1000)));
		nvps.add(new BasicNameValuePair("sex", "0"));
		nvps.add(new BasicNameValuePair("birthday", "1960-01-01"));
		nvps.add(new BasicNameValuePair("schoolId", "1"));
		nvps.add(new BasicNameValuePair("classId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.7 意见反馈
	private void feedback() throws Exception {
		System.out.println("test=feedback");
		HttpPost httppost = new HttpPost("http://" + host + "s/feedback");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("content", "反馈123"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.8 查询积分
	private void queryPoints() throws Exception {
		System.out.println("test=queryPoints");
		HttpPost httppost = new HttpPost("http://" + host + "s/queryPoints");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.2.9 积分明细
	private void pointsList() throws Exception {
		System.out.println("test=pointsList");
		HttpPost httppost = new HttpPost("http://" + host + "s/pointsList");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.3.1 资讯列表
	private void jxzxList(String timeStart, String timeEnd, String type) throws Exception {
		System.out.println("test=jxzxList");
		HttpPost httppost = new HttpPost("http://" + host + "s/jxzxList");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("timeEnd", timeEnd));
		nvps.add(new BasicNameValuePair("timeStart", timeStart));
		nvps.add(new BasicNameValuePair("type", type));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.3.1 资讯列表
	private void jxzxInfo() throws Exception {
		System.out.println("test=jxzxInfo");
		HttpPost httppost = new HttpPost("http://" + host + "s/jxzxInfo");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("newsId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.3.3 点赞
	private void jxzxLike() throws Exception {
		System.out.println("test=jxzxLike");
		HttpPost httppost = new HttpPost("http://" + host + "s/jxzxLike");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("newsId", "1"));
		nvps.add(new BasicNameValuePair("like", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.3.4 评论
	private void jxzxComment() throws Exception {
		System.out.println("test=jxzxComment");
		HttpPost httppost = new HttpPost("http://" + host + "s/jxzxComment");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("newsId", "1"));
		nvps.add(new BasicNameValuePair("comment", "评论123"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.4.1 班级列表
	private void chatClassList() throws Exception {
		System.out.println("test=chatClassList");
		HttpPost httppost = new HttpPost("http://" + host + "s/chatClassList");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.4.2 群聊成员列表
	private void chatClassMember() throws Exception {
		System.out.println("test=chatClassMember");
		HttpPost httppost = new HttpPost("http://" + host + "s/chatClassMember");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("chatClassId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.4.3 家长老师详情
	private void userInfo() throws Exception {
		System.out.println("test=userInfo");
		HttpPost httppost = new HttpPost("http://" + host + "s/userInfo");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userId", "126681002"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.4.5 修改群公告
	private void classNotice() throws Exception {
		System.out.println("test=classNotice");
		HttpPost httppost = new HttpPost("http://" + host + "s/classNotice");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("chatClassId", "1"));
		nvps.add(new BasicNameValuePair("content", "公告公告"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.5.1 问题列表
	private void questionList(String timeStart, String timeEnd, String cityCode, String lastUpdate, String userId)
			throws Exception {
		System.out.println("test=questionList");
		HttpPost httppost = new HttpPost("http://" + host + "s/questionList");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		if (timeStart != null)
			nvps.add(new BasicNameValuePair("timeStart", timeStart));
		if (timeEnd != null)
			nvps.add(new BasicNameValuePair("timeEnd", timeEnd));
		if (cityCode != null)
			nvps.add(new BasicNameValuePair("cityCode", cityCode));
		if (cityCode != null)
			nvps.add(new BasicNameValuePair("cityCode", cityCode));
		if (lastUpdate != null)
			nvps.add(new BasicNameValuePair("lastUpdate",
					"" + MDateUtil.parseDateStr(MDateUtil.ISO_EXPANDED_DATE_FORMAT, "2016-03-20").getTime()));
		if (userId != null)
			nvps.add(new BasicNameValuePair("userId",
					"" + MDateUtil.parseDateStr(MDateUtil.ISO_EXPANDED_DATE_FORMAT, "2016-03-20").getTime()));

		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.5.3 提问/更新问题
	private void commitQuestion(String questionId) throws Exception {
		System.out.println("test=commitQuestion");
		HttpPost httppost = new HttpPost("http://" + host + "s/commitQuestion");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (questionId != null)
			nvps.add(new BasicNameValuePair("questionId", "1"));
		nvps.add(new BasicNameValuePair("title", "三角函数问题"));
		nvps.add(new BasicNameValuePair("content", "这个问题怎解？"));
		nvps.add(new BasicNameValuePair("pics", "1,2"));
		nvps.add(new BasicNameValuePair("points", "50"));
		nvps.add(new BasicNameValuePair("gradeId", "1"));
		nvps.add(new BasicNameValuePair("courseId", "1"));
		nvps.add(new BasicNameValuePair("author", "匿名"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.5.4 删除问题
	private void deleteQuestion() throws Exception {
		System.out.println("test=deleteQuestion");
		HttpPost httppost = new HttpPost("http://" + host + "s/deleteQuestion");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("questionId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.5.5 问题详情页
	private void questionInfo() throws Exception {
		System.out.println("test=questionInfo");
		HttpPost httppost = new HttpPost("http://" + host + "s/questionInfo");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("questionId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.5.6 获取解答列表
	private void answerList() throws Exception {
		System.out.println("test=answerList");
		HttpPost httppost = new HttpPost("http://" + host + "s/answerList");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("questionId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.5.7 采纳解答
	private void acceptAnswer() throws Exception {
		System.out.println("test=acceptAnswer");
		HttpPost httppost = new HttpPost("http://" + host + "s/acceptAnswer");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("questionId", "1"));
		nvps.add(new BasicNameValuePair("answerId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.6.1 最新位置查询
	private void locQuery() throws Exception {
		System.out.println("test=locQuery");
		HttpPost httppost = new HttpPost("http://" + host + "s/locQuery");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("imei", imei));
		nvps.add(new BasicNameValuePair("mapType", "0"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.6.2 请求设备上传位置
	private void deviceLocation(String mapType) throws Exception {
		System.out.println("test=deviceLocation");
		HttpPost httppost = new HttpPost("http://" + host + "s/deviceLocation");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("imei", imei));
		if (mapType != null)
			nvps.add(new BasicNameValuePair("mapType", mapType));

		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.6.2 请求设备上传位置
	private void deviceMonitor() throws Exception {
		System.out.println("test=deviceMonitor");
		HttpPost httppost = new HttpPost("http://" + host + "s/deviceMonitor");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("imei", imei));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.7.1 查询作业
	private void homework() throws Exception {
		System.out.println("test=homework");
		HttpPost httppost = new HttpPost("http://" + host + "s/homework");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("classId", "1"));
		// nvps.add(new BasicNameValuePair("lastTime", ""+
		// System.currentTimeMillis()));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.7.2 发布作业
	private void settingHomework() throws Exception {
		System.out.println("test=settingHomework");
		HttpPost httppost = new HttpPost("http://" + host + "s/settingHomework");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("classId", "1"));
		nvps.add(new BasicNameValuePair("courseId", "1"));
		nvps.add(new BasicNameValuePair("commitTime", "" + System.currentTimeMillis()));
		nvps.add(new BasicNameValuePair("content", "这是作业内容"));
		// nvps.add(new BasicNameValuePair("picIds", ""));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.7.3 已发布作业列表
	private void mySettingHomework(String timeStart, String timeEnd) throws Exception {
		System.out.println("test=mySettingHomework");
		HttpPost httppost = new HttpPost("http://" + host + "s/mySettingHomework");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (timeStart != null)
			nvps.add(new BasicNameValuePair("timeStart", timeStart));
		if (timeEnd != null)
			nvps.add(new BasicNameValuePair("timeEnd", timeEnd));

		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.8.1 今天考勤
	private void attendance() throws Exception {
		System.out.println("test=attendance");
		HttpPost httppost = new HttpPost("http://" + host + "s/attendance");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("childId", "1"));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.8.2 历史考勤记录
	private void attendanceHistory(String days, String date) throws Exception {
		System.out.println("test=attendanceHistory");
		HttpPost httppost = new HttpPost("http://" + host + "s/attendanceHistory");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("childId", "1"));

		if (days != null)
			nvps.add(new BasicNameValuePair("days", days));
		if (date != null)
			nvps.add(new BasicNameValuePair("date", date));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 班级考勤统计数据
	private void classAttendance(String date) throws Exception {
		System.out.println("test=classAttendance");
		HttpPost httppost = new HttpPost("http://" + host + "s/classAttendance");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (date != null)
			nvps.add(new BasicNameValuePair("date", date));
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.14 查询课程科目
	private void queryCourse() throws Exception {
		System.out.println("test=queryCourse");
		HttpPost httppost = new HttpPost("http://" + host + "s/queryCourse");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
		} finally {
			response.close();
		}
	}

	// 2.1.16 签到
	private void checkIn() throws Exception {
		System.out.println("test=checkIn");
		HttpPost httppost = new HttpPost("http://" + host + "s/checkIn");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		HttpClientContext context = HttpClientContext.create();
		CloseableHttpResponse response = httpclient.execute(httppost, context);
		try {
			printHeaders(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);

			System.out.println("Http status=" + response.getStatusLine().getStatusCode() + " " + ret + "\n");
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
