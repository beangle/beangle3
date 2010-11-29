<#include "/template/head.ftl"/>
<body>
<table id="menuInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@text name="common.id"/>:</td>
	     <td class="content">${menu.code}  </td>
	     <td class="title" >&nbsp;<@text name="menu.entry"/>:</td>
         <td class="content">${menu.entry!}</td>
	   </tr>	   
	   <tr>
	     <td class="title" >&nbsp;标题:</td>
	     <td class="content">${menu.title}</td>
   	     <td class="title" >&nbsp;英文标题:</td>
	     <td class="content">${menu.engtitle!}</td>
       </tr>
       <tr>
        <td class="title" >&nbsp;<@text name="common.description"/>:</td>
        <td  class="content" >${menu.description!}</td>
	    <td class="title" >&nbsp;<@text name="common.status"/>:</td>
        <td class="content">
            <#if menu.enabled><@text name="action.activate" /><#else><@text name="action.unactivate"/></#if>
        </td>
       </tr>
       <tr>
	     <td class="title" >&nbsp;引用资源:</td>
         <td class="content"><#list menu.resources as resource>(${resource.name})${resource.title}<br></#list></td>
         <td class="title">使用组:</td>
	     <td>
	      <#list groups! as group>${group.name}<#if group_has_next><br></#if></#list>
	     </td>
       </tr>
      </table>
   <form name="menuInfoForm" method="post"></form>
  <script>
  function edit(){
   document.menuInfoForm.action="menu.action?method=edit&menuId=${menu.id}";
   document.menuInfoForm.submit();
  }
   var bar = new ToolBar('menuInfoBar','<@text name="info.module.detail"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@text name="action.back"/>");  
  </script>
 </body>
<#include "/template/foot.ftl"/>
