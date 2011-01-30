//////////////////////////////////////////////
// Cross DHTML TOC
// DynamicSelectableTree.js
// COPYRIGHT ADVANTYS 1999
// COPYRIGHT LU GENGBIAO 2004
// Last modified : Dec 28 2004
// only support Internet Explorer
//////////////////////////////////////////////
// MenuToc_toc
// Constructor
function MenuToc_toc(tocName){
    // Data member
    this.name= tocName
    this.doc = document
    this.rootNode = 0
    this.nodesIndex = new Array
    this.nodesCounter = 0
    this.showRoot = true
    this.showIcons =true
    this.showTextLinks = true

    this.onClick = "";

    // Styles
    this.styleItemFolderLink=0
    this.styleItemFolderNoLink=0
    this.styleItemLink=0
    this.styleItemNoLink=0

    // Icons
    //this.iconPath            = "/images"
    //this.iconWidth           = "24"
    //this.iconHeight          = "22"
    //this.iconPlus            = this.iconPath + "/plus.gif"
    //this.iconMinus           = this.iconPath + "/minus.gif"
    //this.iconPlus1           = this.iconPath + "/plus1.gif"
    //this.iconPlus2           = this.iconPath + "/plus2.gif"
    //this.iconMinus1          = this.iconPath + "/minus1.gif"
    //this.iconMinus2          = this.iconPath + "/minus2.gif"
    //this.iconItem            = this.iconPath + "/sanjiao.gif"
    //this.iconFolderCollapsed = this.iconPath + "/foldercol.gif"
    //this.iconFolderExpanded  = this.iconPath + "/folderexp.gif"
    //this.iconEmpty           = this.iconPath + "/empty.gif"
    //this.iconLine1           = this.iconPath + "/line1.gif"
    //this.iconLine2           = this.iconPath + "/line2.gif"
    //this.iconLine3           = this.iconPath + "/line3.gif"

    // Application data(added by Lu Gengbiao)
    this.multiSelect         = true;
    this.selectChildren      = true;
    this.styleSelected       = "MenuTocSelected";
    this.styleNotSelected    = "MenuTocNotSelected";

    // Methods
    this.initTable          = MenuToc_tocInitTable
    this.display            = MenuToc_tocDisplay
    this.makeFolder         = MenuToc_tocMakeFolder
    this.makeItem           = MenuToc_tocMakeItem
    this.insertNode         = MenuToc_tocInsertNode
    this.nodeClicked        = MenuToc_tocNodeClicked
    this.nodeSelected       = MenuToc_tocNodeSelected       // added by Lu Gengbiao
    this.listSelectedValue  = MenuToc_tocListSelectedValue  // added by Lu Gengbiao
    this.listSelectedName	= MenuToc_tocListSelectedName	// added by Shu Li
    this.changeNodeStyle    = MenuToc_tocChangeNodeStyle    // added by Lu Gengbiao
    this.selectNodes        = MenuToc_tocSelectNodes        // added by Sheng HuiJing
    this.cleanAllSelection  = MenuToc_tocCleanAllSelection  // added by Sheng HuiJing
    this.expand             = MenuToc_tocExpand
    this.expandAll          = MenuToc_tocExpandAll
    this.collapseAll        = MenuToc_tocCollapseAll
    this.nodeClickedAndSelected = MenuToc_tocNodeClickedAndSelected
}

function MenuToc_tocInitTable(){
    if (!this.showIcons){
        this.iconPlus1    = this.iconPlus
        this.iconPlus2    = this.iconPlus
        this.iconMinus    = this.iconMinus
        this.iconMinus1   = this.iconMinus
        this.iconMinus2   = this.iconMinus
        this.iconLine3    = this.iconEmpty
    }
    this.rootNode.initTable();
    this.rootNode.initialize(0, 1, '');
}

// MenuToc_tocDisplay
// Display the Toc
function MenuToc_tocDisplay(){
	this.initTable();	
	this.rootNode.display();
	var expandLevel = MenuToc_tocDisplay.arguments[MenuToc_tocDisplay.arguments.length-1]
    this.rootNode.expand(expandLevel)
}

// MenuToc_tocMakeFolder
// Create a folder
function MenuToc_tocMakeFolder(description,href,target,icon,value){
    newFolder = new MenuToc_folder(this,description,href,target,icon,value)
	if (!this.rootNode){
		this.rootNode = newFolder;
	}
    return newFolder;
}

