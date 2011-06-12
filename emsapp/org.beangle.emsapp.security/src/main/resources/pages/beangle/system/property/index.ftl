[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
静态参数
<hr/></div>
[#if staticNames?size=0]
<br>
[#else]
[@b.grid items=staticNames var="name" width="90%"]
	[@b.row]
	 [@b.col title="序号" width="10%"]${name_index+1}[/@]
	 [@b.col title="参数名称" width="20%"]${name}[/@]
	 [@b.col title="参数值" width="25%"]${config.get(name)}[/@]
	[/@]
[/@]
[/#if]

可编辑参数 [@bs.guard res="property"]
[@b.a href="property!bulkEdit" target="editable-properties"]编辑所有[/@]&nbsp;
[@b.a href="property!newConfig" target="editable-properties"]新增配置[/@]
[/@]
<hr/>
[@b.div id="editable-properties" href="!dynaInfo"/]
[@b.foot/]