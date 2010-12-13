[#ftl]
<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title>权限系统</title>
  <link href="${base}/static/themes/default/beangle-ui.css" rel="stylesheet" type="text/css">
 </head>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/beangle.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/beangle-ui.js"></script>

 [#if Session['WW_TRANS_I18N_LOCALE']??]
 [#assign language= Session['WW_TRANS_I18N_LOCALE'].language]
 [#else]
 [#assign language="zh"]
 [/#if]
 [#macro i18nName(entity)][#if language?index_of("en")!=-1][#if entity.engName!?trim==""]${entity.name!}[#else]${entity.engName!}[/#if][#else][#if entity.name!?trim!=""]${entity.name!}[#else]${entity.engName!}[/#if][/#if][/#macro]
 [#macro localAttrName(entityName)][#if language?index_of("en")!=-1]#{entityName}.engName[#else]${entityName}.name[/#if][/#macro]
 [#macro yesOrNoOptions(selected)]
 	<option value="0" [#if "0"==selected] selected [/#if]>[@msg.text name="common.no" /]</option> 
    <option value="1" [#if "1"==selected] selected [/#if]>[@msg.text name="common.yes" /]</option> 
    <option value="" [#if ""==selected] selected [/#if]>[@msg.text name="common.all" /]</option> 
 [/#macro]
 [#macro eraseComma(nameSeq)][#if (nameSeq?length>2)]${nameSeq[1..nameSeq?length-2]}[#else]${nameSeq}[/#if][/#macro]
 [#macro getBeanListNames(beanList)][#list beanList as bean]${bean.name}[#if bean_has_next] [/#if][/#list][/#macro]
 
 [#function sort_byI18nName entityList]   
   [#return sort_byI18nNameWith(entityList,"")]
 [/#function]
 
 [#function sort_byI18nNameWith entityList nestedAttr]
   [#local name="name"]
   [#if nestedAttr!=""]
      [#local name=[nestedAttr,name]/]
   [/#if]
   [#return entityList?sort_by(name)]
 [/#function] 

 [#macro text name][@msg.text name/][/#macro]
 [#macro getMessage][@s.actionmessage theme="beangle"/][@s.actionerror theme="beangle"/][/#macro]
 [#macro searchParams]<input name="params" type="hidden" value="${Parameters['params']!('')}"][/#macro]
 <script>
 if (window.addEventListener) {window.addEventListener("load", adaptFrameSize, false);}else if (window.attachEvent) {window.attachEvent("onload", adaptFrameSize);}else {window.onload = adaptFrameSize;}
 </script>
