[#ftl]
<div id="t2">
[#if sql_history?size!=0]<a href="${base}/database/sql!history.action?clear=1"]清除历史</a>[#else]没有历史[/#if]
[#list sql_history! as his_sql]
 <li>${his_sql}</li>
[/#list]
</div>
