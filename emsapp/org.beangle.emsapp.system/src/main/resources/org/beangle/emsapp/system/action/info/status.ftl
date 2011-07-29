[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
	<em>摘要</em>
	<hr>
	<table width="100%">
		<tr>
			<td>名称:</td><td>${runtimeMBean.name}</td><td>虚拟机名称:</td><td>${runtimeMBean.vmName}</td>
		</tr>
		<tr>
			<td>OS名称:</td><td>${osMBean.name} ${osMBean.version}</td><td>体系结构:</td><td>${osMBean.arch}</td>
		</tr>
		<tr>
			<td>处理器个数:</td><td>${osMBean.availableProcessors}</td><td>系统负载:</td><td>${osMBean.systemLoadAverage!}</td>
		</tr>
		<tr>
			<td>${b.text("server.info")}:</td><td>${serverProps["server.info"]}</td><td>协议/端口:</td><td>${serverProps["server.protocol"]} ${serverProps["server.port"]}</td>
		</tr>
		<tr>
			<td>${b.text("user.dir")}:</td><td colspan="3">${serverProps["user.dir"]}</td>
		</tr>
		<tr>
			<td>${b.text("server.path")}:</td><td colspan="3">${serverProps["server.path"]}</td>
		</tr>
		<tr>
			<td>启动于:</td><td>${upAt?string("yyyy-MM-dd HH:mm:ss")}(当前:${b.now?string("yyyy-MM-dd HH:mm:ss")})</td><td>运行时间:</td><td>[#assign upsecond=runtimeMBean.uptime/1000?int][#if (upsecond>3600)]${(upsecond/3600)?int}小时[/#if]${((upsecond%3600)/60)?int}分${(upsecond%60)}秒</td>
		</tr>
	</table>
	<br/>
	<em>内存使用(单位:MB)</em>
	<hr>
	<div  class="ui-widget ui-widget-content ui-corner-all" id="progressbar">
	<table width="100%" style="border-collapse:collapse">
	<tr>
	<td style="background-color:yellow">used:${(TotalMem-FreeMem)/1024/1024}</td>
	<td style="width: ${(FreeMem/MaxMem)?int}%;background-color:#40AA53">free:${FreeMem/1024/1024}</td>
	<td style="width: ${100-(TotalMem/MaxMem)?int}%;background-color:#5C9CCC">unallocated:${(MaxMem-TotalMem)/1024/1024}</td>
	</tr></table>
	</div>
	<table width="100%">
		<tr>
			<td colspan="6">堆区最大:${MaxMem/1024/1024}MB&nbsp;&nbsp;空闲:${FreeMem/1024/1024}MB&nbsp;&nbsp;共计:${TotalMem/1024/1024}MB&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td>内存区名称:</td>
			<td>类型:</td>
			<td>使用:</td>
			<td>初始:</td>
			<td>最大:</td>
		</tr>
		[#list memPoolMBeans as memPoolMBean]
		<tr>
			<td>${memPoolMBean.name}</td>
			<td>${memPoolMBean.type}</td>
			<td>${memPoolMBean.usage.used/1024/1024}</td>
			<td>${memPoolMBean.usage.init/1024/1024}</td>
			<td>${memPoolMBean.usage.max/1024/1024}</td>
		</tr>
		[/#list]
	</table>
	<br/>
	<em>线程</em>
	<hr>
	<table width="100%">
		<tr>
			<td>当前线程数:${threadMBean.threadCount}</td><td>线程数峰值:${threadMBean.peakThreadCount}</td>
			<td>共启动线程:${threadMBean.totalStartedThreadCount}</td><td>守护线程数:${threadMBean.daemonThreadCount}</td>
		</tr>
	</table>
	<br/>
	<em>运行时</em>
	<hr>
	<table width="100%">
		<tr>
			<td>虚拟机参数:</td><td colspan="3">[#list runtimeMBean.inputArguments as inputArgument]${inputArgument}[#if inputArgument_has_next]<br/>[/#if][/#list]</td>
		</tr>
		<tr>
			<td>启动类路径:</td><td colspan="3">${runtimeMBean.bootClassPath?replace(":","<br/>:")!}</td>
		</tr>
	</table>
[@b.foot/]