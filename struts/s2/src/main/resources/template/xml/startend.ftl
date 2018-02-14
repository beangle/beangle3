[#ftl/]
<script type="text/javascript">beangle.ui.load("My97DatePicker");</script>
[#list tag.dates as date]
[#if date.label??]
<label for="${date.id}" class="title">
[#if (date.required!"")=="true"]<em class="required">*</em>[/#if]${date.label}:</label>[/#if]
<input type="text" id="${date.id}" [#if date.title??]title="${date.title}"[/#if] class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if date.maxDate??],maxDate:'${date.maxDate}'[/#if][#if date.minDate??],minDate:'${date.minDate}'[/#if]})" name="${date.name}" value="${date.value!}" ${tag.parameterString}/>
[#if date.comment??]<label class="comment">${date.comment}</label>[/#if]
[/#list]
