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
			$("#addParam").click(function() {
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
								+ ").reg' value='' placeholder='请输入属性css选择字符串'>");
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
		//测试一下
		$("#testParser").click(function() {
			var url = prompt("请输入你要测试的url：");
			if(url){
				var reg = encodeURIComponent($("#urlReg").val());
				var u1 = encodeURIComponent(url);
				var u = "/data/test?name=${name}&urlReg="+reg+"&url="+u1;
				location.href=u;
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
		<div class="panel-heading">添加数据
		<button type="button" class="btn btn-xs btn-primary pull-right" id="testParser">测试一下</button>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" action="/data/add" method="post">
				<input type="text" hidden="hidden" value="${name}" name="name">
				<div class="form-group">
					<label class="col-xs-2 control-label" for="name">urlReg:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="urlReg"
							required="required" name="data.urlReg" value="${data.urlReg}"
							placeholder="请输入要匹配的url正则表达式">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="isOk">页面验证字段:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="isOk"
							value="${data.isOk}" name="data.isOk"
							placeholder="请输入页面必须满足的条件字段,如果不填,则不进行验证">
					</div>
				</div>
				<div class="form-group" id="param_div">
					<label class="col-xs-2 control-label" for="param">属性 <button type="button" class="btn btn-xs btn-success" id="addParam">+</button>:</label>
					<c:forEach items="${data.params}" var="p">
						<div class="col-xs-9 col-xs-offset-2 input-group">
							<span class="input-group-addon "> <label>${p.key}</label>
							</span> <span class="input-group-addon "> <select name="data.params(${p.key}).vt"><c:forEach items="${valueTypes}" var="v"><option ${p.value.vt==v?'selected':''}>${v}</option></c:forEach></select>
							</span><input type="text" class="form-control" id="param"
								required="required" name="data.params(${p.key}).reg"
								value="${p.value.reg}" placeholder="请输入属性css选择字符串"> <span
								class="input-group-addon"> <input type="button"
								class="btn btn-xs btn-danger delParam" value="删除">
							</span>
						</div>
					</c:forEach>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="db">DB:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="db" value="${data.db}"
							name="data.db" required="required" placeholder="请输入存储的数据库名">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="telephone">表名:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="dataTable"
							required="required" value="${data.dataTable}"
							name="data.dataTable" placeholder="请输入数据保存表名">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label" for="isStoreHtml">是否保存html:</label>
					<div class="col-xs-9">
						<input type="checkbox" class="checkbox" id="isStoreHtml"
							value="true" ${data.isStoreHtml?'checked="checked"':''}
							name="data.isStoreHtml">
					</div>
				</div>
				<div class="form-group ${data.isStoreHtml?'':'hidden'}"
					id="htmlTableDiv">
					<label class="col-xs-2 control-label" for="htmlTable">html表名:</label>
					<div class="col-xs-9">
						<input type="text" class="form-control" id="htmlTable"
							value="${data.htmlTable}" name="data.htmlTable"
							placeholder="请输入html保存的表名">
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