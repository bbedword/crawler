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
	function delUrlReg(a) {
		if (confirm("确定要删除吗？")) {
			var us = a.parentNode.parentNode.childNodes;
			var u = encodeURIComponent(us[3].innerHTML);
			location.href = "/data/remove?name=${cg.name}&urlReg=" + u;
		}
	}

	function addUrl() {
		var file = confirm("确定添加自定义url？");
		if (file) {
			location.href = "/url/add?name=${cg.name}";
		}
	}

	function addThread() {
		var isRunning = '${fn:length(cg.crawlers)==0}';
		if (isRunning == 'true') {
			var num = prompt("请输入你要增加的线程数：");
			if (num) {
				if (!isNaN(num)) {
					location.href = "/crawler/addThread?name=${cg.name}&num="
							+ num;
				} else {
					alert("请输入数字类型！");
				}
			}
		} else {
			alert("请先启动爬虫！");
		}
	}
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<b>数据模板</b>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>urlReg</th>
						<th>数据库</th>
						<th>表名</th>
						<th>html表名</th>
						<th><a type="button" class="btn btn-xs btn-success"
							href="/data/showAdd?name=${cg.name}">添加</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cg.parser.dataRegs}" var="data" varStatus="d">
						<tr>
							<td>${d.index+1}</td>
							<td title="${data.value.urlReg}">${data.value.urlReg}</td>
							<td title="${data.value.db}">${data.value.db}</td>
							<td title="${data.value.dataTable}">${data.value.dataTable}</td>
							<td title="${data.value.htmlTable}">${data.value.htmlTable}</td>
							<td><a type="button" class="btn btn-xs btn-info"
								href="/data/view?name=${cg.name}&urlReg=${data.key}">详情</a>
							<td><a type="button" class="btn btn-xs btn-danger" href="#"
								onclick="delUrlReg(this)">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<b>url过滤模板</b>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>类型</th>
						<th>filter</th>
						<th><a type="button" class="btn btn-xs btn-success"
							href="/data/viewFilter?name=${cg.name}">编辑</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cg.parser.filters}" var="filter" varStatus="f">
						<tr>
							<td>${f.index+1}</td>
							<td title="${filter.value.ft}">${filter.value.ft}</td>
							<td title="${filter.value.filter}">${filter.value.filter}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<a type="button" href="/url/listRaw?name=${cg.name}" target="_blank"><b>url队列</b></a>
			<a type="button" class="btn btn-xs btn-success pull-right" href="#"
				onclick="addUrl();">添加</a>
		</div>
		<div class="panel-body">
			<div>url总共:${urlAllNum}，未完成：${urlNotFinish}</div>
			<div class="progress progress-striped active">
				<div class="progress-bar" role="progressbar" aria-valuenow="60"
					aria-valuemin="0" aria-valuemax="100"
					style="width: <fmt:formatNumber type="percent" value=" ${(urlAllNum-urlNotFinish)/urlAllNum}" ></fmt:formatNumber>;">
					<span><fmt:formatNumber type="percent"
							value=" ${(urlAllNum-urlNotFinish)/urlAllNum}"></fmt:formatNumber></span>
				</div>
			</div>
			<div>
				存活线程：
				<ol>
					<c:forEach items="${cg.crawlers}" var="c">
						<li>${c}</li>
					</c:forEach>
				</ol>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<b>爬虫组参数</b>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>名字</th>
						<th>种子url</th>
						<th>线程数</th>
						<th>编码</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td title="${cg.name}">${cg.name}</td>
						<td title="${cg.seedUrl}"><a href="${cg.seedUrl}"
							target="_blank">${cg.seedUrl}</a></td>
						<td title="${cg.threadNum}">${cg.threadNum} <input
							type="button" class="btn btn-xs btn-success" value="+"
							onclick="addThread();" /></td>
						<td title="${cg.charset}">${cg.charset}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>