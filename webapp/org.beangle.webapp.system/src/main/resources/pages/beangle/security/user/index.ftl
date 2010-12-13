[#ftl]
[#include "/template/head.ftl"/]
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/Menu.js"></script> 
 <body>
   <table id="userBar"></table>
   <table class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view">[#include "searchForm.ftl"/]</td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
	var form=document.userSearchForm;

	var action="user.action";
	function searchUser(pageNo,pageSize,orderBy){
		form.action=action+"?method=search";
		goToPage(form,pageNo,pageSize,orderBy);
	}

	searchUser();
	var bar = new ToolBar('userBar','<a href="dashboard.action">权限管理</a>-->[@text "ui.userIndex"/]',null,true,true);
	bar.setMessage('[@getMessage/]');
	bar.addHelp();
  </script>
</html>
