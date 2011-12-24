[#ftl]
[@b.head/]
<div>
	<div id="object_div" style="float:left;width:25%;overflow:auto">
		<script>
		function changeSchema(schema){
			self.location='${b.url('browser')}?schema='+schema;
		}
		</script>
		<select id="schema" name="schema" onchange="changeSchema(this.value)">
			[#list schemas as s]
			<option value="${s}" [#if schema==s]selected="selected"[/#if]>${s}</option>
			[/#list]
		</select>
		<ul style="padding-left:15px;">
		<li>tables
		<ul style="padding-left:15px;">
		[#list tables?sort_by("name") as tb]
		<li><a href="${b.url('table')}?table.name=${tb.name}" target="_blank">${tb.name}</a></li>
		[/#list]
		</ul>
		</li>
		<li>sequences
		<ul style="padding-left:15px;">
		</ul>
		</li>
	</div>
	
	[@sj.div id="sql_div" href="${b.url('sql')}" cssStyle="float:right;width:75%;"][/@]
</div>
[@b.foot/]