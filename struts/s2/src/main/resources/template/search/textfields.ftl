[#ftl]
[#list tag.fields as field]
<div class="search-item"><label for="${field.id}">${field.label}:</label><input type="text" id="${field.id}" name="${field.name}" value="${Parameters[field.name]!?html}" maxlength="${field.maxlength}" ${tag.parameterString}/></div>
[/#list]
