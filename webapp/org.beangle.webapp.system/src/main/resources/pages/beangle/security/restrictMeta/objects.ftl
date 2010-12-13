[#ftl]
[#include "/template/head.ftl"/]
<BODY LEFTMARGIN="0" TOPMARGIN="0">
[#assign labInfo]限制参数和参数组管理[/#assign]  
[#include "/template/back.ftl"/] 
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/itemSelect.js"></script>
<table width="100%">
   <tr>
     <td width="25%" valign="top">
	   <table  width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${base}/static/images/action/info.gif" align="top"/>
	          <B>参数组列表</B>
	      </td>
	      <td  class="infoTitle" align="left" valign="bottom">
	       
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>
   [@table.table width="100%" id="paramListTable"]
     [@table.thead]
      [@table.td text="序号"/]
      [@table.td width="70%" text="名称"/]
      [@table.td text="操作"/]
     [/@]
	 <tr>
	   <td align="center">0</td>
	   <td align="center" class="padding" style="height:20px;"id="defaultGroup"
        onclick="javascript:setSelectedRow(paramListTable,this);searchParam('')" 
        onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">所有参数</td>
       <td></td>
	 </tr>
     [@table.tbody simplePageBar=true datas=paramGroups;paramGroup,paramGroup_index]
      <td>${paramGroup_index + 1}</td>
      <td class="padding" style="height:20px;" onclick="javascript:setSelectedRow(paramListTable,this);searchParam('${paramGroup.id}')">
        ${paramGroup.name}
      </td>
      <td><a href="#" onclick="removeGroup(${paramGroup.id})">删除</a></td>
    </tr>
	[/@]
   [/@]
   </td>
   <td>
   <iframe  src="param.action?method=search" id="paramFrame" name="paramFrame" 
   marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
   </td>
   </tr>
  </table>
 </body>
<script>

   function searchParam(groupId){
      paramFrame.location="param.action?method=search&paramGroup.id="+groupId;
   }

</script>
