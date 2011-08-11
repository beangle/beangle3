[#ftl]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
[#--<?xml version="1.0" encoding="UTF-8" ?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">--]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="${locale.language}" xml:lang="${locale.language}">
<head>
	<title>[#if tag.parameters['title']??]${tag.parameters['title']} - [/#if]${(systemVersion.name)!} ${(systemVersion.version)!}</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="content-style-type" content="text/css"/>
	<meta http-equiv="content-script-type" content="text/javascript"/>
	[@beangle_js_head compressed=(Parameters['devMode']?exists)?string("false","true") /]
	<script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script>
${tag.body}
</head>
<body>
[/#if]

[#macro beangle_js_head(compressed)]
[#if compressed=="false"]
	[@sj.head  compressed="false"/]
	<link id="beangle_theme_link" href="${base}/static/themes/${b.theme.ui}/beangle-ui.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/themes/${b.theme.ui}/colorbox.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/static/scripts/colorbox/jquery-colorbox-1.3.17.1.js"></script>
	<script type="text/javascript">if ( typeof window.JSON === 'undefined' ) { document.write('<script src="${base}/static/scripts/history/json2.js"><\/script>'); }</script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.adapter.jquery.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.html4.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-2.4.2.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-2.4.2.js"></script>
[#else]
	[@sj.head/]
	<link id="beangle_theme_link" href="${base}/static/themes/${b.theme.ui}/beangle-ui.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/themes/${b.theme.ui}/colorbox.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/static/scripts/colorbox/jquery-colorbox-1.3.17.1.min.js"></script>
	<script type="text/javascript">if ( typeof window.JSON === 'undefined' ) { document.write('<script src="${base}/static/scripts/history/json2.min.js"><\/script>'); }</script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.adapter.jquery.min.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.min.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.html4.min.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-2.4.2.min.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-2.4.2.min.js"></script>
[/#if]
[/#macro]
