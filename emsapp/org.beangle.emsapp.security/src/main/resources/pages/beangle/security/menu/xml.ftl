[#ftl][#assign scopes=["public","protected","private"] /]
[#assign content]
<?xml version="1.0" encoding="UTF-8" ?>
<settings>
  <resources>
    [#list resources?sort_by('name') as rsrc]
    <resource name="${rsrc.name}" title="${rsrc.title}" remark="${rsrc.remark!}" scope="${scopes[rsrc.scope]}"  categories="[#list rsrc.categories as ca]${ca.name}[#if ca_has_next],[/#if][/#list]"/>
    [/#list]
  </resources>
  <profiles>
    [#list menuProfiles as mp]
    <profile name="${mp.name}" category="${mp.category.name}">
      [#list mp.menus?sort_by('code') as m]
      <menu [#if m.entry??]entry="${m.entry?xml}"[/#if] name="${m.name!}" title="${m.title}" [#if m.parent??]parent="${(m.parent.name)!}"[/#if] remark="${(m.parent.remark)!}"/>
      [/#list]
    </profile>
    [/#list]
  </profiles>
</settings>
[/#assign]
<pre>
${content?html}
</pre>