// MenuToc_tocMakeItem
// Create a item
function MenuToc_tocMakeItem(description, href, target, icon, value , onClick){
    newItem = new MenuToc_item(this,description, href, target, icon, value, onClick)
    return newItem
}

// insertNode
// Insert a item
function MenuToc_tocInsertNode(parentFolder, nodeToInsert){
    //alert(parentFolder)
    return parentFolder.addChild(nodeToInsert)
}

// nodeClicked
// Event
function MenuToc_tocNodeClicked(nodeId){
    var clickedNode = this.nodesIndex[nodeId];
    clickedNode.setState(!clickedNode.isExpanded);
}
// MenuToc_tocExpandAll
//
function MenuToc_tocExpandAll(){
    this.rootNode.expandAll();
}
// MenuToc_tocExpand
//
function MenuToc_tocExpand(maxlevel){
    this.rootNode.expand(maxlevel);
}
// MenuToc_tocCollapseAll
//
function MenuToc_tocCollapseAll(){
    this.expand(1)
}
// MenuToc_folder
// Constructor
function MenuToc_folder(activeToc,description,href,target,icon,value){
    // Data member
    this.id = -1
    this.toc = activeToc
    this.desc = description
    this.href = href
    this.target = target
    this.layer = 0
    this.iconSrc = this.toc.iconFolderExpanded
    this.iconPersoSrc = icon
    if (icon)
        this.iconSrc = this.iconPersoSrc
    this.iconImg = 0
    this.nodeImg = 0
    this.isLastNode = 0
    this.isExpanded = false
    this.isFolder = true
    this.children = new Array //TODO
    this.childrensCounter = 0
    this.html = 0               // added by lu Gengbiao
    this.htmlWrited = false;    // added by lu Gengbiao

    // application data(added by Lu Gengbiao)
    this.selected = false
    this.value    = value;

    // Methods
    this.initTable      = MenuToc_folderInitTable
    this.initialize     = MenuToc_folderInitialize
    this.setState       = MenuToc_folderSetState
    this.addChild       = MenuToc_folderAddChild
    this.createIndex    = MenuToc_createIndex
    this.display        = MenuToc_display
    this.hide           = MenuToc_hide
    this.writeTable     = MenuToc_writeTable
    this.draw           = MenuToc_folderDraw
    this.outputLink     = MenuToc_folderOutputLink
    this.expand         = MenuToc_folderExpand
    this.expandAll      = MenuToc_folderExpandAll
    this.expandRec      = MenuToc_folderExpandRec;
}

function MenuToc_folderInitTable(){
    this.createIndex();
    this.toc.doc.write("<table id='" + this.toc.name + "folder" + this.id + "' border=0 cellspacing=0 cellpadding=0>\n</table>\n")
    for (var i=0 ; i < this.childrensCounter; i++){
        this.children[i].initTable();
    }
}
// MenuToc_folderInitialize
//
function MenuToc_folderInitialize(level, lastNode, leftSide){
    var nc = this.childrensCounter
    //2006-12-27 文件夹菜单中,出第一层之外前面都加空白
    if(level>1){
      leftSide = leftSide + "<img src='" + this.toc.iconLine3 + "' width=15px height=" + this.toc.iconHeight + ">"
    }
    if (level!=0 || this.toc.showRoot){
       var auxEv = "<a href='javascript:" + this.toc.name +".nodeClicked("+this.id+")'>"
       //var auxEv = "<a>";
       if (level>0){
		   // chaostone change name to id
           if (lastNode){ //the last 'brother' in the children array           
				this.draw(leftSide + auxEv + "<img id='" + this.toc.name + "nodeIcon" + this.id + "' src='" + this.toc.iconPlus1 + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + " border=0></a>")
			    //leftSide = leftSide + "<img src='" + this.toc.iconEmpty + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">"
				this.isLastNode = 1;
			} else{
				this.draw(leftSide + auxEv + "<img id='" + this.toc.name + "nodeIcon" + this.id + "' src='" + this.toc.iconPlus2 + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + " border=0></a>")
				//leftSide = leftSide + "<img src='" + this.toc.iconLine3 + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">"
				this.isLastNode = 0;
			}
		} else
			this.draw('');
	}

    if (nc > 0){
	    level = level + 1
        for (var i=0 ; i < this.childrensCounter; i++){
            if (i == this.childrensCounter-1)
                this.children[i].initialize(level, 1, leftSide)
            else
                this.children[i].initialize(level, 0, leftSide)
         }
    }
}

