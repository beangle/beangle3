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
/** -----------------------table tree --------------------------\
 |			  from http://sstree.tigris.org/					|
 |			 introduce by chaostone 2005-10-11					|
 |--------------------------------------------------------------|
 |<table>														|
 |	<tr id="1">													|
 |	  <td>														|
 |	  <div class="tree-tier1">									|
 |		  <a href="#" class="tree-folder"onclick="toggleRows(this)"></a>|
 |		other html code..										|
 |		</div>													|
 |	  </td>														|
 |	</tr>														|
 |	<tr id="1.1">												|
 |	  <td>														|
 |	  <div class="tree-tier2">									|
 |		  <a href="#" class="tree-doc" onclick="toggleRows(this)"></a>|
 |		other html code..										|
 |		</div>													|
 |	  </td>														|
 |	</tr>														|
 |  </table>													|
 \-------------------------------------------------------------*/
var treeIdComma=".";
// for collapse or display child nodes.
function toggleRows(elm) {
	var fireColumn=getFireColumnIndex(elm);
	var fireCell=getFireCell(elm);
	var rows =fireCell.parentNode.parentNode.getElementsByTagName("TR");
	var thisID = fireCell.parentNode.id + treeIdComma;
	var newDisplay = "none";
	if(elm.className=="tree-folder"){
		if (document.all) newDisplay = "block"; //IE4+ specific code
		else newDisplay = "table-row"; //Netscape and Mozilla
		elm.className="tree-folder-open";
	}else{
		elm.className="tree-folder";
	}
	// When expanding, only expand one level.  Collapse all desendants.
	var matchDirectChildrenOnly = (newDisplay != "none");
	for (var j = 0; j < rows.length; j++) {
		var s = rows[j];
		if (matchStart(s.id, thisID, matchDirectChildrenOnly)) {
			s.style.display = newDisplay;
			var cell = s.getElementsByTagName("td")[fireColumn]; 
			var tier = cell.getElementsByTagName("div")[0];
			var folder = tier.getElementsByTagName("a")[0];

			if (folder.getAttribute("onclick") != null) {
				folder.className="tree-folder";
			}
		}
	}
}

function matchStart(target, pattern, matchDirectChildrenOnly) {
	var pos = target.indexOf(pattern);
	if (pos != 0) return false;
	if (!matchDirectChildrenOnly) return true;
	if (target.slice(pos + pattern.length, target.length).indexOf(treeIdComma) >= 0) return false;
	return true;
}

function collapseAllRows() {
	var rows = document.getElementsByTagName("tr");
	for (var j = 0; j < rows.length; j++) {
		var r = rows[j];
		if (r.id.indexOf(treeIdComma) >1 ) {
			r.style.display = "none";
		}
	}
}

/**
 * added by chaostone for collapse special depth
 * 2005-10-11 
 */
function countchar(id){
	var cnt=0;
	for(var i=0;i<id.length;i++){
		if(id.charAt(i)==treeIdComma) cnt++;
	}
	return cnt+1;
}

function collapseAllRowsFor(depth) {
 var rows = document.getElementsByTagName("tr");
 for (var j = 0; j < rows.length; j++) {
   var r = rows[j];
   if(countchar(r.id) >depth ) r.style.display = "none";
   if(countchar(r.id) >=depth ) {
	 var rowFolder=document.getElementById(r.id+"_folder");
	 if(rowFolder){
	 	rowFolder.className="tree-folder";
	 }
   }
 }
}

/**
 * added by chaostone for display special depth.
 * 2005-10-11
 */
function displayAllRowsFor(depth) {
	var rows = document.getElementsByTagName("tr");
	for (var j = 0; j < rows.length; j++) {
		var r = rows[j];
		if (countchar(r.id) > depth) r.style.display = "";
		if (countchar(r.id) >= depth) {
			var rowFolder=document.getElementById(r.id+"_folder");
			if(rowFolder){
	 			rowFolder.className="tree-folder-open";
			}
		}
	}
}

/**
 * for  tree select 
 * @params ele 选择的行
 * @params toggleParent是否在选中时,级联选中父节点
 */
function treeToggle(elm,callback,toggleParent){
	var rows = document.getElementsByTagName("tr");
	var thisID = elm.parentNode.parentNode.id;
	var checked = elm.checked;
	if(null==toggleParent){
	  toggleParent=true;
	}
	fireColumn=getFireColumnIndex(elm);
	for (var i = 0; i < rows.length; i++) {
		 var r = rows[i];
		 if (r.id!=""&&((r.id.indexOf(thisID)==0)||(thisID.indexOf(r.id)==0))){
			 var cell = r.getElementsByTagName("td")[fireColumn];
			 var input = cell.getElementsByTagName("input")[0];
			 if(input.disabled) continue;
			 var fireCallback=false;
			 if(thisID.indexOf(r.id)==0){
				if(checked && toggleParent) input.checked=true;
				if(thisID==r.id) fireCallback=true;
			 }else{
				input.checked = checked;
				fireCallback=true;
			 }
			 if(fireCallback && callback) callback(input);
		 }
   }			 
}

function getFireCell(ele){
	var p = ele;
	while(p && p.tagName.toLowerCase()!="td"){
		if(p==p.parentNode) p=null;
		else p=p.parentNode;
	}
	if(!p) {alert("cannot find fired cell")}
	return p;
}
function getFireColumnIndex(elm){
	var cell=getFireCell(elm);
	var rowTds=cell.parentNode.getElementsByTagName("td");
	var fireColumn=0;
	for(j=0;j<rowTds.length;j++){
		if(rowTds[j]==cell)  fireColumn=j;
	}
	return fireColumn;
}

function treeToggleAll(elm,callback){
	var fireColumn=getFireColumnIndex(elm);
	var rows = document.getElementsByTagName("tr");
	var thisID = elm.parentNode.parentNode.id;
	var checked = elm.checked;
	for (var i = 0; i < rows.length; i++) {
		 var r = rows[i];
		 if (r.id){
			 var cell = r.getElementsByTagName("td")[fireColumn];
			 var inputs=cell.getElementsByTagName("input");
			 if(inputs.length==1){
				 var input = cell.getElementsByTagName("input")[0];
				 if(!input.disabled) {
				 	input.checked = checked;
				 	if(callback) callback(input);
				 }
			 }
		 }
	} 
};
