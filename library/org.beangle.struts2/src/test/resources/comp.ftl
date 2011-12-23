[#ftl/]
[#macro anchor href="" extra...]
<a href="${b.url(href)}" [#if extra?size>0][#list extra?keys as k] ${k}="${extra[k]}"[/#list][/#if]>[#nested/]</a>
[/#macro]

[#list 1..2 as b]
${watch.reset()!}${watch.start()!}
[#list 1..1000 as i]
[@s.a href="/demo/security/user.action" target="_blank${i}"][/@]
[/#list]
${watch.time}&nbsp;
[/#list]
<br/>
[#list 1..10 as dd]
${watch.reset()!}${watch.start()!}
[#list 1..1000 as i]
[@b.a href="user" target="_blank${i}"][/@]
[/#list]
${watch.time}&nbsp;
[/#list]
<br/>
[#list 1..10 as b]
${watch.reset()!}${watch.start()!}
[#list 1..1000 as i]
[@anchor href="user"  target="_blank${i}"][/@]
[/#list]
${watch.time}&nbsp;
[/#list]
