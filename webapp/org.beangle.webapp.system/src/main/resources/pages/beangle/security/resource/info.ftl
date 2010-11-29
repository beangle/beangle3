<#include "/template/head.ftl"/>
<#include "scope.ftl"/>
<body>
<table id="resourceInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;标题:</td>
	     <td class="content">${resource.title}</td>
	     <td class="title" >&nbsp;<@text name="common.name"/>:</td>
         <td class="content">${resource.name}</td>
	   </tr>
       <tr>
        <td class="title" >&nbsp;<@text name="common.description"/>:</td>
        <td  class="content">${resource.description!}</td>
	    <td class="title">&nbsp;<@text name="common.status"/>:</td>
        <td class="content">
            <#if resource.enabled><@text name="action.activate" /><#else><@text name="action.unactivate"/></#if>
        </td>
       </tr>
       <tr>
	     <td class="title" >&nbsp;引用菜单:</td>
         <td class="content"><#list menus as menu>(${menu.code})${menu.title}<br></#list></td>
         <td class="title">&nbsp;可见范围:</td>
         <td class="content"><@resourceScope resource.scope/></td>
       </tr>
       <tr>
	     <td class="title">使用组:</td>
	     <td>
	      <#list groups as group>${group.name}<#if group_has_next>&nbsp;</#if></#list>
	     </td>
	     <td class="title">适用用户类别:</td>
	     <td>
	      <#list resource.categories as category>[${category.name}]<#if category_has_next>&nbsp;</#if></#list>
	     </td>
	   </tr>
       <tr>
	     <td class="title">使用用户:</td>
	     <td colspan="3">
	      <#list users as user>${user.name}<#if user_has_next>&nbsp;</#if></#list>
	     </td>
	   </tr>
      </table>
      <#list resource.objects as object>
      <fieldSet  align=center> 
 	   <legend>数据权限 限制对象</legend>
	   <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;名称:</td>
	     <td class="content">${object.name}</td>
	     <td class="title" >&nbsp;描述:</td>
         <td class="content">${pattern.remark}</td>
	   </tr>
       </table>
	  </fieldSet>
	  </#list>
  <script>
   var bar = new ToolBar('resourceInfoBar','<@text name="security.resource.info"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@text name="action.back"/>");  
  </script>
 </body>
<#include "/template/foot.ftl"/>
