[#ftl/]
<ul id="${tag.id}" class="nav nav-tabs" ${tag.parameterString}>
  [#list tag.tabs as tab]
  <li><a href="#${tab.id}" data-toggle="tab">${tab.label}</a></li>
  [/#list]
</ul>
<div id="${tag.id}_content" class="tab-content">
${tag.body}
</div>
<script>
  $(function () {
    $('#${tag.id} li:eq(${tag.selected}) a').tab('show')
    $('#${tag.id} a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
  })
  })
</script>
