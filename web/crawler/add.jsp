<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>桃谷</title>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">添加任务</div>
		<div class="panel-body">
			<form class="form-horizontal" action="/crawler/add" method="post"
				onsubmit="return confirmParams();">
				<div class="form-group">
					<label class="col-xs-2 control-label" for="name">name:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="name"
							name="config.name" value="${config.name}"
							placeholder="请定义爬虫组任务的名字">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="seed">seed:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="seed" required="required"
							name="config.seedUrl" value="${config.seedUrl}"
							placeholder="请定义爬虫组要爬取的起始url">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="threadNum">爬虫个数:</label>
					<div class="col-xs-9">
						<input type="number" class="form-control" id="threadNum" min="1"
							value="${config.threadNum}" name="config.threadNum"
							placeholder="请输入爬虫组中爬虫个数，默认20个">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="charset">网页编码:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="charset"
							value="${config.charset}" name="config.charset"
							placeholder="请输入要爬取网页的编码方式，默认为gb2312">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-2 col-xs-10">
						<button type="submit" class="btn btn-success">保存</button>
						<button type="button" class="btn btn-default"
							onclick="javascript:history.back();">返回</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>