[#ftl]
[@b.head/]
[#include "nav.ftl"/]
<style>
.c{
	text-align: center;
}
.r{
	text-align: right;
}
.l{
	text-align: left;
}
.t{
	vertical-align: top;
}
.b{
	vertical-align: bottom;
}
.odd{
	background-color: #D4D4D4;
}
.even{
	background-color: #EEEEEE;
}
.bd1{
	border: solid #888888 1px;
}
.bg1{
	background-color: #CCCCCC;
}
.bg2{
	background-color: #DDDDDD;
}
</style>

	<p/>
	[@b.a href="!index"]Reload[/@] |
	[@b.a href="!index?do=${active?string('deactivate','activate')}"]${active?string('Deactivate','Activate')}[/@] | [@b.a href="!index?do=clear"]CLEAR[/@]
	<p/>
	Last update: ${lastUpdate?string("dd.MM.yy HH:mm:ss")}<br/>
	Activation: ${(activation?string("dd.MM.yy HH:mm:ss"))!}<br/>
	Deactivation: ${(deactivation?string("dd.MM.yy HH:mm:ss"))!}<br/>
	Active duration: [#if activation??][#if deactivation??]${deactivation.time-activation.time}[/#if]${lastUpdate.time-activation.time}[/#if]
	<p/>
	[#if generalStatistics?size>0]
	<table>
		<tr>
			<th class="c bd1 bg1">Connects</th>
			<td>${generalStatistics[0]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Flushes</th>
			<td>${generalStatistics[1]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Prepare statements</th>
			<td>${generalStatistics[2]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Close statements</th>
			<td>${generalStatistics[3]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Session opens</th>
			<td>${generalStatistics[5]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Session closes</th>
			<td>${generalStatistics[4]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Total Transactions</th>
			<td>${generalStatistics[6]}</td>
		</tr>
		<tr>
			<th class="c bd1 bg1">Successfull Transactions</th>
			<td>${generalStatistics[7]}</td>
		</tr>
			<th class="c bd1 bg1">Optimistic failures</th>
			<td>${generalStatistics[8]}</td>
		</tr>
	</table>
[/#if]
[@b.foot/]