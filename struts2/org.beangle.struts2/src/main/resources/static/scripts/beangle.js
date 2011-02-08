(function( window, undefined ) {
	if(beangle) return;
	var beangle=function (){
		return true;
	};
	/** extend function */
	beangle.extend= function(map){
		for(attr in map){
			var attrs=attr.split(".");
			var obj=beangle;
			for(var i=0 ;i<attrs.length-1;i++){
				obj[attrs[i]]=obj[attrs[i]]||{};
				obj=obj[attrs[i]];
			}
			obj[attrs[attrs.length-1]]=map[attr];
		}
	}
	window.beangle=beangle;
	window.bg=beangle;
	beangle.extend({
		//jump to href or anchor
		Go : function (obj,target){
			url=obj;
			if(typeof obj =="object" && obj.tagName.toLowerCase()=="a"){
				url=obj.href;
				if(!target){
					target=bg.findTarget(obj);
				}
			}
			if(!target) target="_self";
			if("_self"==target){ self.location=url;}
			else if("_parent"==target){self.parent.location=url;}
			else{
				if(!bg.isAjaxTarget(target)){
					//FIXME _blank,_top
					document.getElementById(target).src=url;
				}else{
					jQuery('#'+target).load(url);
				}
			}
		},
		getContextPath : function (){
			return self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)
		},
		ready:function (fn){
			if (window.addEventListener) {
				 window.addEventListener("load", fn, false);
			}else if (window.attachEvent) {
				window.attachEvent("onload", fn);
			}else {window.onload = fn;}
		},
		isAjaxTarget : function (target){
			if(!target) return false;
			if(target==""||target=="new"||target=="_blank"||target=="_self"||target=="_parent"){
				return false;
			}
			targetEle=document.getElementById(target);
			if(!targetEle) return false;
			tagName=targetEle.tagName.toLowerCase();
			if(tagName=="iframe" || tagName=="object"){
				return false;
			}
			return true;
		},
		normalTarget : function(target){
			if(target==""||target=="new"||target=="_blank"||target=="_self"||target=="_parent"){
				return target;
			}
			if(!document.getElementById(target)) return "_self";
			else return target;
		},
		findTarget : function(ele){
			p=ele.parentNode;
			finalTarget="_self";
			while(p){
				if(p.id && p.className  && p.className.indexOf("_ajax_container")>-1){
					finalTarget = p.id;
					break;
				}else{
					if(p==p.parentNode) p=null;
					else p=p.parentNode;
				}
			}
			ele.target=finalTarget;
			return finalTarget;
		}
	});
	
	// Assert------------------------
	beangle.extend({
		assert:{
			notNull : function(object,message){
				if(null==object){
					alert(message);
				}
			}
		}
	});
	
	// Logger----------------------------
	beangle.extend({
		logger:{
			// debug=0;info=1;warn=2;error=3;fatal=4;disabled=5
			level : 1,
			debug : function(message){
				if(beangle.logger.level<=0){
					var msg = '[beangle] ' + message;
					if (window.console && window.console.log) {
						window.console.log(message);
					}else if (window.opera && window.opera.postError) {
						window.opera.postError(msg);
					}else{
						alert(msg);
					}
				}
			}
		}
	});
	
	// Event--------------------------------------------------
	beangle.extend({
		event:{
			portable: function (e){
				if(!e){
					return window.event;
				}else{
					return e;
				}
			},
			/**
			 *获得事件背后的元素
			 */
			getTarget: function (e){
				e=bg.event.portable(e);
				if(document.all){
					return e.srcElement;
				}else{
					return e.target;
				}
			}
		}
	});
	
	// Input----------------------------------------------------
	beangle.extend({
		input:{
			toggleCheckBox : function (chk,event){
				bg.input.boxAction(chk, "toggle",event);
			},
			/**
			 * 返回单选列表中选择的值
			 * @return 没有选中时,返回""
			 */
			getRadioValue : function (radioName){
				return bg.input.boxAction(document.getElementsByName(radioName), "value");
			},
			
			/**
			 * 返回多选列表中选择的值
			 * @return 多个值以,相隔.没有选中时,返回"" 
			 */
			getCheckBoxValues : function (chkname){
				var tmpIds= bg.input.boxAction(document.getElementsByName(chkname), "value");
				if(tmpIds==null)return "";
				else return tmpIds;
			},
			/**
			 * modify by chaostone 2006-8-2
			 * 将反选取消,改为全选或者全部取消
			 */
			boxAction : function (box, action,event){
				var val = "";
				if (box){
					if (! box[0]){
						if (action == "selected"){
							return box.checked;
						} else if (action == "value"){
							if (box.checked)
								val = box.value;
						} else if (action == "toggle"){
							var srcElement = beangle.event.getTarget(event);
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
							    var srcElement = beangle.event.getTarget(event);
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
		}
	});

	//IFrame--------------------------------------------------------
	beangle.extend({
		iframe:{
			adaptSelf:function (){
				bg.iframe.adapt(self);
			},
			/** iframe 页面自适应大小
			 * @targObj    iframe
			 * @extraHight 
			 */
			adapt: function (targObj,extraHight){
				if(null==targObj || targObj.name=="")
					return;
				if(targObj.parent == targObj)return;
				if (targObj.parent == window.top) {
					if(targObj.parent.document.body.style.overflowY=="hidden") return;
				}
				var frames = targObj.parent.document.getElementsByName(targObj.name);
				if(frames.length<1) return;
				var targWin=frames[0];
				if(targWin != null && (targWin.scrolling=="no" || targWin.className=="autoadapt")) {
					var heightValue = targObj.document.body.scrollHeight;
					totalHeight=heightValue + ((null==extraHight)?0:extraHight);
					myHeight=0;
					if(targWin.style.height){
						myHeight=parseInt(targWin.style.height.substring(0,targWin.style.height.length-2));
					}
					if((totalHeight>0) &&  totalHeight> myHeight){
						targWin.style.height = totalHeight+"px";
						bg.logger.debug('adapt frame:'+targObj.name+" height "+targWin.style.height);
					}
				}
				bg.iframe.adapt(targObj.parent);
			}
		}
	});
	//About From
	beangle.extend({
		form:{
			submit : function (myForm,action,target,onsubmit){
				if((typeof myForm)=='string') myForm=document.getElementById(myForm);
				if(onsubmit){
					var rs=null;
					if(typeof onsubmit == "function"){
						rs=onsubmit(myForm);
					}else{
						rs=eval(onsubmit);
					}
					if(!rs){
						//if(rs == undefined) alert("ensure onsubmit function return true/false");
						return;
					}
				}
				//FIXME check target is(iframe,reserved,div)
				var submitTarget=(null!=target)?target:myForm.target;
				if(action==null){
					action=myForm.action;
				}
				if(action.indexOf("http://")==0){
					action=action.substring(action.indexOf("/",7));
				}
				myForm.action=action;
				if(!submitTarget){
					submitTarget=bg.findTarget(myForm);
				}
				if(!bg.isAjaxTarget(submitTarget)){
					myForm.target=bg.normalTarget(submitTarget);
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
						submitx = document.createElement('input');
						submitx.setAttribute("id",sumbitBtnId);
						submitx.setAttribute("type",'submit');
						submitx.setAttribute("style",'display:none');
					}
					myForm.appendChild(submitx);
				}
				var options_submit = {id:sumbitBtnId,jqueryaction:"button",targets:submitTarget,href:'#'};
				if (typeof jQuery != "undefined") {
					jQuery.struts2_jquery.bind(jQuery('#'+sumbitBtnId), options_submit);
				}
				//myForm.submit();
				submitx.click();
			},
	
			/**
			 * 提交要求含有id的表单
			 * @param form 带提交的表单
			 * @param id 要提交id的名称
			 * @param isMulti(可选)是否允许多个id选择,默认支持一个
			 * @param action (可选) 指定form的action
			 */
			submitId : function (form,id,isMulti,action,promptMsg){
				var selectId = bg.input.getCheckBoxValues(id);
				if(null==isMulti)
					isMulti=false;
				if(""==selectId){
					alert(isMulti?"请选择一个或多个进行操作":"请选择一个进行操作");
					return;
				}
				if(!isMulti && (selectId.indexOf(",")>0)){
					alert("请仅选择一个");
					return;
				}
				if(null!=action){
					form.action=action;
				}else{
					action=form.action;
				}
				bg.form.addInput(form,(isMulti?(id+'s'):id),selectId,"hidden");
				if(null!=promptMsg){
					if(!confirm(promptMsg))return;
				}
				bg.form.submit(form,action);
			},
			/**
			 * 向form中添加一个input。
			 * @param form 要添加输入的form
			 * @param name input的名字
			 * @param value input的值
			 * @param type input的类型，默认为hidden
			 * @author chaostone 2006-4-7
			 */
			addInput : function (form,name,value,type){
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
			},
			ecodeParams : function (params){
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
			},
			/**
			 * 从form表单中，抽出含有指定前缀的输出参数，
			 * 将其作为一个参数加入到to表单中。
			 */
			setSearchParams : function (from,to,prefix){
				bg.form.addInput(to,'params',"");
				var params=bg.form.getInputParams(from,prefix,false);
				bg.form.addInput(to,'params',params);
			},

			addHiddens : function (form,paramSeq){
				bg.assert.notNull(paramSeq,"paramSeq for addHiddens must not be null");
				var params = paramSeq.split("&");
				for(var i=0;i<params.length;i++){
					if(params[i]!=""){
						var name = params[i].substr(0,params[i].indexOf("="));
						var value =params[i].substr(params[i].indexOf("=")+1);
						bg.form.addInput(form,name,value,"hidden");
					}
				}
			},
			
			addParamsInput : function (form,value){
				bg.form.addInput(form,"params",value,"hidden");
			},
			transferParams : function (from ,to,prefix,getEmpty){
				if(getEmpty==null)
					getEmpty=true;
				var params = bg.form.getInputParams(from,prefix,getEmpty);
				bg.form.addHiddens(to,params);
			},

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
			getInputParams : function (form,prefix,getEmpty){
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
			},
			goToPage : function (form,pageNo,pageSize,orderBy){
				if((typeof form)!="object"){alert("[goToPage:]form is not well defined.");return;}
				//form.method="post"; for avoid "method" input
				if(null!=pageNo){
					if(isNaN(pageNo)){
						alert("输入分页的页码是:"+pageNo+",它不是个整数");
						return;
					}
					bg.form.addInput(form,"pageNo",pageNo,"hidden");
				}else{
					bg.form.addInput(form,"pageNo",1,"hidden");
				}
				if(null!=pageSize){
					if(isNaN(pageSize)){
						alert("输入分页的页长是:"+pageSize+",它不是个整数");
						return;
					}
					bg.form.addInput(form,"pageSize",pageSize,"hidden");
				}else{
					bg.form.addInput(form,"pageSize","","hidden");
				}
				if(null!=orderBy&&orderBy!="null"){
					bg.form.addInput(form,"orderBy",orderBy,"hidden");
				}else{
					bg.form.addInput(form,"orderBy","","hidden");
				}
				//alert("in goToPage");
				form.submit();
			},
			goToFirstPage : function (form){
				bg.form.goToPage(form,1);
			}
		}
	});
	
	//select---------------------
	beangle.extend({
		select:{
			getValues : function (select){
				var val = "";
				var options = select.options;
				for (var i=0; i<options.length; i++){
					if (val != "")
						val = val + ",";
					val = val + options[i].value;
				}
				return val;
			},
			getSelectedValues : function (select){
				var val = "";
				var options = select.options;
				for (var i=0; i<options.length; i++){   
					if (options[i].selected){
						if (val != "")
							val = val + ",";	
						val = val + options[i].value;
					}
				}
				return val;
			},
			hasOption : function (select, op){ 
				for (var i=0; i<select.length; i++ ){
					if (select.options[i].value == op.value)
						return true;
				}
				return false;
			},
			
			moveSelected : function (srcSelect, destSelect){
				for (var i=0; i<srcSelect.length; i++){
					if (srcSelect.options[i].selected){ 
						var op = srcSelect.options[i];
						if (!bg.select.hasOption(destSelect, op)){
						   destSelect.options[destSelect.length]= new Option(op.text, op.value);
						}
					 }
				}
				bg.select.removeSelected(srcSelect);   
				bg.select.clearStatus(srcSelect);
			},
			
			clearStatus : function (select){
				for (var i=0; i<select.length; i++)
					select.options[i].selected = false;
			},

			removeSelected : function (select){
				var options = select.options;
				for (var i=options.length-1; i>=0; i--){   
					if (options[i].selected){  
						options[i] = null;
					}
				}
			},
			/**
			 * 设定选择框中的选择项(单项)
			 */
			setSelected : function (select,idSeq){
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
		}
	});

	// Cookie----------------------------------------------------------------------------------------
	beangle.extend({
		cookie:{
			get : function (cookieName) {
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
			},
			set : function (name, value, path){
				if(null==path)
					path="/";
				expires=new Date();
				expires.setTime(expires.getTime()+(86400*30));
				document.cookie=name+"="+value+"; expires="+expires.toGMTString()+"; path="+path;
			}
		}
	});
	
	// Page---------------------------------------------------------------------
	if(typeof pages=="undefined"){
		pages=new Object();
	}

	function Page(id,action,target){
		this.id=id;
		this.action=action;
		this.target=target;
		this.paramMap={};
		this.params = function(){
			return this.paramMap;
		}
		this.maxPageNo=1;
		pages[id]=this;
		
		this.addParams = function(paramSeq){
			bg.assert.notNull(paramSeq,"paramSeq for addHiddens must not be null");
			var paramArray = paramSeq.split("&");
			for(var i=0;i<paramArray.length;i++){
				oneParam=paramArray[i];
				if(oneParam!=""){
					var name = oneParam.substr(0,oneParam.indexOf("="));
					var value = oneParam.substr(oneParam.indexOf("=")+1);
					this.paramMap[name]=value;
				}
			}
		}
		// 检查分页参数
		this.checkPageParams = function (pageNo, pageSize,orderBy){
			if(null!=pageNo){
				if(isNaN(pageNo)){
					bg.alert("输入分页的页码是:"+pageNo+",它不是个整数");
					return false;
				}
				if(pages[id].maxPageNo!=null){
					if(pageNo>this.maxPageNo){
						pageNo=this.maxPageNo;
					}
				}
				this.paramMap['pageNo']=pageNo;
			}
			if(null!=pageSize){
				if(isNaN(pageSize)){
					bg.alert("输入分页的页长是:"+pageSize+",它不是个整数");
					return false;
				}
				this.paramMap["pageSize"]=pageSize;
			}
			if(null!=orderBy && orderBy!="null"){
				this.paramMap["orderBy"]=orderBy;
			}
			return true;
		}
		// jump to page using form submit
		this.goPageNormal = function (pageNo,pageSize,orderBy){
			myForm=document.createElement("form");
			myForm.setAttribute("action",this.action);
			myForm.setAttribute("method","POST");
			for(var key in this.paramMap){
				value=this.paramMap[key];
				if(value != ""){
					bg.form.addInput(myForm,key,value,"hidden");
				}
			}
			document.body.appendChild(myForm);
			myForm.submit();
		}
		// jump to page using ajax
		this.goPageAjax = function (pageNo,pageSize,orderBy){
			myForm=document.getElementById("form_"+this.id);
			if(null==myForm){
				myForm=document.createElement("form");
				myForm.setAttribute("id","form_"+this.id);
				myForm.setAttribute("action",this.action);
				myForm.setAttribute("method","POST");
				document.body.appendChild(myForm);
			}
			for(var key in this.paramMap){
				value=this.paramMap[key];
				if(value!=""){
					bg.form.addInput(myForm,key,value,"hidden");
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
			options_submit.targets = this.target;
			options_submit.href = this.action;
			options_submit.formids = "form_"+this.id;
			$.struts2_jquery.bind($('#submitx'),options_submit);
			// myForm.submit();
			submitx.click();
		}
	}
	bg.extend({
		page:function (id,action,target){return new Page(id,action,target);},
		'page.goPage':function (id, pageNo, pageSize, orderBy){
			if(pages[id]==null){
				bg.alert("page id for ["+ id +"] is not well defined.");
				return false;
			}
			onePage=pages[id];
			if(onePage.checkPageParams(pageNo,pageSize,orderBy)){
				if(onePage.target && document.getElementById(onePage.target)){
					onePage.goPageAjax(pageNo,pageSize,orderBy);
				}else{
					onePage.goPageNormal(pageNo,pageSize,orderBy);
				}
			}
		}
	});
	// alert(document.body);
	beangle.ready(beangle.iframe.adaptSelf);
})(window);