function portableEvent(e){
   if(!e){
     return window.event;
   }else{
     return e;
   }
}
/**
 * 选择复选框中的值，返回一个数组
 * auth:zhouqi
 *
 */
function selectedCheckBoxContents(cb_name) {
	try {
		var cb = document.getElementsByName(cb_name);
		var yCount = selectedCheckBoxLength(cb_name);
		
		var yContent = new Array(yCount);
		for (var i = 0; i < yCount; i++) {
			for (var j = i; j < cb.length; j ++) {
				if (cb[j].checked) {
					yContent[i] = cb[j].value;
					break;
				}
			}
		}
		return yContent;
	} catch (e) {
		return null;
	}
}

function selectedCheckBoxLength(cb_name) {
	
	try {
		var cb = document.getElementsByName(cb_name);
		var yCount = 0;
		for (var i = 0; i < cb.length; i++) {
			if (cb[i].checked) {
				yCount ++;
			}
		}
		return yCount;
	} catch (e) {
		return 0;
	}
}

 /**
  *  获得正在发生的事件
  *  同时兼容ie和ff的写法
  */
function getEvent()
{  
    if(document.all)   return window.event;    
    func=getEvent.caller;        
    while(func!=null){  
        var arg0=func.arguments[0];
        if(arg0)
        {
          if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation))
          {  
          return arg0;
          }
        }
        func=func.caller;
    }
    return null;
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

function MakeFull(){
	var args = MakeFull.arguments;
	var resize_able = args[1];
	if (null == resize_able) {
		resize_able = "no";
	}
	var url = args[0];
	var name = "";
	if (args.length > 1){
		name = args[1];
	}
	var closeSelf = false;
	if (args.length > 2){
		closeSelf = args[2];
	}

    var width=1024;
    var height=735;
    if (screen.width == 800){
        width = 800;
        height = 570;
    }

    var win = window.open(url,name,'toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=' + resize_able + ',scrollbars=yes');
	win.moveTo(0,0);
	win.resizeTo(width,height);
    win.focus();

	if (closeSelf)
		CloseWin();
}

function CloseWin(){
    var ua=navigator.userAgent;
    var ie=navigator.appName=="Microsoft Internet Explorer"?true:false;
    if(ie){
        var IEversion=parseFloat(ua.substring(ua.indexOf("MSIE ")+5,ua.indexOf(";",ua.indexOf("MSIE "))));
        if(IEversion< 5.5){
            var str  = '<object id=noTipClose classid="clsid:ADB880A6-D8FF-11CF-9377-00AA003B7A11">';
            str += '<param name="Command" value="Close"></object>';
            document.body.insertAdjacentHTML("beforeEnd", str);
            document.all.noTipClose.Click();
        } else{
            window.opener =null;
            window.close();
        }
    } else {
        window.close();
    }
}


function turnit(name, index, count){
	var currentTree = eval(name+index);
	
	if (currentTree.style.display=="none") {
		for (var i=1; i<=count; i++) {
			try{   
				var tree = eval(name+i);
				tree.style.display="none";
			} catch(e){
			}
		}
		currentTree.style.display="block";
	} else {
		currentTree.style.display="none"; 
	}
}

function MM_changeSearchBarStyle(bar) { 
    if ((obj=MM_findObj(bar))!=null) { 
  		if (obj.style.visibility == 'hidden'){
  		    obj.style.visibility = 'visible';
  		    obj.style.display = 'block';
  		} else {
  		    obj.style.visibility = 'hidden';
  		    obj.style.display = 'none';
  		}
    }
}

function MM_showHideLayers() { //v6.0
  showHiden(MM_showHideLayers.arguments);
}

function showHiden(args)
{
  var i,p,v,obj;
  for (i=0; i<(args.length-2); i+=3){
  	if ((obj=MM_findObj(args[i]))!=null) { 
  		v=args[i+2];
    	if (obj.style) { 
    		obj=obj.style; 
    		v=(v=='show')?'visible':(v=='hide')?'hidden':v; 
    	}
    	obj.visibility=v;     	
    }
  } 
}

