[#ftl]
[#macro enableInfo enabled]
[#if enabled]<img height="15" width="15" src="${b.theme.iconurl('actions/activate.png')}" alt="activate"/>${b.text("action.activate")}[#else]<img height="15" width="15" src="${b.theme.iconurl('actions/freeze.png')}" alt="freezen"/><em>${b.text("action.freeze")}</em>[/#if]
[/#macro]

[#macro shortEnableInfo enabled]
[#if enabled]<img height="15" width="15" src="${b.theme.iconurl('actions/activate.png')}" alt="activate" title="${b.text("action.activate")}"/>[#else]<img height="15" width="15" src="${b.theme.iconurl('actions/freeze.png')}" alt="freezen" title="${b.text("action.freeze")}"/>[/#if]
[/#macro]