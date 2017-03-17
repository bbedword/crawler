<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>修改密码</title>
<script type="text/javascript">
	function confirmParams() {
		var p = $("#password").val();
		var rep = $("#rePassword").val();
		if (p === rep) {
		} else {
			$.globalMessenger().post({
			    message: "密码不一致！",
			    type: 'info',
			    showCloseButton: true});
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div class="panel panel-primary col-md-6 col-md-offset-2">
		<div class="panel-heading">
			<h3 class="panel-title">修改密码</h3>
		</div>
		<div class="panel-body">
			<div class="well well-sm">
				<form class="form-horizontal" role="form" method="post"
					action="/user/changePassword" onsubmit="return confirmParams();">
					<div class="form-group">
						<label for="firstname" class="col-sm-3 control-label">旧密码</label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="oldPassword"
								name="oldPassword" required="required" placeholder="请输入旧密码">
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-3 control-label">新密码</label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="password"
								required="required" name="password" placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-3 control-label">确认新密码</label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="rePassword"
								required="required" name="rePassword" placeholder="请再次输入密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-9">
							<button type="submit" class="btn btn-primary">提交</button>
							<input type="reset" class="btn btn-default" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
