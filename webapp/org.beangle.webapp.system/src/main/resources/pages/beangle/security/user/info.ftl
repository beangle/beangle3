[#ftl]
[#include "/template/head.ftl"/]
<body>
  <table id="userInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;[@text name="common.name" /]:</td>
	     <td class="content"> ${user.name!}</td>
	     <td class="title" >&nbsp;[@text name="user.fullname" /]:</td>
	     <td class="content">${(user.fullname)!}  </td>
	   </tr>	   
	   <tr>
	     <td class="title" >&nbsp;[@text name="common.email" /]:</td>
	     <td class="content">${user.mail!} </td>
	     <td class="title" >&nbsp;[@text name="common.createdAt" /]:</td>
         <td class="content">${user.createdAt?string("yyyy-MM-dd")}</td>	     
	   </tr> 
	   <tr>
	     <td class="title" >&nbsp;[@text name="common.status" /]:</td>
	     <td class="content">
	        [#if user.state!(1)==1] [@text name="action.activate" /]
	        [#else][@text name="action.freeze" /]
	        [/#if]
	     </td>
	     <td class="title" >&nbsp;[@text name="common.updatedAt" /]:</td>
         <td class="content">${user.updatedAt?string("yyyy-MM-dd")}</td>	     
	   </tr>
	   <tr>
        <td class="title" >&nbsp;[@text name="userCategory" /]:</td>
        <td  class="content">
        	[#list user.categories as category]${category.name}[#if category.id==user.defaultCategory.id](默认)[/#if][#if category_has_next],[/#if][/#list]
        </td>
        <td class="title" >&nbsp;[@text name="common.creator" /]:</td>
	    <td class="content">${(user.creator.name)!}  </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;[@text name="group" /]:</td>
        <td  class="content" colspan="3">
             [#list user.groups! as group]
                  ${group.name}&nbsp;
             [/#list]
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;[@text name="user.mngGroups" /]:</td>
        <td  class="content" colspan="3">
             [#list user.mngGroups! as group]
                  ${group.name}&nbsp;
             [/#list]
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;[@text name="common.remark" /]:</td>
        <td class="content" colspan="3">${user.remark!}</td>
       </tr>
       <tr>
        <td class="title" >&nbsp;权限控制台:</td>
        <td class="content" colspan="3"><a target="_blank" href="user!dashboard.action?user.id=${user.id}" id="dashboardHref" >查看权限用户控制台</A></td>
       </tr>
      </table>
  <script>
   var bar = bg.ui.toolbar('userInfoBar','[@text name="user" /][@text name="common.detail" /]',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addBack("[@text name="action.back"/]");
  </script>
 </body>
[#include "/template/foot.ftl"/]
