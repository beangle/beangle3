[#ftl]
[#macro enableInfo enabled]
[#if enabled]<img height="15" width="15" src="${base}/static/icons/beangle/16x16/actions/activate.png" alt="activate"/>[@b.text name="action.activate" /][#else]<img height="15" width="15" src="${base}/static/icons/beangle/16x16/actions/freeze.png" alt="freezen"/><em>[@b.text name="action.freeze"/]</em>[/#if]
[/#macro]

[#macro shortEnableInfo enabled]
[#if enabled]<img height="15" width="15" src="${base}/static/icons/beangle/16x16/actions/activate.png" alt="activate" title="[@b.text name="action.activate" /]"/>[#else]<img height="15" width="15" src="${base}/static/icons/beangle/16x16/actions/freeze.png" alt="freezen" title="[@b.text name="action.freeze"/]"/>[/#if]
[/#macro]