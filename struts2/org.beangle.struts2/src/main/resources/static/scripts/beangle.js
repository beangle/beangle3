function getContextPath(){
	return self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)
}
//Box----------------------------------------------------
function toggleCheckBox(chk,event){
	boxAction(chk, "toggle",event);
}

function getRadioValue(radio){
	return boxAction(radio, "value");
}
	
function getCheckBoxValue(chk){
	return boxAction(chk, "value");
}
/**
 * modify by chaostone 2006-8-2
 * 将反选取消,改为全选或者全部取消
 */
function boxAction(box, action,event){
	var val = "";
	if (box){
		if (! box[0]){
			if (action == "selected"){
				return box.checked;
			} else if (action == "value"){
				if (box.checked)
					val = box.value;
			} else if (action == "toggle"){
				var srcElement = getEventTarget(event);
				box.checked = srcElement.checked;
				if(typeof box.onchange =="function"){
					box.onchange();
				}
			}
		} else{
			for (var i=0; i<box.length; i++){
				if (action == "selected"){
					if (box[i].checked)
						return box[i].checked;
				} else if (action == "value"){
					if (box[i].checked){
						if (box[i].type == "radio"){
							val = box[i].value;
						} else if (box[i].type == "checkbox"){
							if (val != "")
								val = val + ",";	
							val = val + box[i].value;
						}
					}
				} else if (action == "toggle"){
				    var srcElement = getEventTarget(event);
					box[i].checked = srcElement.checked;
					if(typeof box[i].onchange =="function"){
						box[i].onchange();
					}
				}
			}
		}
	}

	if (action == "selected")
		return false;
	else
		return val;
}

//Event--------------------------------------------------
function portableEvent(e){
	if(!e){
		return window.event;
	}else{
		return e;
	}
}

/**
 *获得事件背后的元素
 */
function getEventTarget(e){
	e=portableEvent(e);
	if(document.all){
		return e.srcElement;
	}else{
		return e.target;
	}
}

//IFrame--------------------------------------------------------
function adaptFrameSize(){
	f_frameStyleResize(self);
}
/* iframe 页面自适应大小
 * @targObj    iframe
 * @extraHight 
 */
function f_frameStyleResize(targObj,extraHight){
	if(null==targObj || targObj.name=="")
		return;
	if (targObj.parent == window.top) {
		if(targObj.parent.document.body.style.overflowY=="hidden") return;
	}
	var frames = targObj.parent.document.getElementsByName(targObj.name);
	if(frames.length<1) return;
	var targWin=frames[0];
	if(targWin != null) {
		var HeightValue = targObj.document.body.scrollHeight;
		if(null==extraHight)
			extraHight=0;
		targWin.style.height = HeightValue+extraHight;
	}
	f_frameStyleResize(targObj.parent);
}

//About From
function submit(myForm,action,target){
	//FIXME check target is(iframe,reserved,div)
	var submitTarget=(null!=target)?target:myForm.target;
	if(action==null){
		action=myForm.action;
	}
	if(action.indexOf("http://")==0){
		action=action.substring(action.indexOf("/",7));
	}
	if(submitTarget==""||submitTarget=="_blank"||submitTarget=="_self"||submitTarget=="_parent"){
		myForm.target=submitTarget;
		myForm.action=action;
		myForm.submit();
		return;
	}
	//fix myForm without id
	if(null==myForm.id||''==myForm.id){
		myForm.setAttribute("id",myForm.name);
	}
	var sumbitBtnId=myForm.id+"_submit";
	var submitx=document.getElementById(sumbitBtnId);
	
	if(null==submitx){
		if(document.all){
			var inputHTML="<button id='"+sumbitBtnId+"' type='submit' style='display:none'></button>";
			submitx = myForm.document.createElement(inputHTML);
		}else{
			submitx = document.createElement('button');
			submitx.setAttribute("id",sumbitBtnId);
			submitx.setAttribute("type",'submit');
			submitx.setAttribute("style",'display:none');
		}
		myForm.appendChild(submitx);
	}

	var options_submit = {};
	options_submit.jqueryaction = "button";
	options_submit.id = sumbitBtnId;
	options_submit.targets = submitTarget;
	options_submit.href = action;
	options_submit.formids = myForm.id;
	if (typeof $ != "undefined") {
		$.struts2_jquery.bind($('#'+sumbitBtnId), options_submit);
	}
	//myForm.submit();
	submitx.click();
}

/**
 * 提交要求含有id的表单
 * @param form 带提交的表单
 * @param id 要提交id的名称
 * @param isMulti(可选)是否允许多个id选择,默认支持一个
 * @param action (可选) 指定form的action
 */
