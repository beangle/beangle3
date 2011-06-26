[#ftl]
[@b.head]
<style type="text/css">
.banner{background-color:#e0ecff;height:25px;padding-top:2px;padding-bottom:2px;}
.banner_area{float:right;padding-right:10px}
</style>
[/@]

<div class="banner">
	<div class="banner_area">
	<a href="#" onclick="editAccount()">${b.text('action.myAccount')}</a>&nbsp;&nbsp;
	[@b.a href="!welcome" target="main"]${b.text('action.backHome')}[/@]&nbsp;&nbsp;
	[@b.a href="logout" target="top"]${b.text('action.logout')}[/@]
	</div>
	[@b.form]
	<div class="banner_area">
		[@b.a href="/security/my" target="_blank" title="查看登录记录"]${user.fullname?html}(${user.name?html})[/@]&nbsp;&nbsp;
		[@b.select name="security.categoryId" items=user.categories title="entity.userCategory"  style="width:100px" value=categoryId option="id,title"/]
		<input type="submit" value="切换"/>
	</div>
	[/@]
</div>

<table id="mainTable" style="width:100%;height:95%" >
<tr>
   <td style="height:100%;width:14%" id="leftTD" valign="top">
	   [#include "menus.ftl"/]
   </td>
   <td style="width:86%" id="rightTD" valign="top">
   [@b.div id="main" href="!welcome"/]
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