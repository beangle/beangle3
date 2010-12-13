[#ftl]
[#include "/template/head.ftl"/]
<body>
<table id="resourceInfoBar"></table>
     <table class="infoTable">
	   <tr>
         <td class="title" >[@text name="common.description"/]:</td>
         <td  class="content">${pattern.description!}</td>
	     <td class="title" >使用参数:</td>
         <td class="content" colspan="3">[#list pattern.paramGroup.params as param](${param.name})${param.description}<br>[/#list]</td>
	   </tr>
       <tr>
         <td class="title" >限制模式:</td>
         <td class="content" colspan="3">${pattern.content!}</td>
       </tr>
       <tr>
       </tr>
      </table>
  <script>
   var bar = new ToolBar('resourceInfoBar','[@text name="security.restrictionPattern.info"/]',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addBack("[@text name="action.back"/]");  
  </script>
 </body>
[#include "/template/foot.ftl"/]
