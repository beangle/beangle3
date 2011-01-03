[#ftl]
<table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchUser)">
	<tr>
		<td  colspan="2" class="infoTitle" align="left" valign="bottom" ><img src="${base}/static/images/action/info.gif" align="top"/><em>[@b.text "ui.searchForm"/]</em></td>
	</tr>
	<tr>
		<td  colspan="2" style="font-size:0px">
			<img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top"/>
		</td>
	</tr>
	<form name="pageGoForm" method="post" action="menu!search.action" target="contentFrame">
		<input name="orderBy" type="hidden" value="menu.code"/>
		<tr>
		<td class="title">配置:</td>
		<td><select  name="menu.profile.id" style="width:100px;" >
			[#list profiles as profile]
			<option value="${profile.id}">${profile.name}</option>
			[/#list]
			</select>
		</td>
	   </tr>
	   <tr>
		<td class="title">[@b.text name="common.code"/]:</td>
		<td><input type="text" name="menu.code"  style="width:100px;"/></td>
	   </tr>
	   <tr>
		<td class="title">标题:</td>
		<td><input type="text" name="menu.title"  style="width:100px;"/></td>
	   </tr>
	   <tr>
		<td class="title">入口:</td>
		<td><input type="text" name="menu.entry"  style="width:100px;"/></td>
		</tr>
		<tr><td>[@b.text "common.status"/]:</td><td><select  name="menu.enabled" style="width:100px;" >
		   		<option value="" selected="selected">..</option>
		   		<option value="true">[@b.text "action.activate"/]</option>
		   		<option value="false" >[@b.text "action.freeze"/]</option>
		  </select>
		</td></tr>
	 <tr><td colspan="2" align="center"><input type="submit" value="[@b.text name="action.query"/]" /></td></tr>
	 </form>
</table>
