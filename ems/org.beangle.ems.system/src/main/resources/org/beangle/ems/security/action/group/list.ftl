[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[#include "../status.ftl"/]
[@b.grid  items=userGroups var="userGroup" sortable="false"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.export")}",action.exportData("name:${b.text("common.name")},remark:${b.text("common.remark")},owner.name:${b.text("common.creator")},createdAt:${b.text("common.createdAt")},updatedAt:${b.text("common.updatedAt")},users:${b.text("group.users")}",null,"&fileName=用户组"));
	[/@]
	[@b.row]
		<tr id="${userGroup.code}">
		[@b.boxcol/]
		[@b.col property="name" width="35%" title="common.name"]
			<div class="tier${userGroup.depth}" align="left">
			[#if (userGroup.children?size==0)]
				<a href="#" class="doc"/>
			[#else]
				<a href="#" class="folder_open" id="${userGroup.code}_folder" onclick="toggleRows(this)" >   </a>
			[/#if]
				[@b.a href="!info?id=${userGroup.id}"]${userGroup.code} ${userGroup.name}[/@]
			</div>
		[/@]
		[@b.col width="20%" property="owner.name" title="common.creator"/]
		[@b.col width="10%" property="enabled" title="common.status"][@enableInfo userGroup.enabled/][/@]
		[@b.col width="15%" property="updatedAt" title="common.updatedAt"]${userGroup.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col title="设置权限" width="15%"][@b.a target="_blank" href="authority!edit?group.id=${userGroup.id}"]<img style="border:0px" src="${b.theme.iconurl('actions/config.png')}"/>设置权限[/@][/@]
		</tr>
	[/@]
[/@]
[@b.foot/]