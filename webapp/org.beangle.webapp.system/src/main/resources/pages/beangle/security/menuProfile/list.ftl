[#ftl]
[@b.xhtmlhead/]

[@b.entitybar id="menubar" title="系统菜单配置管理" entity="menuProfile" action="menu-profile.action"]
bar.addItem("[@b.text "action.new"/]",action.add());
bar.addItem("[@b.text "action.edit"/]",action.edit());
bar.addItem("[@b.text "action.delete"/]",action.remove('删除时，会级联删除配置对应的所有菜单,确定删除?'));
bar.addHelp();
[/@]
[@b.grid width="100%" id="listTable" ]
   [@b.gridhead]
	 [@b.selectAllTh name="menuProfileId"/]
	 [@b.gridth text="名称" /]
	 [@b.gridth text="使用的用户类别" /]
   [/@]
   [@b.gridbody datas=menuProfiles;menuProfile]
	 [@b.selectTd name="menuProfileId" value="${menuProfile.id}"/]
	 <td>&nbsp;${menuProfile.name}</td>
	 <td>[#if menuProfile.category??]${menuProfile.category.name}[/#if]</td>
   [/@]
 [/@]
 
[#include "/template/foot.ftl"/]
