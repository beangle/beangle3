[#ftl/]
<script type="text/javascript">beangle.load(["my97"]);</script>
<div class="search-item">[#if tag.label??]<label for="${tag.id}" style="font-weight:inherit">${tag.label}:</label>[/#if]<input type="text" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if tag.maxDate??],maxDate:'${tag.maxDate}'[/#if][#if tag.minDate??],minDate:'${tag.minDate}'[/#if]})" name="${tag.name}" value="${tag.value}" ${tag.parameterString}/></div>