// MenuToc_folderDraw
//
function MenuToc_folderDraw(leftSide){
    this.html = "<table id='" + this.toc.name + "folder" + this.id + "' border=0 cellspacing=0 cellpadding=0>\n"
    this.html += '<tr><td>';

    this.html += leftSide
    if (this.toc.showIcons){
        this.outputLink();
        //this.html += "<img name='" + this.toc.name + "folderIcon" + this.id + "' src='" + this.iconSrc + "' border=0></a>";
        this.html += "<img id='" + this.toc.name + "folderIcon" + this.id + "' src='" + this.iconSrc + "' width=0 height=0 border=0></a>"
    }
    this.html += '</td><td valign=middle nowrap>';
    if (this.toc.showTextLinks && this.href){
        // this line added by Lu Gengbiao
        var strOnClick = "";
        if (this.toc.onClick != "")
            strOnClick = " onClick='" + this.toc.onClick + "'";
        this.html += "<a id='" + this.toc.name + "Link" + this.id + "' class='" + this.toc.styleItemFolderLink + "' href='" + this.href + "' target='" + this.target + "'" + strOnClick + ">"
        this.html += this.desc + '</a>'
    } else
        this.html += "<font class='" + this.toc.styleItemFolderNoLink + "'>" + this.desc + "</font>"
    // changed by chaostone from </td> to </td></tr>.
    this.html += '</td></tr>';
    this.html += "</table>\n";
}
// MenuToc_folderOutputLink
//
function MenuToc_folderOutputLink(){
    if (this.href){
        var strOnClick = "";
        if (this.toc.onClick != "")
            strOnClick = " onClick='" + this.toc.onClick + "'";

        this.html += "<a class='" + this.toc.styleItemFolderLink + "' href='" + this.href +  "' target='" + this.target + "'" + strOnClick + ">"
    }
}
// MenuToc_folderSetState
// Change the state of a folder
function MenuToc_folderSetState(isExpanded){
    if (isExpanded == this.isExpanded)
        return

    this.isExpanded = isExpanded;
    MenuToc_applyChanges(this)
}

// MenuToc_folderAddChild
//
function MenuToc_folderAddChild(childNode){
    this.children[this.childrensCounter] = childNode
    this.childrensCounter++
    return childNode
}
// MenuToc_folderExpandAll
//
function MenuToc_folderExpandAll(){
    this.expand(1000)
}

// MenuToc_folderExpand
//
function MenuToc_folderExpand(maxLevel){
    if (this.isExpanded)
        this.toc.nodeClicked(this.id)
    var currentLevel = 1
    if (maxLevel)
        this.expandRec(maxLevel,currentLevel)
    else
        this.expandRec(1,currentLevel)
}

// MenuToc_folderExpand
//
function MenuToc_folderExpandRec(maxlevel,currentlevel){
    var i = 0
    this.toc.nodeClicked(this.id)
    if (currentlevel<maxlevel){
        currentlevel++;
        for (i=0; i < this.childrensCounter; i++){
            if (this.children[i].isFolder)
                this.children[i].expandRec(maxlevel,currentlevel)
        }
    }
}

// MenuToc_item
// Constructor
function MenuToc_item(activeToc, description, href, target, icon, value, toc_href){
    // Data member
    this.id = -1
    this.toc = activeToc
    this.desc = description
    this.href = href
    this.target = target
    this.layer = 0
    this.iconImg = 0
    this.iconSrc = this.toc.iconItem
    if (icon)
    this.iconSrc = icon
    this.html = 0               // added by lu Gengbiao
    this.htmlWrited = false;    // added by lu Gengbiao

    // application data(added by Lu Gengbiao)
    this.selected = false
    this.value    = value
    this.toc_href = toc_href
    // Methods
    this.initTable      = MenuToc_itemInitTable
    this.initialize     = MenuToc_itemInitialize
    this.draw           = MenuToc_itemDraw
    this.display        = MenuToc_display
    this.hide           = MenuToc_hide
    this.writeTable     = MenuToc_writeTable
    this.createIndex    = MenuToc_createIndex
}

function MenuToc_itemInitTable(){
    this.createIndex();
    this.toc.doc.write("<table id='" + this.toc.name + "item" + this.id + "'  border=0 cellspacing=0 cellpadding=0>\n</table>\n")
}

