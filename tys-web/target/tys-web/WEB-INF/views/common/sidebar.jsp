<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>平安家校通</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
<div class="sidebar" id="sidebar">
	<ul class="nav nav-list">
		<li <c:if test="${sidebar=='teacher'||sidebar=='student'}">class="active"</c:if> >
			<a href="#" class="dropdown-toggle">
				<i class="icon-list-alt"></i>
				<span class="menu-text"> 学校管理 </span>

				<b class="arrow icon-angle-down"></b>
			</a>
			<ul class="submenu">
				<li <c:if test="${sidebar=='teacher'}">class="active"</c:if> >
					<a href="<%=request.getContextPath()%>/getteachers.html">
						<i class="icon-double-angle-right"></i>
						教师管理
					</a>
				</li>
				<li <c:if test="${sidebar=='student'}">class="active"</c:if> >
					<a href="<%=request.getContextPath()%>/getstudents.html">
						<i class="icon-double-angle-right"></i>
						学生管理
					</a>
				</li>
			</ul>
		</li>
		
	</ul><!--/.nav-list-->
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left"></i>
	</div>
</div>
  </body>
</html>
