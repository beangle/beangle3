[#ftl/][@b.head/]

	<table id="bar"></table>
	<table class="infoTable" style="word-break: break-all">
		<tr>
			<td class="title">操作人员：</td>
			<td>${sysLog.user.fullname}</td>
			<td class="title">访问资源路径(URI)：</td>
			<td>${sysLog.URI}</td>
		</tr>
		<tr>
			<td class="title" style="width: 13%">IP：</td>
			<td style="width: 30%">${sysLog.host?default("")}</td>
			<td class="title" style="width: 20%">日志日期：</td>
			<td>${sysLog.time?string('yyyy-MM-dd HH:mm:ss')}</td>
		</tr>
		<tr valign="top">
			<td class="title" >操作内容：</td>
			<td colspan="3" >${sysLog.operation?replace('\n', '<br/>')}</td>
		</tr>
		<tr valign="top">
			<td class="title">操作参数：</td>
			<td colspan="3">${sysLog.params?replace('\n', '<br/>')}<br/></td>
		</tr>
	</table>
	<br/><br/>
	<script type="text/javascript">
		var bar = new ToolBar("bar", "日志详细信息", null, true, true);
		bar.addBack("${b.text("action.back")}");
	</script>

[@b.foot/]
