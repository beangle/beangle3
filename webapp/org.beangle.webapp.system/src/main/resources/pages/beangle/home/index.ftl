[#ftl]
[#include "/template/simpleHead.ftl"/]
<style>
#BakcGroup_image_td {
  background-repeat: repeat;
  background-position: center center;
  background-image: url(images/home/push_top.jpg);
}
</style>
<body style="overflow-y:hidden;">
<table  style="width:100%;background-color:#e0ecff" border="0" cellpadding="0" cellspacing="0">
	<tr align="right" valign="middle">
		<td ></td>
     	<td>
     		<a href="${base}/security/my.action" target="_blank" title="查看登录记录">${user.fullname}(${user.name})</a>&nbsp;&nbsp;
     		身份:[#if categories?size==1]${categories?first.name}[#else]
     		<select name="security.categoryId" onchange="changeUserCategory(this.value);" style="width:100px" >
     		[#list categories as category]<option value="${category.id}" [#if (Session['security.categoryId']!0)==category.id]selected[/#if]>${category.name}</option>[/#list]
     		</select>
     		[/#if]
        </td>
        <td width="140px">
         <a href="#" onclick="editAccount()">[@text 'action.myAccount'/]</a>&nbsp;&nbsp;
         <a href="#" onclick="home()">[@text 'action.backHome'/]</a>&nbsp;&nbsp;
         <a href="#" onclick="logout()">[@text 'action.logout'/]</a>
        </td>
        <td width="5px"><td>
	</tr>
</table>
[#--
<table  style="width:100%;background-color:#e0ecff" border="0" cellpadding="0" cellspacing="0">
	<tr height='20px'>
		[#macro i18nNameTitle(entity)][#if language?index_of("en")!=-1][#if (entity.engTitle!"")?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]      
		[#list menus! as module]
		<td align="center" width="100">
        <A style="cursor:hand" HREF="home.action?method=moduleList&parentCode=${module.code}" target="leftFrame" >
          [@i18nNameTitle module/]
        </A>
		</td>
		[/#list]
		<td>&nbsp;</td>
	</tr>
</table>
 --]
<table id="mainTable" style="width:100%;height:95%" cellpadding="0" cellspacing="0" border="0">
 <tr>
   <td style="HEIGHT:100%;width:14%" border="0" id="leftTD" valign="top">
	   [#include "menus.ftl"/]
   </td>
   <td width="0%" height="100%" bgcolor="#ffffff" style="cursor:w-resize;">
	   <a onClick="horizontalSwitch('left_tag')">
	      <img id="left_tag" style="cursor:hand" src="${base}/static/images/home/push_left.jpg" border="0" >
	   </a>
    </td>
   <td style="width:86%" id="rightTD">
	   <iframe HEIGHT="100%" WIDTH="100%" SCROLLING="auto" 
	     FRAMEBORDER="0" src="home.action?method=welcome" name="main" id="main">
	   </iframe>
   </td>
 </tr>
</table>

</body>
<script>
  function home() {
      main.location="home.action?method=welcome";
  }
  function logout() {
      self.location = 'logout.action';
  }
  function editAccount(){
      var url = "${base}/security/my!edit.action";
      var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(350,250);
  }
  function changeUserCategory(category){
     self.location="home.action?method=index&security.categoryId="+category;
  }
  //调整水平比例
  function horizontalSwitch(id) {
      var fullpath = document.getElementById(id).src;
      var filename = fullpath.substr(fullpath.lastIndexOf("/")+1, fullpath.length);
	  switch (filename) {
			case "push_left.jpg":
				document.getElementById(id).src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"pull_left.jpg";
				break;
			case "pull_left.jpg":
				document.getElementById(id).src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"push_left.jpg";
				break;	
	  }
      if(leftTD.style.width=='14%'){
        leftTD.style.width="0%";rightTD.style.width="100%";
      }else{
         leftTD.style.width="14%";rightTD.style.width="86%";
      }
   }
</script>
[#include "/template/foot.ftl"/]
