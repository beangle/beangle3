[#ftl]
[@b.grid id="listTable" width="100%"  target="ui-tabs-1"]
	[@b.entitybar id="patternBar" title="数据权限 限制模式" entity="pattern" action="restrict-meta.action"  target="ui-tabs-1"]
		bar.addItem("[@b.text "action.info"/]",action.single('patternInfo'));
		bar.addItem("[@b.text "action.add"/]",action.method('editPattern'));
		bar.addItem("[@b.text "action.edit"/]",action.single('editPattern'));
	[/@]
	[@b.gridhead]
		[@b.selectAllTh name="patternId"/]
		[@b.gridth  width="10%" sort="pattern.remark" text="描述" /]
		[@b.gridth  width="70%" sort="pattern.content" text="内容" /]
	[/@]
	[@b.gridbody datas=patterns;pattern]
	  [@b.selectTd name="patternId" value=pattern.id/]
	  <td>${pattern.remark!}</td>
	  <td>${pattern.content!}</td>
	[/@]
[/@]