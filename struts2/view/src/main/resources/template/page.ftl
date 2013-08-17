[#ftl]

[#-- pageId curPage sortable headIndex  scheme fixPageSize--]
[#macro bar pageId curPage extra...]
  <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/beangle/page.js?v=2"></script>
  <script>
  if(pages["${pageId}"]==null){
    pages["${pageId}"]=new Object();
  }
  
  pages["${pageId}"].id="${pageId}";
  pages["${pageId}"].action="${request.requestURI}";
  pages["${pageId}"].target="${extra['target']!""}";
  pages["${pageId}"].params=new Object();
  [#list Parameters?keys as key]
  pages["${pageId}"].params["${key}"]="${Parameters[key]?js_string}";
  [/#list]
  [#if extra['sortable']!false]
  initSortTable('${pageId}',${extra['headIndex']!(0)},"${Parameters['orderBy']!('null')}");
  [/#if]
  </script>
  [#if extra['fixPageSize']?? && (extra['fixPageSize']=='1' || extra['fixPageSize']=='true')]
    [#assign fixPageSize=true]
  [#else]
    [#assign fixPageSize=false]
  [/#if]
  [#if b.isPage(curPage)]
    [#local pageBaseTemplate]/template/${extra['scheme']!"xhtml"}/pageBar.ftl[/#local]
    [#include pageBaseTemplate/]
  [/#if]
[/#macro]