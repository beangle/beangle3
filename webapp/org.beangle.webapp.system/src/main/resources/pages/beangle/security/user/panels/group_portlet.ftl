[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">用户组信息</span><span class="ui-icon ui-icon-plusthick"></span></div>
	<div class="portlet-content">
	  <table class="infoTable">
	   <tr>
		<td class="title">${b.text("group")}</td>
		<td class="title">成员</td>
		<td class="title">授权</td>
		<td class="title">管理</td>
	   </tr>
	   [#list user.groups as m]
	   <tr align="right">
	   <td>${m.group.name}</td>
	   <td>[#if m.member]<img src="${b.theme.iconurl('actions/activate.png')}"/>[/#if]</td>
	   <td>[#if m.granter]<img src="${b.theme.iconurl('actions/activate.png')}"/>[/#if]</td>
	   <td>[#if m.manager]<img src="${b.theme.iconurl('actions/activate.png')}"/>[/#if]</td>
	   </tr>
	   [/#list]
	   </table>
	</div>
</div>
