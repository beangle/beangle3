/*----------------------------------------------
 * Beangle UI
 * include ToolBar,Grid,EntityAction 
 */
(function( bg, undefined ) {
	bg.alert=function(msg){
		alert(msg);
	}
	bg.uitheme="default"
	
	function NamedFunction(name,func,objectCount){
		this.name=name;
		this.func=func;
		this.objectCount=(null==objectCount)?'ge0':objectCount;		
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
			//bg.alert("cannot find div with id " + divId);
			return;
		}
		this.id=divId;
		this.separator="&nbsp;";
		this.bar.className="toolbar notprint";
		var imageRoot=self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)+"/static/themes/"+ bg.uitheme +"/icons/",
			imagePath=imageRoot + "16x16/actions/";
		
		this.setTitle=function(newTitle,imageName){
			if(!newTitle) return;
			if(imageName==null)imageName="action-info";
			this.title_div.innerHTML=genIconElement(null,imageName) + '<strong>'+newTitle+"</strong>";
		}
		this.setSeparator=function(separator){
			this.separator=separator;
		}
		/**
		 * 设置抬头
		 */
		this.init = function (title,imageName){
			var title_div = document.createElement('div'), msg_div, items_div;
			title_div.className="toolbar-title";
			this.bar.appendChild(title_div);
			this.title_div=title_div;
			this.setTitle(title,imageName);
			items_div = document.createElement('div');
			items_div.className="toolbar-items";
			items_div.id=this.id+"_items";
			this.items_div=items_div;
			this.bar.appendChild(items_div);
			msg_div = document.createElement('div');
			msg_div.className="toolbar-msg";
			msg_div.style.display="none";
			msg_div.id=this.id+"_msg";
			this.bar.appendChild(msg_div);
		}
		this.init(title,imageName);
		
		this.addHr=function(){
			hrdiv=this.appendDiv(null,"toolbar-line");
			hrdiv.innerHTML='<img height="1" width="100%" align="top" src="' + imagePath + 'keyline.png" />';
		}
		
		function genIconElement(action,cssClassOrIconPath){
			var cssClass ="action-default";
			if(null != cssClassOrIconPath){
				if(cssClassOrIconPath.indexOf('.')>-1){
					if(cssClassOrIconPath.charAt(0)!='/'){
						cssClassOrIconPath=imagePath+cssClassOrIconPath;
					}
					return '<img class="toolbar-img" src="'+cssClassOrIconPath+'"/>';
				}
				else cssClass=cssClassOrIconPath;
			}
			if(null==cssClassOrIconPath && null != action){
				if(typeof action == "object") action = action.name;
				if(typeof action=="string"){
					if(action.indexOf("add")==0 || action.indexOf("batchAdd")==0 ||action.indexOf("new")==0) cssClass= "action-new";
					else if(action.indexOf("remove")==0||action.indexOf("delete")==0) cssClass="action-edit-delete";
					else if(action.indexOf("update")==0||action.indexOf("edit")==0||action.indexOf("batchEdit")==0) cssClass= "action-update";
					else if(action.indexOf("export")==0) cssClass="action-excel";
					else if(action.indexOf("copy")==0) cssClass="action-edit-copy";
					else if(action.indexOf("print")==0) cssClass= "action-print";
					else if(action.indexOf("refresh")==0) cssClass="action-refresh";
					else if(action.indexOf("close")==0) cssClass="action-close";
					else if(action.indexOf("save")==0) cssClass= "action-save";
					else if(action.indexOf("download")==0) cssClass="action-download";
				}
			}
			return '<span class="toolbar-icon ' + cssClass + '" ></span>';
		}
		/**
		 * 设置按钮的动作
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
				this.addItem("返回",function (){history.back(-1)},'action-backward');
			}else{
				this.addItem(title,function (){history.back(-1)},'action-backward');
			}
		}
		this.addHelp = function (module){
			this.addItem("帮助",function (){
				if(null==module) bg.alert("施工中..");
				else window.open("help.action?helpId="+module);
				},'action-help-contents');
		}

		this.addPrint = function (msg){
			if(null==msg) this.addItem("打印","print()");
			else this.addItem(msg,"print()");
		}

		this.addClose = function (msg){
			if(''==msg|| null==msg)	msg="关闭";
			this.addItem(msg,"window.close()",'action-close');
		}
		/**
		 * 添加按钮
		 */
		this.addItem = function(title,action,imageName,alt,objectCount){
			this.addSeparatorAsNeed();
			var item_div = document.createElement('div');
			item_div.innerHTML=genIconElement(action,imageName)+title;
			item_div.onmouseout=MouseOutItem;
			item_div.onmouseover=MouseOverItem;
			setAction(item_div,action);
			if(!objectCount) { if(typeof action=='object'){objectCount=action.objectCount;}}
			if(!objectCount) objectCount='ge0';
			item_div.className=("toolbar-item toolbar-item-"+objectCount) + ((objectCount!='ge0')?" toolbar-item-disabled":"");
			item_div.title=(alt==null?title:alt);
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
			item_div.className="toolbar-item";
			var menuTableId=this.id+this.itemCount+"_menu";
			item_div.id=menuTableId;
			item_div.tabIndex = 0;
			item_div.title=alt||title;
			item_div.onmouseout=MouseOutItem;
			item_div.onmouseover=MouseOverItem;
			this.items_div.appendChild(item_div);
			if(action == null){
				item_div.innerHTML=genIconElement(null,imageName) + title + '&nbsp;'+ genIconElement(null,'action-downarrow');
				item_div.onclick=function (event){displayMenu(event);};
			}else{
				var span1 = document.createElement("span");
				span1.innerHTML=genIconElement(action,imageName)+title;
				setAction(span1,action);
				var span2 = document.createElement("span");
				span2.innerHTML=genIconElement(action,"action-downarrow");
				span2.onclick = function (event){displayMenu(event);};
				item_div.appendChild(span1);
				item_div.appendChild(span2);
			}
			item_div.onblur = function (event){hiddenMenu(event);};
			var menu = new Menu(menuTableId,item_div);
			this.itemCount++;
			return menu;
		}
		
		function hiddenMenu(event){
			var div=bg.event.getTarget(event);
			while(div && div.tagName.toLowerCase()!='div'){
				div=div.parentNode;
			}
			var menu=div.lastElementChild || div.lastChild;
			if(null==menu || menu.tagName.toLowerCase()!='table'){alert('menu is null then return and target is '+div);return;}
			if(menu.style.visibility!=""&&menu.style.visibility!="hidden"){
				for(var i = 0;i < menu.rows.length;i++){
					if(menu.rows[i].cells[0].className=='toolbar-menuitem-transfer'){
						return;
					}
				}
				menu.style.visibility="hidden";
			}
		}
		
		function displayMenu(event){
			var div=bg.event.getTarget(event);
			while(div && div.tagName.toLowerCase()!='div'){
				div=div.parentNode;
			}
			var menu=div.lastElementChild || div.lastChild;
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
			table.id=id+"Table";
			var mytablebody = document.createElement("tbody");
			table.appendChild(mytablebody);
			if (jQuery("#" + id).find("span").length>0) {
				table.onclick = function (event){displayMenu(event);};
			}
			item_div.appendChild(table);
			this.table=table; 
			/**
			 * 在菜单中添加一个条目
			 */
			this.addItem = function (title,action,imageName,alt){
				var itemTd = document.createElement('td');
				itemTd.innerHTML=genIconElement(action,imageName)+title;
				itemTd.onmouseout=MouseOutMenuItem;
				itemTd.onmouseover=MouseOverMenuItem;
				itemTd.title=alt||title;
				setAction(itemTd,action);
				itemTd.className="toolbar-menuitem";
				itemTd.width="100%";
				var tr = document.createElement('tr');
				tr.appendChild(itemTd);
				if(this.table.tBodies.length==0) this.table.appendChild(document.createElement("tbody"));
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
			if(o) jQuery(o).removeClass("toolbar-item").addClass("toolbar-item-transfer");
		}
		/**
		 * 当鼠标离开工具栏的按钮时
		 */
		function MouseOutItem(e){
			var o=bg.event.getTarget(e);
			while (o&&o.tagName.toLowerCase()!="div"){o=o.parentNode;}
			if(o) jQuery(o).removeClass("toolbar-item-transfer").addClass("toolbar-item");
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
		else if(ranks.length==0) ranks=[onePage.pageSize];
		
		if(!titles) titles={first:'« First',previous:'‹ Previous',next:'Next ›',last:'Last »',no:'No:',size:'Size:',change:'Click me to change page size',pagesize:'Page Size'};
		var maxPageNo = onePage.maxPageNo;
		addAnchor=function(text,pageNumber){
			var pageHref=document.createElement('a');
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
		var labelspan=document.createElement('span');
		labelspan.innerHTML="<strong>" + onePage.startNo +"</strong> - <strong>"+ onePage.endNo + "</strong> of <strong>" + onePage.total + "</strong>";
		labelspan.style.padding="0px 2px 0px 2px";
		pageDiv.appendChild(labelspan);
		var numSpan=jQuery(labelspan)
		numSpan.attr('title',titles['change'])
		numSpan.mouseover(function (){this.className='pgbar-label'});
		numSpan.mouseout(function(){this.className=''});
		// 为了防止其他信息上下移动错误
		numSpan.click(function(){this.parentNode.style.marginTop="0px";this.nextSibling.style.display='';this.style.display='none'});
		
		var pagespan=document.createElement('span');
		pagespan.style.display="none";
		//add pagesize select
		if(ranks && (ranks.length>0)){
			var pageNoSelect=document.createElement('select');
			pageNoSelect.id=pageDiv.id+"_select";
			pagespan.appendChild(pageNoSelect);
			pageNoSelect.className="pgbar-selbox";
			pageNoSelect.title=titles['pagesize']||'Page Size';
			var selectIndex=0;
			for(var i=0;i<ranks.length;i++){
				if(ranks[i]==onePage.pageSize) selectIndex=i;
				pageNoSelect.options.add(new Option(titles['size']+ranks[i], ranks[i]));
			}
			pageNoSelect.selectedIndex = selectIndex;
		}

		//add pageno input
		var pageInput=document.createElement('input');
		pageInput.className="pgbar-input";
		pagespan.appendChild(pageInput);

		var pageInputLabel = document.createElement('label');
		pagespan.appendChild(pageInputLabel);
		
		jQuery(pageInputLabel).attr("for",pageDiv.id+"_input").text("/"+maxPageNo+" ").toggleClass("pgbar-input-label");
		
		var pageInputJ=jQuery(pageInput)
		pageInputJ.attr("value",onePage.pageNo);
		pageInputJ.attr("id",pageDiv.id+"_input");
		pageInputJ.attr('title',(onePage.startNo +" - " + onePage.endNo + " of " + onePage.total));
		pageInputJ.focus(function(){this.value=''});
		pageInputJ.blur(function(){if(!this.value) this.value= onePage.pageNo});
		
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
			enableSingleRowSelect : false,
			enableDynaBar:false,
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
				var myclass=this.className;
				selectIndex=myclass.indexOf("griddata-selected");
				if(-1 != selectIndex) return;
				overIndex=myclass.indexOf("griddata-over");
				if(-1 == overIndex){
					this.className=myclass+" "+ "griddata-over"
				}else{
					this.className=myclass.substring(0,overIndex);
				}
			},
			setGridMessage : function (gridId,message){
				var msgDiv1=document.getElementById(gridId+'_bar1_msg');
				var msgDiv2=document.getElementById(gridId+'_bar2_msg');
				if(msgDiv1){
					msgDiv1.style.display=(message?"":"none");
					msgDiv1.innerHTML=(message?message:"");
				}
				if(msgDiv2){
					msgDiv2.style.display=(message?"":"none");
					msgDiv2.innerHTML=(message?message:"");
				}
			},
			toggleAll : function(event){
				var ele =  bg.event.getTarget(event);
				//find fired grid table
				var ownGridTable=ele;
				while(ownGridTable.tagName != null && ownGridTable.tagName.toLowerCase()!="table"){
					ownGridTable=ownGridTable.parentNode;
					if(null==ownGridTable) break;
				}
				var firstCell=ele.parentNode;
				var isFireOnBoxCell=true;
				if(null==ownGridTable) return;

				var selectedCount=0;
				jQuery("#"+ownGridTable.id + " .gridselect").each(function(){
					if(ele.checked){
						jQuery(this).find("input").attr("checked","checked");
						jQuery(this).parent("tr").addClass("griddata-selected");
						selectedCount++;
					}else{
						if(jQuery(this).find("input").is(":checked")){
							jQuery(this).find("input").removeAttr("checked");
							jQuery(this).parent("tr").removeClass("griddata-selected");
						}
					}
				});
				
				bg.ui.grid.notifyGridbar(ownGridTable.id,selectedCount);
			},
			/**通知gridbar中的按钮,更新是否显示等状态*/
			notifyGridbar: function (gridId,selectedCount){
				//change toolbar item
				var changeToolbarItem=function(){
					if(selectedCount>=2) {
						if(jQuery(this).hasClass("toolbar-item-e1")) jQuery(this).addClass('toolbar-item-disabled');
						else jQuery(this).removeClass('toolbar-item-disabled');
					} else if(selectedCount==1) {
						if(jQuery(this).hasClass("toolbar-item-ge2"))	jQuery(this).addClass('toolbar-item-disabled');
						else jQuery(this).removeClass('toolbar-item-disabled');
					} else{
						if(jQuery(this).hasClass("toolbar-item-ge0"))	jQuery(this).removeClass('toolbar-item-disabled');
						else jQuery(this).addClass('toolbar-item-disabled');
					}
				};
				if(bg.ui.grid.enableDynaBar){
					jQuery('#'+gridId+'_bar1_items .toolbar-item').each(changeToolbarItem);
					jQuery('#'+gridId+'_bar2_items .toolbar-item').each(changeToolbarItem);
				}
				if(selectedCount>1){
					bg.ui.grid.setGridMessage(gridId,"已选择 <b>"+selectedCount+"</b> 条");
				}else{
					bg.ui.grid.setGridMessage(gridId,"");
				}
			},
			/**
			 * 行选函数。单击行内的任一处，可以选定行头的checkbox或者radio 用法:onclick="toggleRow(event)"
			 */
			toggleRow : function (event){
				var ele =  bg.event.getTarget(event);
				//find fired grid table
				var ownGridTable=ele;
				while(ownGridTable.tagName != null && ownGridTable.tagName.toLowerCase()!="table"){
					ownGridTable=ownGridTable.parentNode;
					if(null==ownGridTable) break;
				}
				var changed=true;
				var firstCell=null;
				var isFireOnBoxCell=false;
				if(null!=ele && ele.tagName.toLowerCase()=="td"){
					firstCell = ele.parentNode.firstChild;
					//find first cell
					while(firstCell.tagName == null || firstCell.tagName.toLowerCase()!="td"){
						firstCell=firstCell.nextSibling;
					}
					isFireOnBoxCell=(ele==firstCell);
					//shall we reserve other select
					// find box input
					ele=firstCell.firstChild;
					while(((typeof ele.tagName)=="undefined")||ele.tagName.toLowerCase()!="input"){
						ele=ele.nextSibling;
						if(ele==null)return;
					}
					ele.checked = !ele.checked;
				}else if((ele.type=="checkbox")||(ele.type=="radio")){
					firstCell=ele.parentNode;
					isFireOnBoxCell=true;
				}else{
					changed=false;
				}
				if(null==ele || null==firstCell || null==ownGridTable || !changed) return;
				
				// 改变选定行的颜色
				var row=firstCell.parentNode;
				if((typeof row.className)=="undefined") return;
				if(ele.checked) jQuery(row).removeClass("griddata-over").addClass("griddata-selected");
				else jQuery(row).removeClass("griddata-selected").addClass("griddata-over");
				
				var selectedCount=0;
				if(ele.type=="radio") {
					if(ele.checked)	selectedCount=1;
				}else{
					var isReserveOtherSelect = !bg.ui.grid.enableSingleRowSelect || isFireOnBoxCell || event.ctrlKey ;
					jQuery("#"+ownGridTable.id + " .gridselect").each(function(){
						if(jQuery(this).find("input").is(":checked")){
							if(firstCell != this && !isReserveOtherSelect){
								jQuery(this).find("input").removeAttr("checked");
								jQuery(this).parent("tr").removeClass("griddata-selected");
							}else{
								selectedCount++;
							}
						}
					});
					if(!isReserveOtherSelect){
						jQuery("#"+ownGridTable.id + " .gridselect-top").each(function(){
							jQuery(this).find("input").removeAttr("checked");
						});
					}
				}

				bg.ui.grid.notifyGridbar(ownGridTable.id,selectedCount);
				// 激发自定义事件
				if(typeof ele.onchange =="function") ele.onchange();
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
				var table= document.getElementById(tableId), thead = table.tHead, tbody, orderBy, columnSort ,i ,j, head, row, cell, desc, asc, orignRowCls;
				if(!thead || thead.rows.length==0){
					//bg.alert("sortTable ["+tableId+"] without thead"); 
					return;
				}
				orderBy=onePage.orderby;
				columnSort = function(event){
					// this is a td/th
					bg.ui.grid.sort(onePage,this);
				}
				for(j=0;j<thead.rows.length;j++){
					head=thead.rows[j];
					for(i=0;i<head.cells.length;i++){
						cell=head.cells[i];
						if(cell.className=="gridhead-sortable" && null!=cell.id){
							cell.onclick = columnSort;
							cell.onmouseover=bg.ui.grid.overSortTableHeader;
							cell.onmouseout=bg.ui.grid.outSortTableHeader;
							cell.title="点击按 ["+cell.innerHTML+"] 排序";
							desc=cell.id.replace(/\,/g," desc,")+" desc";
							if(typeof cell.desc !="undefined"){
								desc=cell.desc;
							}
							if(orderBy.indexOf(desc)!=-1){
								cell.className="gridhead-desc"
									cell.innerHTML=cell.innerHTML+'<span class="gridhead-icon action-sort-desc"></span>'
								continue;
							}
							asc = cell.id+" asc";
							if(typeof cell.asc !="undefined"){
								asc = cell.asc;
							}
							if(orderBy==asc){
								cell.className="gridhead-asc"
									cell.innerHTML=cell.innerHTML+'<span class="gridhead-icon action-sort-asc"></span>'
								continue;
							}
						}
					}
				}
				tbody=document.getElementById(tableId+"_data");
				if(!tbody)	return;
				for(j=0;j<tbody.rows.length;j++){
					row=tbody.rows[j];
					orignRowCls=row.className;
					if(orignRowCls){
						orignRowCls=" "+orignRowCls;
					}else{
						orignRowCls="";
					}
					if(j%2==1){
						row.className="griddata-odd" + orignRowCls;
					}else{
						row.className="griddata-even" + orignRowCls;
					}
					row.onclick = bg.ui.grid.toggleRow;
					row.onmouseover=bg.ui.grid.mouseOverGrid;
					row.onmouseout=bg.ui.grid.mouseOverGrid;
				}
			},
			fillEmpty : function (divId,pageSize,size,msg){
				var emptydiv=document.getElementById(divId), emptyCnt=pageSize-size, heightpx, emptyLabel;
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
		var selfaction = this;
		
		function applyMethod(action,method){
			var last1=action.lastIndexOf("!"), lastDot=action.lastIndexOf("."), shortAction=action, sufix="";
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
			var form=this.getForm();
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
			},bg.ui.grid.enableDynaBar?'ge1':'ge0');
		}
		this.add = function(){
			return new NamedFunction('add',function(){
				var form=selfaction.getForm();
				if(""!=selfaction.page.paramstr) bg.form.addHiddens(form,selfaction.page.paramstr);
				bg.form.addInput(form,selfaction.entity + '.id',"");
				if(""!=selfaction.page.paramstr) bg.form.addParamsInput(form,selfaction.page.paramstr);
				bg.form.submit(form,applyMethod(selfaction.page.actionurl,"edit"));
			});
		}
		
		this.info = function(){
			return new NamedFunction('info',function(){
				selfaction.submitIdAction('info',false)
			},bg.ui.grid.enableDynaBar?'e1':'ge0');
		}
		
		this.edit = function (){
			return new NamedFunction('edit',function(){
				selfaction.submitIdAction('edit',false);
			},bg.ui.grid.enableDynaBar?'e1':'ge0');
		}
		
		this.single = function(methodName,confirmMsg,extparams){
			return new NamedFunction(methodName,function(){
				var form=selfaction.getForm();
				if(null!=extparams) bg.form.addHiddens(form,extparams);
				selfaction.submitIdAction(methodName,false,confirmMsg);
			},bg.ui.grid.enableDynaBar?'e1':'ge0');
		}
		
		this.multi = function(methodName,confirmMsg,extparams,ajax){
			return new NamedFunction(methodName,function(){
				try {
					var form = selfaction.getForm();
					if(null!=extparams) bg.form.addHiddens(form, extparams);
					selfaction.submitIdAction(methodName, true, confirmMsg,ajax);
				}catch(e){
					bg.alert(e)
				}
			},bg.ui.grid.enableDynaBar?'ge1':'ge0');
		}
		this.method=function(methodName,confirmMsg,extparams,ajax){
			return  new NamedFunction(methodName,function(){
				var form=selfaction.getForm();
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
			if(extparams.indexOf("&") != 0) extparams = "&" + extparams;
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
			var base=bg.getContextPath();
			if(uimodule=="validity"){
				jQuery.struts2_jquery.requireCss("/static/themes/" + bg.uitheme + "/jquery.validity.css",base);
				jQuery.struts2_jquery.require("/static/scripts/jquery/jquery-validity-1.1.1.js",null,base);
				if(jQuery.struts2_jquery.scriptCache["/static/scripts/i18n/zh_CN/jquery.validity.js"] && callback){
					callback()
				}else{
					jQuery.struts2_jquery.require("/static/scripts/i18n/zh_CN/jquery.validity.js",callback,base);
				}
			}else if(uimodule=="tabletree"){
				jQuery.struts2_jquery.requireCss("/static/themes/" + bg.uitheme + "/beangle-ui-tabletree.css",base);
				jQuery.struts2_jquery.require("/static/scripts/beangle/beangle-ui-tabletree.js",callback,base);
			}else if(uimodule=="colorbox"){
				jQuery.struts2_jquery.requireCss("/static/themes/" + bg.uitheme + "/colorbox.css",base);
				jQuery.struts2_jquery.require("/static/scripts/jquery/jquery-colorbox-1.3.17.1.min.js",callback,base);
			}else if(uimodule=="jquery.pstrength"){
				jQuery.struts2_jquery.requireCss("/static/themes/" + bg.uitheme + "/jquery-pstrength.css",base);
				jQuery.struts2_jquery.require("/static/scripts/jquery/jquery-pstrength-2.1.js",callback,base);
				jQuery.struts2_jquery.require("/static/scripts/i18n/zh_CN/jquery-pstrength.js",callback,base);
			}			
		}
	});
})(beangle)


