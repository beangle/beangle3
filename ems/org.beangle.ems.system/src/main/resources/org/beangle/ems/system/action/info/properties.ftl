[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[@b.tabs style="width: 90%"]
	[@b.tab label="java"]
		<table >
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			[#list javaProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${javaProps[key]}</td>
			</tr>
			[/#list]
		</table>
	[/@]
	[@b.tab label="os"]
		<table>
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			[#list osProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${osProps[key]}</td>
			</tr>
			[/#list]
		</table>
	[/@]
	[@b.tab label="user"]
		<table>
			<tr class="thead">
				<td>系统属性</td>
				<td>值</td>
			</tr>
			[#list userProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${userProps[key]}</td>
			</tr>
			[/#list]
		</table>	
	[/@]
	[@b.tab label="extra"]
		<table>
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			[#list extraProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${extraProps[key]}</td>
			</tr>
			[/#list]
		</table>
	[/@]
[/@]
[@b.foot/]