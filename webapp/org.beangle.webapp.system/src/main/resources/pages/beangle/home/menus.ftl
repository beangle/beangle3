<script language="javascript" src="${base}/static/scripts/common/SelectableTree.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/ieemu.js"></script> 
 <#macro i18nNameTitle(entity)><#if language?index_of("en")!=-1><#if entity.engTitle!?trim=="">${entity.title!}<#else>${entity.engTitle!}</#if><#else><#if entity.title!?trim!="">${entity.title!}<#else>${entity.engTitle!}</#if></#if></#macro>
 <table id="menu_panel">
  <tr>
   <td height="10"></td>
  </tr>
  <tr>
   <td>
	  <SCRIPT language="javascript">
		var menuTree = new MenuToc_toc('menuTree');
		menuTree.multiSelect = true;
		menuTree.selectChildren = false;
		menuTree.styleSelected = 'MenuTocSelected';
		menuTree.styleNotSelected = 'MenuTocItemLinkStyle';
		menuTree.onClick = '';
		menuTree.styleItemLink = 'MenuTocItemLinkStyle';
		menuTree.styleItemNoLink = 'MenuTocItemNoLinkStyle';
		menuTree.styleItemFolderLink = 'MenuTocItemFolderLinkStyle';
		menuTree.styleItemFolderNoLink = 'MenuTocItemFolderNoLinkStyle';
		menuTree.showRoot = false;
		menuTree.showIcons = true;
		menuTree.showTextLinks = true;
		menuTree.iconWidth = '24';
		menuTree.iconHeight = '22';
		menuTree.iconPath = '';
		menuTree.iconEmpty = 'static/images/tree/empty.gif';
		menuTree.iconPlus = 'static/images/tree/plus.gif';
		menuTree.iconPlus1 = 'static/images/tree/plus.gif';
		menuTree.iconPlus2 = 'static/images/tree/plus.gif';
		menuTree.iconMinus = 'static/images/tree/minus.gif';
		menuTree.iconMinus1 = 'static/images/tree/minus.gif';
		menuTree.iconMinus2 = 'static/images/tree/minus.gif';
		menuTree.iconLine1 = 'static/images/tree/line.gif';
		menuTree.iconLine2 = 'static/images/tree/line.gif';
		menuTree.iconLine3 = 'static/images/tree/line.gif';
		menuTree.iconItem = 'static/images/tree/sanjiao.gif';
		menuTree.iconFolderCollapsed = 'static/images/tree/entityfolder.gif';
		menuTree.iconFolderExpanded = 'static/images/tree/entityfolder.gif';
        try{
        <#assign nodeIndex=0>
		menuTree.node${Parameters['parentCode']!} = menuTree.makeFolder('root','','','','1');
		
		<#list menus! as module>
		 <#assign nodeIndex=nodeIndex+1>
		 <#if module.entry??>
		     menuTree.node${module.code} = menuTree.makeItem('<@i18nNameTitle module/>','${(module.entry)!}','main','','<@i18nNameTitle module/>', 'javascript:menuTree.nodeClickedAndSelected(${module_index + 1})');
		     menuTree.insertNode(menuTree.node${module.code[0..module.code?length-3]}, menuTree.node${module.code});
         <#else>
		     menuTree.node${module.code} = menuTree.makeFolder('<@i18nNameTitle module/>','javascript:menuTree.nodeClicked(${nodeIndex})','','','<@i18nNameTitle module/>');
		     <#if module.code?length==2>
		         <#assign superCode=''>
		     <#else>
		         <#assign superCode=module.code[0..module.code?length-3]>
		     </#if>
		     if(typeof menuTree.node${superCode} != "undefined"){
		     	menuTree.insertNode(menuTree.node${superCode}, menuTree.node${module.code});
		     }else{
		        menuTree.insertNode(menuTree.node${Parameters['parentCode']!}, menuTree.node${module.code});
		     }
		  </#if>
		</#list>
		}catch(e){
		alert("菜单加载出错!");
		}
		menuTree.display(menuTree.node${Parameters['parentCode']!},1);
	  </SCRIPT>
   </td>
  </tr>
 </table>