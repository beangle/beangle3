[#ftl/][@b.head/]
[#include "../nav.ftl"/]
[@b.form name="codeMetaListForm" ]
	[@b.grid items=codeMetas var="codeMeta" id="coderListGrid" filterable="true"]
		[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		[/@]
		[@b.row]
			[@b.boxcol/]
			[@b.col width="18%" property="name" title="common.name" /]
			[@b.col width="18%" property="title" title="common.title"/]
			[@b.col width="60%" property="className" title="实体类名" /]
		[/@]
	[/@]
[/@]
[@b.foot /]