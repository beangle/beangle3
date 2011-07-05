[#ftl/][@b.head/]
 
	[#assign labInfo]规则详细信息[/#assign]
	[#include "/template/back.ftl"/]
	[#list ruleList as rule]
	 <table class="infoTable">
	   <tr>
		 <td class="title">&nbsp;适用业务:</td>
		 <td class="content">${rule.business}</td>
		 <td class="title">&nbsp;${b.text("common.name")}:</td>
		 <td class="content">${rule.name?if_exists}</td>
	   </tr>
	   <tr>
		 <td class="title">&nbsp;服务名:</td>
		 <td colspan="3" class="content">${rule.serviceName?if_exists}</td>
	   </tr>
	   <tr>
		 <td class="title">&nbsp;规则描述:</td>
		 <td colspan="3" class="content">${rule.description}</td>
	   </tr>
	   <tr>
		 <td class="title">&nbsp;管理容器:</td>
		 <td class="content">${rule.factory}</td>
		 <td class="title">&nbsp;${b.text("common.status")}:</td>
		 <td class="content">
	  		  [#if rule.state?if_exists == true]${b.text("common.yes")}[#else]${b.text("common.no")}[/#if]
		 </td>
	   </tr>
	   <tr>
		<td class="title">&nbsp;${b.text("common.createdAt")}:</td>
		<td class="content">${(rule.createdAt?string("yyyy-MM-dd"))?if_exists}</td>
		<td class="title">&nbsp;${b.text("common.updatedAt")}:</td>
		<td class="content">${(rule.updatedAt?string("yyyy-MM-dd"))?if_exists}</td>
	   </tr>
	  </table>
	  <table class="infoTable">
	   <tr><td colspan="3" align="center">规则参数列表</td></tr>
	   <tr>
		<td align="center">&nbsp;参数名称:</td>
		<td align="center">&nbsp;参数类型:</td>
		<td align="center">&nbsp;上级参数:</td>
	   </tr>
	   [#list rule.params as param]
	   <tr>
		<td align="center">${param.name}</td>
		<td align="center">${param.type}</td>
		<td align="center">${(param.parent.name)?if_exists}</td>
	   </tr>
	   [/#list]
	  </table>
	[/#list]
  
[@b.foot/]
