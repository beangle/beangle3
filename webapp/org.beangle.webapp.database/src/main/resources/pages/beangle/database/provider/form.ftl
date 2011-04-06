[#ftl]
[@b.form name="providerForm" action="save" ]
	<input type="hidden" name="provider.id" value="${provider.id!}"/>
	<label for="provider.name">name</label>:<input name="provider.name" id="provider.name" value="${provider.name!}"/>
	<label for="provider.dialect">dialect</label>:<input name="provider.dialect" id="provider.dialect" value="${provider.dialect!}"/>
	[@b.submit value="保存"/]
[/@]