[#ftl]
[#include "/template/head.ftl"/]
[#include "../status.ftl"/]
<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/TableTree.js"></script>
<script> defaultColumn=1;</script>
<body>
[@b.actionbar id="menu" title="菜单列表" entity="menu" action="menu.action"]
	action.addParam('menu.profile.id',"${Parameters['menu.profile.id']}");
	function activate(isActivate){
		return action.multi("activate","确定操作?","isActivate="+isActivate);
	}
	function exportData(){
		return action.method("export",null,"keys=code,title,entry,description,enabled&titles=代码,标题,入口,描述,是否可用");
	}
	function preview(){
		window.open(action+"?method=preview[#list Parameters?keys as key]&${key}=${Parameters[key]}[/#list]");
	}
	bar.addItem("[@text "action.new"/]",action.add(),'new.gif');
	bar.addItem("[@text "action.edit"/]",action.edit(),'update.gif');
	bar.addItem("[@text "action.freeze"/]",activate(0),'${base}/static/icons/beangle/16x16/actions/freeze.png');
	bar.addItem("[@text "action.activate"/]",activate(1),'${base}/static/icons/beangle/16x16/actions/activate.png');
	bar.addItem("[@text "action.export"/]",exportData());
	bar.addItem("[@text "action.delete"/]",action.remove());
	bar.addItem("打印","preview()","print.gif");
[/@]
<table width="100%" class="grid" id="menuTable">
	<tr class="gridhead">
		<td class="select"><input type="checkbox" onClick="treeToggleAll(this)"></td>
		<td>标题</td>
		<td>英文标题</td>
		<td width="10%">[@text name="common.code"/]</td>
		<td width="10%">[@text name="common.status"/]</td>
	</tr>
	<tbody>
  [#assign firstLevel=0]
    [#if Parameters['menu.code']?? ]
       [#assign firstLevel=Parameters['menu.code']?length]
    [/#if]
    [#if (firstLevel<2)] [#assign firstLevel=2][/#if]
    
  [#list menus?sort_by("code") as menu]
	[#assign tdid="1-"]
    [#if menu.code?length!=1]
	    [#list 1..menu.code?length as menuIdChar]
	        [#if menuIdChar%2==1]
	        [#assign tdid = tdid + menu.code[menuIdChar-1..menuIdChar] +"-"]
	        [/#if]
	    [/#list]
    [/#if]
    [#assign tdid = tdid[0..tdid?length-2]]
    
    <tr [#if (menu.code?length >firstLevel)]style="display: none;"[/#if] id="${tdid}"  onmouseover="swapOverTR(this,this.className)" 
         onmouseout="swapOutTR(this)" onclick="onRowChange(event);"
         title="${menu.entry!}">
       <td   class="select">
    	   <input type="checkBox" name="menuId" value="${menu.id}" onclick="treeToggle(this,false)">
       </td>
	   <td>
	    <div class="tier${menu.code?length/2}">
	    [#if (menu.children?size==0)]
    	    <a href="#" class="doc">
	    [#else]
	        <a href="#" class="folder_open"  onclick="toggleRows(this)" >   </a>
        [/#if]
	       <a href="menu.action?method=info&menu.id=${menu.id}">${menu.title}</a>
	    </div>
	   </td>
	   <td>&nbsp;${menu.engTitle!}</td>
	   <td>&nbsp;${menu.code}</td>
       <td align="center">[@enableInfo menu.enabled/]</td>
	  </tr>
	[/#list]
	[@page.bar curPage=menus pageId="menuTable"/]
	</tbody>
</table>

<script>
   //展开所有菜单
   displayAllRowsFor(1);
</script>
</body>
[#include "/template/foot.ftl"/]
