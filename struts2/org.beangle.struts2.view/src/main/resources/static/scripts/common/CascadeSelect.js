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
/*----------------------------------------------------------------------------\
|                             Cascade Select 1.0                               |
|------------------------------------------------------------------------------|
|                         Created by Ti hua Duan                               |
|------------------------------------------------------------------------------|
|                  Copyright (c) 2005 - 2008 Ti hua Duan                       |
|------------------------------------------------------------------------------|
| This software is used for cascade selection.                                 |
| - - - - - - - - - - - - - - - - - - - - - - - - - -- - - - -- - -- - - - - - |
| Usage: here is a example.                                                    |
|    <body>                                                                    |     
|    <form name="form" id="ddd">                                               |
|    <select id="first" name="obj.first" >                                     |
|        <option value="0">choose one...</option>                              |
|    </select>                                                                 |
|    <select id="second" name="obj.second" >                                   |
|        <option value="0"> choose one...</option>                             |
|    </select>                                                                 |
|    <select id="third" name="obj.third" >                                     |
|        <option value="0"> choose one...</option>                             |
|    </select>                                                                 |
|    </form>                                                                   |
|    </body>                                                                   |
|    <script type="text/JavaScript" src="cascadeSelect.js"></script>           |
|    <script>                                                                  |
|         // initialize  first array                                           |
|        var firstArray = new Array(2);                                        |
|        for(var i=0;i<2;i++){                                                 |
|          firstArray[i]= new Array(3);                                        |
|          firstArray[i][0]= null;  // no parent                               |
|          firstArray[i][1]=i;      // value represent id                      |
|          firstArray[i][2]="first" + i; // name to display                    |
|         }                                                                    |
|         // initialize second array                                           |
|        var secondArray = new Array(4);                                       |
|        for(var i=0;i<4;i++){                                                 |
|          secondArray[i]= new Array(3);                                       |
|          secondArray[i][0]= 0;                                               |
|          secondArray[i][1]=i;                                                |
|          secondArray[i][2]="second" + i;                                     |
|         }                                                                    |
|    initSelect(firstArray,document.form.first,null,document.form.second);     |
|    initSelect(secondArray,document.form.second,document.form.first,null);    |
|    </script>                                                                 |
|------------------------------------------------------------------------------|
| Created 2005-10-23 |                                    | Updated 2005-10-23 |
\------------------------------------------------------------------------------*/
/* store select.id->valueArray
   and   select.id.child->child.id
*/
var defaultWidthStyle='155px';
var valueMap= new Object();
// default tip for select
var tip ='<option value="" selected> select one...</option>';
// init a select.self,parent,child is select id.
function initSelect(valueArray,self,parent,child){
    if(null==self) return;
    var parentSelect = null;
    var selfSelect = document.getElementById(self)
    if(parent!=null)
      parentSelect = document.getElementById(parent);
    valueMap[self]=valueArray;
    // change default width
    defaultWidthStyle=selfSelect.style.width;
    tip='<option value="" selected>' + selfSelect.options[0].innerHTML +'</option>';
    // add child select id not the select
    if(null!=child)
      valueMap[self+".child"]= child;
    // gen select options
    var options = "";    
    for(i=0;i<valueArray.length;i++){
      // alert(valueArray[i][0] + ":" +parentSelect.value);
       if((null==parentSelect)||(valueArray[i][0]==parentSelect.value)){       
       if(valueArray[i][1]==selfSelect.value)
          options += '<option value="'+valueArray[i][1]+'" selected>'+valueArray[i][2]+'</option>';         
       else options += '<option value="'+valueArray[i][1]+'">'+valueArray[i][2]+'</option>';
        }
     }
    // output
    if(selfSelect.value=="")
        options +='<option value="" selected> '+selfSelect.options[0].innerHTML +' </option>';
    var outHTML = '<select  id="' + self + '" name="'+ selfSelect.name + 
       '" onChange="changeSelect(this)" style="width:'+ defaultWidthStyle+';">'+options+'</select>';
    selfSelect.outerHTML=outHTML;
    
    //alert(selfSelect.outerHTML);
}

// select change function.you should not use it directly.
function changeSelect(select1){
    if(null==select1) return;
    // avoid bug 2005-10-28
    var select = document.getElementById(select1.id);
    var options = "";
    // get valueArray
    var valueArray = valueMap[valueMap[select.id+".child"]];
    if(null==valueArray) return;
    // get childSelect element
    var childSelect = document.getElementById(valueMap[select.id+".child"]);
    // generate options ready for default tip
    options= tip;   
    // check for option select. 2005-10-28
    if(select.options.length>0)
      for(i=0;i<valueArray.length;i++){
	  if(select.options[select.selectedIndex].value==valueArray[i][0])
	    options +='<option value="'+valueArray[i][1]+'">'+valueArray[i][2]+'</option>';
      }
       
    // output
    var outHTML='<select  id="' + childSelect.id + 
         '" name="' + childSelect.name + 
         '" onChange="changeSelect(this)" style="width:'+defaultWidthStyle+';">'+options+'</select>';
    childSelect.outerHTML=outHTML; 
    changeSelect(childSelect);
}
