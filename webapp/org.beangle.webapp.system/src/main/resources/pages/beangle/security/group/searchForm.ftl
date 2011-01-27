[#ftl]
<form id="groupSearchForm" name="groupSearchForm" action="${b.url('!search')}"  target="contentFrame" method="post">
<table class="searchTable">
	<tr>
		<td  colspan="2">
		<img src="${base}/static/images/action/info.gif" align="top"/><em>${b.text("ui.searchForm")}</em>
		</td>
	</tr>
	<tr>
		<td  colspan="2" style="font-size:0px"><img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
	</tr>
	<tr><td>${b.text("common.name")}:</td><td><input type="text" name="userGroup.name" style="width:100px;" /></td></tr>
	<tr><td>${b.text("common.creator")}:</td><td><input type="text" name="userGroup.creator.name" style="width:100px;" /></td></tr>
	<tr>
		<td>${b.text("userCategory")}:</td>
		<td>
			<select  name="userGroup.category.id" style="width:100px;" >
				<option value="" >请选择身份</option>
				[#list categories as category]
					<option value="${category.id}" >${category.name}</option>
				[/#list]
			</select>
		</td>
	</tr>
	<tr><td colspan="2" align="center"><input  type="submit" value="${b.text("action.query")}" /></td></tr>
</table>
</form>
