[#ftl]
<form id="userSearchForm" name="userSearchForm" target="contentFrame" action="${base}/security/user!search.action" method="post">
<table class="searchTable">
	<tr>
		<td  colspan="2">
		<img src="${base}/static/images/action/info.gif" alt="info" class="toolbar-icon"/><em>[@b.text "ui.searchForm"/]</em>
		</td>
	</tr>
	<tr>
		<td  colspan="2" style="font-size:0px">
			<img src="${base}/static/images/action/keyline.gif" height="2" width="100%" alt="keyline"/>
		</td>
	</tr>
	<tr><td><label for="user.name">[@b.text "user.name"/]:</label></td><td><input type="text" id="user.name" name="user.name" style="width:100px;" /></td></tr>
	<tr><td><label for="user.fullname">[@b.text "user.fullname"/]:</label></td><td><input type="text" id="user.fullname" name="user.fullname" style="width:100px;" /></td></tr>
	<tr><td><label for="user.creator.fullname">[@b.text "common.creator"/]:</label></td><td><input type="text" id="user.creator.fullname" name="user.creator.fullname" style="width:100px;" /></td></tr>
	<tr><td><label for="groupName">[@b.text "group"/]:</label></td><td><input type="text" id="groupName" name="groupName" style="width:100px;" /></td></tr>
	<tr>
		<td><label for="categoryId">[@b.text name="userCategory" /]:</label></td>
		<td>
		<select id="categoryId" name="categoryId" style="width:100px;" >
			<option value="" >请选择身份</option>
			[#list categories as category]
			<option value="${category.id}">${category.name}</option>
		[/#list]
		</select>
		</td>
	</tr>
	<tr>
		<td><label for="user.status">[@b.text "common.status"/]:</label></td>
		<td><select id="user.status" name="user.status" style="width:100px;" >
			<option value="1" selected="selected">[@b.text "action.activate"/]</option>
			<option value="0" >[@b.text "action.freeze"/]</option>
		</select>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="查询"/></td>
	</tr>
	</table>
</form>
