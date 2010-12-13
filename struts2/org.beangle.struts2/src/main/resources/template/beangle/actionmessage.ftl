[#ftl]
[#if (actionMessages?? && actionMessages?size > 0)]<div class="message fade-ffff00"  id="message">[#list actionMessages as amsg]${amsg}[/#list]</div>[/#if]