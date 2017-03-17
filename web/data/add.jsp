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
	$(document).ready(function() {
			$("#addParam").click(
						function() {
							var paramKey = prompt("请输入你要添加的属性字段：");
							if (paramKey) {
								var div = $("<div class='col-xs-9 col-xs-offset-2 input-group'></div>");
								var span = $("<span class='input-group-addon'></span>");
								var label = $("<label></label>");
								label.html(paramKey);
								span.append(label);
								div.append(span);
								var span2= $("<span class='input-group-addon'> <select name='data.params("+paramKey+").vt'><c:forEach items='${valueTypes}' var='v'><option>${v}</option></c:forEach></select></span>");
								div.append(span2);
								var input = $("<input type='text' class='form-control ' id='param' required='required' name='data.params("
										+ paramKey
										+ ").reg' value='${data.urlReg}' placeholder='请输入属性css选择字符串'>");
								div.append(input);
								var delSpan = $("<span class='input-group-addon'>");
								var del = $("<input type='button'  class='btn btn-xs btn-danger delParam' value='删除'>");
								delSpan.append(del);
								div.append(delSpan);
								$("#param_div").append(div);
							}
						});
						//删除属性节点
						$("#param_div").on("click", ".delParam", function() {
							if(confirm("确定要删除此属性？")){
								$(this).parent().parent().remove();
							}
						});
						//是否保存html
						$("#isStoreHtml").click(function() {
							var v = $(this).is(":checked");
							if (v) {
								$("#htmlTableDiv").removeClass("hidden");
							} else {
								$("#htmlTableDiv").addClass("hidden");
							}
						});
					});
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">添加数据</div>
		<div class="panel-body">
			<form class="form-horizontal" action="/data/add" method="post"
				onsubmit="return confirmParams();">
				<input type="text" hidden="hidden" name="name" value="${name}" />
				<div class="form-group">
					<label class="col-xs-2 control-label" for="urlReg">urlReg:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="urlReg"
							required="required" name="data.urlReg" 
							placeholder="请输入要匹配的url正则表达式">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="isOk">页面验证字段:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="isOk"
							name="data.isOk"
							placeholder="请输入页面必须满足的条件字段,如果不填,则不进行验证">
					</div>
				</div>
				<div class="form-group" id="param_div">
					<label class="col-xs-2 control-label" for="param">属性 <input
						type="button" class="btn btn-xs btn-danger" value="+"
						id="addParam">:
					</label>
					<div class="col-xs-9 input-group">
						<span class="input-group-addon "> <label>test</label>
						</span><span class="input-group-addon "> <select name="data.params(test).vt"><c:forEach items="${valueTypes}" var="v"><option ${p.value.vt==v?'selected':''}>${v}</option></c:forEach></select>
							</span> <input type="text" class="form-control" id="param"
							required="required" name="data.params(test).reg"
							 placeholder="请输入属性css选择字符串"> <span
							class="input-group-addon"> <input type="button"
							class="btn btn-xs btn-danger delParam" value="删除">
						</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="real_name">DB:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="db" 
							name="data.db" required="required" placeholder="请输入存储的数据库名">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="telephone">表名:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="dataTable"
							required="required"
							name="data.dataTable" placeholder="请输入数据保存表名">
					</div>
				</div>
					<div class="form-group">
					<label class="col-xs-2 control-label" for="isStoreHtml">是否保存html:</label>
					<div class="col-xs-9">
						<input type="checkbox" class="checkbox" id="isStoreHtml"
							value="true" 
							name="data.isStoreHtml">
					</div>
				</div>
				<div class="form-group hidden" id="htmlTableDiv">
					<label class="col-xs-2 control-label" for="email">html表名:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="htmlTable"
							required="required"
							name="data.htmlTable" placeholder="请输入html保存的表名">
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