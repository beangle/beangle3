[#ftl/][@b.head/]
<style type="text/css">
	.selectable {
	border:1px solid #000;
	float:left;
	height:15px;
	margin:5px;
	padding:5px;
	width:100px;
	}
	
	.selectablelist {
	border:1px solid #000;
	height:25px;
	margin:10px;
	padding:12px;
	list-style-type: none;
	}
	
	.ui-selected {
	background:#F39814;
	color:#FFF;
	}
	
	.ui-selecting {
	background:#FECA40;
	}
</style>
[#macro codeMatrix coders divId]
<div  id="${divId}" >
<div><img src="${base}/static/icons/beangle/48x48/information.png" width="20px"/>请选择代码.<span id="selectresult_${divId}"/></span></div>
[@sj.div selectableOnStopTopics="onstop_${divId}" selectable="true" cssStyle="loat: left; border-right: 1px dotted #FECA40"]
	[#list coders as coder]
	<div id="${coder.className}" class="selectable ui-corner-all">${coder.name}</div>
	[/#list]
[/@sj.div]
<script type="text/javascript">
$.subscribe('onstop_${divId}', function(event,data) {
	var result = $("#selectresult_${divId}").empty();
	if($(".ui-selected").length==1){
		$(".ui-selected").each(function(){
		className=codeDict[$(this).html()];
		result.append("你选择的是:<em><a target='_blank' href='baseCode!search.action?codeName=" + className + "'>" + $(this).html() + "</a></em>"+'&nbsp;&nbsp;&nbsp;&nbsp;'+'<img valign="bottom" src="${base}/static/images/action/excel.png" title="excel导出"/><a href="baseCode!export.action?codeName='+className+'">导出</a/>');
		});
	}else if($(".ui-selected").length>1){
		result.append("你选择了"+$(".ui-selected").length+"个基础代码。");
		classNames=",";
		$(".ui-selected").each(function(){
			classNames=classNames+codeDict[$(this).html()]+",";
		});
		document.exportForm['codeName'].value=classNames;
		result.append('<img valign="bottom" src="${base}/static/images/action/excel.png" title="excel导出"/><a href="#" onclick="document.exportForm.submit()"/>导出</a/>');
	}
});
</script>
</div>
[/#macro]

[@b.toolbar title="ui.basecode.frame"]
   bar.addHelp();
[/@]

[@sj.tabbedpanel id="basecodeTabs"]
	[@sj.tab label="标准维护" target="newCodeDiv"/]
	[@sj.tab label="学校标准" target="schoolCodeDiv"/]
	[@sj.tab label="国家标准" target="nationCodeDiv"/]
	[@b.div id="newCodeDiv" href="!coderlist"/]
	[@codeMatrix schoolcoders "schoolCodeDiv"/]
	[@codeMatrix nationcoders "nationCodeDiv"/]
	[#list 1..50 as i]<br/>[/#list]
[/@]

[@s.form name="exportForm" action="baseCode!export"]
	<input name="codeName" value="" type="hidden"/>
[/@s.form]

<script language="javascript">
   var codeDict={};
   [#list schoolcoders as coder]
   codeDict['${coder.name}']='${coder.className}';
   [/#list]
   [#list nationcoders as coder]
   codeDict['${coder.name}']='${coder.className}';
   [/#list]
</script>
[@b.foot /]