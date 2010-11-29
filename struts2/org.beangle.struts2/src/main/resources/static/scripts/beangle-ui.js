/*----------------------------------------------
 * Beangle UI
 * include ToolBar,Page,Action 
 */
//ToolBar----------------------------------------------------------
var defaultToolBarImageName="info.gif";
var defaultItemImageName="action.gif";
var helpImageName="help.gif";
var imagePath=self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)+"/static/images/action/";
var debugEnable=false;


function NamedFunction(name,func){
	this.name=name;
	this.func=func;
}

function getImagePath(path,imageName){
	if(imageName.charAt(0)=='/'){
		return imageName;
	}else{
		return path+imageName;
	}
}
function getDefaultImageName(action){
	if(null==action) return defaultItemImageName;
	if (typeof action == "object") {
		action=action.name;
	}
	if(typeof action=="string"){
		if(action.indexOf("add")!=-1||action.indexOf("new")!=-1) return "new.gif";
		if(action.indexOf("remove")!=-1||action.indexOf("delete")!=-1) return "delete.gif";
		if(action.indexOf("update")!=-1||action.indexOf("edit")!=-1||action.indexOf("Edit")!=-1) return "update.gif";
		if(action.indexOf("export")!=-1) return "excel.png";
		if(action.indexOf("copy")!=-1) return "copy.gif";
		if(action.indexOf("print")!=-1) return "print.gif";
		if(action.indexOf("refresh")!=-1) return "refresh.gif";
		if(action.indexOf("close")!=-1) return "close.gif";
		if(action.indexOf("save")!=-1) return "save.gif";
		if(action.indexOf("download")!=-1) return "download.gif";
		else return defaultItemImageName;
	}else return defaultItemImageName;   
}
/**
 * 生成一个工具栏
 * @param tableId 工具栏对应的表格
 * @param title 工具栏的标题
 * @param imageName 工具栏顶头的图片名称
 * @param hasSeparator 工具栏中是否添加分隔符
 * bug1,不要把toolbar对应的表格放在form内
 */
function ToolBar(tableId,title,imageName,hasKeyLine,hasSeparator){
	this.hasSeparator=hasSeparator;
	if(!hasKeyLine){
		this.hasKeyLine=false;
	}else{
		this.hasKeyLine=hasKeyLine;
	}
	this.itemCount=0;
	this.table=document.getElementById(tableId);
	if(null==this.table){
		alert("cannot find table with id " + tableId);
		return;
	}
	this.table.className="toolBar";

	this.addItem=addItem;
	this.addSeparator=addSeparator;
	this.addSeparatorAsNeed=addSeparatorAsNeed;
	this.addMenu=addMenu;
	this.addBack=addBack;
	this.addClose=addClose;
	this.addHelp=addHelp;
	this.addPrint=addPrint;
	this.addBackOrClose=addBackOrClose;
	this.addBlankItem=addBlankItem;
	this.init=initToolBar;
	this.setMessage=setMessage;
	this.init(title,imageName);
	debug(this.table.innerHTML);
}

function addBack(msg){
	if(null==msg){
		this.addItem("返回",function (){history.back(-1)},'backward.gif');
	}else{
		this.addItem(msg,function (){history.back(-1)},'backward.gif');
	}
}
function addHelp(module){
	this.addItem("帮助",function (){
		if(null==module) alert("施工中..");
		else window.open("help.do?method=help&helpId="+module);},'help.png');
}

function addPrint(msg){
	if(null==msg){
		this.addItem("打印","print()");
	}else{
		this.addItem(msg,"print()");
	}
}

function addClose(msg){
	if(''==msg|| null==msg){
		msg="关闭";
	}
	this.addItem(msg,"window.close()",'close.gif');
}

function debug(msg){
	if(debugEnable){
		alert(msg);
	}
}
/**
 *  设置工具栏的消息区
 *
 */
function setMessage(msg){
	if (typeof msg == "undefined") return;
	var itemTd = document.createElement('td');
	itemTd.innerHTML = msg;
	this.tr.appendChild(itemTd);
}
/**
 *  设置抬头
 *
 */
