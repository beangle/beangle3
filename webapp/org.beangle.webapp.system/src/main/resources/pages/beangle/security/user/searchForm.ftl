[#ftl]
   <table class="searchTable">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${base}/static/images/action/info.gif" align="top"/>
          <B>[@text "ui.searchForm"/]</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form id="userSearchForm" name="userSearchForm" target="contentFrame" action="${base}/security/user!search.action" method="post">
     <tr><td>[@text "user.name"/]:</td><td><input type="text" name="user.name" style="width:100px;" /></td></tr>
     <tr><td>[@text "user.fullname"/]:</td><td><input type="text" name="user.fullname" style="width:100px;" /></td></tr>
     <tr><td>[@text "common.creator"/]:</td><td><input type="text" name="user.creator.fullname" style="width:100px;" /></td></tr>
     <tr><td>[@text "group"/]:</td><td><input type="text" name="groupName" style="width:100px;" /></td></tr>
     <tr>
 		<td>[@text name="userCategory" /]:</td>
 		<td>
 		<select  name="categoryId" style="width:100px;" >
 			<option value="" >请选择身份</option>
     		[#list categories as category]
     			<option value="${category.id}" >${category.name}</option>
      		[/#list]
	  	</select>
	  	</td>
 	 </tr>
		 <tr><td>[@text "common.status"/]:</td><td><select  name="user.status" style="width:100px;" >
		   		<option value="1" selected>[@text "action.activate"/]</option>
		   		<option value="0" >[@text "action.freeze"/]</option>
		  </select>
		</td></tr>
     <tr>
     <td colspan="2" align="center">
     <input type="submit" value="查询"/>
     </td>
     </tr>
     </form>
	</table>
