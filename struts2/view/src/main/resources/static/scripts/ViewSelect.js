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
 /// add by duantihua 2005-11-21
	 var  selectStatus =new Object();	 
	 for(var i=1; i<viewNum;i++){
        selectStatus["viewTD"+i]=false;
     }
     selectStatus["viewTD0"]=true;
    // change view 
    function changeView(S){
     while (S.tagName!="TD")
	     {S=S.parentNode;}
     for(var i=0; i<viewNum;i++){
       if(null==document.getElementById("viewTD"+i)){
          continue;
       }
       if(S!=document.getElementById("viewTD"+i)){
	       document.getElementById("viewTD"+i).className="padding";
	       selectStatus["viewTD"+i]=false;
        }else{
	     S.className="transfer";
	     selectStatus["viewTD"+i]=true;        
        }
        }
    }
    // td event
    function viewMouseOver(event){
      var S=getEventTarget(event);
      while (S.tagName!="TD")
       {S=S.parentNode;}
       S.className="transfer";
    }

    function viewMouseOut(event){
     var S=getEventTarget(event);
     while (S.tagName!="TD")
       {S=S.parentNode;}
      if(!selectStatus[S.id]){
        S.className="padding";
                 }
    }
