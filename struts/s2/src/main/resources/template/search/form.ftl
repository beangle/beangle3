[#ftl]
<form id="${tag.id}" name="${tag.name}" [#if tag.cssClass??] class="${tag.cssClass}"[/#if] action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if][#if tag.onsubmit??] onsubmit="${tag.onsubmit}"[/#if]>
[#if Parameters['_params']??]<input name="_params" type="hidden" value="${Parameters['_params']?html}" />[/#if]
<div class="search-widget">
[#if tag.title??]
<div class="search-header">
  <span class="toolbar-icon action-info"></span><em>${tag.title}</em>
</div>
<div class="border-bottom-1px border-blue" style="margin: 0px 0px 4px 0px;width:100%;"></div>
[/#if]
${tag.body}
[#if !tag.body?contains('submit')]
  <div class="search-footer"><button class="btn btn-outline-primary btn-sm" type="submit" onclick="bg.form.submit('${tag.id}');return false;"><i class="fa fa-search fa-sm"></i> ${b.text('action.search')}</button></div>
[/#if]
</div>
</form>
