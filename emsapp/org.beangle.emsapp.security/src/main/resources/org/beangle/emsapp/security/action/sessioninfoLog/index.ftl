[#ftl]
[@b.head/]
[#include "../monitor/nav.ftl"/]
[@b.form target="sessioninfoLogResult"]
<table width="100%" class="searchTable">
	<tr>
		<td>登录名:<input name="sessioninfoLog.username" value="" style="width:100px" maxlength="32"/>
		姓名:<input name="sessioninfoLog.fullname" value="" style="width:100px" maxlength="32"/>
		</td>
		<td>
			用户组名:<input name="groupName" value="" style="width:100px" maxlength="50"/>
		</td>
	</tr>
	<tr>
		<td>[@b.startend name="startTime,endTime" label="登录起始,截止时间" style="width:100px" /]</td>
		<td>ip:<input name="sessioninfoLog.ip" value="" style="width:100px" maxlength="32"/>
			[@b.submit action="!search" value="登录明细"/]
			[@b.submit action="!loginCountStat" value="次数统计"/]
			[@b.submit action="!timeIntervalStat" value="时段统计"/]
			[@b.reset/]
		</td>
	</tr>
</table>
[/@]
[@b.div id="sessioninfoLogResult" /]
[@b.foot/]