package com.tys.util;

import java.io.IOException;

import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
@SuppressWarnings("rawtypes")
public class MFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		//请求行信息
		String method = request.getMethod();
//		String requestURI = request.getRequestURI();
//		String queryString = request.getQueryString();
//		String protocol = request.getProtocol();
		//网络连接信息
//		String remoteAddr = request.getRemoteAddr();
		String requestURL = request.getRequestURL().toString();
//		sb.append(remoteAddr).append("\t\t");
		sb.append(method).append("\t").append(requestURL).append("\n\t\t\tparameters : ");
		
		Enumeration paraNames = request.getParameterNames();
		while(paraNames.hasMoreElements()){
			String paraName = (String) paraNames.nextElement();
			String para = request.getParameter(paraName);
			sb.append(paraName).append("=").append(para).append("&");
		}
		sb.append("\n");
//		if(queryString != null)
//			sb.append("?").append(queryString).append("\n");

		//请求头
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			//MUtil.log(headerName + " : " + request.getHeader(headerName) + "<br>");
			Enumeration values = request.getHeaders(headerName);
			while (values.hasMoreElements()) {
				sb.append("\t\t\t").append(headerName).append(" : ").append((String) values.nextElement()).append("\n");
			}
		}
		MUtil.log(sb.toString());
		filterChain.doFilter(request, response);
	}

}
