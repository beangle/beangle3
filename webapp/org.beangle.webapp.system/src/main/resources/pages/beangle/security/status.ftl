<#macro enableInfo enabled>
<#if enabled><img height="15px" width="15px" src="${base}/static/icons/beangle/16x16/actions/activate.png"/><@text name="action.activate" /><#else><font color="red"><img height="15px" width="15px" src="${base}/static/icons/beangle/16x16/actions/freeze.png"/><@text name="action.freeze"/></font></#if>
</#macro>

<#macro shortEnableInfo enabled>
<#if enabled><img height="15px" width="15px" src="${base}/static/icons/beangle/16x16/actions/activate.png" title="<@text name="action.activate" />"/><#else><img height="15px" width="15px" src="${base}/static/icons/beangle/16x16/actions/freeze.png" title="<@text name="action.freeze"/>"/></#if>
</#macro>