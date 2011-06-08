/*----------------------------------------------
 * Beangle UI
 * include ToolBar,Grid,EntityAction 
 */
(function( bg, undefined ) {
	bg.alert=function(msg){
		alert(msg);
	}
	bg.uitheme="default"
	
	function NamedFunction(name,func){
		this.name=name;
		this.func=func;
	}
	/**
	 * 生成一个工具栏
	 * @param divId 工具栏对应的div
	 * @param title  工具栏的标题
	 * @param imageName  工具栏顶头的图片名称
	 */
	function ToolBar(divId,title,imageName){
		this.itemCount=0;
		this.bar=document.getElementById(divId);
		if(null==this.bar){
			bg.alert("cannot find div with id " + divId);
			return;
		}
		this.id=divId;
		this.separator="|";
		this.bar.className="toolbar";
		var defaultToolBarImageName="info.png";
		var defaultItemImageName="action.png";
		var helpImageName="help.png";
		var imagePath=self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)+"/static/themes/"+ bg.uitheme +"/icons/16x16/actions/";
		
		this.setTitle=function(newTitle,imageName){
			if(!newTitle) return;
			if(imageName==null)imageName=defaultToolBarImageName;
			this.title_div.innerHTML='<img class="toolbar-icon" src="'+getImagePath(imagePath,imageName)+'" /><strong>'+newTitle+"</strong>";
		}
		this.setSeparator=function(separator){
			this.separator=separator;
		}
		/**
		 * 设置抬头
		 * 
		 */
		this.init = function (title,imageName){
			var title_div = document.createElement('div');
			title_div.className="toolbar-title";
			this.bar.appendChild(title_div);
			this.title_div=title_div;
			this.setTitle(title);
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
		
		this.addHr=function(){
			hrdiv=this.appendDiv(null,"toolbar-line");
			hrdiv.innerHTML='<img height="1" width="100%" align="top" src="' + imagePath + 'keyline.png" />';
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
				if(action.indexOf("add")!=-1||action.indexOf("new")!=-1) return "new.png";
				if(action.indexOf("remove")!=-1||action.indexOf("delete")!=-1) return "edit-delete.png";
				if(action.indexOf("update")!=-1||action.indexOf("edit")!=-1||action.indexOf("Edit")!=-1) return "update.png";
				if(action.indexOf("export")!=-1) return "excel.png";
				if(action.indexOf("copy")!=-1) return "edit-copy.png";
				if(action.indexOf("print")!=-1) return "print.png";
				if(action.indexOf("refresh")!=-1) return "refresh.png";
				if(action.indexOf("close")!=-1) return "close.png";
				if(action.indexOf("save")!=-1) return "save.png";
				if(action.indexOf("download")!=-1) return "download.png";
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
		
		this.addBack = function (title){
			if(null==title){
				this.addItem("返回",function (){history.back(-1)},'backward.png');
			}else{
				this.addItem(title,function (){history.back(-1)},'backward.png');
			}
		}
		this.addHelp = function (module){
			this.addItem("帮助",function (){
				if(null==module) bg.alert("施工中..");
				else window.open("help.action?helpId="+module);},'help-contents.png');
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
			this.addItem(msg,"window.close()",'close.png');
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
		this.addDiv=function(className){
			var newDiv = document.createElement('div');
			if(className)newDiv.className=className;
			this.items_div.appendChild(newDiv);
			return newDiv;
		}
		this.appendDiv=function(id,className){
			var newDiv = document.createElement('div');
			if(id)newDiv.setAttribute("id",id);
			if(className)newDiv.className=className;
			document.getElementById(this.id).appendChild(newDiv);
			return newDiv;
		}
		/**
		 * 添加分隔符
		 * 
		 */
		this.addSeparator = function (){
			if(this.separator){
				this.addDiv("toolbar-separator").innerHTML=this.separator;
			}
		}
		
		this.addSeparatorAsNeed = function (){
			if(this.itemCount!=0){
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
			this.addDiv("toolbar-group-separator").innerHTML="&nbsp;";
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
			item_div.innerHTML='<img src="'+imagePath+'downarrow.png" class="toolbar-icon" />'+title;
			var menu = new Menu(menuTableId,item_div);
			item_div.onclick=function (event){displayMenu(event);};
			this.itemCount++;
			return menu;
		}
		
		function displayMenu(event){
			div=bg.event.getTarget(event);
			while(div && div.tagName.toLowerCase()!='div'){
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
			var o=bg.event.getTarget(e);
			while (o && o.tagName.toLowerCase()!="td"){o=o.parentNode;}
			if(o)o.className="toolbar-menuitem";
		}
		
		function MouseOverMenuItem(e){
			var o=bg.event.getTarget(e);
			while (o && o.tagName.toLowerCase()!="td"){o=o.parentNode;}
			if(o)o.className="toolbar-menuitem-transfer";
		}
		/**
		 * 当鼠标经过工具栏的按钮时
		 * 
		 */
		function MouseOverItem(e){
			var o=bg.event.getTarget(e);
			while (o&&o.tagName.toLowerCase()!="div"){o=o.parentNode;}
			if(o)o.className="toolbar-item-transfer";
		}
		/**
		 * 当鼠标离开工具栏的按钮时
		 */
		function MouseOutItem(e){
			var o=bg.event.getTarget(e);
			while (o&&o.tagName.toLowerCase()!="div"){o=o.parentNode;}
			if(o)o.className="toolbar-item";
		}
	}
	bg.extend({'ui.toolbar':function (divId,title,imageName){
		return new ToolBar(divId,title,imageName);
		}
	});
	
	bg.extend({'ui.gridbar':function(divIds,title){
		this.divIds=divIds;
		this.pageId=null;
		this.title=title;
		this.toolbars=[];
		for(var i=0;i<divIds.length;i++){
			this.toolbars[i]=bg.ui.toolbar(divIds[i],title);
			this.toolbars[i].setSeparator("");
			document.getElementById(divIds[i]).className="gridbar";
			document.getElementById(divIds[i]+"_items").className="gridbar-items";
		}
		this.pageId=function(givenId){
			this.pageId=givenId;
			return this;
		}
		this.addItem=function(title,action,imageName,alt){
			for(var i=0;i<this.toolbars.length;i++){
				this.toolbars[i].addItem(title,action,imageName,alt);
			}
		}
		this.addBack=function(title,action){
			for(var i=0;i<this.toolbars.length;i++){
				this.toolbars[i].addBack(title);
			}
		}
		this.addBackOrClose=function(){
			for(var i=0;i<this.toolbars.length;i++){
				this.toolbars[i].addBackOrClose();
			}
		}
		this.addBlankItem=function(title,action,imageName,alt){
			for(var i=0;i<this.toolbars.length;i++){
				this.toolbars[i].addBlankItem(title,action,imageName,alt);
			}
		}
		this.addPage=function(onePage,ranks,titles){
			this.myPage=onePage;
			for(var i=0;i<this.toolbars.length;i++){
				pageDiv=this.toolbars[i].appendDiv(divIds[i]+'_page',"girdbar-pgbar");
				bg.ui.pagebar(onePage,pageDiv,ranks,titles);
			}
			return this;
		}
		this.addEntityAction=function(entity,onePage){
			return new bg.entityaction(entity,onePage);
		}
		this.addPrint=function(msg){
			for(var i=0;i<this.toolbars.length;i++){
				this.toolbars[i].addPrint(msg);
			}
		}
		this.addMenu=function(title,action,imageName,alt){
			return new menus(title,action,imageName,alt,this.toolbars);
		}
		function menus(title,action,imageName,alt,bars){
			var menu = new Array();
			for(var i=0;i<bars.length;i++){
				menu[i] = bars[i].addMenu(title,action,imageName,alt);
			}
			this.addItem = function (title,action,imageName,alt){
				for(var i=0;i<menu.length;i++){
					menu[i].addItem(title,action,imageName,alt)
				}
			}
		}
	}});
	
	bg.extend({'ui.pagebar':function (onePage,pageDiv,ranks,titles){
		if(onePage.total==0) return;
		if(!ranks) ranks=[10,20,30,50,70,100,200,500,1000];
		if(!titles) titles={first:'« First',previous:'‹ Previous',next:'Next ›',last:'Last »',no:'No:',size:'Size:',change:'Click me to change page size'};
		maxPageNo= onePage.maxPageNo;
		addAnchor=function(text,pageNumber){
			pageHref=document.createElement('a');
			pageHref.setAttribute("href","#");
			pageHref.innerHTML=text;
			pageHref.style.padding="0px 2px 0px 2px";
			pageDiv.appendChild(pageHref);
			jQuery(pageHref).click(function(){onePage.goPage(pageNumber)});
		}
		if(onePage.pageNo>1){
			addAnchor(titles['first'],1);
			addAnchor(titles['previous'],onePage.pageNo-1);
		}
		labelspan=document.createElement('span');
		labelspan.innerHTML="<strong>" + onePage.startNo +"</strong> - <strong>"+ onePage.endNo + "</strong> of <strong>" + onePage.total + "</strong>";
		labelspan.style.padding="0px 2px 0px 2px";
		pageDiv.appendChild(labelspan);
		var numSpan=jQuery(labelspan)
		numSpan.attr('title',titles['change'])
		numSpan.mouseover(function (){this.className='pgbar-label'});
		numSpan.mouseout(function(){this.className=''});
		numSpan.click(function(){this.parentNode.style.marginTop="0px";this.nextSibling.style.display='';this.style.display='none'});
		
		pagespan=document.createElement('span');
		pagespan.style.display="none";
		pageInput=document.createElement('input');
		pagespan.innerHTML=titles['no'];
		pageInput.className="pgbar-input";
		pagespan.appendChild(pageInput);

		var pageInputJ=jQuery(pageInput)
		pageInputJ.attr("value",onePage.pageNo+"/"+maxPageNo);
		pageInputJ.attr("id",pageDiv.id+"_input");
		pageInputJ.focus(function(){this.value=''});
		pageInputJ.blur(function(){if(!this.value) this.value= onePage.pageNo+"/"+maxPageNo});
		//pageInputJ.change(function(){onePage.goPage(this.value)});
		
		if(ranks && (ranks.length>0)){
			pageNoSelect=document.createElement('select');
			pageNoSelect.id=pageDiv.id+"_select";
			pagespan.appendChild(pageNoSelect);
			pageNoSelect.className="pgbar-selbox";
			pageNoSelect.title="page size";
			var selectIndex=0;
			for(var i=0;i<ranks.length;i++){
				if(ranks[i]==onePage.pageSize) selectIndex=i;
				pageNoSelect.options.add(new Option(ranks[i], ranks[i]));
			}
			//jQuery(pageNoSelect).change(function (){onePage.goPage(1,this.value)});
			pageNoSelect.selectedIndex = selectIndex;
		}
		//add go button
		var submitBtn = document.createElement('input');
		submitBtn.setAttribute("type",'button');
		submitBtn.setAttribute("name",'gogo');
		submitBtn.value="Go"
		submitBtn.className="pgbar-go";
		pagespan.appendChild(submitBtn);
		var changePage=function(){
			var pageNo=document.getElementById(pageDiv.id+'_input').value;var endIndex=pageNo.indexOf("/"+onePage.maxPageNo);
			if(-1!=endIndex){pageNo=pageNo.substring(0,endIndex)}
			onePage.goPage(pageNo,document.getElementById(pageDiv.id+'_select').value);
		}
		jQuery(submitBtn).click(function (){changePage()});
		
		pageDiv.appendChild(pagespan);
		jQuery(pagespan).keypress(function(event){
			if (!event) {event = window.event;}
			if (event && event.keyCode && event.keyCode == 13) {changePage();return false;}
		});
		
		if(onePage.pageNo<onePage.maxPageNo){
			addAnchor(titles['next'],onePage.pageNo+1);
			addAnchor(titles['last'],onePage.maxPageNo);
		}
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
			mouseOverGrid : function (){
				if((typeof this.className)=="undefined") return;
				myclass=this.className;
				selectIndex=myclass.indexOf("griddata-selected");
				if(-1 != selectIndex) return;
				overIndex=myclass.indexOf("griddata-over");
				if(-1 == overIndex){
					this.className=myclass+" "+ "griddata-over"
				}else{
					this.className=myclass.substring(0,overIndex);
				}
			},
			/**
			 * 行选函数。单击行内的任一处，可以选定行头的checkbox或者radio 用法:onclick="onRowChange(event)"
			 */
			onRowChange : function (event){    
				ele =  bg.event.getTarget(event);
				var changed=true;
				if(null!=ele && ele.tagName.toLowerCase()=="td"){
					var firstChild = ele.parentNode.firstChild;
					while(firstChild.tagName ==null || firstChild.tagName.toLowerCase()!="td"){
						firstChild=firstChild.nextSibling;
					}
					ele=firstChild.firstChild;
					while(((typeof ele.tagName)=="undefined")||ele.tagName.toLowerCase()!="input"){
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
					row=ele.parentNode.parentNode;
					if((typeof row.className)=="undefined") return;
					selectIndex=row.className.indexOf("griddata-selected");
					if(ele.checked){
						if(-1 == selectIndex) row.className=row.className +" "+ "griddata-selected";
					}else{
						if(-1 != selectIndex) row.className=row.className.substring(0,selectIndex);
					}
				}
			},
			//列排序对应的onePage和选中的列
			sort : function (onePage,ele){
				if(null==onePage){
					bg.alert("无法找到元素对应的排序表格！");return;
				}
				var orderByStr=null;
				if(ele.className=="gridhead-sortable"){
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
				onePage.goPage(1,null,orderByStr);
			},

			/**
			 * 初始化排序表格<br/>
			 * 此函数主要是向已经待排序表格的列头1)添加鼠标事件响应和显示效果. 2)负责将事件传递到用户定义的函数中.
			 * 
			 * 凡是要排序的列,请注名排序单元格的id 和class. 其中id是排序要传递的字段,class为定值gridhead-kable.
			 * 除此之外,用户(使用该方法的人)需要自定义一个钩子函数"sortBy(what)",以备调用.
			 * 
			 * @param tableId 待排序表格的id
			 * @param onePage 与表格对应的page对象
			 */
			init : function (tableId,onePage){
				var table= document.getElementById(tableId);
				var thead=table.tHead;
				if(!thead || thead.rows.length==0){
					bg.alert("sortTable ["+tableId+"] without thead"); 
					return;
				}
				orderBy=onePage.orderby;
				columnSort = function(event){
					// this is a td/th
					bg.ui.grid.sort(onePage,this);
				}
				for(var j=0;j<thead.rows.length;j++){
					head=thead.rows[j];
					for(var i=0;i<head.cells.length;i++){
						cell=head.cells[i];
						if(cell.className=="gridhead-sortable" && null!=cell.id){
							cell.onclick = columnSort;
							cell.onmouseover=bg.ui.grid.overSortTableHeader;
							cell.onmouseout=bg.ui.grid.outSortTableHeader;
							cell.title="点击按 ["+cell.innerHTML+"] 排序";
							var desc=cell.id.replace(/\,/g," desc,")+" desc";
							if(typeof cell.desc !="undefined"){
								desc=cell.desc;
							}
							if(orderBy.indexOf(desc)!=-1){
								cell.className="gridhead-desc"
									cell.innerHTML=cell.innerHTML+'<img src="'+bg.getContextPath()+'/static/themes/' + bg.uitheme + '/icons/16x16/actions/sort-desc.png"  style="border:0"  alt="Arrow" />'
								continue;
							}
							var asc=cell.id+" asc";
							if(typeof cell.asc !="undefined"){
								asc=cell.asc;
							}
							if(orderBy==asc){
								cell.className="gridhead-asc"
									cell.innerHTML=cell.innerHTML+'<img src="'+bg.getContextPath()+'/static/themes/' + bg.uitheme + '/icons/16x16/actions/sort-asc.png"  style="border:0"  alt="Arrow" />'
								continue;
							}
						}
					}
				}
				var tbody=document.getElementById(tableId+"_data");
				if(!tbody)	return;
				for(var j=0;j<tbody.rows.length;j++){
					var row=tbody.rows[j];
					orignClassName=row.className;
					if(orignClassName){
						orignClassName=" "+orignClassName;
					}else{
						orignClassName="";
					}
					if(j%2==1){
						row.className="griddata-odd" + orignClassName;
					}else{
						row.className="griddata-even" + orignClassName;
					}
					row.onclick = bg.ui.grid.onRowChange;
					row.onmouseover=bg.ui.grid.mouseOverGrid;
					row.onmouseout=bg.ui.grid.mouseOverGrid;
				}
			},
			fillEmpty : function (divId,pageSize,size,msg){
				emptydiv=document.getElementById(divId);
				var emptyCnt=pageSize-size;
				if(emptyCnt>7) emptyCnt=7;
				heightpx=emptyCnt*16;
				if(size==0){
					emptyLabel=document.createElement("div");
					emptyLabel.innerHTML=(msg||'No result matched your search.');
					emptyLabel.style.paddingTop=heightpx/2-16 +"px";
					emptydiv.appendChild(emptyLabel);
				}
				emptydiv.style.height=heightpx+"px";
			}
		}
	});
	
	// Action---------------------------------------------------------------------
	//this.action,this.paramstring,this.target
	function EntityAction(entity,onePage){
		this.entity=entity;
		this.page=onePage;
		this.formid="form_" + bg.randomInt();

		//record self for closure method
		selfaction = this;
		
		function applyMethod(action,method){
			last1=action.lastIndexOf("!");
			lastDot=action.lastIndexOf(".");
			shortAction=action;
			sufix="";
			if(-1 == last1) last1 = lastDot;
			if(-1!=last1){
				shortAction=action.substring(0,last1);
			}
			if(-1!=lastDot){
				sufix=action.substring(lastDot);
			}
			return shortAction+"!"+method+sufix;
		}
		this.getForm=function (){
			return this.page.getForm();
		};
		this.addParam = function(name,value){
			bg.form.addInput(this.getForm(),name,value);
		}
		if(null!=this.page.target&&''!=this.page.target){
			var fm = this.getForm();
			if(null!=fm) fm.target=this.page.target;
		}
		
		this.beforeSubmmitId = function(method) {
			var ids = bg.input.getCheckBoxValues(entity+".id");
			if (ids == null || ids == "") {
				bg.alert("你没有选择要操作的记录！");
				return false;
			}
			form=this.getForm();
			form.action = applyMethod(this.page.actionurl, method);
			if(this.page.paramstr){
				bg.form.addHiddens(form,this.page.paramstr);
				bg.form.addParamsInput(form,this.page.paramstr);
			}
			return true;
		}
		this.submitIdAction=function (method,multiId,confirmMsg,ajax){
			if (this.beforeSubmmitId(method)) {
				if(null!=confirmMsg && ''!=confirmMsg){
					if(!confirm(confirmMsg))return;
				}
				bg.form.submitId(this.getForm(),this.entity + ".id",multiId,null,null,ajax);
			}
		}
		this.remove=function(confirmMsg){
			confirmMsg=confirmMsg||'确认删除?';
			return new NamedFunction('remove',function(){
				selfaction.submitIdAction('remove',true,confirmMsg);
			});
		}
		this.add = function(){
			return new NamedFunction('add',function(){
				form=selfaction.getForm();
				if(""!=selfaction.page.paramstr) bg.form.addHiddens(form,selfaction.page.paramstr);
				bg.form.addInput(form,selfaction.entity + '.id',"");
				if(""!=selfaction.page.paramstr) bg.form.addParamsInput(form,selfaction.page.paramstr);
				bg.form.submit(form,applyMethod(selfaction.page.actionurl,"edit"));
			});
		}
		
		this.info = function(){
			return new NamedFunction('info',function(){
				selfaction.submitIdAction('info',false)
			});
		}
		
		this.edit = function (){
			return new NamedFunction('edit',function(){
				selfaction.submitIdAction('edit',false);
			});
		}
		
		this.single = function(methodName,confirmMsg,extparams){
			return new NamedFunction(methodName,function(){
				form=selfaction.getForm();
				if(null!=extparams) bg.form.addHiddens(form,extparams);
				selfaction.submitIdAction(methodName,false,confirmMsg);
			});
		}
		
		this.multi = function(methodName,confirmMsg,extparams,ajax){
			return new NamedFunction(methodName,function(){
				try {
					form = selfaction.getForm();
					if(null!=extparams) bg.form.addHiddens(form, extparams);
					selfaction.submitIdAction(methodName, true, confirmMsg,ajax);
				}catch(e){
					bg.alert(e)
				}
			});
		}
		this.method=function(methodName,confirmMsg,extparams,ajax){
			return  new NamedFunction(methodName,function(){
				form=selfaction.getForm();
				if(null!=confirmMsg && ''!=confirmMsg){
					if(!confirm(confirmMsg))return;
				}
				if(null!=extparams){
					bg.form.addHiddens(form,extparams);
				}
				if(""!=selfaction.page.paramstr){
					bg.form.addHiddens(form,selfaction.page.paramstr);
					bg.form.addParamsInput(form,selfaction.page.paramstr);
				}
				bg.form.submit(form,applyMethod(selfaction.page.actionurl ,methodName),null,null,ajax);
			});
		}
		
		this.exportData=function(properties,format,extparams){
			format = format || "xls";
			properties = properties||"";
			extparams = extparams||"";
			extparams = "&format=" + format +"&properties=" + properties + extparams;
			return selfaction.method('export',null,extparams,false);
		}
	}
	
	bg.extend({entityaction:EntityAction});
	
	bg.extend({'ui.module':{
		moduleClick:function (moudleId){
			var id= document.getElementById(moudleId);
			if(id.className=="module collapsed"){
				id.className="module expanded";
			}else{
				id.className="module collapsed";
			}
		}
	}
	});
	bg.extend({'ui.load':
		function (uimodule,callback){
			base=bg.getContextPath();
			if(uimodule=="validity"){
				jQuery.struts2_jquery.requireCss("/static/themes/" + bg.uitheme + "/jquery.validity.css",base);
				jQuery.struts2_jquery.require("/static/scripts/jquery.validity.js",null,base);
				if(jQuery.struts2_jquery.scriptCache["/static/scripts/lang/jquery.validity_zh.js"] && callback){
					callback()
				}else{
					jQuery.struts2_jquery.require("/static/scripts/lang/jquery.validity_zh.js",callback,base);
				}
			}else if(uimodule=="tabletree"){
				jQuery.struts2_jquery.requireCss("/static/themes/" + bg.uitheme + "/beangle-ui-tabletree.css",base);
				jQuery.struts2_jquery.require("/static/scripts/beangle-ui-tabletree.js",callback,base);
			}else if(uimodule=="My97DatePicker"){
				jQuery.struts2_jquery.require("/static/scripts/My97DatePicker/WdatePicker.js",callback,base);
			}
		}
	});
})(beangle)
