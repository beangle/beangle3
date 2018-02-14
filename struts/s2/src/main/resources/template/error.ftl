[#ftl]
[@b.head /]

[#assign message][@b.messages/][/#assign]
[#assign exceptionStack]${(stack.pop().exceptionStack)?if_exists}[/#assign]

<div style="width:100%;text-align:center;">
  [#if message?length > 0]
  ${message}
  [#else]
    <div class="ui-widget">
      <div class="actionError">
        <div style="padding: 0.3em 0.7em;" class="ui-state-error ui-corner-all">
          <span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span>
          <span>服务器内部错误</span>
        </div>
      </div>
    </div>
  [/#if]
  [<a href="javascript:history.back();">${b.text("attr.backPage")}</a>]
[#if exceptionStack?length > 0]
  [<a id="stackTraceA" href="javascript:displayStactTrace()">显示日志</a>]
[/#if]
  <input type="hidden" name="stackTrace" value="${stackTrace?if_exists}">
  <input type="hidden" name="recorder" value="${Session['security.userId']?if_exists}">
</div>
<div id="stackTraceDiv" style="border:1px solid #f42e2e;display:none;padding:0;text-align:left;width:100%;font-size:16px;background:#fefe7e;color:#f42e2e">
<pre>${exceptionStack?replace("com.ekingstar.eams","<span style='color:blue;font-weight:bold'>com.ekingstar.eams</span>")?replace("com/ekingstar/eams","<span style='color:blue;font-weight:bold'>com/ekingstar/eams</span>")}</pre>
</div>
<script>
function displayStactTrace(){
    var stackTraceDiv = document.getElementById('stackTraceDiv');
  var ele = document.getElementById('stackTraceA');
    if(ele.innerHTML == "隐藏日志"){
      ele.innerHTML = "显示日志";
        stackTraceDiv.style.display='none';
    }else{
        ele.innerHTML = "隐藏日志";
    stackTraceDiv.style.display='block';
    }
}
</script>
[@b.foot /]
