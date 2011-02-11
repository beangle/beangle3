[#ftl]
[@b.head/]
<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css" />
<script  type="text/javascript" src="${base}/static/scripts/common/TableTree.js"></script>
<script type="text/javascript">defaultColumn=1;</script>
[#include "../status.ftl"/]
[#function convert_code(code)]
	[#local tdid="1-"]
	[#if code?length!=1]
		[#list 1..code?length as menuIdChar]
			[#if menuIdChar%2==1]
			[#local tdid = tdid + code[menuIdChar-1..menuIdChar] +"-"]
			[/#if]
		[/#list]
	[/#if]
	[#return tdid[0..tdid?length-2]]
[/#function]

[@b.grid  items=menus var="menu"]
[@b.gridbar title="菜单列表"]
	action.addParam('menu.profile.id',"${Parameters['menu.profile.id']!}");
	function activate(isActivate){
		return action.multi("activate","确定操作?","isActivate="+isActivate);
	}
	function exportData(){
		return action.method("export",null,"keys=code,title,entry,description,enabled&titles=代码,标题,入口,描述,是否可用");
	}
	function preview(){
		window.open("${b.url('!preview')}?${b.paramstring}");
	}
	bar.addItem("${b.text("action.new")}",action.add(),'new.gif');
	bar.addItem("${b.text("action.edit")}",action.edit(),'update.gif');
	bar.addItem("${b.text("action.freeze")}",activate(0),'${base}/static/icons/beangle/16x16/actions/freeze.png');
	bar.addItem("${b.text("action.activate")}",activate(1),'${base}/static/icons/beangle/16x16/actions/activate.png');
	bar.addItem("${b.text("action.export")}",exportData());
	bar.addItem("${b.text("action.delete")}",action.remove());
	bar.addItem("打印","preview()","print.gif");
[/@]
	[@b.row ]
		<tr title="${menu.entry!}" id="${convert_code(menu.code)}">
		[@b.boxcol onclick="treeToggle(this,false)" /]
		[@b.col property="title" title="标题"]
		<div class="tier${menu.code?length/2}" align="left">
		[#if (menu.children?size==0)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open"  onclick="toggleRows(this)" >   </a>
		[/#if]
			[@b.a href="!info?menu.id=${menu.id}"]${menu.title}[/@]
		</div>
		[/@]
		[@b.col property="engTitle" title="英文标题"/]
		[@b.col property="code" width="10%" title="common.code"/]
		[@b.col property="enabled" width="10%" title="common.status"][@enableInfo menu.enabled/][/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
</script>
[@b.foot/]