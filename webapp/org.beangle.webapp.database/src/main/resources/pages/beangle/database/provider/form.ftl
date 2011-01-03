[#ftl]
[@s.form name="providerForm" action="provider!save" ]
	<input type="hidden" name="provider.id" value="${provider.id!}"/>
	<label for="provider.name">name</label>:<input name="provider.name" id="provider.name" value="${provider.name!}"/>
	<label for="provider.dialect">dialect</label>:<input name="provider.dialect" id="provider.name" value="${provider.dialect!}"/>
	[@sj.submit value="保存" targets="provider_div"/]
[/@s.form]