// MenuToc_itemInitialize
//
function MenuToc_itemInitialize(level, lastNode, leftSide){
    if (level>0){
        if (lastNode){
            if (this.toc.showIcons){
                this.draw(leftSide + "<img src='" + this.toc.iconLine1 + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">")
                leftSide = leftSide + "<img src='" + this.toc.iconEmpty + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">"
            } else
                this.draw(leftSide +"<img src='" + this.toc.iconEmpty + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">")
        } else{
           if (this.toc.showIcons){
                this.draw(leftSide + "<img src='" + this.toc.iconLine2 + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">")
                leftSide = leftSide + "<img src='" + this.toc.iconLine3 + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">"
           } else
                this.draw(leftSide +"<img src='" + this.toc.iconEmpty + "' width=" + this.toc.iconWidth + " height=" + this.toc.iconHeight + ">")
        }
    } else
        this.draw('');
}

// MenuToc_itemDraw
//
function MenuToc_itemDraw(leftSide){
    var html = "<table id='" + this.toc.name + "item" + this.id + "' border=0 cellspacing=0 cellpadding=0>\n";

    /*if (this.href&&this.toc_href){
       html += "<a href='" + this.toc_href + ">";
    }*/
    html += "<tr><td valign=top nowrap>";
    html += leftSide;
    html += "</td><td valign=middle nowrap>";
    if (this.toc.showIcons){
        /*if (this.href)
            html += "<a href='" + this.href + "' target='" + this.target + "'>"*/
        html += "<img id='" + this.toc.name + "itemIcon" + this.id +"' src='"+ this.iconSrc +"' border=0>";
        /*if (this.href)
            html += '</a>'*/
    }

    if (this.href && this.toc.showTextLinks){
        // the id added by Lu Gengbiao
        var strOnClick = "";
        if (this.toc.onClick != "")
            strOnClick = " onClick='" + this.toc.onClick + "'";
        html += "<a id='" + this.toc.name + "Link" + this.id + "' class='" + this.toc.styleItemLink + "' href='" + this.href +  "' target='" + this.target + "'" + strOnClick + ">" + this.desc + "</a>"
    } else
        html += "<font class='" + this.toc.styleItemNoLink + "'>" + this.desc + "</font>";

    /*if (this.href&&this.toc_href){
       html += "</a>";
    }*/
    html += "</td></tr></table>\n";
	this.html = html;
}

// MenuToc_display
//
function MenuToc_display(){
    this.writeTable();
    //this.layer.style.display = 'block';
    if (this.layer){
        this.layer.style.display = 'block'
    }
}
// hide folder
//
function MenuToc_hide(){
    this.writeTable();
    if (this.layer){
        if (this.layer.style.display == 'none')
            return;
        this.layer.style.display = 'none'
    }
    if (this.isFolder)
        this.setState(0);
}

function MenuToc_writeTable(){
    var typeName;
    if (this.isFolder)
        typeName = 'folder';
    else
        typeName = 'item';
    if (!this.htmlWrited){
        this.htmlWrited = true;
        if (this.id > 0 || this.toc.showRoot){
            var table = eval('document.all.' + this.toc.name + typeName + this.id)
            table.outerHTML = this.html;
            this.layer = eval(this.toc.name + typeName + this.id);
            if (this.toc.showIcons){
                 this.iconImg = eval(this.toc.name + typeName + "Icon"+ this.id)
            }
            if (this.isFolder && this.id>0){
                 this.nodeImg = eval(this.toc.name + "nodeIcon" + this.id)
            }           
        }
    }
	if (this.selected)
		this.toc.changeNodeStyle(this.id)
}

// MenuToc_applyChanges
//
function MenuToc_applyChanges(folder){
    var i=0
    if (folder.isExpanded){
        if (folder.nodeImg)
            if (folder.isLastNode)
                folder.nodeImg.src = folder.toc.iconMinus1
            else
                folder.nodeImg.src = folder.toc.iconMinus2

        if (folder.iconPersoSrc)
            folder.iconImg.src =folder.iconPersoSrc
        else
            folder.iconImg.src = folder.toc.iconFolderExpanded

        for (i=0; i<folder.childrensCounter; i++)
            folder.children[i].display()
    } else{
        if (folder.nodeImg)
            if (folder.isLastNode)
                folder.nodeImg.src = folder.toc.iconPlus1
            else
                folder.nodeImg.src = folder.toc.iconPlus2

        if (folder.iconPersoSrc)
            folder.iconImg.src =folder.iconPersoSrc
        else
            folder.iconImg.src = folder.toc.iconFolderCollapsed

        for (i=0; i<folder.childrensCounter; i++)
            folder.children[i].hide()
    }
}

