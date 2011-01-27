[#ftl]
<form id="userSearchForm" name="userSearchForm" action="${b.url('!search')}" method="post">
<table class="search-widget">
	<tr>
		<td>
		<img src="${base}/static/images/action/info.gif" alt="info" class="toolbar-icon"/><em>${b.text("ui.searchForm")}</em>
		</td>
	</tr>
	<tr>
		<td style="font-size:0px">
			<img src="${base}/static/images/action/keyline.gif" height="2" width="100%" alt="keyline"/>
		</td>
	</tr>
	<tr><td class="search-item"><label for="user.name">${b.text("user.name")}:</label><input type="text" id="user.name" name="user.name" style="width:100px;" /></td></tr>
	<tr><td class="search-item"><label for="user.fullname">${b.text("user.fullname")}:</label><input type="text" id="user.fullname" name="user.fullname" style="width:100px;" /></td></tr>
	<tr><td class="search-item"><label for="user.creator.fullname">${b.text("common.creator")}:</label><input type="text" id="user.creator.fullname" name="user.creator.fullname" style="width:100px;" /></td></tr>
	<tr><td class="search-item"><label for="groupName">${b.text("group")}:</label><input type="text" id="groupName" name="groupName" style="width:100px;" /></td></tr>
	<tr>
		<td class="search-item"><label for="categoryId">${b.text("userCategory")}:</label>
		<select id="categoryId" name="categoryId" style="width:105px;" >
			<option value="" >请选择身份</option>
			[#list categories as category]
			<option value="${category.id}">${category.name}</option>
		[/#list]
		</select>
		</td>
	</tr>
	<tr>
		<td class="search-item"><label for="user.status">${b.text("common.status")}:</label>
		<select id="user.status" name="user.status" style="width:105px;" >
			<option value="1" selected="selected">${b.text("action.activate")}</option>
			<option value="0" >${b.text("action.freeze")}</option>
		</select>
		</td>
	</tr>
	<tr>
		<td align="center">[@sj.submit value="查询" targets="userlist"/]</td>
	</tr>
	</table>
</form>