function initToolBar(title,imageName){
	var tr = document.createElement('tr');
	var titleTd = document.createElement('td');
	if(imageName==null){
		imageName=defaultToolBarImageName;
	}
	titleTd.innerHTML='<img class="icon" src="'+getImagePath(imagePath,imageName)+'"><B>'+title+"</B>";
	titleTd.align="left";
	tr.appendChild(titleTd);
	tr.align="center";
	addTR(this.table,tr);
	this.tr=tr;
	debug("generator tr");
	if(this.hasKeyLine){
		var keyLineTR = document.createElement('tr');
		var keyLineTd = document.createElement('td');		
		keyLineTd.innerHTML='<img height="2" width="100%" align="top" src="'+imagePath+'keyline.gif">';
		//keyLineTd.innerHTML='<img  height="1" width="1" src="'+imagePath+'spacer.gif">';
		keyLineTd.className="keyLine";
		keyLineTd.colSpan=40;
		keyLineTR.appendChild(keyLineTd);
		addTR(this.table,keyLineTR);
	}
}

function addTR(table,tr){
	if(table.tBodies.length==0){
		table.appendChild(document.createElement("TBODY"));   
	}
	table.tBodies[0].appendChild(tr);
}
/**
 * 添加菜单
 *
 */
function addItem(title,action,imageName,alt){
	this.addSeparatorAsNeed();
	var itemTd = document.createElement('td');
	if(null==imageName){
		imageName=getDefaultImageName(action);
	}
	if(alt==""||alt==null){
		alt=title;
	}
	itemTd.innerHTML='<img class="icon" src="'+getImagePath(imagePath,imageName)+'" alt="' +alt+'">'+title;

	itemTd.onmouseout=MouseOut;
	itemTd.onmouseover=MouseOver;
	setAction(itemTd,action);
	itemTd.className="padding";
	itemTd.width="1%";// adaptive
	itemTd.title=alt;
	this.tr.appendChild(itemTd);
	this.itemCount++;
	return itemTd;
}
/**
 *  添加分隔符
 *
 */
function addSeparator(){  
	var separatorTd = document.createElement('td');
	separatorTd.innerHTML="|";
	separatorTd.className="separator";
	this.tr.appendChild(separatorTd);  
}
function addSeparatorAsNeed(){
	if(this.itemCount!=0&&this.hasSeparator){
		this.addSeparator();
	}
}
/**
 * 在工具栏中添加一个菜单
 */
function addMenu(title,action,imageName,alt){
	this.addSeparatorAsNeed();
	var itemTd = document.createElement('td');
	if(null==imageName){
		imageName=getDefaultImageName(action);
	}
	if(alt==""||alt==null){
		alt=title;
	}
	var menuTableId="NewMenu"+this.itemCount;
	var innerHTML='<img class="icon" src="'+getImagePath(imagePath,imageName)+'" alt="' +alt+'">'+title;
	itemTd.className="padding";
	itemTd.width="1%";// adaptive 
	itemTd.id=menuTableId+"_TD";
	itemTd.onmouseout=function (event){MourseMenuEvent(event,menu_str);}
	itemTd.onmouseover=function (event){MourseMenuEvent(event,menu_str);}
	itemTd.onblur=function (event){MourseMenuEvent(event,menu_str);}
	itemTd.className="padding";
	this.tr.appendChild(itemTd);

	var menu = new Menu(menuTableId);
	var menu_str="MOL["+MOL.length+"]";
	var newMenu_obj=	null;
	
	if(null!=action){
		itemTd.innerHTML=innerHTML;
		var downarrow = document.createElement('td');
		downarrow.innerHTML='<img class="icon" src="'+imagePath+'downarrow.gif" >';
		downarrow.id=menuTableId+"_DdTD";
		this.tr.appendChild(downarrow);
		newMenu_obj = new MenuObj(title,menu.table.id, itemTd.id,downarrow.id, "transfer", "padding", "Q");

		setAction(itemTd,action);
		downarrow.onclick=function (event){MCH(event,menu_str);};
		downarrow.onmouseout=function (event){MourseMenuEvent(event,menu_str);}
		downarrow.onmouseover=function (event){MourseMenuEvent(event,menu_str);}
		downarrow.onblur=function (event){MourseMenuEvent(event,menu_str);}
		downarrow.className="padding";
		downarrow.width="8px";
	}else{
		newMenu_obj = new MenuObj(title,menu.table.id, itemTd.id,"", "transfer", "padding", "Q");
		itemTd.innerHTML=innerHTML+'<img src="'+imagePath+'downarrow.gif" class="icon">';;
		itemTd.onclick=function (event){MCH(event,menu_str);};
	}
	this.itemCount++;
	debug("after new menu");
	return menu;
}
/**
 * 生成一个菜单
 */
