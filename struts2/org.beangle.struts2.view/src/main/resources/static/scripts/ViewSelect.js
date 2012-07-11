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
