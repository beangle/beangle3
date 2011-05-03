[#ftl]
[#list tag.fields as field]
<tr><td class="search-item"><label for="${field.id}">${field.label}:</label><input type="text" id="${field.id}" name="${field.name}" value="${Parameters[field.name]!?html}"${tag.parameterString}/></td></tr>
[/#list]