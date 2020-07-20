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
  ${b.script("requirejs","require.js")}
  ${b.script("jquery","jquery.min.js")}
  ${b.script("bui","js/jquery-history.js")}
  ${b.script("bui","js/beangle.js")}
  ${b.script("bui","js/beangle-ui.js")}
  ${b.css("bui","css/beangle-ui.css")}
  <script type="text/javascript">
    [#if tag.loadui]
    beangle.register("${b.static_base()}/",{
        "adminlte":{js:"AdminLTE/3.0.5/dist/js/adminlte.min.js",css:["AdminLTE/3.0.5/dist/css/adminlte.min.css"]},
        "beangle":{js:"bui/0.3.0/js/beangle.js"},
        "bootstrap":{js:"bootstrap/4.5.0/js/bootstrap.bundle.min.js",css:["bootstrap/4.5.0/css/bootstrap.min.css"]},
        "bui":{js:"bui/0.3.0/js/beangle-ui.js",css:["bui/0.3.0/css/beangle-ui.css"]},
        "bui-ajaxchosen":{js:"bui/0.3.0/js/beangle-ui-ajaxchosen.js",deps:["chosen"]},
        "bui-tabletree":{js:"bui/0.3.0/js/beangle-ui-tabletree.js",css:["bui/0.3.0/css/beangle-ui-tabletree.css"]},
        "chosen":{js:"chosen/1.8.7/chosen.jquery.min.js",css:["chosen/1.8.7/chosen.min.css"]},
        "font-awesome":{css:["font-awesome/5.13.0/css/all.min.css"]},
        "jquery":{js:"jquery/3.5.1/jquery.min.js"},
        "jquery-colorbox":{js:"jquery-colorbox/1.6.4/jquery.colorbox-min.js",css:["jquery-colorbox/1.6.4/example1/colorbox.css"]},
        "jquery-form":{js:"jquery-form/4.2.2/jquery.form.min.js"},
        "jquery-history":{js:"bui/0.3.0/js/jquery-history.js"},
        "jquery-pstrength":{js:"bui/0.3.0/js/jquery-pstrength,jquery-pstrength_zh.js",css:["bui/0.3.0/css/jquery.pstrength.css"]},
        "jquery-treetable":{js:"bui/0.3.0/js/jquery-treetable.js",css:["bui/0.3.0/css/jquery.treetable.css"]},
        "jquery-ui":{js:"jquery-ui/1.12.1/jquery-ui.min.js",css:["jquery-ui/1.12.1/jquery-ui.min.css"]},
        "jquery-validity":{js:"bui/0.3.0/js/jquery-validity.js",css:["bui/0.3.0/css/jquery.validity.css"]},
        "kindeditor":{js:"kindeditor/4.1.12/kindeditor-all-min,lang/zh-CN.js",css:["kindeditor/4.1.12/themes/default/default.css"]},
        "my97":{js:"my97/4.8/WdatePicker.js"},
        "popperjs":{js:"popper.js/1.16.0/dist/umd/popper.js"},
        "requirejs":{js:"requirejs/2.3.6/require.js"},
        "sj-jquery":{js:"struts2-jquery/3.6.1/js/struts2/jquery.struts2.js",deps:["sj-jquery-subscribe"]},
        "sj-jquery-subscribe":{js:"struts2-jquery/3.6.1/js/plugins/jquery.subscribe.js"},
        "sj-jquery-ui":{js:"struts2-jquery/3.6.1/js/struts2/jquery.ui.struts2.js",deps:["sj-jquery"]}
    });
    bg.load(["jquery-form","bootstrap","font-awesome","adminlte"])
    bg.load(["jquery-ui","chosen","jquery-colorbox","my97"]);
    bg.load(["sj-jquery-subscribe","sj-jquery","sj-jquery-ui"],function (){
        jQuery.struts2_jquery.version="3.6.1";
        jQuery.scriptPath ="${b.static_base()}/struts2-jquery/3.6.1/"
        jQuery.struts2_jquerySuffix = "";
        jQuery.ajaxSettings.traditional = true;
        jQuery.ajaxSetup ({cache: false});
     });
  [/#if]
   beangle.getContextPath=function(){
      return "${base}";
   }
   var App = {contextPath:'${base}'}
  </script>
  [@include_if_exists path="head_ext.ftl"/]
  ${tag.body}
 </head>
 <body style="font-size:13px">
[/#if]
