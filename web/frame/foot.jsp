<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${SESSION_USER==null}">
	<nav class="navbar navbar-inverse navbar-fixed-bottom bg-primary" 
		role="navigation">
		<div  id="nav_foot" class="col-md-offset-4 col-md-4 text-center ">
			如果您发现bug或者有好的建议，欢迎Email：liushaobo@91taogu.com<br>
		</div>
	</nav>
</c:if>