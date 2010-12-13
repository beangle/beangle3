[#ftl]
[#include "/template/head.ftl"/]
[#include "../status.ftl"/]
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="groupBar"></table>
  [@table.table width="100%" id="groupListTable" sortable="true"]
	   [@table.thead]
	     [@table.selectAllTd id="groupId"/]
	     [@table.sortTd id="userGroup.name" width="20%" name="common.name"/]
   	     [@table.sortTd width="15%" id="userGroup.owner.name" name="common.creator"/]
  	     [@table.sortTd width="15%" id="userGroup.updatedAt" name="common.updatedAt"/]
  	     [@table.sortTd width="15%" id="userGroup.remark" text="适用身份"/]
  	     [@table.sortTd width="10%" id="userGroup.enabled" name="common.status"/]
  	     [@table.td text="设置权限"/]
	   [/@]
	   [@table.tbody datas=groups;group]
        [@table.selectTd type="checkbox" id="groupId" value="${group.id}"/]
	    <td ><A href="group!info.action?groupId=${group.id}">${group.name} </a></td>
        <td >${group.owner.name}</td>
        <td >${group.updatedAt?string("yyyy-MM-dd")}</td>
        <td >[#if group.category??]${group.category.name}[/#if]</td>
        <td>[@enableInfo group.enabled/]</td>
        <td><a target="_blank" href="authority!edit.action?group.id=${group.id}"><img style="border:0px" src="${base}/static/icons/beangle/16x16/actions/config.png"/>设置权限</a></td>
	   [/@]
    [/@]
  [@htm.actionForm name="actionForm" entity="group" action="group.action"/]
 <script>
   var bar = new ToolBar('groupBar','用户组列表',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addItem("[@text "action.new"/]",add,'new.gif');
   bar.addItem("[@text "action.modify"/]",edit,'update.gif');
   bar.addItem("[@text "action.delete"/]",remove,'delete.gif');
   bar.addItem("[@text "action.export"/]",exportData,'excel.png');
   
   function exportData(){
      form=document.actionForm;
      addInput(form,"keys","name,description,creator.name,createdAt,updatedAt,users");
      addInput(form,"titles","[@text "common.name"/],[@text "common.description"/],[@text "common.creator"/],[@text "common.createdAt"/],[@text "common.updatedAt"/],[@text "group.users"/]");
      exportList();
   }
  </script>
 </body>
[#include "/template/foot.ftl"/]
