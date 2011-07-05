[#ftl/][@b.head/]
[#include "../nav.ftl"/]

<table class="indexpanel">
	<tr>
	<td width="20%" class="index_view">
	[@b.form theme="search" action="!search" target="rule_content"]
		[@b.textfield label="common.name" name="rule.name"/]
		[@b.textfield label="适用业务"  name="rule.business"/]
		[@b.field label="common.status"]
		<select name="rule.enabled" style="width:100px;" value="${Parameters["rule.enabled"]?if_exists}">
			<option value="1" selected>${b.text("common.enabled")}</option>
			<option value="0" >${b.text("common.disabled")}</option>
		</select>
		[/@]
	[/@]
	</td>
	<td class="index_content">
		[@b.div id="rule_content" href="!search"/]
	</td>
	</tr>
</table>
[@b.foot/]
