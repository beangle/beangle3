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
	[@sj.head /]
	<link id="beangle_theme_link" href="${base}/static/themes/${b.theme.ui}/beangle-ui.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-2.4.1.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-2.4.1.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script>
${tag.body}
</head>
<body>
[/#if]