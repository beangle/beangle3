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
    <link rel="icon" href="data:;base64,=">
    <base href="${b.base}/"/>
  ${b.css("bootstrap","css/bootstrap.min.css")}
  ${b.css("bui","css/beangle-ui.css")}
  ${b.script("jquery","jquery.min.js")}
  ${b.script("bui","js/beangle.js")}
  ${b.script("bui","js/beangle-ui.js")}

  <script type="text/javascript">
    [#if tag.loadui]
    beangle.amd.register("${b.static_base()}/",{
        "beangle":{js:"bui/0.8.3/js/beangle.js"},
        "bootstrap":{js:"bootstrap/4.6.1/js/bootstrap.bundle.min.js",css:["bootstrap/4.6.1/css/bootstrap.min.css"]},
        "bui":{js:"bui/0.8.3/js/beangle-ui.js",css:["bui/0.8.3/css/beangle-ui.css"]},
        "bui-ajaxchosen":{js:"bui/0.8.3/js/beangle-ui-ajaxchosen.js",deps:["chosen"]},
        "chosen":{js:"chosen/1.8.7/chosen.jquery.js",css:["chosen/1.8.7/chosen.min.css"]},
        "echarts":{js:"echarts/5.3.1/dist/echarts.min.js"},
        "ems-shell":{js:"ems-shell/0.0.5/js/ems-shell-min.js",css:["ems-shell/0.0.5/css/ems-shell-min.css"],deps:["wujie"]},
        "font-awesome":{css:["font-awesome/7.2.0/css/all.min.css"]},
        "fullcalendar":{js:"fullcalendar/5.10.2/main.min.js",css:["fullcalendar/5.10.2/main.min.css"]},
        "fullcalendar-locale":{js:"fullcalendar/5.10.2/locales-all.min.js",deps:["fullcalendar"]},
        "jquery":{js:"jquery/3.6.0/jquery.min.js"},
        "jquery-colorbox":{js:"jquery-colorbox/1.6.4/jquery.colorbox-min.js",css:["jquery-colorbox/1.6.4/example1/colorbox.css"]},
        "jquery-form":{js:"jquery-form/4.2.2/jquery.form.min.js"},
        "jquery-pstrength":{js:"bui/0.8.3/js/jquery-pstrength,jquery-pstrength_zh.js",css:["bui/0.8.3/css/jquery.pstrength.css"]},
        "jquery-treetable":{js:"bui/0.8.3/js/jquery-treetable.js",css:["bui/0.8.3/css/jquery.treetable.css"]},
        "jquery-ui":{js:"jquery-ui/1.13.1/jquery-ui.min.js",css:["jquery-ui/1.13.1/jquery-ui.min.css"]},
        "jquery-validity":{js:"bui/0.8.3/js/jquery-validity.js",css:["bui/0.8.3/css/jquery.validity.css"]},
        "jsrender":{js:"jsrender/1.0.2/jsrender.js"},
        "kindeditor":{js:"kindeditor/4.1.12/kindeditor-all-min,lang/zh-CN.js",css:["kindeditor/4.1.12/themes/default/default.css"]},
        "my97":{js:"my97/4.8.5/WdatePicker.js"},
        "popperjs":{js:"popper.js/1.16.0/dist/umd/popper.js"},
        "wujie":{js:"wujie/2.1.0/index.js"}
    });
    beangle.require(["jquery-form","font-awesome"])
    beangle.require(["jquery-ui","chosen","bui-ajaxchosen","jquery-colorbox","my97"]);
  [/#if]
  </script>
  [@include_if_exists path="head_ext.ftl"/]
  ${tag.body}
 </head>
 <body class="text-sm">
[/#if]
