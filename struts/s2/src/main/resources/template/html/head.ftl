[#ftl]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
<!DOCTYPE html>
<html lang="zh_CN">
<head>
  <title>[#if tag.parameters['title']??]${tag.parameters['title']}[/#if]</title>
  <meta http-equiv="content-type" content="text/html;charset=utf-8" />
  <meta http-equiv="pragma" content="no-cache"/>
  <meta http-equiv="cache-control" content="no-cache"/>
  <meta http-equiv="expires" content="0"/>
  <meta http-equiv="content-style-type" content="text/css"/>
  <meta http-equiv="content-script-type" content="text/javascript"/>
  [@b.agent/]
  [#if tag.loadui]
  [@beangle_uijs_head/]
  [#else]
  [@beangle_js_head/]
  [/#if]
${tag.body}
</head>
<body>
[/#if]

[#macro beangle_uijs_head]
  ${b.script("jquery","jquery.min.js")}
  ${b.script("bui","js/jquery-form.js")}
  ${b.script("bui","js/jquery-history.js")}
  ${b.script("bui","js/beangle.js")}
  ${b.script("bui","js/beangle-ui.js")}
  ${b.script("my97","WdatePicker.js")}
  ${b.script("bui","js/jquery-colorbox.js")}
  ${b.script("bui","js/jquery-chosen.js")}
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery.ui.core.js?bg=3.5.0&compress=no"></script>
  <script type="text/javascript" src="${base}/static/js/plugins/jquery.subscribe,/js/struts2/jquery.struts2,jquery.ui.struts2.js?bg=3.5.0&compress=no"></script>
  ${b.script("bootstrap","js/bootstrap.min.js")}
  <script type="text/javascript">
  var App = {contextPath:"${base}"};
  beangle.base='${b.static_base()}/bui/0.0.5';
  beangle.renderAs("struts");

  jQuery(document).ready(function () {
    jQuery.struts2_jquery.version="3.6.1";
    jQuery.scriptPath = App.contextPath+"/static/";
    jQuery.struts2_jquerySuffix = "";
    jQuery.ajaxSettings.traditional = true;
    jQuery.ajaxSetup ({cache: false});});
    </script>
  <link id="jquery_theme_link" rel="stylesheet" href="${base}/static/themes/smoothness/jquery-ui.css?s2j=3.7.1" type="text/css"/>
    ${b.css("bootstrap","css/bootstrap.min.css")}
  ${b.css("bootstrap","css/bootstrap-theme.min.css")}
  ${b.css("bui","css/beangle-ui.css")}
  ${b.css("bui","css/colorbox.css")}
  ${b.css("bui","css/chosen.css")}

[/#macro]

[#macro beangle_js_head]
  ${b.script("jquery","jquery.min.js")}
  ${b.script("bui","js/beangle.js")}
  ${b.css("bui","css/beangle-ui.css")}
[/#macro]
