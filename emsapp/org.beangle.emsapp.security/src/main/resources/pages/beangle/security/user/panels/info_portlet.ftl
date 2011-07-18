[#ftl]
	<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">基本信息</span>
	
	</div>
	<div class="portlet-content">
	  <table class="infoTable">
	   <tr>
		 <td class="title" >&nbsp;${b.text("common.name")}:</td>
		 <td class="content"> ${user.name!}</td>
		 <td class="title" >&nbsp;${b.text("user.fullname")}:</td>
		 <td class="content">${(user.fullname)!}  </td>
	   </tr>
	   <tr>
		 <td class="title" >&nbsp;${b.text("common.email")}:</td>
		 <td class="content">${user.mail!} </td>
		 <td class="title" >&nbsp;${b.text("common.createdAt")}:</td>
		 <td class="content">${user.createdAt?string("yyyy-MM-dd")}</td>
	   </tr>
	   <tr>
		 <td class="title" >&nbsp;${b.text("common.status")}:</td>
		 <td class="content">
			[#if user.state!(1)==1] ${b.text("action.activate")}
			[#else]${b.text("action.freeze")}
			[/#if]
		 </td>
		 <td class="title" >&nbsp;${b.text("common.updatedAt")}:</td>
		 <td class="content">${user.updatedAt?string("yyyy-MM-dd")}</td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;${b.text("entity.userCategory")}:</td>
		<td  class="content">
			[#list user.categories as category]${category.name}[#if category.id==user.defaultCategory.id](默认)[/#if][#if category_has_next],[/#if][/#list]
		</td>
		<td class="title" >&nbsp;${b.text("common.creator")}:</td>
		<td class="content">${(user.creator.name)!}  </td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;${b.text("common.remark")}:</td>
		<td class="content" colspan="3">${user.remark!}</td>
	   </tr>
	  </table>
	 </div>
	</div>
