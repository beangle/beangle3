[#ftl]
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
<table class="search-widget">
[#if tag.title??]
<tr><td><img src="${b.theme.iconurl("actions/info.png")}" alt="info" class="toolbar-icon"/><em>${tag.title}</em></td></tr>
<tr><td style="font-size:0px"><img src="${b.theme.iconurl("actions/keyline.png")}" height="2" width="100%" alt="keyline"/></td></tr>
[/#if]
${tag.body}[#if !tag.body?contains('submit')]<tr><td align="center"><input type="reset" value="${b.text('action.reset')}"/>&nbsp;&nbsp;<input type="submit" value="查询" onclick="bg.form.submit('${tag.id}');return false;"/></td></tr>[/#if]
</table>
</form>