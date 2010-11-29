<#include "/template/head.ftl"/>
<BODY >
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td align="center">     
      <span class="contentTableTitleTextStyle">
       <font color="red"><@s.actionerror/></font>
      </span><br><br>
      [<a href="javascript:history.back();"> <@text name="attr.backPage"/> </a>]     
      [<a href="javascript:displayStactTrace()">显示日志</a>]      
    </td>
  </tr>
</table>
<input type="hidden" name="stackTrace" value="${stackTrace?if_exists}">
<input type="hidden" name="recorder" value="${Session['user.key']?if_exists.loginName?if_exists}">
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
<tr>
<td align="center">
<div id="stackTraceDiv" style="position:relative;width:100%;display:none;font-size:8px">
   <table width="100%">
      <tr>
          <td>${stackTrace?if_exists}</td>
      </tr>
   </table>
</div>
</td>
</tr>
</table>
<script>
var display=false;
function displayStactTrace(){
    var stackTraceDiv = document.getElementById('stackTraceDiv');
    if(display==false){
        stackTraceDiv.style.display='block';
        display=true;
    }
    else{
        stackTraceDiv.style.display='none';
        display=false;
    }
}
</script>
</BODY>
<#include "/template/foot.ftl"/>
