[#ftl/][@b.head/]
<p>${coder.name?if_exists}列表,[@b.a href="!index"]<img src="${b.theme.iconurl("actions/backward.png")}"/>返回代码列表[/@]</p>
[@b.form name="listForm" action="!search" ]
	[@b.grid items=baseCodes var="baseCode"]
		[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.method("edit"));
		bar.addItem("${b.text("action.modify")}",action.single("edit"));
		bar.addItem("${b.text("action.delete")}",action.multi("removeCode"));
		bar.addItem("${b.text("action.export")}","exportList()");
		[/@]
		<tr>
			<td align="center" width="3%" >
				<img src="static/images/action/search.png"  align="top" onclick="document.getElementById('listForm').submit()" alt="${b.text('info.filterInResult')}"/>
			</td>
			<td><input style="width:99%" type="text" name="${shortName}.code" maxlength="32" value="${Parameters['${shortName}.code']?if_exists}"/></td>
			<td><input style="width:99%" type="text" name="${shortName}.name" maxlength="32" value="${Parameters['${shortName}.name']?if_exists}"/></td>
			<td><input style="width:99%" type="text" name="${shortName}.engName" maxlength="32" value="${Parameters['${shortName}.engName']?if_exists}"/></td>
			<td><input style="width:99%" type="hidden" name="${shortName}.updatedAt" maxlength="32" value="${Parameters['${shortName}.updatedAt']?if_exists}"/></td>
			<td><input style="width:99%" type="hidden" name="${shortName}.effectiveAt" maxlength="32" value="${Parameters['${shortName}.enabled']?if_exists}"/></td>
			<td><input style="width:99%" type="hidden" name="${shortName}.invalidAt" maxlength="32" value="${Parameters['${shortName}.enabled']?if_exists}"/></td>		  
	   </tr>
	   [@b.row]
		 	[@b.boxcol/]
			[@b.col property="code" title="common.code"/]
		 	[@b.col property="name" title="common.name"/]
		 	[@b.col property="engName" title="common.engName"/]
		 	[@b.col property="updatedAt" title="common.updatedAt"]
		 		${(baseCode.updatedAt?string("yyyy-MM-dd"))?if_exists}
		 	[/@]
		 	[@b.col property="effectiveAt" title="生效日期"]
		 		${(baseCode.effectiveAt?string("yyyy-MM-dd"))?if_exists}
		 	[/@]
		 	[@b.col property="invalidAt" title="失效日期"]
		 		${(baseCode.invalidAt?string("yyyy-MM-dd"))?if_exists}
		 	[/@]
	   	[/@]
	[/@]
[/@]	
 <script type="text/javascript">
   function query(pageNo,pageSize,orderBy){
	var form = document.listForm;
	form.action="baseCode!search.action?codeName=${coder.className?if_exists}";
	form.target = "_self";
	goToPage(form,pageNo,pageSize,orderBy);
   }
	keys="code,name,engName,updatedAt,effectiveAt,invalidAt";
	titles="代码,名称,英文名,修改时间,生效时间,失效时间";
[#list 1..30 as i]<br/>[/#list]
[@b.foot/]