[#ftl]
[@b.head/]
[#include "nav.ftl"/]
静态参数
<hr/>
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

可编辑参数 [@bs.guard res="property"][@b.a href="property" target="editable-properties"]编辑[/@][/@]
<hr/>
<div id="editable-properties">
[@b.grid width="95%" items=propertyConfigs var="config"]
	[@b.row]
		[@b.col title="序号" width="7%"]${config_index+1}[/@]
		[@b.col title="参数名称" width="20%" property="name" style="text-align:left"/]
		[@b.col title="类型" width="15%" property="type"/]
		[@b.col title="参数值" width="35%" property="value"/]
		[@b.col title="说明" width="15%" property="description"/]
	[/@]
[/@]
</div>
[@b.foot/]