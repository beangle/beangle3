[#ftl]
[@b.grid   items=patterns var="pattern"]
	[@b.gridbar]
		bar.addItem("${b.text("action.add")}",action.method('editPattern'));
		bar.addItem("${b.text("action.edit")}",action.single('editPattern'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="remark" title="描述" /]
		[@b.col width="70%" property="content" title="内容" /]
		[@b.col width="20%" property="entity.name" title="entity.restrictEntity"/]
	[/@]
[/@]