[#ftl]
[@b.head]
<style type="text/css">
.banner{background-color:#e0ecff;height:20px;padding-top:2px;padding-bottom:2px;}
.banner_area{float:right;padding-right:10px}
</style>
[/@]

<div class="banner">
	<div class="banner_area">
	<a href="#" onclick="editAccount()">${b.text('action.myAccount')}</a>&nbsp;&nbsp;
	<a href="${b.url('!welcome')}" target="main">${b.text('action.backHome')}</a>&nbsp;&nbsp;
	<a href="${b.url('logout')}" target="top">${b.text('action.logout')}</a>
	</div>
	<form id="categoryForm" name="categoryForm" method="post" action="${b.url('!index')}">
	<div class="banner_area">
		<a href="${b.url('/security/my')}" target="_blank" title="查看登录记录">${user.fullname}(${user.name})</a>&nbsp;&nbsp;
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
   [@sj.div id="main" href="${b.url('!welcome')}"/]
   </td>
 </tr>
</table>

<script type="text/javascript">
  function editAccount(){
	  var url = "${b.url('/security/my!edit')}";
	  var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(350,250);
  }
</script>

[@b.foot/]