function highlightButton(s) {
	if ("INPUT"==event.srcElement.tagName)
		event.srcElement.className=s;
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; 
  for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++){
    x.src=x.oSrc;
  }
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}

MM_reloadPage(true);

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function popupCommonWindow(url)
{
	var name = '';
	if (popupCommonWindow.arguments.length > 1){
		name = popupCommonWindow.arguments[1];
	}

    var width = 500;
    var height = 600;
    if (popupCommonWindow.arguments.length > 2)
       width = popupCommonWindow.arguments[2];
    if (popupCommonWindow.arguments.length > 3)
       height = popupCommonWindow.arguments[3];
    
    var win = window.open(url, name, 'scrollbars=yes,width='+width+',height='+height+',status=no,resizable=yes,depended=yes');
	win.self.resizeTo(width, height);
	win.self.moveTo((screen.width-width)/2, (screen.height-height)/2);
	win.self.focus();
}

function popupCommonSelector(url){
	var name = '';
	if (popupCommonSelector.arguments.length > 1)
	{
		name = popupCommonSelector.arguments[1];
	}
	popupCommonWindow(url, name);
}

function popupMiniCommonSelector(){
    var args = popupMiniCommonSelector.arguments;
    
	var url = args[0];
	var top = 0;
	var left = 0;
	if (args.length>1){top=args[1];}
	if (args.length>2){left=args[2];}
	
    var selector = 0;
    if (selector&&(selector.closed)){
        selector.close();
    }    
	var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,resizable=yes,depended=yes,width=1,height=1,left=1000,top=1000');		
	selector.self.moveTo(top,left);
	selector.self.resizeTo(430,735);
    
}

function disableButton(){
    var args = disableButton.arguments;
    var formObjName = args[0];
    if (args.length >1){
       for (var i=1; i<args.length; i++){
         if (checkObjExist(formObjName, args[i])){
             var objName = args[i];
             document.forms[formObjName].elements[objName].disabled = true;
         }
       }
    }
}
 
function checkObjExist(formObjName, objName){
    var formObj = document.forms[formObjName];
    if (formObj.elements[objName] != "undefined") {
       return true;
    }else{
       return false;
    }
}
 
// TOGGLE OBJECT DISPLAY ==============
function toggleDisplay(objId) {

    if (document.all['mainTable'].height=='100%') {
        document.all['mainTable'].height='83%';
    }else{
        document.all['mainTable'].height='100%';
    }
    
	document.all[objId].style.display = (document.all[objId].style.display == "none") ? "block" : "none";
	if (document.all[objId + "_handle"]) {
		//alert(document.all[objId + "_handle"].src);
		var fullpath = document.all[objId + "_handle"].src;
		var filename = fullpath.substr(fullpath.lastIndexOf("/")+1, fullpath.length);
		
		switch (filename) {
			case "push_top.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"pull_top.jpg";
				break;
			case "pull_top.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"push_top.jpg";
				break;
			case "push_left.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"pull_left.jpg";
				break;
			case "pull_left.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"push_left.jpg";
				break;	
		}

	}
}

function toggleUnDisplay(objId) {
	document.all[objId].style.display = document.all[objId].style.display = "none" ;
}

//???????????????????????????????????????????????????????????????
var currClass="";
var swapClass="";
//????????????					
function swapOverTR(obj,objClass){
							
    if(objClass!="roll_down"){    	
	    currClass=objClass;
	    obj.className="highlight";
	    swapClass=objClass;
	}
}
//????????????
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
     box.parentNode.parentNode.className="brightStyle";
  }
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
/**
 * 根据点击选择框的选中状态，确定一批复选框的选中状态
 */
function checkboxSelected(chk){
	return boxAction(chk, "selected");
}

function getRadioValue(radio){
	return boxAction(radio, "value");
}
	