function Menu(id){
	var table=document.createElement("table");
	table.className="barMenu";
	table.id=id;
	//table.border="1"
	var tr = document.createElement('tr');
	var td = document.createElement('td');
	td.innerHTML="";
	tr.appendChild(td);
	var mytablebody = document.createElement("TBODY");
	mytablebody.appendChild(tr);
	table.appendChild(mytablebody);
	document.getElementsByTagName("body").item(0).appendChild(table);

	this.table=table; 
	this.addItem=addMenuItem;
}
/**
 * 在菜单中添加一个条目
 */
function addMenuItem(title,action,imageName,alt){
	var itemTd = document.createElement('td');
	if(null==imageName){
		imageName=getDefaultImageName(action);
	}
	if(alt==""||alt==null){
		alt=title;
	}
	itemTd.innerHTML='<img class="icon" src="'+getImagePath(imagePath,imageName)+'" alt="' +alt+'">'+title;

	itemTd.onmouseout=MU_D;
	itemTd.onmouseover=MO_D;
	setAction(itemTd,action);
	itemTd.className="barMenuTransfer";
	itemTd.width="100%";
	var tr = document.createElement('tr');
	tr.appendChild(itemTd);
	addTR(this.table,tr);
}
/**
 * 设置按钮的动作
 *
 */
function setAction(itemTd,action){
	if(null==action){
		return;
	}
	if(typeof action=='function'){
		itemTd.onclick=action;
		return;
	}
	if(typeof action=='string'){
		if (action.indexOf('(')!=-1){
			itemTd.onclick= function (){eval(action);}
		}
		else if(action.indexOf('.action')!=-1){
			itemTd.onclick=function (){Go(action)}	 
		}else{
			alert("unsuported action description:"+action);
		}
	}
	if(typeof action=='object'){
		itemTd.onclick=action.func;
		return;
	}
}
var MOL=new Array();
/**
 * 一个下拉菜单对象
 * @param name 名称
 * @param tableId 悬挂的表格id
 * @param tdId 悬挂的单元格id
 * @param 
 * @param 
 *
 */
function MenuObj(name,tableId,tdId,_D,_E,_F,_G,_I){
	this.name=name;
	this.divObj=eval('document.getElementById("' + tableId + '")');
	this.divStyleObj=eval('document.getElementById("' + tableId + '").style');
	this.refTDObj=eval('document.getElementById("' + tdId + '")');
	if (_D)
	this.DdTDObj=eval('document.getElementById("' + _D + '")');
	
	this.bOn=_E;
	this.bOf=_F;
	this.bA=_G;
	
	this.SBS=SBS;
	this.showing=false;
	this.TM=TM;
	document.onclick=MCH;
	this.Direction=_I;
	MOL[MOL.length]=this;	
	this.strShow='visible';
	this.strHide='hidden';
}
/**
 * 获得表达式对应的对象
 */
function ReferOfPoint(ObjRef){
    var theObj=null;
	if (ObjRef){
		if (typeof ObjRef != 'object'){
			theObj=eval(ObjRef);
	    }
		else{
			theObj=ObjRef;
	    }
		return theObj;
	} else{
		return false;
	}
}

function TM(){
	if (!this.showing){
		var RelObjCords=getXY(this.refTDObj);
		if (this.Direction){
			this.divStyleObj.top =  RelObjCords.top + -this.divObj.offsetHeight;
			this.divStyleObj.left =  RelObjCords.left;
		}
		else{
			this.divStyleObj.top =  RelObjCords.top + 18;
			this.divStyleObj.left =  RelObjCords.left;
		}		
		var pCurrMenuObj=ReferOfPoint(this);
		CM(this);
		this.SBS('clicked');
		this.divStyleObj.visibility =  this.strShow;
		this.showing=true;
	}
	else{
		this.divStyleObj.visibility =  this.strHide;
		this.showing=false;
		this.SBS();
	}
}
/**
 * 当鼠标经过工具栏的按钮时
 *
 */
