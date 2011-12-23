[#ftl]
[@b.head/]
[#include "nav.ftl"/]
[@b.messages slash="true"/]
[@b.form action="!saveProfile"]
<table width="70%">
	<tr style="background-color:#e1e8f5">
		<td>用户种类</td>
		<td>上限</td>
		<td>过期时间</td>
		<td>单用户最大会话数</td>
	</tr>
	[#list categoryProfiles?sort_by(["category"]) as profile]
	<tr style="background-color:${(profile_index%2!=0)?string("#e1e8f5","#FFFFFF")}">
		<td>${profile.category.title}(${profile.category.name})</td>
		<td><input name="max_${profile.category.id}" value="${profile.capacity}" style="width:50px" maxlength="5"/></td>
		<td><input name="inactiveInterval_${profile.category.id}" value="${profile.inactiveInterval}" style="width:50px" maxlength="5"/>分</td>
		<td><input name="maxSessions_${profile.category.id}" value="${profile.userMaxSessions}" style="width:35px" maxlength="2"/></td>
	</tr>
	[/#list]
	<tr>
		<td  colspan="5">注:最大会话数指单个用户同时在线数量&nbsp;&nbsp;
		&nbsp;
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="提交" onsubmit="validateProfile"/]
		</td>
	</tr>
</table>
[/@]
<script type="text/javascript">
	function validateProfile(form){
		[#list categoryProfiles as profile]
		if(!(/^\d+$/.test(form['max_${profile.category.id}'].value))){alert("${profile.category.title}最大用户数限制应为0或正整数");return false;}
		if(!(/^\d+$/.test(form['maxSessions_${profile.category.id}'].value))){alert("${profile.category.title}最大会话数应为0或正整数");return false;}
		if(!(/^\d+$/.test(form['inactiveInterval_${profile.category.id}'].value))){alert("${profile.category.title}过期时间应为0或正整数");return false;}
		[/#list]
		return true;
	}
</script>
[@b.foot/]