function getCheckBoxValue(chk){
	return boxAction(chk, "value");
}

function getCheckBoxRelativeValue(input, chkBox){
	var val = "";
	if (chkBox)
	{
		if (! chkBox[0])
		{
			if (chkBox.checked)
				val = input.value;
		} else
		{
			for (var i=0; i<chkBox.length; i++)
			{
				if (chkBox[i].checked)
				{
					if(val!="")
					{
					  val = val + ",";	
					}
					val = val + input[i].value;
					
				}
			}
		}
	}
	return val;
}

//
function toggleCheckBox(chk,event){
    boxAction(chk, "toggle",event);
}

function getAllOptionValue(select)
{
	var val = "";
	var options = select.options;
	for (var i=0; i<options.length; i++)
	{   
		if (val != "")
			val = val + ",";	
		val = val + options[i].value;
	}
	return val;
}

function getSelectedOptionValue(select)
{
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

function hasOption(select, op){ 
	for (var i=0; i<select.length; i++ ){

    		if (select.options[i].value == op.value)
            return true;
    }    
	return false;
}

function clearSelectStatus(select){
    //CLEAR
    for (var i=0; i<select.length; i++)
        select.options[i].selected = false;
}

function appendSelectedOption(srcSelect, destSelect){ 
	for (var i=0; i<srcSelect.length; i++){
	   
		if (srcSelect.options[i].selected){ 
		 
			var op = srcSelect.options[i];
			if (hasOption(destSelect, op)){
			   window.alert("?????????????????????");
			}else{
			   destSelect.options[destSelect.length]= new Option(op.text, op.value);
			}
		 }
	 }
              
     clearSelectStatus(srcSelect);
}

function removeSelectedOption(select){
	var options = select.options;
	for (var i=options.length-1; i>=0; i--){   
		if (options[i].selected){  
			options[i] = null;
		}
	}
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

function unCheckAllBox(box){
     for (var i=0; i<box.length; i++){
           box[i].checked = false;
     }
}

function setSelectorIdAndDescriptions(){

   var args = setSelectorIdAndDescriptions.arguments;
   var ids = args[0]; 
   var descriptions = args[1];
   var idsTarget = args[2];
   var descriptionsTarget = args[3];
   
   var tempIds, tempDescriptions;
   
   if (args.length>4){
       if (args[4]=="join"){
          tempIds = joinIds(document.all[idsTarget].value, ids);
          tempDescriptions = joinNames(document.all[descriptionsTarget].value, descriptions);
       }
       if (args[4]=="reset"){
          tempIds = "";
          tempDescriptions = "";
       }
   } else {
       tempIds = ids;
       tempDescriptions = descriptions;
   }
   
   document.all[idsTarget].value = tempIds;
   document.all[descriptionsTarget].value = tempDescriptions;
}

/*?????????id????????????????????????*/
function joinIds(targetIds, joinIds){
   if (targetIds != ""){
       var tempIds = joinIds.split(",");
       var currentIds = "," + targetIds + ",";
       
       for (var i=0; i<tempIds.length; i++){
          if (!(currentIds.indexOf(tempIds[i]+",")>0)){
              currentIds = currentIds + tempIds[i] + ",";
          }
       }
       
       var ids = currentIds.split(",");
       var finalIds = ",";
       for (var i=0; i<ids.length; i++){
           if (ids[i]!=""){
              finalIds = finalIds + ids[i] + ",";
           }
       }
       return finalIds.substr(1, finalIds.length-2);
   } else {
       return joinIds;
   }
}

/*???????????????????????????????????????*/
function joinNames(targetNames, joinNames){
   if (targetNames!=""){
       var tempNames = joinNames.split(",");
       var currentNames = "," + targetNames + ",";
       
       for (var i=0; i<tempNames.length; i++){
          if (!(currentNames.indexOf(tempNames[i]+",")>0)){
              currentNames = currentNames + tempNames[i] + ",";
          }
       }
       
       var names = currentNames.split(",");
       var finalNames = ",";
       for (var i=0; i<names.length; i++){
          if (names[i]!=""){
              finalNames = finalNames + names[i] + ",";
          }
       }
       return finalNames.substr(1, finalNames.length-2);
   } else {
       return joinNames;
   }     
}

/*??????????????????????????????*/
function choiceTargetSelectorOption(selected, targetSelectorName){
    if (selected!=""){
        var targetSelector = document.getElementsByName(targetSelectorName)[0];
        for (var index=0; index<targetSelector.options.length; index++){
            if (targetSelector.options[index].value==selected){
                targetSelector.selectedIndex = index;
            }
        }
     }
}

/*??????radioBox*/
function choiceTargetRadioBox(selected, targetRadioName){
   if (selected != ""){
       var targetRadio=document.all[targetRadioName];
	   for(var i=0; i<targetRadio.length; i++){
	      if (targetRadio[i].value == selected){
	          targetRadio[i].checked=true;
	      }
	  }
   }
}

    function adaptFrameSize(){
       f_frameStyleResize(self);
    }
    /* iframe 页面自适应大小
     * @targObj    iframe
     * @extraHight 
     */
	function f_frameStyleResize(targObj,extraHight){
		if(targObj.name!=""&&targObj.name!='main'){
	       var frames = targObj.parent.document.getElementsByName(targObj.name);
	       if(frames.length<1) return;
	       var targWin=frames[0];
 		   if(targWin != null) {
	 			var HeightValue = targObj.document.body.scrollHeight;	
	 			if(null==extraHight)
	 			    extraHight=0;		 		
 			    targWin.style.height = HeightValue+extraHight;
 			    //targWin.style.width= targObj.document.body.scrollWidth;
	 		}
	 		targObj.parent.f_frameStyleResize(targObj.parent);
	 	}
 	}
	function frameResize(frameName){
 			var targWin =parent.document.all[frameName];
		    if(targWin != null) {
		 		var HeightValue = document.body.scrollHeight;
				targWin.style.pixelHeight = HeightValue;
		     }
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
       else{
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
    }
    function noScolling(targObj){
       var targWin = targObj.parent.document.all[targObj.name];
 	   alert(targObj.name)
 	   targWin.scrolling="no";
 	   
    }
    
    var setError;
    setError=function (msg){
        setOperationMessage("error",msg);
    }
    var setMessage;
    setMessage=function (msg){
        setOperationMessage("message",msg);
    }
    function setOperationMessage(divId,msg){
        if(""==msg) return;
        var errorDiv = $(msg);
        if(null==errorDiv){
           errorDiv=parent.document.getElementById(msg);
           if(null==errorDiv) alert(msg);
        }
        if(null!=errorDiv)
          errorDiv.innerHTML=msg;
    }

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
	//引发一个onChange事件
	function fireChange(ele){	
		 if(document.all){
	       		ele.fireEvent("onchange");
	     }
	     else{
  		    var evt   =   document.createEvent('HTMLEvents');
		    evt.initEvent('change',true,true);
		     ele.dispatchEvent(   evt   );
	     }
	 }
  //引发一个onClick事件
	function fireClick(ele){
		 if(document.all){
	       		ele.fireEvent("onclick");
	     }
	     else{
  		    var evt   =   document.createEvent('HTMLEvents');
		    evt.initEvent('click',true,true);
		alert(evt);
		     ele.dispatchEvent(   evt   );
	     }
	 }
  /**
   * 创建ActiveX
   */
  function newActiveX(name){
	  try{
	   return  new ActiveXObject(name); 
	  }catch(e){
	    alert("1.导出程序需要你本机安装Microsoft办公程序及其组件."+
	    "\n2.在浏览器菜单项中:[工具]->[internet选项..]->[安全]选项卡中选择[自定义级别..]按钮,之后将其中activeX部分的设置改为'启用'.");
	    return;
	  }
  }
  function getContextPath(){
       return self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)
  }
