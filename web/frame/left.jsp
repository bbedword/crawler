<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/other/menu.css" rel="stylesheet">
<c:if test="${user!=null}">
<div class="col-xs-2">
	<ul id="ul_0" class="main-nav nav nav-tabs nav-stacked">
	</ul>
</div>
<script src="//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://libs.baidu.com/json/json2/json2.js"></script>
<!-- 左侧菜单加载，点击刷新光标驻留 -->
<script type="text/javascript">
$(document).ready(function(){
	function loadMenus(result){
		  $.each(result, function(i, field){
		    	var ul = $("#ul_"+field.parent_id);
		    	if(ul.length){
		    		var li = createLi(field);
						ul.append(li);
		    	}else{
		    		ul = $("<ul></ul>");
		    		ul.attr("id","ul_"+field.parent_id);
		    		ul.addClass("nav nav-list secondmenu collapse");
		    		var li = createLi(field);
		    		ul.append(li);
		    		var a = $("#li_"+field.parent_id).find("a").first();	
		    		a.attr("href","#ul_"+field.parent_id);
		    		a.addClass("nav-header collapsed");
		    		a.attr("data-toggle","collapse");
		    		a.append("<span class='pull-right glyphicon glyphicon-chevron-toggle'></span>");
		    		$("#li_"+field.parent_id).removeClass("hasUrl");
		    		$("#li_"+field.parent_id).append(ul);
		    	}
		    });
	  }
	  
	  function createLi(field){
			var a = $("<a></a>");
			var url = (field.url==null||field.url=='null'||field.url=='#')?"#ul_"+field.id:field.url;
			a.attr("href",url);
			a.append(field.icon);
			a.append(" ");
			a.append(field.name);
			var li = $("<li></li>");
			li.attr("id","li_"+field.id);
			li.addClass("hasUrl");
			li.append(a);
			return li;
	  }
	
	var user_menus = sessionStorage.getItem("${user}_menus");
	 if(user_menus){
		var result = JSON.parse(user_menus);
		loadMenus(result);
	}else{ 
		var url = "/user/menu";
		  $.getJSON(url,function(result){
			  var json = JSON.stringify(result);
			 sessionStorage.setItem("${user}_menus", json);
			 loadMenus(result);
		  });
	}
	
	$("#ul_0").delegate(".hasUrl","click",function(){
		var id = $(this).attr("id");
		if (id) {
			sessionStorage.setItem("${user}_click_menu",id,{path: '/'});
		}
	});
	
	var click_menu = sessionStorage.getItem("${user}_click_menu");
	if (click_menu) {
		var uls = $("#" + click_menu).parents("ul");
		$.each(uls, function(i, v) {
			v.className += ' in';
		});
		$("#" + click_menu).addClass("active");
	}
	});
</script>
</c:if>