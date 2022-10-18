[#ftl]
[#if tag.hasTr]${tag.body}[#else]<tr ${tag.parameterString}>${tag.body}</tr>[/#if]