function MouseOver(e){
	var S=getEventTarget(e);
	//var S=e.srcElement;
	while (S.tagName!="TD")
	{S=S.parentNode;}
	S.className="transfer";
}
/**
 * 当鼠标离开工具栏的按钮时
 */
function MouseOut(e){
	var S=getEventTarget(e);
	//var S=e.srcElement;
	while (S.tagName!="TD")
	{S=S.parentNode;}
	S.className="padding";
}

function MouseUp(e){
	var S=getEventTarget(e);
	//var S=e.srcElement;
	while (S.tagName!="TD")
	{S=S.parentNode;}
	S.className="padding";
}
/**
 * 当鼠标经过或离开菜单时的事件响应类
 */
function MourseMenuEvent(e, srcObj){
	if (!e) 
	var e=window.event;
	var pCurrMenuObj=ReferOfPoint(srcObj);
	if (!pCurrMenuObj.showing){
		if (e.type == 'mouseover')
			pCurrMenuObj.SBS('on');
		else if ((e.type == 'mouseout') || (e.type == 'blur'))
			pCurrMenuObj.SBS();
	}
}

function SBS(wS){
	if (typeof this.refTDObj != "undefined"){
		if (wS == 'on')	{
			if (this.bOn){
				if (typeof this.DdTDObj != "undefined")
					this.DdTDObj.className=this.bOn;
				this.refTDObj.className=this.bOn;
			}
		}
		else if (wS == 'clicked'){
			if (this.bA){
				if (typeof this.DdTDObj != "undefined")
					this.DdTDObj.className=this.bA;
				this.refTDObj.className=this.bA;
			}
		}else{
			if (this.bOf){
				if (typeof this.DdTDObj != "undefined")
					this.DdTDObj.className=this.bOf;
				this.refTDObj.className=this.bOf;
			}
		}
	}
}

function getXY(Obj){
	for (var sumTop=0,sumLeft=0;Obj!=document.body;sumTop+=Obj.offsetTop,sumLeft+=Obj.offsetLeft, Obj=Obj.offsetParent);
	return {left:sumLeft,top:sumTop}
}

function CM(callerObj){
	for (aIndex=0;aIndex < MOL.length; aIndex++){
		if ((callerObj) && (callerObj.name != MOL[aIndex].name)){	
			if (MOL[aIndex].showing){
				MOL[aIndex].TM();
				MOL[aIndex].SBS();
			}
		}else{
			if (MOL[aIndex].showing){
			MOL[aIndex].TM();
			MOL[aIndex].SBS();
			}
		}
	}
}
function MCH(e, srcObj, srcIsMenuDiv){
	var srcElem;
	portableEvent(e).cancelBubble=true;
	if (srcObj){
		var pCurrMenuObj=ReferOfPoint(srcObj); 
		if (!srcIsMenuDiv){
		   pCurrMenuObj.divObj.onclick="MCH(event,"+srcObj+",true)";
		}
		pCurrMenuObj.TM();
	}else{
		CM();
	}

}
///菜单条目的鼠标进入和离开事件响应方法
function MO_D(e){
	var S=getEventTarget(portableEvent(e));
	while (S.tagName!="TD")
	{S=S.parentNode;}
	S.className="barMenuPadding";
}
function MU_D(e){
	var S=getEventTarget(portableEvent(e));
	while (S.tagName!="TD")
	{S=S.parentNode;}
	S.className="barMenuTransfer";
}
function Go(url){
 self.location=url;
}

function addBackOrClose(backCaption, closeCaption) {
	if (parent.location == self.location && (window.history.length <= 1 || window.history.length == null)) {
		this.addClose((null == closeCaption) ? "关闭" : closeCaption);
	} else {
		this.addBack((null == backCaption) ? "后退" : backCaption);
	}
}
//增加空白功能点
function addBlankItem() {
	this.addSeparatorAsNeed();
	var itemTd = document.createElement('td');
	itemTd.innerHTML="";
	itemTd.width="100%";
	this.tr.appendChild(itemTd);
	this.itemCount++;
}

