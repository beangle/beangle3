[#ftl]
[@b.head title="Dashboard"/]
<style type="text/css">
img {border:0 none;vertical-align:middle}
.module{margin:0px 3px 5px;line-height:1.3em;float:left;width:42%;}
</style>

[#macro menuitem image="" link="" name="" remark=""]
<tr>
	<td>[@b.a href=link]<img height="48" style="margin-right:1em" alt="" width="48" src="${b.theme.iconurl(image!,48)}" />[/@]</td>
	<td style="vertical-align:middle">
		<div class="link">[@b.a href="${link}"]${name}[/@]</div>
		<div style="color:gray; text-decoration:none;">${remark!}</div>
	</td>
</tr>
[/#macro]
[#include "../nav.ftl"/]
<table>
<tr>
	<td width="40%" valign="top">
		<table id="management-links" style="padding-left: 2em;">
			[@menuitem image="user.gif" link="user" name="用户管理" remark="创建用户,修改密码,分配角色"/]
			[@menuitem image="role.gif" link="role" name="角色管理" remark="创建角色,修改角色,维护组内用户,分配资源权限和数据权限"/]
			[@menuitem image="menu.gif" link="menu" name="菜单资源" remark="注册/启用/禁用资源,创建菜单配置,维护菜单项,启用/禁用菜单"/]
			[@menuitem image="monitor.gif" link="monitor" name="系统监控" remark="管理在线用户,控制在线数量,查询登录历史"/]
		</table>
	</td>
	<td valign="top">
	[@b.div id="data" href="!stat" /]
	</td>
</tr>
</table>

[@b.foot/]
