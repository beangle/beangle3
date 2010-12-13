[#ftl]
[#if (actionErrors?? && actionErrors?size > 0)]<div class="message fade-ffff00"  id="errors">[#list actionErrors as amsg]${amsg}[/#list]</div>[/#if]