//Page---------------------------------------------------------------------
if(typeof pages=="undefined"){
	pages=new Object();
}
/**
 * 初始化排序表格<br>
 * 此函数主要是向已经待排序表格的列头1)添加鼠标事件响应和显示效果.
 * 2)负责将事件传递到用户定义的函数中.
 *
 * 凡是要排序的列,请注名排序单元格的id 和class.
 * 其中id是排序要传递的字段,class为定值gridhead-sort.
 * 除此之外,用户(使用该方法的人)需要自定义一个钩子函数"sortBy(what)",以备调用.
 * @param tableId 待排序表格的id
 * @param rowIndex 标题行的位置 
 * @param orderBy orderBy 字句
 * 用法:
 *    <table>
 */
function initSortTable(tableId,rowIndex,orderBy){
	var table= document.getElementById(tableId);
	if(null==rowIndex)
		rowIndex=0;
	var head=table.tBodies[0].rows[rowIndex];
	if(null==head){
		alert("sortTable ["+tableId+"] with out thead"); 
		return;
	}
	columnSort = function(){
		sort(tableId,this);
	}
	for(var i=0;i<head.cells.length;i++){
		if(head.cells[i].className=="gridhead-sort" && null!=head.cells[i].id){
			head.cells[i].onclick = columnSort;
			head.cells[i].onmouseover=overSortTableHeader;
			head.cells[i].onmouseout=outSortTableHeader;
			head.cells[i].title="点击按 ["+head.cells[i].innerHTML+"] 排序";
			var desc=head.cells[i].id.replace(/\,/g," desc,")+" desc";
			if(typeof head.cells[i].desc !="undefined"){
				desc=head.cells[i].desc;
			}
			if(orderBy.indexOf(desc)!=-1){
				head.cells[i].className="gridhead-desc"
				head.cells[i].innerHTML=head.cells[i].innerHTML+'<img src="'+getContextPath()+'/static/images/action/sortDesc.gif"  style="border:0"  alt="Arrow" />'
				continue;
			}
			var asc=head.cells[i].id+" asc";
			if(typeof head.cells[i].asc !="undefined"){
				asc=head.cells[i].asc;
			}
			if(orderBy==asc){
				head.cells[i].className="gridhead-asc"
				head.cells[i].innerHTML=head.cells[i].innerHTML+'<img src="'+getContextPath()+'/static/images/action/sortAsc.gif"  style="border:0"  alt="Arrow" />'
				continue;
			}
		}
	}
}	
//鼠标经过和移出排序表格的表头时
function overSortTableHeader(){
	this.style.color='white';
	this.style.backgroundColor ='green'
}
function outSortTableHeader(){
	this.style.borderColor='';
	this.style.color='';
	this.style.backgroundColor ='';
}

//鼠标经过数据行的效果
var currClass="";
var swapClass="";
function swapOverTR(obj,objClass){
	if(objClass!="roll_down"){
		currClass=objClass;
		obj.className="highlight";
		swapClass=objClass;
	}
}

function swapOutTR(obj){
	if(obj.className!="roll_down"){
	obj.className=swapClass;
	}
}
/**
 * 行选函数。单击行内的任一处，可以选定行头的checkbox或者radio
 * 用法:onclick="onRowChange(event)"
 */
function onRowChange(event){    
	ele =  getEventTarget(event);
	var changed=true;
	if(null!=ele&&ele.tagName=="TD"){
		var firstChild = ele.parentNode.firstChild;
		if(firstChild.tagName!="TD"){
			firstChild=firstChild.nextSibling;
		}
		ele=firstChild.firstChild;
		while(((typeof ele.tagName)=="undefined")||ele.tagName!="INPUT"){
			ele=ele.nextSibling;
			if(ele==null)return;
		}
		ele.checked = !ele.checked;
	}else if((ele.type=="checkbox")||(ele.type=="radio")){
	}else{
		changed=false;
	}
	//改变选定行的颜色
	if(null!=ele&&changed){
		if(typeof ele.onchange =="function"){
			ele.onchange();
		}
		if(ele.type=="radio") return;
		changeTRColor(ele);
	}
}
/**
 * 改变选定单选框和复选框所在的行的颜色
 * 用法在checkbox或者radiobox中增加一个onchange
 */
