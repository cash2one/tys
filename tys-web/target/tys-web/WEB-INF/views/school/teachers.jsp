<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>教师信息</title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/WEB-INF/views/common/css.jsp" %>
	</head>

	<body>
		<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
		<div class="main-container container-fluid">
			<a class="menu-toggler" id="menu-toggler" href="#">
				<span class="menu-text"></span>
			</a>
			<%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
			<div class="main-content">
				 
				<div class="page-content">
					<div class="page-header position-relative">
						<h1>
							<small>
								<i class="icon-user"></i>
								教师信息
							</small>
						</h1>
					</div> 
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th width="15%">教师编号</th>
										<th width="15%">教师姓名</th>
										<th width="15%">年龄</th>
										<th>电话</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${teacherList}"  var="obj"  >
									<tr>
										<td><a href="#">${obj.id}</a></td>
										<td>${obj.name}</td>
										<td>${obj.age}</td>
										<td>${obj.phone}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							
					 		<div class="dataTables_paginate paging_bootstrap pagination">
							  <button class="btn btn-success btn-mini" onclick="location.href='<%=request.getContextPath() %>/manager/students?pagenum=${pagenum-1}'" <c:if test="${pagenum <= 1}">disabled="disabled"</c:if>    >&laquo;</button>
							  <button class="btn btn-success btn-mini" disabled="disabled">第 ${pagenum} 页</button>
							  <button class="btn btn-success btn-mini" onclick="location.href='<%=request.getContextPath() %>/manager/students?pagenum=${pagenum+1}'" <c:if test="${length < 8}">disabled="disabled"</c:if> >&raquo;</button>
					 		</div>
							 
							<!--PAGE CONTENT ENDS-->
					</div><!--/.span-->
				</div><!--/.row-fluid-->
			</div><!--/.page-content-->
		</div><!--/.main-content-->
	</div><!--/.main-container-->

		<%@include file="/WEB-INF/views/common/js.jsp" %>
		
	</body>
</html>