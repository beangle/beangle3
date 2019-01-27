[#ftl]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
<!DOCTYPE html>
<html lang="zh_CN">
  <head>
    <title>[#if tag.parameters['title']??]${tag.parameters['title']}[/#if]</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="content-style-type" content="text/css"/>
    <meta http-equiv="content-script-type" content="text/javascript"/>
  [#if tag.loadui]
  [@beangle_uijs_head/]
  [#else]
  [@beangle_js_head/]
  [/#if]
  ${tag.body}
 </head>
 <bodystyle="font-size:13px">
[/#if]

[#macro beangle_uijs_head]
  ${b.script("jquery","jquery.min.js")}
  ${b.script("jquery-ui","js/base/jquery.ui.core.js")}
  ${b.script("jquery-ui","js/plugins/jquery-form.js")}
  ${b.script("jquery-ui","js/plugins/jquery-colorbox.js")}
  ${b.script("jquery-ui","js/plugins/jquery-chosen.js")}
  ${b.script("jquery-ui","js/plugins/jquery.subscribe.js")}
  ${b.script("jquery-ui","js/struts2/jquery.struts2.js")}
  ${b.script("jquery-ui","js/struts2/jquery.ui.struts2.js")}
  ${b.script("bootstrap","js/bootstrap.min.js")}
  ${b.script("bui","js/jquery-history.js")}
  ${b.script("bui","js/beangle.js")}
  ${b.script("bui","js/beangle-ui.js")}
  ${b.script("my97","WdatePicker.js")}
  <script type="text/javascript">
  jQuery(document).ready(function () {
    jQuery.struts2_jquery.version="3.6.1";
    jQuery.scriptPath ="${b.static_url('jquery-ui','')}"
    jQuery.struts2_jquerySuffix = "";
    jQuery.ajaxSettings.traditional = true;
    jQuery.ajaxSetup ({cache: false});});
    beangle.base="${b.static_url('bui','')}"
  </script>
  ${b.css("bootstrap","css/bootstrap.min.css")}
  ${b.css("bootstrap","css/bootstrap-theme.min.css")}
  ${b.css("font-awesome","css/font-awesome.css")}
  ${b.css("jquery-ui","css/jquery-ui.css")}
  ${b.css("jquery-ui","css/jquery.colorbox.css")}
  ${b.css("jquery-ui","css/jquery.chosen.css")}
  ${b.css("bui","css/beangle-ui.css")}
[/#macro]

[#macro beangle_js_head]
  ${b.script("jquery","jquery.min.js")}
  ${b.script("bui","js/beangle.js")}
  ${b.css("bui","css/beangle-ui.css")}
[/#macro]
