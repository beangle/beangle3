[#ftl]
[@b.grid id="listTable" width="100%" items=patterns var="pattern" target="ui-tabs-1"]
	[@b.gridbar]
		bar.addItem("${b.text("action.info")}",action.single('patternInfo'));
		bar.addItem("${b.text("action.add")}",action.method('editPattern'));
		bar.addItem("${b.text("action.edit")}",action.single('editPattern'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="remark" name="描述" /]
		[@b.col width="70%" property="content" name="内容" /]
	[/@]
[/@]