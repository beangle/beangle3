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
/** -----------------------table tree -----------------------------\
 |              from http://sstree.tigris.org/                     |
 |             introduce by chaostone 2005-10-11                   |
 |-----------------------------------------------------------------|
 |Usage:                                                           |
 |<link href="css/tableTree.css" rel="stylesheet" type="text/css"> |
 |<script language="JavaScript" type="text/JavaScript"             |
 |    src="scripts/tableTree.js"></script>                         |
 |<!--if your collapse column is not 0,here can be redefined       |
 |<script> defaultColumn=1;</script>                               |
 |.......you own html code....                                     |
 |<table>                                                          |
 |    <tr id="1">                                                  |
 |      <td>                                                       |
 |      <div class="tier1">	                                       |
 |	      <a href="#" class="folder"onclick="toggleRows(this)"></a>|
 |        other html code..                                        |
 |	    </div>                                                     |
 |      </td>                                                      |
 |      other td                                                   |
 |    </tr>                                                        |
 |    <tr id="1.1">                                                |
 |      <td>                                                       |
 |      <div class="tier2">	                                       |
 |	      <a href="#" class="doc" onclick="toggleRows(this)"></a>  |
 |        other html code..                                        |
 |	    </div>                                                     |
 |      </td>                                                      |
 |      other td                                                   |
 |    </tr>                                                        |
 |  </table>                                                       |
 \----------------------------------------------------------------*/
// default collapse column
var defaultColumn=0;
// for collapse or display child nodes.
var treeImagePath=self.location.pathname.substring(0,self.location.pathname.substring(1).indexOf('/')+1)+"/static/images/tree/";
function toggleRows(elm) {
 var rows = document.getElementsByTagName("TR");
 elm.style.backgroundImage = "url("+treeImagePath+"plus.gif)";
 var newDisplay = "none";
 var thisID = elm.parentNode.parentNode.parentNode.id + "-";
 // Are we expanding or contracting? If the first child is hidden, we expand
  for (var i = 0; i < rows.length; i++) {
   var r = rows[i];
   if (matchStart(r.id, thisID, true)) {

    if (r.style.display == "none") {
     if (document.all) newDisplay = "block"; //IE4+ specific code
     else newDisplay = "table-row"; //Netscape and Mozilla
     elm.style.backgroundImage = "url("+treeImagePath+"minus.gif)";
    }
    break;
   }
 }

 // When expanding, only expand one level.  Collapse all desendants.
 var matchDirectChildrenOnly = (newDisplay != "none");
 
 for (var j = 0; j < rows.length; j++) {
   var s = rows[j];
   if (matchStart(s.id, thisID, matchDirectChildrenOnly)) {
     s.style.display = newDisplay;
     // chang 0 to defaultColumn by chaostone for add a checkbox before tree sometime
     var cell = s.getElementsByTagName("td")[defaultColumn]; 
     var tier = cell.getElementsByTagName("div")[0];
     var folder = tier.getElementsByTagName("a")[0];

     if (folder.getAttribute("onclick") != null) {
      folder.style.backgroundImage = "url("+treeImagePath+"plus.gif)";
     }
   }
 }
}

function matchStart(target, pattern, matchDirectChildrenOnly) {
 var pos = target.indexOf(pattern);
 if (pos != 0) return false;
 if (!matchDirectChildrenOnly) return true;
 if (target.slice(pos + pattern.length, target.length).indexOf(".") >= 0) return false;
 return true;
}

function collapseAllRows() {
 var rows = document.getElementsByTagName("tr");
 for (var j = 0; j < rows.length; j++) {
   var r = rows[j];
   if (r.id.indexOf(".") >1 ) {
     r.style.display = "none";
   }
 }
}
/**
  added by chaostone for collapse special depth
  2005-10-11 
*/
function collapseAllRowsFor(depth) {
 var rows = document.getElementsByTagName("tr");
 for (var j = 0; j < rows.length; j++) {
   var r = rows[j];
   if (r.id.lastIndexOf(".") >depth ) {
     r.style.display = "none";
   }
 }
}
/**
  added by chaostone for display special depth.
  2005-10-11
*/
function displayAllRowsFor(depth) {
 var rows = document.getElementsByTagName("tr");
 for (var j = 0; j < rows.length; j++) {
   var r = rows[j];
   if (r.id.lastIndexOf(".") > depth) {
     r.style.display = "";
   }
 }
}
    /**
     * for  tree select 
     * @params ele 选择的行
     * @params toggleParent是否在选中时,级联选中父节点
     */
    function treeToggle(elm,toggleParent){
       var rows = document.getElementsByTagName("tr");
       var thisID = elm.parentNode.parentNode.id;
       var checked = elm.checked;
       if(null==toggleParent){
          toggleParent=true;
       }
       for (var i = 0; i < rows.length; i++) {
             var r = rows[i];             
             if (r.id!=""&&((r.id.indexOf(thisID)==0)||(thisID.indexOf(r.id)==0))){
                 var cell = r.getElementsByTagName("td")[0];
                 var input = cell.getElementsByTagName("input")[0];                 
                 if(thisID.indexOf(r.id)==0){
                    if(checked&&toggleParent)
                       input.checked=true;
                 }else{
                    input.checked = checked;
                 }
             }
       }             
    }
    function treeToggleAll(elm){
     var rows = document.getElementsByTagName("tr");
       var thisID = elm.parentNode.parentNode.id;
       var checked = elm.checked;
        for (var i = 0; i < rows.length; i++) {
             var r = rows[i];     
             if (r.id.indexOf("1")>-1){
                 var cell = r.getElementsByTagName("td")[0];
                 var input = cell.getElementsByTagName("input")[0];
                 input.checked = checked; 
             }             
        } 
    }
