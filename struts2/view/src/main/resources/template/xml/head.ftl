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
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery-1.10.2.min.js,jquery.ui.core-1.10.3.min.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-form-3.38.0.min.js,jquery-history-1.8.0.min.js,jquery-colorbox-1.4.27.min.js,jquery-chosen-1.0.0.min.js"></script>
  <script type="text/javascript" src="${base}/static/js/plugins/jquery.subscribe.min.js,/js/struts2/jquery.struts2.min.js,/js/struts2/jquery.ui.struts2.min.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/beangle/beangle-3.3.3.min.js,beangle-ui-3.3.3.min.js"></script>
[#else]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery-1.10.2.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery.ui.core-1.10.3.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-form-3.38.0.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-history-1.8.0.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-colorbox-1.4.27.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/plugins/jquery-chosen-1.0.0.js"></script>
  <script type="text/javascript" src="${base}/static/js/plugins/jquery.subscribe.js,/js/struts2/jquery.struts2.js,/js/struts2/jquery.ui.struts2.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/beangle/beangle-3.3.3.js"></script>
  <script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-3.3.3.js"></script>
[/#if]
  <script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script>
  <script type="text/javascript">jQuery(document).ready(function () {jQuery.struts2_jquery.version="3.6.1";jQuery.scriptPath = "${base}/static/";jQuery.struts2_jquery.minSuffix = "";jQuery.ajaxSettings.traditional = true;jQuery.ajaxSetup ({cache: false});});</script>
  <link id="jquery_theme_link" rel="stylesheet" href="${base}/static/themes/smoothness/jquery-ui.css?s2j=3.6.1" type="text/css"/>
  <link id="beangle_theme_link" href="${base}/static/themes/${b.theme.name}/beangle-ui.css,colorbox.css,chosen.css" rel="stylesheet" type="text/css" />
[/#macro]

[#macro beangle_js_head]
[#if tag.compressed]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery-1.10.2.min.js,/scripts/beangle/beangle-3.3.3.min.js"></script>
  <link id="beangle_theme_link" rel="stylesheet" href="${base}/static/themes/${b.theme.name}/beangle-ui.css" type="text/css"/>
[#else]
  <script type="text/javascript" src="${base}/static/scripts/jquery/jquery-1.10.2.js,/scripts/beangle/beangle-3.3.3.js"></script>
  <link id="beangle_theme_link" href="${base}/static/themes/${b.theme.name}/beangle-ui.css" rel="stylesheet" type="text/css" />
[/#if]
[/#macro]
