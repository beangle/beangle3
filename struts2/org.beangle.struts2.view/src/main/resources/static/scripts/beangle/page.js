if(typeof pages=="undefined"){
	pages=new Object();
}
function getContextPath(){
	return self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)
}
/**
 * 初始化排序表格<br>
 * 此函数主要是向已经待排序表格的列头1)添加鼠标事件响应和显示效果.
 * 2)负责将事件传递到用户定义的函数中.
 *
 * 凡是要排序的列,请注名排序单元格的id 和class.
 * 其中id是排序要传递的字段,class为定值tableHeaderSort.
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
		if(head.cells[i].className=="tableHeaderSort" && null!=head.cells[i].id){
			head.cells[i].onclick = columnSort;
			head.cells[i].onmouseover=overSortTableHeader;
			head.cells[i].onmouseout=outSortTableHeader;
			head.cells[i].title="点击按 ["+head.cells[i].innerHTML+"] 排序";
			var desc=head.cells[i].id.replace(/\,/g," desc,")+" desc";
			if(typeof head.cells[i].desc !="undefined"){
				desc=head.cells[i].desc;
			}
			if(orderBy.indexOf(desc)!=-1){
				head.cells[i].className="tableHeaderSortDesc"
				head.cells[i].innerHTML=head.cells[i].innerHTML+'<img src="'+getContextPath()+'/static/images/action/sortDesc.gif"  style="border:0"  alt="Arrow" />'
				continue;
			}
			var asc=head.cells[i].id+" asc";
			if(typeof head.cells[i].asc !="undefined"){
				asc=head.cells[i].asc;
			}
			if(orderBy==asc){
				head.cells[i].className="tableHeaderSortAsc"
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

//列排序对应的pageId和选中的列
function sort(pageId,ele){
	if(null==pageId){
		alert("无法找到元素对应的排序表格！");return;
	}
	var orderByStr=null;
	if(ele.className=="tableHeaderSort"){
		if(typeof ele.asc!="undefined"){
			orderByStr=ele.asc;
		}
		else{
			orderByStr=ele.id+" asc";
		}
	}else if(ele.className=="tableHeaderSortAsc"){
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