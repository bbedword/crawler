<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>桃谷</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-primary col-sm-4 col-sm-offset-4">
			<div class="panel-heading">
				<h3 class="panel-title">登录</h3>
			</div>
			<div class="panel-body">
				<div class="well well-sm">
					<form class="form-horizontal" role="form" method="post"
						action="/user/login">
						<div class="form-group">
							<label for="firstname" class="col-sm-3 control-label">昵称</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="name" name="name"
									required="required" placeholder="请输入昵称">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password"
									required="required" name="password" placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<button type="submit" class="btn btn-primary">登录</button>
								<input type="reset" class="btn btn-default" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>