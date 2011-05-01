[#ftl]
[#assign scopes={'0':'公开','1':'公有','2':'私有'}/]
[#assign scopeNames={'0':'public','1':'protected','2':'private'}/]
[#assign scopeTitles={'0':'公开资源,不限权限','1':'公有资源,需要登录即可访问','2':'私有资源,需要权限分配'}/]
[#macro resourceScope scope]
	<img src="${b.theme.iconurl('status/'+ scopeNames[scope?string] + '.png')}"/>${scopes[scope?string]}
[/#macro]

[#macro resourceScopeSelect scope]
	<select name="resource.scope" style="width:100px">
	[#if (scope<0)]<option value="">...</option>[/#if]
	[#list 0..2 as i]
	  <option value="${i}" [#if scope=i]selected="selected"[/#if]>[@resourceScope i/]</option>
	[/#list]
	</select>
[/#macro]

[#macro resourceScopeRadio scope]
	[#list 0..2 as i]
	  <input type="radio" name="resource.scope" value="${i}" id="resource_scope${i}" [#if scope=i]checked="checked"[/#if]> <label for="resource_scope${i}">${scopeTitles[i?string]}</label>
	  [#if i_has_next][/#if]
	[/#list]
[/#macro]

[#macro enableInfo enabled]
[#if enabled]<img height="15px" width="15px" src="${b.theme.iconurl('actions/activate.png')}"/>${b.text("action.activate")}[#else]<font color="red"]<img height="15px" width="15px" src="${b.theme.iconurl('actions/freeze.png')}"/>${b.text("action.freeze")}</font>[/#if]
[/#macro]