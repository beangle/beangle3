/**
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
var defaultToolBarImageName="info.gif";
var defaultItemImageName="action.gif";
var helpImageName="help.gif";
var imagePath=self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)+"/static/images/action/";
var debugEnable=false;

function getImagePath(path,imageName){
	if(imageName.charAt(0)=='/'){
		return imageName;
	}else{
		return path+imageName;
	}
}
function getDefaultImageName(action){
   if(null==action) return defaultItemImageName;
   if(typeof action!="function"){
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
   }
   else return defaultItemImageName;   
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
   var itemTd = document.createElement('td');   
   itemTd.innerHTML=msg;
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
   if(typeof action=='function'){
      itemTd.onclick=action;
   }
   else if (action.indexOf('(')!=-1){
     itemTd.onclick= function (){eval(action);}
   }
   else if(action.indexOf('.do')!=-1){
     itemTd.onclick=function (){Go(action)}	 
   }else{
      alert("unsuported action description:"+action);
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
