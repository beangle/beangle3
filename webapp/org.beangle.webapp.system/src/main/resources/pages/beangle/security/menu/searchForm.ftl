   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchUser)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${base}/static/images/action/info.gif" align="top"/>
          <B><@text "ui.searchForm"/></B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="pageGoForm" method="post" action="menu.action?method=search" target="contentFrame">
    	<input name="orderBy" type="hidden" value="menu.code"/>
	   <tr>
	    <td class="title">菜单配置</td>
	    <td><select  name="menu.profile.id" style="width:100px;" >
	        <#list profiles as profile>
	        <option value="${profile.id}">${profile.name}</optino>
	        </#list>
	        </select>
	    </td>
	   </tr>
       <tr>
	    <td class="title"><@text name="common.code"/>:</td>
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
		<tr><td><@text "common.status"/>:</td><td><select  name="menu.enabled" style="width:100px;" >
		   		<option value="" selected>..</option>
		   		<option value="true"><@text "action.activate"/></option>
		   		<option value="false" ><@text "action.freeze"/></option>
		  </select>
		</td></tr>
     <tr><td colspan="2" align="center"><button onclick="search();"><@text name="action.query"/></button></td></tr>
     </form>
	</table>
