package com.tys.netty.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.tys.util.MStrUtil;

public class HttpRpcService {

	private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();

	private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
			.setConnectionManager(CONNECTION_MANAGER).build();

	private HttpRpcService() {
	}

	public static HttpRpcService getInstance() {
		return new HttpRpcService();
	}

	public String httpGet(String url) {
		return this.httpGet(url, null);
	}

	public String httpGet(String url, Header[] headers) {
		if (MStrUtil.isNull(url)) {
			return null;
		}
		HttpGet httpGet = new HttpGet(url);
		if (null != headers) {
			httpGet.setHeaders(headers);
		}
		return this.execute(httpGet);
	}

	public String httpPost(String url) {
		return this.httpPost(url, null);
	}

	public String httpPost(String url, HttpEntity entity) {
		String result = null;
		if (MStrUtil.isNull(url)) {
			return result;
		}

		HttpPost httpPost = new HttpPost(url);
		if (null != entity) {
			httpPost.setEntity(entity);
		}
		result = this.execute(httpPost);
		if (null != entity) {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private String execute(HttpRequestBase request) {
		String result = null;
		CloseableHttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			response = HTTP_CLIENT.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= HttpServletResponse.SC_OK
					&& statusCode < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				responseEntity = response.getEntity();
				result = responseEntity != null ? EntityUtils
						.toString(responseEntity) : null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (request != null) {
					request.releaseConnection();
				}
				if (responseEntity != null) {
					EntityUtils.consume(responseEntity);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
