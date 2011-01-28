var action="";
var entity="";

var actionQueryStr = "";

function beforSubmmit(method) {
	var ids = getSelectIds(entity+"Id");
	if (ids == null || ids == "") {
		alert("你没有选择要操作的记录！");
		return false;
	}
	form.action = action + "?method=" + method;
	if(""!=actionQueryStr){
		addHiddens(form,actionQueryStr);
		addParamsInput(form,actionQueryStr);
	}
	return true;
}
function edit() {
	if (beforSubmmit("edit")) {
		submitId(form, entity + 'Id', false);
	}
}

function info(giveId) {
	if(giveId==null){
		if (beforSubmmit("info")) {
			submitId(form, entity + 'Id', false);
		}
	}else{
		form.action = action + "?method=info";
		addInput(form,entity + "Id",giveId, "hidden");
		form.submit();
	}
}
function remove(){
	if (beforSubmmit("remove")) {
		submitId(form,entity + "Id",true,null,"确认操作?");
	}
}
function add(){
	form.action = action + "?method=edit";
	if(""!=actionQueryStr)addHiddens(form,actionQueryStr);
	addInput(form,entity + 'Id',"");
	if(""!=actionQueryStr)addParamsInput(form,actionQueryStr);
	form.submit();
}
function multiAction(method,confirmMsg){
	submitAction(method,true,confirmMsg);
}
function singleAction(method,confirmMsg){
	submitAction(method,false,confirmMsg);
}
function singleAction(method,tar,confirmMsg){
	submitAction(method,false,confirmMsg);
	form.target = tar;
}
function submitAction(method,multiId,confirmMsg){
	if (beforSubmmit(method)) {
		if(null!=confirmMsg){
			if(!confirm(confirmMsg))return;
		}
		submitId(form,entity + "Id",multiId);
	}
}
function exportList(format){
	form.action=action + "?method=export";
	if(null==format){
		format="xls";
	}
	if(!confirm("是否导出查询条件内的所有数据?")) return;
	if (typeof keys != "undefined") {
		addInput(form, "keys", keys);
	}
	if(typeof titles != "undefined"){
		addInput(form,"titles",titles);
	}
	addInput(form,"format",format);
	addHiddens(form,actionQueryStr);
	
	if(typeof configExport =="function"){
		configExport();
	}
	form.submit();
}
function submitActionForm(){
	addHiddens(form,actionQueryStr);
	form.submit();
}
var multiIdAction=multiAction;