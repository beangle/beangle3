[#ftl]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
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
[#if tag.compressed]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery,jquery.ui.core.js?bj=3.3.4"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-form,jquery-history,jquery-colorbox,jquery-chosen.js?bj=3.3.4"></script>
  <script type="text/javascript" src="${base}/static/js/plugins/jquery.subscribe,/js/struts2/jquery.struts2,jquery.ui.struts2.js?bj=3.3.4"></script>
  <script type="text/javascript" src="${base}/static/scripts/beangle/beangle,beangle-ui.js?bj=3.3.4"></script>
[#else]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery.ui.core.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-form.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-history.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-colorbox.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-chosen.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/js/plugins/jquery.subscribe,/js/struts2/jquery.struts2,jquery.ui.struts2.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/beangle/beangle.js?bj=3.3.4&compress=no"></script>
  <script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui.js?bj=3.3.4&compress=no"></script>
[/#if]
  <script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js?compress=no"></script>
  <script type="text/javascript">jQuery(document).ready(function () {jQuery.struts2_jquery.version="3.6.1";jQuery.scriptPath = "${base}/static/";jQuery.struts2_jquerySuffix = "";jQuery.ajaxSettings.traditional = true;jQuery.ajaxSetup ({cache: false});});</script>
  <link id="jquery_theme_link" rel="stylesheet" href="${base}/static/themes/smoothness/jquery-ui.css?s2j=3.6.1" type="text/css"/>
  <link id="beangle_theme_link" href="${base}/static/themes/${b.theme.name}/beangle-ui,colorbox,chosen.css" rel="stylesheet" type="text/css" />
[/#macro]

[#macro beangle_js_head]
[#if tag.compressed]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery,/scripts/beangle/beangle.js"></script>
  <link id="beangle_theme_link" rel="stylesheet" href="${base}/static/themes/${b.theme.name}/beangle-ui.css" type="text/css"/>
[#else]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery,/scripts/beangle/beangle.js?compress=no"></script>
  <link id="beangle_theme_link" href="${base}/static/themes/${b.theme.name}/beangle-ui.css?compress=no" rel="stylesheet" type="text/css" />
[/#if]
[/#macro]
