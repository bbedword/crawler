<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>数据模板</title>
<script type="text/javascript">
	function delCrawler(a) {
		if (confirm("确定要删除吗？"))
			var us = a.parentNode.parentNode.childNodes;
		var u = us[3].innerHTML;
		location.href = "/crawler/remove?name=" + u;
	}
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>name</th>
						<th>运行状态</th>
						<th>创建时间</th>
						<th><a type="button" class="btn btn-xs btn-success"
							href="/crawler/add.jsp">添加</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${crawlers}" var="crawler" varStatus="c">
						<tr>
							<td>${c.index+1}</td>
							<td>${crawler.name}</td>
							<td><button
									class="btn btn-xs ${fn:length(crawler.crawlers)==0?'btn-danger':'btn-success'}">${fn:length(crawler.crawlers)==0?'停止':'运行中'}</button></td>
							<td><fmt:formatDate value="${crawler.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><a type="button" class="btn btn-xs btn-info"
								href="/crawler/view?name=${crawler.name}">详情</a> <a
								type="button"
								class="btn btn-xs ${fn:length(crawler.crawlers)==0?'btn-success':'btn-danger'}"
								href="/crawler/${fn:length(crawler.crawlers)==0?'start':'stop'}?name=${crawler.name}">${fn:length(crawler.crawlers)==0?'运行':'停止'}</a>
								<a type="button" class="btn btn-xs btn-danger" href="#"
								onclick="delCrawler(this)">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>