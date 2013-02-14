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

function OnReturn(form){
   this.form=form;
   this.elemts=new Array();
   this.add=addElemement;
   this.focus=setFocus;
   this.focusById=setFocusById;
   this.select=true;
}
function addElemement(ele){
  this.elemts.push(ele);
}
  // 设置焦点
  function setFocus(event){
     if(event.keyCode==13){
        var target = beangle.event.getTarget(event);
        var name=target.name;
        for(var i=0;i<this.elemts.length-1;i++){
          if(name==this.elemts[i]){
             if(this.form[this.elemts[i+1]] && this.form[this.elemts[i+1]].type!="hidden"){
                 this.form[this.elemts[i+1]].focus();
                 if(this.form[this.elemts[i+1]].type=="text"){
                    this.form[this.elemts[i+1]].select();
                 }
                 break;
             }else{
                 name=this.elemts[i+1];
                 continue
             }
          }
        }
     }
  }
  
  function setFocusById(event){
     if(event.keyCode==13){
        var target = getEventTarget(portableEvent(event));
        var name=target.id;
        for(var i=0;i<this.elemts.length-1;i++){
          if(name==this.elemts[i]){
             if(document.getElementById(this.elemts[i+1]) && document.getElementById(this.elemts[i+1]).type!="hidden"){
                 document.getElementById(this.elemts[i+1]).focus();
                 if(document.getElementById(this.elemts[i+1]).type=="text"){
                    document.getElementById(this.elemts[i+1]).select();
                 }
                 break;
             }else{
                 name=this.elemts[i+1];
                 continue;
             }
          }
        }
     }
  }  
