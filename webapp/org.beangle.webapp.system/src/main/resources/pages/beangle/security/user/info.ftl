[#ftl]
[@b.xhtmlhead/]
<body>
  <table id="userInfoBar"></table>
	 <table class="infoTable">
	   <tr>
		 <td class="title" >&nbsp;[@b.text name="common.name" /]:</td>
		 <td class="content"> ${user.name!}</td>
		 <td class="title" >&nbsp;[@b.text name="user.fullname" /]:</td>
		 <td class="content">${(user.fullname)!}  </td>
	   </tr>
	   <tr>
		 <td class="title" >&nbsp;[@b.text name="common.email" /]:</td>
		 <td class="content">${user.mail!} </td>
		 <td class="title" >&nbsp;[@b.text name="common.createdAt" /]:</td>
		 <td class="content">${user.createdAt?string("yyyy-MM-dd")}</td>
	   </tr>
	   <tr>
		 <td class="title" >&nbsp;[@b.text name="common.status" /]:</td>
		 <td class="content">
			[#if user.state!(1)==1] [@b.text name="action.activate" /]
			[#else][@b.text name="action.freeze" /]
			[/#if]
		 </td>
		 <td class="title" >&nbsp;[@b.text name="common.updatedAt" /]:</td>
		 <td class="content">${user.updatedAt?string("yyyy-MM-dd")}</td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;[@b.text name="userCategory" /]:</td>
		<td  class="content">
			[#list user.categories as category]${category.name}[#if category.id==user.defaultCategory.id](默认)[/#if][#if category_has_next],[/#if][/#list]
		</td>
		<td class="title" >&nbsp;[@b.text name="common.creator" /]:</td>
		<td class="content">${(user.creator.name)!}  </td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;[@b.text name="group" /]:</td>
		<td  class="content" colspan="3">
			 [#list user.groups! as group]
				  ${group.name}&nbsp;
			 [/#list]
		 </td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;[@b.text name="user.mngGroups" /]:</td>
		<td  class="content" colspan="3">
			 [#list user.mngGroups! as group]
				  ${group.name}&nbsp;
			 [/#list]
		 </td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;[@b.text name="common.remark" /]:</td>
		<td class="content" colspan="3">${user.remark!}</td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;权限控制台:</td>
		<td class="content" colspan="3"><a target="_blank" href="user!dashboard.action?user.id=${user.id}" id="dashboardHref" >查看权限用户控制台</a></td>
	   </tr>
	  </table>
  <script type="text/javascript">
   var bar = bg.ui.toolbar('userInfoBar','[@b.text name="user" /][@b.text name="common.detail" /]');
   bar.setMessage('[@b.messages/]');
   bar.addBack("[@b.text name="action.back"/]");
  </script>
 </body>
[#include "/template/foot.ftl"/]
