[#ftl]
[@b.head/]
[@b.toolbar title="限制参数和参数组管理"]bar.addBack();[/@]
<script  type="text/javascript" src="${base}/static/scripts/itemSelect.js"></script>
<table width="100%">
   <tr>
	 <td width="25%" valign="top">
	   <table  width="100%">
		<tr>
		  <td  class="infoTitle" align="left" valign="bottom">
		   <img src="${base}/static/images/action/info.gif" align="top"/>
			  <em>参数组列表</em>
		  </td>
		  <td  class="infoTitle" align="left" valign="bottom">
		  </td>
		<tr>
		  <td  colspan="8" style="font-size:0px">
			  <img src="${base}/static/images/action/keyline.gif" height="2" width="100%" align="top"/>
		  </td>
	   </tr>
	  </table>
	[@b.grid  ]
	 [@b.row]
	  [@b.col title="序号"/]
	  [@b.col width="70%" title="名称"/]
	  [@b.col title="操作"/]
	 [/@]
	 <tr>
	   <td align="center">0</td>
	   <td align="center" class="padding" style="height:20px;"id="defaultGroup"
		onclick="javascript:setSelectedRow(paramListTable,this);searchParam('')"
		onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">所有参数</td>
	   <td></td>
	 </tr>
	 [@b.gridbody simplePageBar=true items=paramGroups;paramGroup,paramGroup_index]
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
   <iframe  src="${b.url('param!search')}" id="paramFrame" name="paramFrame"
   marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
   </td>
   </tr>
  </table>
<script type="text/javascript">
	function searchParam(groupId){
		paramFrame.location="${b.url('param!search')}?paramGroup.id="+groupId;
	}
</script>
[@b.foot/]