function changeTRColor(box){
	if(box.checked){
		box.parentNode.parentNode.className="roll_down";
	}else{
		box.parentNode.parentNode.className="bright";
	}
}

//列排序对应的pageId和选中的列
function sort(pageId,ele){
	if(null==pageId){
		alert("无法找到元素对应的排序表格！");return;
	}
	var orderByStr=null;
	if(ele.className=="gridhead-sort"){
		if(typeof ele.asc!="undefined"){
			orderByStr=ele.asc;
		}
		else{
			orderByStr=ele.id+" asc";
		}
	}else if(ele.className=="gridhead-asc"){
		if(typeof ele.desc!="undefined"){
			orderByStr=ele.desc;
		}
		else{
			orderByStr=ele.id.replace(/\,/g," desc,")+" desc";
		}
	}else{
		orderByStr="";
	}
	goPage(pageId,1,null,orderByStr);
}

//检查分页参数
function checkPageParams(id,pageNo, pageSize,orderBy){
	if(pages[id]==null){
		alert("table id for ["+ id +"] is not well defined.");
		return false;
	}
	tableParams=pages[id].params;
	if(tableParams==null){
		alert("[goPage:]table's params is not well defined.");return false;
	}
	if(null!=pageNo){
		if(isNaN(pageNo)){
			alert("输入分页的页码是:"+pageNo+",它不是个整数");
			return false;
		}
		if(pages[id].maxPageNo!=null){
			if(pageNo>pages[id].maxPageNo){
				pageNo=pages[id].maxPageNo;
			}
		}
		tableParams['pageNo']=pageNo;
	}
	if(null!=pageSize){
		if(isNaN(pageSize)){
			alert("输入分页的页长是:"+pageSize+",它不是个整数");
			return false;
		}
		tableParams["pageSize"]=pageSize;
	}
	if(null!=orderBy && orderBy!="null"){
		tableParams["orderBy"]=orderBy;
	}
	return true;
}
function goPage(id, pageNo, pageSize, orderBy){
	if(checkPageParams(id,pageNo,pageSize,orderBy)){
		if(typeof pages[id].target != undefined && null!=pages[id].target && ""!=pages[id].target){
			goPageAjax(id,pageNo,pageSize,orderBy);
		}else{
			goPageNormal(id,pageNo,pageSize,orderBy);
		}
	}
}

function goPageNormal(id,pageNo,pageSize,orderBy){
	myForm=document.createElement("form");
	myForm.setAttribute("action",pages[id].action);
	myForm.setAttribute("method","POST");
	for(var key in tableParams){
		value=tableParams[key];
		if(value!=""){
			addInput(myForm,key,value,"hidden");
		}
	}
	myPage =document.getElementById(id);
	myPage.appendChild(myForm);
	myForm.submit();
}

function goPageAjax(id,pageNo,pageSize,orderBy){
	myForm=document.getElementById("form_"+id);
	if(null==myForm){
		myForm=document.createElement("form");
		myForm.setAttribute("id","form_"+id);
		myForm.setAttribute("action",pages[id].action);
		myForm.setAttribute("method","POST");
		myPage =document.getElementById(id);
		myPage.appendChild(myForm);
	}
	for(var key in tableParams){
		value=tableParams[key];
		if(value!=""){
			addInput(myForm,key,value,"hidden");
		}
	}
	var submitx=document.getElementById("submitx");
	if(null==submitx){
		if(document.all){
			var inputHTML="<button id='submitx' type='submit' style='display:none'></button>";
			submitx = myForm.document.createElement(inputHTML);
		}else{
			submitx = document.createElement('button');
			submitx.setAttribute("id","submitx");
			submitx.setAttribute("type",'submit');
			submitx.setAttribute("style",'display:none');
		}
		myForm.appendChild(submitx);
	}
	
	var options_submit = {};
	options_submit.jqueryaction = "button";
	options_submit.id = "submitx";
	options_submit.targets = pages[id].target;
	options_submit.href =pages[id].action;
	options_submit.formids = "form_"+id;
	$.struts2_jquery.bind($('#submitx'),options_submit);
	//myForm.submit();
	submitx.click();
}
//Action---------------------------------------------------------------------
if (typeof entityActions == "undefined") {
	entityActions = new Object();
}