function submitId(form,id,isMulti,action,promptMsg){
	var selectId = getSelectIds(id);
	if(null==isMulti)
		isMulti=false;
	if(""==selectId){
		alert(isMulti?"请选择一个或多个进行操作":"请选择一个进行操作");
		return;
	}
	if(!isMulti&&isMultiId(selectId)){
		alert("请仅选择一个");
		return;
	}
	if(null!=action){
		form.action=action;
	}else{
		action=form.action;
	}
	if(isMulti){
		addInput(form,id+'s',selectId,"hidden");
	}else{
		addInput(form,id,selectId,"hidden");
	}
	if(null!=promptMsg){
		if(!confirm(promptMsg))return;
	}
	submit(form,action);
}
/**
 * 从form表单中，抽出含有指定前缀的输出参数，
 * 将其作为一个参数加入到to表单中。
 */
function setSearchParams(from,to,prefix){
	addInput(to,'params',"");
	var params=getInputParams(from,prefix,false);
	addInput(to,'params',params);
}
function ecodeParams(params){
	if(""==params)return "";
	var paramsPair=params.split("&");
	var newParams="";
	for(var i=0;i<paramsPair.length;i++){
		if(paramsPair[i]!=""){
			var eqIndex = paramsPair[i].indexOf("=");
			newParams+="&"+paramsPair[i].substr(0,eqIndex);
			if(-1!=eqIndex){
				newParams+="=";
				newParams+=escape(paramsPair[i].substr(eqIndex+1));
			}
		}
	}
	return newParams;
}

/**
 * 返回单选列表中选择的值<br>
 * @return 没有选中时,返回""
 */
function getSelectId(radioBoxName){
	return getRadioValue(document.getElementsByName(radioBoxName))
}
/**
 * 返回多选列表中选择的值<br>
 * @return 多个值以,相隔.没有选中时,返回"" 
 */
function getSelectIds(checkBoxName){
	var tmpIds=getCheckBoxValue(document.getElementsByName(checkBoxName));
	if(tmpIds==null){
		return "";
	}
	else{
		return tmpIds;
	}
}

function getSelectValues(obj){
	var tmpValues = "";
	if (null == obj) {
		return "";
	}
	for(var i = 0; i < obj.options.length; i++) {
		tmpValues += obj.options[i].value + ",";
	}
	return tmpValues;
}

function getAllOptionValue(select){
	var val = "";
	var options = select.options;
	for (var i=0; i<options.length; i++){
		if (val != "")
			val = val + ",";
		val = val + options[i].value;
	}
	return val;
}

function hasOption(select, op){ 
	for (var i=0; i<select.length; i++ ){
		if (select.options[i].value == op.value)
			return true;
	}
	return false;
}
function moveSelectedOption(srcSelect, destSelect){
	for (var i=0; i<srcSelect.length; i++){
		if (srcSelect.options[i].selected){ 
		 
			var op = srcSelect.options[i];
			if (!hasOption(destSelect, op)){
			   destSelect.options[destSelect.length]= new Option(op.text, op.value);
			}
		 }
	}
	removeSelectedOption(srcSelect);   
	clearSelectStatus(srcSelect);
}
function clearSelectStatus(select){
	for (var i=0; i<select.length; i++)
		select.options[i].selected = false;
}

function removeSelectedOption(select){
	var options = select.options;
	for (var i=options.length-1; i>=0; i--){   
		if (options[i].selected){  
			options[i] = null;
		}
	}
}
function getSelectedOptionValue(select){
	var val = "";
	var options = select.options;
	for (var i=0; i<options.length; i++)
	{   
		if (options[i].selected)
		{
			if (val != "")
				val = val + ",";	
			val = val + options[i].value;
		}
	}
	return val;
}

function isMultiId(str){
	return str.indexOf(",")>0;
}
/**
 * 计算id串中,id的个数
 * added by chaostone 2006-7-16
 */
function countId(ids){
	if(""==ids) return 0;
	var length=1;
	if(isMultiId(ids)) {
		var regex =/,/g;
		var matchArray = new Array();
		matchArray = ids.match(regex);
		length=matchArray.length +1;
	}
	return length;
}

/**
 * 向form中添加一个input。
 * @param form 要添加输入的form
 * @param name input的名字
 * @param value input的值
 * @param type input的类型，默认为hidden
 * @author chaostone 2006-4-7
 */
function addInput(form,name,value,type){
	//防止设置form的属性
	if(form[name]!=null && (typeof form[name])!="string"){
		form[name].value=value;
	}else{
		if(null==type)
			type="hidden";
		var input=null;
		if(document.all){
			var inputHTML="<input name='"+name+"' value='"+ value+"' type='"+type+"'>";
			input = form.document.createElement(inputHTML);
		}else{
			input = document.createElement('input');
			input.setAttribute("name",name);
			input.setAttribute("value",value);
			input.setAttribute("type",type);
		}
		form.appendChild(input);
	}
}

