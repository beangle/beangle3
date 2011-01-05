[#ftl]
[@b.xhtmlhead]
<style type="text/css">
.banner{background-color:#e0ecff;height:20px;padding-top:2px;padding-bottom:2px;}
.banner_area{float:right;padding-right:10px}
</style>
[/@]

<div class="banner">
	<div class="banner_area">
	<a href="#" onclick="editAccount()">[@b.text 'action.myAccount'/]</a>&nbsp;&nbsp;
	<a href="home!welcome.action" target="main">[@b.text 'action.backHome'/]</a>&nbsp;&nbsp;
	<a href="logout.action" target="top">[@b.text 'action.logout'/]</a>
	</div>
	<form id="categoryForm" name="categoryForm" method="post" action="home!index.action">
	<div class="banner_area">
		<a href="${base}/security/my.action" target="_blank" title="查看登录记录">${user.fullname}(${user.name})</a>&nbsp;&nbsp;
		<label for="security.categoryId">身份:</label> 
		<select id="security.categoryId" name="security.categoryId" style="width:100px" >
		[#list categories as category]<option value="${category.id}" [#if (Session['security.categoryId']!0)==category.id]selected="selected"[/#if]>${category.name}</option>[/#list]
		</select>
		<input type="submit" value="切换"/>
	</div>
	</form>
</div>

<table id="mainTable" style="width:100%;height:95%" >
<tr>
   <td style="height:100%;width:14%" id="leftTD" valign="top">
	   [#include "menus.ftl"/]
   </td>
   <td style="width:86%" id="rightTD" valign="top">
	[@b.iframe src="home!welcome.action" name="main" id="main" class="autoadapt" width="100%"]main content frame[/@]
   </td>
 </tr>
</table>

<script type="text/javascript">
  function editAccount(){
	  var url = "${base}/security/my!edit.action";
	  var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(350,250);
  }
</script>

[#include "/template/foot.ftl"/]