function getEntityAction(formId){
	if (null == formId || typeof formId != "string") {
		for (var id in entityActions) {
			return entityActions[id];
		}
	}
	else {
		if (typeof entityActions[formId] == "undefined") {
			alert("without actionform with id" + formId);
		}
		else {
			return entityActions[formId];
		}
	}
}

function EntityAction(formId,entity,action,actionQueryStr){
	this.formId=formId;
	this.entity=entity;
	this.action=action;
	this.actionQueryStr=actionQueryStr;
	this.getForm=function (){
		return  document.getElementById(this.formId);
	};
	
	this.remove=function(){
		return new NamedFunction('remove',function(){
			singleAction(formId,'remove','确认删除?');
		});
	}
	this.add = function(){
		return new NamedFunction('add',function(){
			add(formId);
		});
	}
	
	this.info = function(){
		return new NamedFunction('info',function(){
			singleAction(formId,'info');
		});
	}
	
	this.edit = function (){
		return "singleAction('"+formId+"','edit')";
	}
	
	this.single = function(methodName,confirmMsg){
		return new NamedFunction(methodName,function(){
			singleAction(formId,methodName,confirmMsg);
		});
	}
	
	this.method=function(methodName){
		return  new NamedFunction(methodName,function(){
			aform=getEntityAction(formId);
			form=aform.getForm();
			if(""!=aform.actionQueryStr){
				addHiddens(form,aform.actionQueryStr);
				addParamsInput(form,aform.actionQueryStr);
			}
			submit(form,aform.action + "?method=" + methodName);
		});
	}
}

function beforeSubmmitId(formId,method) {
	aform=getEntityAction(formId);
	var ids = getSelectIds(aform.entity+"Id");
	if (ids == null || ids == "") {
		alert("你没有选择要操作的记录！");
		return false;
	}
	form=aform.getForm();
	form.action = aform.action + "?method=" + method;
	if(""!=aform.actionQueryStr){
		addHiddens(form,aform.actionQueryStr);
		addParamsInput(form,aform.actionQueryStr);
	}
	return true;
}

function submitIdAction(formId,method,multiId,confirmMsg){
	aform=getEntityAction(formId);
	if (beforeSubmmitId(aform.formId,method)) {
		if(null!=confirmMsg && ''!=confirmMsg){
			if(!confirm(confirmMsg))return;
		}
		submitId(aform.getForm(),aform.entity + "Id",multiId);
	}
}
function multiAction(formId,method,confirmMsg){
	submitIdAction(formId,method,true,confirmMsg);
}

function singleAction(formId,method,confirmMsg){
	submitIdAction(formId,method,false,confirmMsg);
}

function edit(formId) {
	aform=getEntityAction(formId);
	if (beforeSubmmitId(formId,"edit")) {
		submitId(aform.getForm(), aform.entity + 'Id', false);
	}
}

function info(formId) {
	aform=getEntityAction(formId);
	form=aform.getForm();
	addInput(form,aform.entity + "Id",giveId, "hidden");
	submit(form,aform.action + "?method=info");
}

function remove(formId){
	aform=getEntityAction(formId);
	if (beforeSubmmitId(aform.formId,"remove")) {
		submitId(aform.getForm(),aform.entity + "Id",true,null,"确认操作?");
	}
}

function add(formId){
	aform=getEntityAction(formId);
	form=aform.getForm();
	if(""!=aform.actionQueryStr)addHiddens(form,aform.actionQueryStr);
	addInput(form,aform.entity + 'Id',"");
	if(""!=aform.actionQueryStr)addParamsInput(form,aform.actionQueryStr);
	submit(form,aform.action + "?method=edit");
}

function exportList(format,formId){
	aform=getEntityAction(formId);
	form=aform.getForm();
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

var multiIdAction=multiAction;