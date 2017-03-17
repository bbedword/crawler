<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title><sitemesh:write property='title' /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="expires" content="Sunday 26 October 2099 01:00 GMT" />
<jsp:include page="/frame/css.jsp"></jsp:include>
<jsp:include page="/frame/js.jsp"></jsp:include>
<sitemesh:write property='head' />
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="/frame/head.jsp"></jsp:include>
		<div class="container-fluid col-xs-offset-1">
			<div class="row">
				<jsp:include page="/frame/left.jsp"></jsp:include>
				<div class="col-xs-9">
					<sitemesh:write property='body' />
				</div>
			</div>
		</div>
	</div>
</body>
</html>

