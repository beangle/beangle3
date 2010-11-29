<#include "/template/head.ftl"/>
<#include "../status.ftl"/>
 <table id="userBar"></table>
 <@table.table width="100%" id="listTable" sortable="true">
   <@table.thead>
     <@table.selectAllTd id="userId"/>
     <@table.sortTd width="10%" id="user.name" name="user.name"/>
     <@table.sortTd width="10%" id="user.fullname" name="user.fullname"/>
     <@table.sortTd width="30%" id="user.mail" name="common.email" />
   	 <@table.sortTd width="10%" id="user.creator.fullname" name="common.creator" />
  	 <@table.sortTd width="15%" id="user.defaultCategory" text="默认身份" />
  	 <@table.sortTd width="15%" id="user.updatedAt" name="common.updatedAt" />
   	 <@table.sortTd width="10%" id="user.status" name="common.status" />
   </@>
   <@table.tbody datas=users;user>
     <@table.selectTd id="userId" value="${user.id}"/>
     <td><A href="user!dashboard.action?user.id=${user.id}" target="_blank">&nbsp;${user.name} </a></td>
     <td>${user.fullname!("")}</td>
     <td>${user.mail}</td>
     <td>${(user.creator.fullname)!}</td>
     <td>${(user.defaultCategory.name)!}</td>
     <td>${user.updatedAt?string("yyyy-MM-dd")}</td>
     <td><@enableInfo user.enabled/></td>
   </@>
 </@>
 <@htm.actionForm name="actionForm" entity="user" action="user.action"/>
 
<script>
   function activateUser(isActivate){
       addInput(form,"isActivate",isActivate);
       multiIdAction("activate","确定提交?");
   }
   function exportUserList(){
       var form=document.actionForm;
       addInput(form,"keys","name,password,status,mail,creator.name,createdAt,updatedAt,groups,mngGroups")
       addInput(form,"titles","登录名,密码,状态,电子邮件,创建者,创建时间,修改时间,用户组,管理用户组");
       form.action=action+"?method=export";
       form.submit();
   }
   var bar = new ToolBar('userBar','用户列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.new"/>",add,'new.gif');
   bar.addItem("<@text "action.modify"/>",edit,'update.gif');
   bar.addItem("<@text "action.freeze"/>","activateUser('false')",'${base}/static/icons/beangle/16x16/actions/freeze.png');
   bar.addItem("<@text "action.activate"/>","activateUser('true')",'${base}/static/icons/beangle/16x16/actions/activate.png');
   bar.addItem("<@text "action.delete"/>",remove,'delete.gif');
   bar.addItem("<@text "action.export"/>",exportUserList,'excel.png');   
  </script>
  </body>
</html>