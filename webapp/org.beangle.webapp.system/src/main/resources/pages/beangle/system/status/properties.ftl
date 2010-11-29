    <@sj.tabbedpanel id="localtabs">
      <@sj.tab id="tab1" target="tone" label="java"/>
      <@sj.tab id="tab2" target="ttwo" label="os"/>
      <@sj.tab id="tab3" target="tthree" label="user"/>
      <@sj.tab id="tab4" target="tfour" label="extra"/>
      <div id="tone">
		<table >
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			<#list javaProps?keys?sort as key>
			<tr>
				<td><@msg.text name=key/></td>
				<td>${javaProps[key]}</td>
			</tr>
			</#list>
		</table>
      </div>
      <div id="ttwo">
		<table>
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			<#list osProps?keys?sort as key>
			<tr>
				<td><@msg.text name=key/></td>
				<td>${osProps[key]}</td>
			</tr>
			</#list>
			
		</table>
	</div>
	<div id="tthree">
		<table>
			<tr class="thead">
				<td>系统属性</td>
				<td>值</td>
			</tr>
			<#list userProps?keys?sort as key>
			<tr>
				<td><@msg.text name=key/></td>
				<td>${userProps[key]}</td>
			</tr>
			</#list>
		</table>
      </div>
      <div id="tfour">
		<table>
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			<#list extraProps?keys?sort as key>
			<tr>
				<td><@msg.text name=key/></td>
				<td>${extraProps[key]}</td>
			</tr>
			</#list>
		</table>
      </div>
    </@>