function addHiddens(form,paramSeq){
	var params = paramSeq.split("&");
	for(var i=0;i<params.length;i++){
		if(params[i]!=""){
			var name = params[i].substr(0,params[i].indexOf("="));
			var value =params[i].substr(params[i].indexOf("=")+1);
			addInput(form,name,value,"hidden");
		}
	}
}
function addParamsInput(form,value){
	addInput(form,"params",value,"hidden");
}
function transferParams(from ,to,prefix,getEmpty){
	if(getEmpty==null)
		getEmpty=true;
	var params = getInputParams(from,prefix,getEmpty);
	addHiddens(to,params);
}
/**
 * 收集给定form中的input||select参数（不论input的类型）.<b> 
 * 但不收集params的input,这个作为保留名称
 * @param form  
 * @param prefix 指明所有input||select的前缀，如果没有前缀可以忽略 
 * @param getEmpty 是否收集值为空的属性
 * @return 返回参数列表串形如：&input1=...&input2=... 
 * @author chaostone 2006-4-7 
 * 
 */
function getInputParams(form,prefix,getEmpty){
	var elems = form.elements;
	var params = "";
	if(null==getEmpty) getEmpty=true;

	for(i =0;i < elems.length; i++){
		if(""!=elems[i].name){
			if("params"==elems[i].name) continue;
			//alert(elems[i].tagName+":"+elems[i].value);
			if((elems[i].value=="")&&(getEmpty==false)) continue;
			if(null!=prefix){
				if(elems[i].name.indexOf(prefix)==0&&elems[i].name.indexOf(".")>1){
					if((elems[i].type=="radio" ||elems[i].type=="checkbox")&& elems[i].checked==false)
						continue;
					if(elems[i].value.indexOf('&')!=-1){
						params+="&" + elems[i].name + "=" + escape(elems[i].value);
					}else{
						params+="&" + elems[i].name + "=" + elems[i].value;
					}
				}
			}else{
				if((elems[i].type=="radio" ||elems[i].type=="checkbox")&& elems[i].checked==false)
					continue;
				if(elems[i].value.indexOf('&')!=-1){
					params+="&" + elems[i].name + "=" + escape(elems[i].value);
				}else{
					params+="&" + elems[i].name + "=" + elems[i].value;
				}
			}
		}
	}
	//alert("[getInputParams]:"+params);
	return params;
}
	
function goToPage(form,pageNo,pageSize,orderBy){
	if((typeof form)!="object"){alert("[goToPage:]form is not well defined.");return;}
	//form.method="post"; for avoid "method" input
	if(null!=pageNo){
		if(isNaN(pageNo)){
			alert("输入分页的页码是:"+pageNo+",它不是个整数");
			return;
		}
		addInput(form,"pageNo",pageNo,"hidden");
	}else{
		addInput(form,"pageNo",1,"hidden");
	}
	if(null!=pageSize){
		if(isNaN(pageSize)){
			alert("输入分页的页长是:"+pageSize+",它不是个整数");
			return;
		}
		addInput(form,"pageSize",pageSize,"hidden");
	}else{
		addInput(form,"pageSize","","hidden");
	}
	if(null!=orderBy&&orderBy!="null"){
		addInput(form,"orderBy",orderBy,"hidden");
	}else{
		addInput(form,"orderBy","","hidden");
	}
	//alert("in goToPage");   
	form.submit();
}

function goToFirstPage(form){
	goToPage(form,1);
}
/**
 * 设定选择框中的选择项(单项)
 */
function setSelected(select,id){
	//alert("setSelected:option:"+id);
	for(var i=0;i<select.options.length;i++){
		if(select.options[i].value==id){
			select.options[i].selected=true;
			//alert("get:"+select.options[i].value)
			break;
		}
	}
}
/**
 * 设定选择框中的选择项(多项)
 */
function setSelectedSeq(select,idSeq){
	if(idSeq.indexOf(',')!=0){
		idSeq=","+idSeq;
	}
	if(idSeq.lastIndexOf(',')!=idSeq.length-1){
		idSeq=idSeq+",";
	}
	for(var i=0;i<select.options.length;i++){
		if(idSeq.indexOf(','+select.options[i].value+',')!=-1)
			select.options[i].selected=true;
	}
}
/**
 * 按照指定action提交form
 * @param {Object} myForm
 * @param {Object} action
 * @param {Object} target
 */
//Cookie----------------------------------------------------------------------------------------
function getCookie(cookieName) {
	var cookieString = document.cookie;
	var start = cookieString.indexOf(cookieName + '=');
	// 加上等号的原因是避免在某些 Cookie 的值里有
	// 与 cookieName 一样的字符串。
	if (start == -1) // 找不到
	return null;
	start += cookieName.length + 1;
	var end = cookieString.indexOf(';', start);
	if (end == -1) return unescape(cookieString.substring(start));
	return unescape(cookieString.substring(start, end));
}

function setCookie(name, value, path){
	if(null==path)
		path="/";
	expires=new Date();
	expires.setTime(expires.getTime()+(86400*30));
	document.cookie=name+"="+value+"; expires="+expires.toGMTString()+"; path="+path;
}