// MenuToc_createIndex
//
function MenuToc_createIndex(){    
    this.id = this.toc.nodesCounter
    this.toc.nodesIndex[this.toc.nodesCounter] = this
    this.toc.nodesCounter++
}
// MenuToc_tocNodeSelected
// Event
// author: Lu Gengbiao
function MenuToc_tocNodeSelected(nodeId){
    var selectedNode = this.nodesIndex[nodeId]
    if(selectedNode.href){
        selectedNode.selected = !selectedNode.selected;
        this.changeNodeStyle(nodeId);
        if (! this.multiSelect){
            // clear previous selected
            for (var i=0 ; i < this.nodesCounter; i++){
                if (nodeId != i && this.nodesIndex[i].selected){
                    this.nodesIndex[i].selected = false;
                    this.changeNodeStyle(i);
                }
            }
        } else if (this.selectChildren){
            for (var i=0 ; i < selectedNode.childrensCounter; i++){
                if (selectedNode.children[i].href)
                    this.nodeSelected(selectedNode.children[i].id);
            }
        }
    }// end if(href)...
}

// author: Lu Geng biao
function MenuToc_tocChangeNodeStyle(nodeId){
    // change link color
	var node = this.nodesIndex[nodeId]
	if (node.htmlWrited){
		var link = eval(this.name + "Link" + nodeId)
		if (this.nodesIndex[nodeId].selected)
			link.className = this.styleSelected
		else
			link.className = this.styleNotSelected
	}
}

// list selected nodes
// Event
// author: Lu Gengbiao
function MenuToc_tocListSelectedValue(){
    var selectedNodes = "";
    for (var i=0 ; i < this.nodesCounter; i++){
        if (this.nodesIndex[i].selected)
            selectedNodes += "," + this.nodesIndex[i].value;
    }
    if (selectedNodes != "")
        selectedNodes = selectedNodes.substring(1);

    return selectedNodes;
}
// list selected nodes's name
// Event
// author: Lu Gengbiao
function MenuToc_tocListSelectedName(){
    var selectedNodes = "";
    for (var i=0 ; i < this.nodesCounter; i++){
        if (this.nodesIndex[i].selected)
            selectedNodes += "\n" + this.nodesIndex[i].desc;
    }
    if (selectedNodes != "")
        selectedNodes = selectedNodes.substring(1);

    return selectedNodes;
}
// select nodes
// Event
// author: Sheng Huijing
function MenuToc_tocSelectNodes(ids){
    if (ids == "")
       return;

    var tempIds = "," + ids + ",";
    for (var i=0 ; i < this.nodesCounter; i++){
    	if (tempIds.indexOf(","+this.nodesIndex[i].value+",")>=0){
    	     this.nodeSelected(i);
    	}
    }
}
// clean all the selections...
// Event
// author: Sheng Hui jing
function MenuToc_tocCleanAllSelection(beginIndex){
    for (var i=beginIndex ; i < this.nodesCounter; i++){
    	if (this.nodesIndex[i].href){
    	   this.nodesIndex[i].selected = false;
           this.changeNodeStyle(i);
        }
    }
}
// clean all the selections...
// Event
// author: Du Ya ming
function MenuToc_tocNodeClickedAndSelected(nodeId){
	for (var i=0 ; i < this.nodesCounter; i++){
	    	if (this.nodesIndex[i].href){
	    	   this.nodesIndex[i].selected = false;
	           this.changeNodeStyle(i);
	        }
	}
    var clickedNode = this.nodesIndex[nodeId];
    clickedNode.setState(!clickedNode.isExpanded);

    var selectedNode = this.nodesIndex[nodeId];
    if(selectedNode.href){
        selectedNode.selected = !selectedNode.selected;
        this.changeNodeStyle(nodeId);

        if (! this.multiSelect){
            // clear previous selected
            for (var i=0 ; i < this.nodesCounter; i++){
                if (nodeId != i && this.nodesIndex[i].selected){
                    this.nodesIndex[i].selected = false;
                    this.changeNodeStyle(i);
                }
            }
        } else if (this.selectChildren){
            for (var i=0 ; i < selectedNode.childrensCounter; i++){
                if (selectedNode.children[i].href)
                    this.nodeSelected(selectedNode.children[i].id);
            }
        }
     }
}
function debug(msg){
   alert(msg);
}
// END
