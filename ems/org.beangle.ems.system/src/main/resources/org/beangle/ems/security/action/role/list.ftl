[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
</script>
[#include "../status.ftl"/]
[@b.grid  items=roles var="role" sortable="false"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.export")}",action.exportData("name:${b.text("common.name")},remark:${b.text("common.remark")},owner.name:${b.text("common.creator")},createdAt:${b.text("common.createdAt")},updatedAt:${b.text("common.updatedAt")},users:${b.text("role.users")}",null,"&fileName=角色"));
	[/@]
	[@b.row]
		<tr id="${role.code}">
		[@b.boxcol width="5%"/]
		[@b.col property="name" width="35%" title="common.name"]
			<div class="tier${role.depth}" align="left">
			[#if (role.children?size==0)]
				<a href="#" class="doc"/>
			[#else]
				<a href="#" class="folder_open" id="${role.code}_folder" onclick="toggleRows(this)" >   </a>
			[/#if]
				[@b.a href="!info?id=${role.id}"]${role.code} ${role.name}[/@]
			</div>
		[/@]
		[@b.col width="20%" property="owner.name" title="common.creator"]${(role.owner.name)!} ${(role.owner.fullname)!}[/@]
		[@b.col width="10%" property="dynamic" title="动态组"]${role.dynamic?string("是","否")}[/@]
		[@b.col width="10%" property="enabled" title="common.status"][@enableInfo role.enabled/][/@]
		[@b.col width="10%" property="updatedAt" title="common.updatedAt"]${role.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col title="设置权限" width="10%"][@b.a target="_blank" href="authority!edit?role.id=${role.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/config.png')}"/>设置权限[/@][/@]
		</tr>
	[/@]
[/@]
[@b.foot/]