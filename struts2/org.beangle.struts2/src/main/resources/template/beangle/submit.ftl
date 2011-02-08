[#ftl]
<input type="submit" value="${tag.value!'Submit'}" onclick="bg.form.submit('${tag.formId}',null,null[#if tag.onsubmit??],${tag.onsubmit}[/#if]);return false;"/>