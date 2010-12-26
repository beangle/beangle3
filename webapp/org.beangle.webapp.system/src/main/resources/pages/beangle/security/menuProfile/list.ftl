[#ftl]
[#include "/template/head.ftl"/]
<BODY LEFTMARGIN="0" TOPMARGIN="0">

<table id="menuBar"></table>
[@b.grid width="100%" id="listTable" sortable="true"]
   [@b.gridhead]
     [@b.selectAllTd name="menuProfileId"/]
     [@b.td text="名称" /]
     [@b.td text="使用的用户类别" /]
   [/@]
   [@b.gridbody datas=menuProfiles;menuProfile]
     [@b.selectTd name="menuProfileId" value="${menuProfile.id}"/]
     <td><A href="user.action?method=info&user.id=${menuProfile.id}" >&nbsp;${menuProfile.name} </a></td>
     <td>[#if menuProfile.category??]${menuProfile.category.name}[/#if]</td>
   [/@]
 [/@]
  [@htm.actionForm name="menuProfileForm" entity="menuProfile" action="menu-profile.action"/]
 
  <script>
   var bar = bg.ui.toolbar('menuBar','系统菜单配置管理',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addItem("[@text "action.new"/]",'add()','new.gif');
   bar.addItem("[@text "action.edit"/]","edit();",'update.gif');
   bar.addItem("[@text "action.delete"/]","multiAction('remove','删除时，会级联删除配置对应的所有菜单,确定删除?')",'delete.gif');
   bar.addHelp();
  </script>
  
 </body>
[#include "/template/foot.ftl"/]
