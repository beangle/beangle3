[#ftl]
[@b.head/]
[@b.css href="tab.css"/]
<script  type="text/javascript" src="${base}/static/scripts/TabPane.js"></script>
[@b.toolbar title="在 ${restriction.pattern.entity.name} 上按照 ${restriction.pattern.remark} 进行数据授权"]
	function save(){if(confirm("确定设置?")){bg.form.submit(document.restrictionForm);}}
	function cancelEdit(){bg.form.submit(document.restrictionForm,'${b.url("!info")}')}
	bar.addItem("${b.text("action.save")}",save,'${b.theme.iconurl("actions/save.png")}');
	bar.addItem("取消",cancelEdit,'${b.theme.iconurl("actions/close.png")}');
[/@]
[@b.form name="restrictionForm" action="!save"]
	<input type="hidden" name="restriction.id" value="${restriction.id!}"/>
	<input type="hidden" name="restrictionType" value="${Parameters['restrictionType']}"/>
	<input type="hidden" name="restriction.pattern.id" value="${restriction.pattern.id}"/>
	<input type="hidden" name="restriction.holder.id" value="${restriction.holder.id}"/>
	<input type="hidden" name="params" value="&restrictionType=${Parameters['restrictionType']}&restriction.holder.id=${Parameters['restriction.holder.id']}"/>
	<div>是否启用:
		<input type="radio" [#if (restriction.enabled)!(true)]checked="checked"[/#if] value="1" name="restriction.enabled"  id="restriction.enabled1"/><label for="restriction.enabled1">启用</label>
		<input type="radio" [#if !(restriction.enabled)!(true)]checked="checked"[/#if] value="0" name="restriction.enabled" id="restriction.enabled0"/><label for="restriction.enabled0">禁用</label>
	</div>
	[@b.tabs]
		[#list restriction.pattern.entity.fields?sort_by("remark") as field]
		[@b.tab label="${field.name}(${field.remark})"]
		[#if ignoreFields?seq_contains(field)]
		<div>
			<input name="ignoreField${field.id}" type="radio" value="1" [#if holderIgnoreFields?seq_contains(field)]checked="checked"[/#if] id="ignoreField${field.id}_1"><label for="ignoreField${field.id}_1">使用通配符*</label>
			<input name="ignoreField${field.id}" type="radio" value="0" [#if !holderIgnoreFields?seq_contains(field)]checked="checked"[/#if] id="ignoreField${field.id}_2"><label for="ignoreField${field.id}_2">选择或填写具体值</label>
		</div>
		[/#if]
		[#if field.multiple && field.keyName?exists]
			[@b.grid items=mngFields[field.name] var="value"]
				[@b.row]
					[@b.boxcol width="10%" property=field.keyName boxname=field.name checked=(aoFields[field.name]?seq_contains(value))!false /]
					[#if field.propertyNames??]
					[#list field.propertyNames?split(",") as pName][@b.col title=pName]${value[pName]!}[/@][/#list]
					[#else]
					[@b.col title="可选值"]${value}[/@]
					[/#if]
				[/@]
			[/@]
		[#else]
		<table class="grid" width="100%">
			<tr><td colspan="2"><input type="text" name="${field.name}" value="${aoFields[field.name]!}"/>[#if field.multiple]多个值请用,格开[/#if]</td></tr>
		</table>
		[/#if]
		[/@]
		[/#list]
	[/@]
[/@]
[@b.foot/]