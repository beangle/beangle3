[#ftl]
[@s.form name="sessionActivityForm" theme="simple"]
<table width="100%" class="searchTable">
	<tr>
		<td>登录名:</td>
		<td><input name="sessionActivity.name" value="" style="width:100px" maxlength="32"/></td>
		<td>姓名:</td>
		<td><input name="sessionActivity.fullname" value="" style="width:100px" maxlength="32"/></td>
		<td>用户身份:</td>
		<td>
			<select name="sessionActivity.category.id" style="width:100px">
				<option value="">..</option>
				[#list categories as category]
				<option value="${category.id}">${category.name}</option>
				[/#list]
			</select>
		</td>
		<td>用户组名:</td>
		<td><input name="groupName" value="" style="width:100px" maxlength="50"/></td>
	</tr>
	<tr>
		<td>登录起始时间:</td>
		<td>[@sj.datepicker value="" cssStyle="width:80px"  name="startTime" displayFormat="yy-mm-dd"/]</td>
		<td>登录截止时间:</td>
		<td>[@sj.datepicker value="" cssStyle="width:80px"  name="endTime" displayFormat="yy-mm-dd"/]</td>
		<td>ip:</td>
		<td><input name="sessionActivity.host" value="" style="width:100px" maxlength="32"/></td>
		<td align="center" colspan="10">
			[@sj.submit type="button" targets="sessionActivityResult" href="${base}/security/activity!search.action" label="登录明细"/]
			[@sj.submit type="button" targets="sessionActivityResult" href="${base}/security/activity!loginCountStat.action" label="次数统计"/]
			[@sj.submit type="button" targets="sessionActivityResult" href="${base}/security/activity!timeIntervalStat.action" label="时段统计"/]
		</td>
	</tr>
</table>
[/@]
<div id="sessionActivityResult"></div>