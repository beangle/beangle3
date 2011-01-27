[#ftl]
[@b.toolbar id="info" name='security.restrictionPattern.info']
bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
   <tr>
	 <td class="title" >${b.text("common.description")}:</td>
	 <td  class="content">${pattern.remark!}</td>
	 <td class="title" >使用参数:</td>
	 <td class="content" colspan="3">[#if pattern.object??]${pattern.object.remark!}(${pattern.object.name!})[/#if]</td>
   </tr>
   <tr>
	 <td class="title" >限制模式:</td>
	 <td class="content" colspan="3">${pattern.content!}</td>
   </tr>
</table>
