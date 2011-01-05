[#ftl]
[@b.xhtmlhead/]

 <table id="groupInfoBar"></table>
	 <table class="infoTable">
	   <tr>
		 <td class="title">[@b.text name="common.name"/]:</td>
		 <td class="content"> ${group.name}</td>
		 <td class="title">[@b.text name="common.creator"/]:</td>
		 <td class="content">${group.owner.name!}  </td>
	   </tr>
	   <tr>
		 <td class="title" >[@b.text name="common.createdAt"/]:</td>
		 <td class="content">${group.createdAt?string("yyyy-MM-dd")}</td>
		 <td class="title" >[@b.text name="common.updatedAt"/]:</td>
		 <td class="content">${group.updatedAt?string("yyyy-MM-dd")}</td>
	   </tr>
	   <tr>
		<td class="title" >适用身份:</td>
		<td  class="content">${group.category.name}</td>
	   	<td class="title" >&nbsp;[@b.text name="common.status" /]:</td>
 		<td class="content">
			[#if group.enabled] [@b.text name="action.activate" /]
			[#else][@b.text name="action.freeze" /]
			[/#if]
		</td>
	   </tr>
	   <tr>
		<td class="title" >[@b.text name="group.users"/]:</td>
		<td  class="content" colspan="3">[#list group.members?sort_by(["user","name"]) as m] ${m.user.fullname}(${m.user.name})&nbsp;[/#list]</td>
	   </tr>
	   <tr>
		<td class="title" >[@b.text name="common.description"/]:</td>
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
  <script type="text/javascript">
   var bar = bg.ui.toolbar('groupInfoBar','[@b.text name="info.group"/]');
   bar.setMessage('[@b.messages/]');
   bar.addBack("[@b.text name="action.back"/]");
  </script>
 
[#include "/template/foot.ftl"/]
