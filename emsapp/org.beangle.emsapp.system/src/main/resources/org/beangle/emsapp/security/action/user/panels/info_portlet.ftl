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
		<td class="title" >&nbsp;有效期限:</td>
		<td class="content">${(user.effectOn?string("yyyy-MM-dd"))!}～${(user.invalidOn?string("yyyy-MM-dd"))!}</td>
		<td class="title" >&nbsp;密码过期:</td>
		<td class="content">[#if user.passwordExpiredAt??]${(user.passwordExpiredAt?string("yyyy-MM-dd"))!}[#else]永不过期[/#if]</td>
	   </tr>
	   <tr>
		<td class="title" >&nbsp;${b.text("common.remark")}:</td>
		<td class="content">${user.remark!}</td>
		<td class="title" >&nbsp;${b.text("common.creator")}:</td>
		<td class="content">${(user.creator.name)!}  </td>
	   </tr>
	  </table>
	 </div>
	</div>
