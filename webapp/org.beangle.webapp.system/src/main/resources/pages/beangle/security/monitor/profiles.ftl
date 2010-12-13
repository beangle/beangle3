[#ftl]
[@s.actionmessage theme="beangle"/]
[@s.form name="profileForm" id="profileForm" theme="simple" action="monitor!saveProfile"]
<table  border="0" cellpadding="0" cellspacing="0" width="50%">
	<tr>
		<td colspan="5" align="center">
		[#assign online=0][#assign max=0]
		[#list onlineProfiles as profile]
		[#assign online=online+profile.online/][#assign max=max+profile.capacity/]
		[/#list]
		<strong>在线${online} / 上限${max}</strong>
		</td>
	</tr>
	<tr style="background-color:#e1e8f5">
		<td>用户种类</td>
		<td>在线</td>
		<td>上限</td>
		<td>过期时间</td>
		<td>单用户最大会话数</td>
	</tr>
	[#list onlineProfiles?sort_by(["category"]) as profile]
	<tr style="background-color:${(profile_index%2!=0)?string("#e1e8f5","#FFFFFF")}">
		<td>${profile.category.name}</td>
		<td>${profile.online}</td>
		<td><input name="max_${profile.category.id}" value="${profile.capacity}" style="width:50px" maxlength="5"/></td>
		<td><input name="inactiveInterval_${profile.category.id}" value="${profile.inactiveInterval}" style="width:50px" maxlength="5"/>分</td>
		<td><input name="maxSessions_${profile.category.id}" value="${profile.userMaxSessions}" style="width:35px" maxlength="2"/></td>
	</tr>
	[/#list]
	<tr>
		<td  colspan="5">注:最大会话数指单个用户同时在线数量&nbsp;&nbsp;
		[@sj.submit type="button" targets="ui-tabs-2" label="提交" /]
		</td>
	</tr>
</table>
[/@]
<script>
	function validateProfile(){
		var form=document.profileForm;
		[#list onlineProfiles as profile]
		if(!(/^\d+$/.test(form['max_${profile.category.id}'].value))){alert("${profile.category.name}最大用户数限制应为整数");return false;}
		if(!(/^\d+$/.test(form['maxSessions_${profile.category.id}'].value))){alert("${profile.category.name}最大会话数应为整数");return false;}
		if(!(/^\d+$/.test(form['inactiveInterval_${profile.category.id}'].value))){alert("${profile.category.name}过期时间应为整数");return false;}
		[/#list]
		return true;
	}
</script>
