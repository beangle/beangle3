[#ftl]
[@b.xhtmlhead/]
<body >

<table id="menuBar"></table>
[@b.grid width="100%" id="listTable" ]
	[@b.entitybar id="menubar" title="系统菜单配置管理" entity="menuProfile" action="menu-profile.action"]
	bar.addItem("[@b.text "action.new"/]",action.add());
	bar.addItem("[@b.text "action.edit"/]",action.edit());
	bar.addItem("[@b.text "action.delete"/]",action.remove('删除时，会级联删除配置对应的所有菜单,确定删除?'));
	bar.addHelp();
	[/@]
   [@b.gridhead]
	 [@b.selectAllTh name="menuProfileId"/]
	 [@b.gridth text="名称" /]
	 [@b.gridth text="使用的用户类别" /]
   [/@]
   [@b.gridbody datas=menuProfiles;menuProfile]
	 [@b.selectTd name="menuProfileId" value="${menuProfile.id}"/]
	 <td><a href="user.action?method=info&user.id=${menuProfile.id}" >&nbsp;${menuProfile.name} </a></td>
	 <td>[#if menuProfile.category??]${menuProfile.category.name}[/#if]</td>
   [/@]
 [/@]
 </body>
[#include "/template/foot.ftl"/]
