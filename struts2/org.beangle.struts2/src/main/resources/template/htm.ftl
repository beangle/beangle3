[#ftl]
[#if (Session['WW_TRANS_I18N_LOCALE'])??]
[#assign language= Session['WW_TRANS_I18N_LOCALE'].language]
[#else]
[#assign language="zh"]
[/#if]

[#macro i18nName(entity)][#if language?index_of("en")!=-1][#if entity.engName!?trim==""]${entity.name!}[#else]${entity.engName!}[/#if][#else][#if entity.name!?trim!=""]${entity.name!}[#else]${entity.engName!}[/#if][/#if][/#macro]

[#macro i18nSelect datas selected  extra...]
  <select [#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list]>
    [#nested]
    [#list datas as data]
       <option value="${data.id}" [#if data.id?string=selected]selected[/#if]>[@i18nName data/]</option>
    [/#list]
  </select>
[/#macro]

[#macro radio2 name value]
  <input type="radio" value="1" name="${name}" [#if value]checked[/#if] >[@msg.text name="common.yes" /]
  <input type="radio" value="0" name="${name}" [#if (!value)]checked[/#if] >[@msg.text name="common.no" /]
[/#macro]

[#macro select2 name selected hasAll extra...]
  <select name="${name}" [#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list]>
    [#if hasAll]<option value="" [#if selected='']selected[/#if]>[@msg.text name="common.all" /]</option>[/#if]
    <option value="1" [#if selected='1']selected[/#if]>[@msg.text name="common.yes" /]</option>
    <option value="0" [#if selected='0']selected[/#if]>[@msg.text name="common.no" /]</option>
  </select>
[/#macro]

[#macro queryStr]
[#list Parameters?keys as key][#if (Parameters[key]?length>0)&&key!="method"]&${key}=${Parameters[key]?js_string}[/#if][/#list][#rt/]
[/#macro]

<!--name action-->
[#macro actionForm entity name action extra...]
	<form name="${name}" id="${name}" method="post" [#list extra?keys as attr]${attr}="${extra[attr]?html}" [/#list]>
	 [#nested]
	</form>
	<script>
	entityActions['${name}']=new EntityAction('${name}','${entity}','${action}','[@queryStr/]');
	var form=document.${name};
	</script>
[/#macro]

