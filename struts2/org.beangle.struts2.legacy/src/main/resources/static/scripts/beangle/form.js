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
       }
       if(isMulti){
          addInput(form,id+'s',selectId,"hidden");
       }else{
          addInput(form,id,selectId,"hidden");
       }
       if(null!=promptMsg){
          if(!confirm(promptMsg))return;
       }
       form.submit();
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
	function isMultiId(str){
		return str.indexOf(",")>0;
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