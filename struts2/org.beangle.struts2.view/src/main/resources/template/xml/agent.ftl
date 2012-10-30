[#ftl/]
[#assign browserjs={"ie6":"json2.js","ie7":"json2.js","ie8":"json2.js"}]
[#if tag.browser?starts_with('Internet Explorer')]
  [#if tag.version?starts_with("6") ]
  <script type="text/javascript" src="${base}/static/scripts/browser/${browserjs['ie6']}"></script>
  [#elseif tag.version?starts_with("7")]
  <script type="text/javascript" src="${base}/static/scripts/browser/${browserjs['ie7']}"></script>
  [#elseif tag.version?starts_with("8")]
  <script type="text/javascript" src="${base}/static/scripts/browser/${browserjs['ie8']}"></script>
  [/#if]
[/#if]
