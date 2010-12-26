[#ftl]
[#include "/template/head.ftl"/]
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
	var action="user!search.action";
	function search(pageNo,pageSize,orderBy){
		form.action=action;
		form.submit();
	}
	search();
	var bar = bg.ui.toolbar('userBar','<a href="dashboard.action">权限管理</a>-->[@text "ui.userIndex"/]',null,true,true);
	bar.setMessage('[@getMessage/]');
	bar.addHelp();
  </script>
</html>
