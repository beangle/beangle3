[#ftl/][@b.head/]
[#include "../nav.ftl"/]

<table class="indexpanel">
	<tr valign="top">
		<td class="index_view" width="24%">
		[@b.form theme="search" action="!search" target="business_log_content"]
			[@b.textfield label="操作内容" name="log.operation"/]
			[@b.textfield label="用户" name="log.operater"/]
			[@b.textfield label="入口" name="log.entry"/]
			[@b.textfield label="资源" name="log.resource"/]
			[@b.textfield label="ip" name="log.ip"/]
			[@b.startend label="common.beginOn,common.endOn" name="beginDate,endDate"/]
		[/@]
		</td>
		<td class="index_content">
			[@b.div id="business_log_content" href="!search"/]
		</td>
	</tr>
</table>
[@b.foot/]
