/*----------------------------------------------
 * Beangle UI
 * include ToolBar,Grid,EntityAction 
 */
(function( bg, undefined ) {
	bg.alert=function(msg){
		alert(msg);
	}
	function NamedFunction(name,func){
		this.name=name;
		this.func=func;
	}
	/**
	 * 生成一个工具栏
	 * @param divId 工具栏对应的div
	 * @param title  工具栏的标题
	 * @param imageName  工具栏顶头的图片名称
	 * @param hasSeparator 工具栏中是否添加分隔符 bug1,不要把toolbar对应的表格放在form内
	 */
	function ToolBar(divId,title,imageName,hasSeparator){
		this.hasSeparator=(null==hasSeparator)?true:hasSeparator;
		this.itemCount=0;
		this.bar=document.getElementById(divId);
		if(null==this.bar){
			bg.alert("cannot find div with id " + divId);
			return;
		}
		this.id=divId;
		this.bar.className="toolbar";
		var defaultToolBarImageName="info.gif";
		var defaultItemImageName="action.gif";
		var helpImageName="help.gif";
		var imagePath=self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)+"/static/images/action/";

		/**
		 * 设置抬头
		 * 
		 */
		this.init = function (title,imageName){
			var title_div = document.createElement('div');
			title_div.className="toolbar-title";
			if(imageName==null){
				imageName=defaultToolBarImageName;
			}
			title_div.innerHTML='<img class="toolbar-icon" src="'+getImagePath(imagePath,imageName)+'" /><strong>'+title+"</strong>";
			this.bar.appendChild(title_div);
			var msg_div = document.createElement('div');
			msg_div.className="toolbar-msg";
			msg_div.id=this.id+"_msg";
			this.bar.appendChild(msg_div);
			var items_div = document.createElement('div');
			items_div.className="toolbar-items";
			items_div.id=this.id+"_items";
			this.items_div=items_div;
			this.bar.appendChild(items_div);
		}
		this.init(title,imageName);
		
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
		 * 设置按钮的动作
		 * 
		 */
		function setAction(item,action){
			if(null==action){
				bg.alert("action should not be null");
				return;
			}
			if(typeof action=='function'){
				item.onclick=action;
				return;
			}
			if(typeof action=='string'){
				if (action.indexOf('(')!=-1){
					item.onclick= function (){eval(action);}
				}
				else if(action.indexOf('.action')!=-1){
					item.onclick=function (){Go(action)}	 
				}else{
					bg.alert("unsuported action description:"+action);
				}
			}
			if(typeof action=='object'){
				item.onclick=action.func;
				return;
			}
		}
		
		this.addBack = function (msg){
			if(null==msg){
				this.addItem("返回",function (){history.back(-1)},'backward.gif');
			}else{
				this.addItem(msg,function (){history.back(-1)},'backward.gif');
			}
		}
		this.addHelp = function (module){
			this.addItem("帮助",function (){
				if(null==module) bg.alert("施工中..");
				else window.open("help.do?method=help&helpId="+module);},'help.png');
		}

		this.addPrint = function (msg){
			if(null==msg){
				this.addItem("打印","print()");
			}else{
				this.addItem(msg,"print()");
			}
		}

		this.addClose = function (msg){
			if(''==msg|| null==msg){
				msg="关闭";
			}
			this.addItem(msg,"window.close()",'close.gif');
		}
		/**
		 * 添加菜单
		 * 
		 */
		this.addItem = function(title,action,imageName,alt){
			this.addSeparatorAsNeed();
			var item_div = document.createElement('div');
			if(null==imageName){
				imageName=getDefaultImageName(action);
			}
			if(alt==""||alt==null){
				alt=title;
			}
			item_div.innerHTML='<img class="toolbar-icon" src="'+getImagePath(imagePath,imageName)+'" alt="' +alt+'" />'+title;

			item_div.onmouseout=MouseOutItem;
			item_div.onmouseover=MouseOverItem;
			setAction(item_div,action);
			item_div.className="toolbar-item";
			item_div.title=alt;
			this.items_div.appendChild(item_div);
			this.itemCount++;
			return item_div;
		}
		/**
		 * 添加分隔符
		 * 
		 */
		this.addSeparator = function (){  
			var separator = document.createElement('div');
			separator.innerHTML="|";
			separator.className="toolbar-separator";
			this.items_div.appendChild(separator);
		}
		
		this.addSeparatorAsNeed = function (){
			if(this.itemCount!=0&&this.hasSeparator){
				this.addSeparator();
			}
		}
		this.addBackOrClose = function (backCaption, closeCaption) {
			if (parent.location == self.location && (window.history.length <= 1 || window.history.length == null)) {
				this.addClose((null == closeCaption) ? "关闭" : closeCaption);
			} else {
				this.addBack((null == backCaption) ? "后退" : backCaption);
			}
		}
		// 增加空白功能点
		this.addBlankItem = function () {
			this.addSeparatorAsNeed();
			var itemTd = document.createElement('td');
			itemTd.innerHTML="";
			itemTd.width="100%";
			this.tr.appendChild(itemTd);
			this.itemCount++;
		}
		/**
		 * 设置工具栏的消息区
		 * 
		 */
		this.setMessage = function (msg){
			if (typeof msg == "undefined") return;
			document.getElementById(this.id+"_msg").innerHTML=msg;
		}
		
		/**
		 * 在工具栏中添加一个菜单
		 */
		this.addMenu = function(title,action,imageName,alt){
			this.addSeparatorAsNeed();
			var item_div = document.createElement('div');
			alt=alt||title;
			item_div.className="toolbar-item";
			var menuTableId=this.id+this.itemCount+"_menu";
			item_div.id=menuTableId;
			item_div.onmouseout=MouseOutItem;
			item_div.onmouseover=MouseOverItem;
			this.items_div.appendChild(item_div);
			item_div.innerHTML=title+'<img src="'+imagePath+'downarrow.gif" class="toolbar-icon" />';
			var menu = new Menu(menuTableId,item_div);
			item_div.onclick=function (event){displayMenu(event);};
			this.itemCount++;
			return menu;
		}
		
		function displayMenu(event){
			div=bg.event.getTarget(event);
			while(div&&div.tagName!='div'){
				div=div.parentNode;
			}
			menu=div.lastElementChild;
			if(null==menu){alert('menu is null then return and target is '+div);return;}
			if(menu.style.visibility!=""&&menu.style.visibility!="hidden"){
				menu.style.visibility="hidden";
				div.className="toolbar-item-transfer";
			}else{
				menu.style.visibility="visible";
				div.className="toolbar-item-selected";
			}
		}
		/**
		 * 生成一个菜单
		 */
		function Menu(id,item_div){
			var table=document.createElement("table");
			table.className="toolbar-menu";
			table.id=id;
			var mytablebody = document.createElement("tbody");
			table.appendChild(mytablebody);
			item_div.appendChild(table);
			this.table=table; 
			/**
			 * 在菜单中添加一个条目
			 */
			this.addItem = function (title,action,imageName,alt){
				var itemTd = document.createElement('td');
				if(null==imageName){
					imageName=getDefaultImageName(action);
				}
				if(alt==""||alt==null){
					alt=title;
				}
				itemTd.innerHTML='<img class="toolbar-icon" src="'+getImagePath(imagePath,imageName)+'" alt="' +alt+'" />'+title;
				itemTd.onmouseout=MouseOutMenuItem;
				itemTd.onmouseover=MouseOverMenuItem;
				setAction(itemTd,action);
				itemTd.className="toolbar-menuitem";
				itemTd.width="100%";
				var tr = document.createElement('tr');
				tr.appendChild(itemTd);
				if(this.table.tBodies.length==0){
					this.table.appendChild(document.createElement("tbody"));
				}
				this.table.tBodies[0].appendChild(tr);
			}
		}
		
		// /菜单条目的鼠标进入和离开事件响应方法
		function MouseOutMenuItem(e){
			var S=bg.event.getTarget(e);
			while (S&&S.tagName!="td"){S=S.parentNode;}
			if(S)S.className="toolbar-menuitem";
		}
		
		function MouseOverMenuItem(e){
			var S=bg.event.getTarget(e);
			while (S.tagName!="td"){S=S.parentNode;}
			if(S)S.className="toolbar-menuitem-transfer";
		}
		/**
		 * 当鼠标经过工具栏的按钮时
		 * 
		 */
		function MouseOverItem(e){
			var S=bg.event.getTarget(e);
			while (S&&S.tagName!="div"){S=S.parentNode;}
			if(S)S.className="toolbar-item-transfer";
		}
		/**
		 * 当鼠标离开工具栏的按钮时
		 */
		function MouseOutItem(e){
			var S=bg.event.getTarget(e);
			while (S&&S.tagName!="div"){S=S.parentNode;}
			if(S)S.className="toolbar-item";
		}
	}
	bg.extend({'ui.toolbar':function (tableId,title,imageName,hasKeyLine,hasSeparator){
		return new ToolBar(tableId,title,imageName,hasKeyLine,hasSeparator);
		}
	});
	bg.extend({
		'ui.grid':{
			// 鼠标经过和移出排序表格的表头时
			overSortTableHeader : function  (){
				this.style.color='white';
				this.style.backgroundColor ='green'
			},
			outSortTableHeader : function (){
				this.style.borderColor='';
				this.style.color='';
				this.style.backgroundColor ='';
			},
			// 鼠标经过数据行的效果
			currClass:"",
			swapClass:"",
			swapOverTR : function (obj,objClass){
				if(objClass!="roll_down"){
					bg.ui.grid.currClass=objClass;
					obj.className="highlight";
					bg.ui.grid.swapClass=objClass;
				}
			},
			swapOutTR : function (obj){
				if(obj.className!="roll_down"){
				obj.className=bg.ui.grid.swapClass;
				}
			},
			/**
			 * 行选函数。单击行内的任一处，可以选定行头的checkbox或者radio 用法:onclick="onRowChange(event)"
			 */
			onRowChange : function (event){    
				ele =  bg.event.getTarget(event);
				var changed=true;
				if(null!=ele&&ele.tagName=="td"){
					var firstChild = ele.parentNode.firstChild;
					if(firstChild.tagName!="td"){
						firstChild=firstChild.nextSibling;
					}
					ele=firstChild.firstChild;
					while(((typeof ele.tagName)=="undefined")||ele.tagName!="input"){
						ele=ele.nextSibling;
						if(ele==null)return;
					}
					ele.checked = !ele.checked;
				}else if((ele.type=="checkbox")||(ele.type=="radio")){
				}else{
					changed=false;
				}
				// 改变选定行的颜色
				if(null!=ele&&changed){
					if(typeof ele.onchange =="function"){
						ele.onchange();
					}
					if(ele.type=="radio") return;
					if(ele.checked){
						ele.parentNode.parentNode.className="roll_down";
					}else{
						ele.parentNode.parentNode.className="bright";
					}
				}
			},
			//列排序对应的pageId和选中的列
			sort : function (pageId,ele){
				if(null==pageId){
					bg.alert("无法找到元素对应的排序表格！");return;
				}
				var orderByStr=null;
				if(ele.className=="gridhead-item-sort"){
					if(typeof ele.asc!="undefined"){
						orderByStr=ele.asc;
					}
					else{
						orderByStr=ele.id+" asc";
					}
				}else if(ele.className=="gridhead-item-asc"){
					if(typeof ele.desc!="undefined"){
						orderByStr=ele.desc;
					}
					else{
						orderByStr=ele.id.replace(/\,/g," desc,")+" desc";
					}
				}else{
					orderByStr="";
				}
				bg.page.goPage(pageId,1,null,orderByStr);
			},

			/**
			 * 初始化排序表格<br/>
			 * 此函数主要是向已经待排序表格的列头1)添加鼠标事件响应和显示效果. 2)负责将事件传递到用户定义的函数中.
			 * 
			 * 凡是要排序的列,请注名排序单元格的id 和class. 其中id是排序要传递的字段,class为定值gridhead-item-sort.
			 * 除此之外,用户(使用该方法的人)需要自定义一个钩子函数"sortBy(what)",以备调用.
			 * 
			 * @param tableId
			 *            待排序表格的id
			 * @param orderBy
			 *            orderBy 字句 用法: <table>
			 */
			init : function (tableId,orderBy){
				var table= document.getElementById(tableId);
				var thead=table.tHead;
				if(!thead || thead.rows.length==0){
					bg.alert("sortTable ["+tableId+"] without thead"); 
					return;
				}
				columnSort = function(event){
					// this is a td/th
					bg.ui.grid.sort(tableId,this);
				}
				for(var j=0;j<thead.rows.length;j++){
					head=thead.rows[j];
					for(var i=0;i<head.cells.length;i++){
						cell=head.cells[i];
						if(cell.className=="gridhead-item-sort" && null!=cell.id){
							cell.onclick = columnSort;
							cell.onmouseover=bg.ui.grid.overSortTableHeader;
							cell.onmouseout=bg.ui.grid.outSortTableHeader;
							cell.title="点击按 ["+cell.innerHTML+"] 排序";
							var desc=cell.id.replace(/\,/g," desc,")+" desc";
							if(typeof cell.desc !="undefined"){
								desc=cell.desc;
							}
							if(orderBy.indexOf(desc)!=-1){
								cell.className="gridhead-item-desc"
									cell.innerHTML=cell.innerHTML+'<img src="'+bg.getContextPath()+'/static/images/action/sortDesc.gif"  style="border:0"  alt="Arrow" />'
								continue;
							}
							var asc=cell.id+" asc";
							if(typeof cell.asc !="undefined"){
								asc=cell.asc;
							}
							if(orderBy==asc){
								cell.className="gridhead-item-asc"
									cell.innerHTML=cell.innerHTML+'<img src="'+bg.getContextPath()+'/static/images/action/sortAsc.gif"  style="border:0"  alt="Arrow" />'
								continue;
							}
						}
					}
				}
			},
			/**
			 * 将pagebar 扩大到表格宽度
			 */
			spanPagebar : function (pageBarId){
				var colspanNumber=30;
				var pageBarTd=document.getElementById(pageBarId);
				if(null == pageBarTd) return ;
				parentEle=pageBarTd.parentNode;
				while(parentEle && parentEle.tagName!='table'){
					parentEle=parentEle.parentNode;
				}
				if(typeof parentEle.tHead.rows[0].cells.length!=undefined){
					colspanNumber=parentEle.tHead.rows[0].cells.length;
				}
				pageBarTd.colSpan=colspanNumber;
			}
		}
	});
	
	// Action---------------------------------------------------------------------
	if (typeof entityActions == "undefined") {
		entityActions = new Object();
	}
	function getEntityAction(id){
		if (null == id || typeof id != "string") {
			for (var fid in entityActions) {
				return entityActions[fid];
			}
		}
		else {
			if (typeof entityActions[id] == "undefined") {
				bg.alert("without actionform with id" + id);
			}
			else {
				return entityActions[id];
			}
		}
	}
	function EntityAction(id,entity,action,actionQueryStr,target){
		this.id=id;
		this.entity=entity;
		this.action=action;
		this.actionQueryStr=actionQueryStr;
		this.target=target;
		entityActions[id]=this;
		this.getForm=function (){
			actionForm=document.getElementById(id+"_form");
			if(null==actionForm){
				actionForm=document.createElement("form");
				actionForm.setAttribute("id",id+"_form");
				actionForm.setAttribute("action",this.action);
				actionForm.setAttribute("method","POST");
				document.body.appendChild(actionForm);
			}
			return  actionForm;
		};
		this.addParam=function(name,value){
			bg.form.addInput(this.getForm(),name,value);
		}
		if(null!=target&&''!=target){
			var fm = this.getForm();
			if(null!=fm) fm.target=target;
		}
		function beforeSubmmitId(id,method) {
			aform=getEntityAction(id);
			var ids = bg.input.getCheckBoxValues(aform.entity+"Id");
			if (ids == null || ids == "") {
				bg.alert("你没有选择要操作的记录！");
				return false;
			}
			form=aform.getForm();
			form.action = aform.action + "?method=" + method;
			if(""!=aform.actionQueryStr){
				bg.form.addHiddens(form,aform.actionQueryStr);
				bg.form.addParamsInput(form,aform.actionQueryStr);
			}
			return true;
		}
		function submitIdAction(id,method,multiId,confirmMsg){
			aform=getEntityAction(id);
			if (beforeSubmmitId(aform.id,method)) {
				if(null!=confirmMsg && ''!=confirmMsg){
					if(!confirm(confirmMsg))return;
				}
				bg.form.submitId(aform.getForm(),aform.entity + "Id",multiId);
			}
		}
		this.remove=function(confirmMsg){
			confirmMsg=confirmMsg||'确认删除?';
			return new NamedFunction('remove',function(){
				submitIdAction(id,'remove',false,confirmMsg);
			});
		}
		this.add = function(){
			return new NamedFunction('add',function(){
				aform=getEntityAction(id);
				form=aform.getForm();
				if(""!=aform.actionQueryStr) bg.form.addHiddens(form,aform.actionQueryStr);
				bg.form.addInput(form,aform.entity + 'Id',"");
				if(""!=aform.actionQueryStr) bg.form.addParamsInput(form,aform.actionQueryStr);
				bg.form.submit(form,aform.action + "?method=edit");
			});
		}
		
		this.info = function(){
			return new NamedFunction('info',function(){
				submitIdAction(id,'info',false)
			});
		}
		
		this.edit = function (){
			return new NamedFunction('edit',function(){
				submitIdAction(id,'edit',false);
			});
		}
		
		this.single = function(methodName,confirmMsg,extparams){
			return new NamedFunction(methodName,function(){
				form=getEntityAction(id).getForm();
				if(null!=extparams) addHiddens(form,extparams);
				submitIdAction(id,methodName,false,confirmMsg);
			});
		}
		
		this.multi = function(methodName,confirmMsg,extparams){
			return new NamedFunction(methodName,function(){
				try {
					form = getEntityAction(id).getForm();
					if(null!=extparams) bg.form.addHiddens(form, extparams);
					submitIdAction(id, methodName, true, confirmMsg);
				}catch(e){
					bg.alert(e)
				}
			});
		}
		this.method=function(methodName,confirmMsg,extparams){
			return  new NamedFunction(methodName,function(){
				aform=getEntityAction(id);
				form=aform.getForm();
				if(null!=extparams){
					bg.form.addHiddens(form,extparams);
				}
				if(""!=aform.actionQueryStr){
					bg.form.addHiddens(form,aform.actionQueryStr);
					bg.form.addParamsInput(form,aform.actionQueryStr);
				}
				bg.form.submit(form,aform.action + "?method=" + methodName);
			});
		}
		this.exportData=function (format,keys,titles,extparams){
			format = format || "xls";
			keys = keys||"";
			titles = titles||"";
			extparams = extparams||"";
			extparams = "&format=" + format +"&keys=" + keys + "&titles=" + titles + extparams;
			return this.method('export',null,extparams);
		}
	}
	bg.extend({entityaction:EntityAction});
})(beangle)
