[#ftl]
[#include "/template/head.ftl"/]
<body> 
 <table id="passwordBar"></table>
 <form name="pageGoForm" method="post" action="" target="contentFrame"></form>
 <table  class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
    </iframe>
    </td>
   </tr>
  </table>
 <script>
   var form=document.pageGoForm;
   function changePassword(){
   	  form.action="password.action?method=changePassword";
      form.submit();
   }
   changePassword();
   
   var bar = new ToolBar('passwordBar','密码修改',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addHelp();
  </script>
</body>
[#include "/template/head.ftl"/]
