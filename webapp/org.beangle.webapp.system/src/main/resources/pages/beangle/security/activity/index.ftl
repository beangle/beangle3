[#ftl]
[@b.form target="sessionActivityResult"]
<table width="100%" class="searchTable">
	<tr>
		<td>登录名:<input name="sessionActivity.name" value="" style="width:100px" maxlength="32"/>
		姓名:<input name="sessionActivity.fullname" value="" style="width:100px" maxlength="32"/>
		</td>
		<td>用户身份:
			<select name="sessionActivity.category.id" style="width:100px">
				<option value="">..</option>
				[#list categories as category]
				<option value="${category.id}">${category.name}</option>
				[/#list]
			</select>
			用户组名:<input name="groupName" value="" style="width:100px" maxlength="50"/>
		</td>
	</tr>
	<tr>
		<td>登录起始/截止时间:[@sj.datepicker value="" cssStyle="width:80px"  name="startTime"  buttonImageOnly="true" displayFormat="yy-mm-dd"/]~[@sj.datepicker value="" cssStyle="width:80px"   buttonImageOnly="true"  name="endTime" displayFormat="yy-mm-dd"/]</td>
		<td>ip:<input name="sessionActivity.host" value="" style="width:100px" maxlength="32"/>
		[@b.submit action="!search" value="登录明细"/]
			[@b.submit action="!loginCountStat" value="次数统计"/]
			[@b.submit action="!timeIntervalStat" value="时段统计"/]
		</td>
	</tr>
</table>
[/@]
[@b.div id="sessionActivityResult" /]