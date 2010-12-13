[#ftl]
[#include "/template/head.ftl"/] 
<body>
 <table id="groupInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title">[@text name="common.name"/]:</td>
	     <td class="content"> ${group.name}</td>
	     <td class="title">[@text name="common.creator"/]:</td>
	     <td class="content">${group.owner.name!}  </td>
	   </tr>	   
	   <tr>
	     <td class="title" >[@text name="common.createdAt"/]:</td>
         <td class="content">${group.createdAt?string("yyyy-MM-dd")}</td>	     
	     <td class="title" >[@text name="common.updatedAt"/]:</td>
         <td class="content">${group.updatedAt?string("yyyy-MM-dd")}</td>	     
	   </tr> 
	   <tr>
        <td class="title" >适用身份:</td>
        <td  class="content">${group.category.name}</td>
	   	<td class="title" >&nbsp;[@text name="common.status" /]:</td>
 	    <td class="content">
	        [#if group.enabled] [@text name="action.activate" /]
	        [#else][@text name="action.freeze" /]
	        [/#if]
	    </td>
       </tr>
       <tr>
        <td class="title" >[@text name="group.users"/]:</td>
        <td  class="content" colspan="3">[#list group.members?sort_by(["user","name"]) as m] ${m.user.fullname}(${m.user.name})&nbsp;[/#list]</td>
       </tr>
	   <tr>
        <td class="title" >[@text name="common.description"/]:</td>
        <td  class="content" colspan="3">${group.description!}</td>
       </tr>
       <tr>
         <td colspan="4">
        <iframe  src="restriction.action?method=info&restriction.holder.id=${group.id}&restrictionType=group" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
        </iframe>
        </td>
       </tr>
      </table>
  <script>
   var bar = new ToolBar('groupInfoBar','[@text name="info.group"/]',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addBack("[@text name="action.back"/]");  
  </script>
 </body>
[#include "/template/foot.ftl"/]
