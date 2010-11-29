   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${base}/static/images/action/info.gif" align="top"/>
          <B>资源查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <form name="pageGoForm" method="post" action="resource!search.action" target="contentFrame">
     <input type="hidden" name="orderBy" value="resource.name asc"/>
   <tr>
    <td class="title">名称:</td>
    <td><input type="text" name="resource.name"  style="width:100px;"/></td>
   </tr>
   <tr>
    <td class="title">标题:</td>
    <td><input type="text" name="resource.title"  style="width:100px;"/></td>
   </tr>
   <tr>
    <td class="title">可见范围:</td>
    <td><@resourceScopeSelect -1/></td>
   </tr>
   <tr><td><@text "common.status"/>:</td><td><select  name="resource.enabled" style="width:100px;" >
	   		<option value="" selected>..</option>
	   		<option value="true"><@text "action.activate"/></option>
	   		<option value="false" ><@text "action.freeze"/></option>
	  </select>
	</td>
   </tr>
   <tr><td colspan="2" align="center"><button onclick="search();"><@text name="action.query"/></button></td></tr>
   </form>
  </table>




	
