[#ftl]
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString}>${tag.body}</form>