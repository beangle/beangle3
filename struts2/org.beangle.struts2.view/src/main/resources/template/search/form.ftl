[#ftl]
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
[#if Parameters['params']??]<input name="params" type="hidden" value="${Parameters['params']?html}" />[/#if]
<table class="search-widget">
[#if tag.title??]
<tr><td><span class="toolbar-icon action-info"></span><em>${tag.title}</em></td></tr>
<tr><td style="font-size:0px"><img src="${b.theme.iconurl("actions/keyline.png")}" height="2" width="100%" alt="keyline"/></td></tr>
[/#if]
${tag.body}[#if !tag.body?contains('submit')]<tr><td align="center"><input type="submit" value="${b.text('action.search')}" onclick="bg.form.submit('${tag.id}');return false;"/>&nbsp;&nbsp;<input type="reset" value="${b.text('action.reset')}"/></td></tr>[/#if]
</table>
</form>