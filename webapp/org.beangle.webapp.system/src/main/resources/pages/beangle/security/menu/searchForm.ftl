[#ftl]
<form name="pageGoForm" method="post" action="menu!search.action" target="contentFrame">
<table class="search-widget" onkeypress="DWRUtil.onReturn(event, searchUser)">
	<tr>
		<td class="infoTitle" align="left" valign="bottom" ><img src="${base}/static/images/action/info.gif" align="top"/><em>[@b.text "ui.searchForm"/]</em></td>
	</tr>
	<tr>
		<td style="font-size:0px">
			<img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top"/>
		</td>
	</tr>
	<tr>
		<td class="search-item">配置:
			<select name="menu.profile.id" >
			[#list profiles as profile]
			<option value="${profile.id}">${profile.name}</option>
			[/#list]
			</select>
		</td>
	</tr>
	<tr>
		<td class="search-item">[@b.text name="common.code"/]:<input type="text" name="menu.code"  /></td>
	</tr>
	<tr>
		<td class="search-item">标题:<input type="text" name="menu.title"/></td>
	</tr>
	<tr>
		<td class="search-item">入口:<input type="text" name="menu.entry"/></td>
	</tr>
	<tr>
		<td class="search-item">[@b.text "common.status"/]:
			<select  name="menu.enabled" >
				<option value="" selected="selected">..</option>
				<option value="true">[@b.text "action.activate"/]</option>
				<option value="false" >[@b.text "action.freeze"/]</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align="center">
		<input name="orderBy" type="hidden" value="menu.code"/>
		<input type="submit" value="[@b.text name="action.query"/]" />
		</td>
	</tr>
</table>
</form>
