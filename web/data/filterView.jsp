<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>桃谷</title>
<script type="text/javascript">
	var size = "${size}";
	$(document)
			.ready(
					function() {
						$("#addFilter")
								.click(
										function() {
											var div = $("<div class='col-xs-9 col-xs-offset-2 input-group'></div>");
											var span = $("<span class='input-group-addon'> <select name='filter["+size+"].ft'><c:forEach items='${FilterTypes}' var='v'><option>${v}</option></c:forEach></select></span>");
											div.append(span);
											var input = $("<input type='text' class='form-control ' id='filter' required='required' name='filter["+size+"].filter' value='' placeholder='请输入属性css选择字符串'>");
											div.append(input);
											var delSpan = $("<span class='input-group-addon'></span>");
											var del = $("<input type='button'  class='btn btn-xs btn-danger delFilter' value='删除'>");
											delSpan.append(del);
											div.append(delSpan);
											$("#filter_div").append(div);
											size++;
										});
						//删除属性节点
						$("#filter_div").on("click", ".delFilter", function() {
							if (confirm("确定要删除此属性？")) {
								$(this).parent().parent().remove();
								size--;
							}
						});
					});

	function confirmFilter() {
		if (size > 0) {
			return true;
		} else {
			alert("没有过滤规则，就直接返回吧！");
			return false;
		}
	}
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">编辑过滤模板</div>
		<div class="panel-body">
			<form class="form-horizontal" action="/data/addFilter" method="post"
				onsubmit="return confirmFilter();">
				<input type="text" hidden="hidden" value="${name}" name="name">
				<div class="form-group" id="filter_div">
					<label class="col-xs-2 control-label" for="filter">filter
						<button type="button" class="btn btn-xs btn-danger" id="addFilter">+</button>:
					</label>
					<c:forEach items="${filters}" var="f" varStatus="m">
						<div class="col-xs-9 input-group ${m.index>0?'col-xs-offset-2':''}">
							<span class="input-group-addon "> <select
								name="filter[${m.index}].ft"><c:forEach
										items="${FilterTypes}" var="v">
										<option ${f.ft==v?'selected':''}>${v}</option>
									</c:forEach></select>
							</span> <input type="text" class="form-control" id="filter"
								value="${f.filter}" name="filter[${m.index}].filter"
								required="required" placeholder="请输入要过滤网址的规则"> <span
								class='input-group-addon'><input type='button'
								class='btn btn-xs btn-danger delFilter' value='删除'></span>
						</div>
					</c:forEach>
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