[#ftl/]
<ul id="${tag.id}" class="nav nav-tabs nav-tabs-compact" ${tag.parameterString}>
  [#list tag.tabs as tab]
  <li class="nav-item"><a href="#${tab.id}" data-toggle="tab" class="nav-link">${tab.label}</a></li>
  [/#list]
</ul>
<div id="${tag.id}_content" class="tab-content">
${tag.body}
</div>
<script>
beangle.require(["bootstrap"],function(){
  $(function () {
    $('#${tag.id} li:eq(${tag.selected}) a').tab('show')
    $('#${tag.id} a').click(function (e) {
      e.preventDefault();
      $(this).tab('show');
    });
  });
});
</script>
