<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default">
	<div class="navbar-header col-xs-12">
		<div class="col-xs-3">
			<a class="navbar-brand" href="#"> 爬虫系统 </a>
		</div>
		<c:if test="${user!=null}">
			<div class="col-xs-2 navbar-right navbar-brand">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#"
					aria-haspopup="true" aria-expanded="false"><small><span
						class="glyphicon glyphicon-user"> </span> ${user}<span
						class="caret"></span> </small></a>
				<ul class="dropdown-menu">
					<li><a href="/change_password.jsp">修改密码</a></li>
					<li role="separator" class="divider"></li>
					<li><a href="/user/logout">退出</a></li>
				</ul>
			</div>
		</c:if>
	</div>
</nav>