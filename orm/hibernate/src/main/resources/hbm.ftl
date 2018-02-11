[#ftl]
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
 "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
[#list classes as cls]
<class name="${cls.className}" entity-name="${cls.entityName}" table="${cls.table.name}" schema="${cls.table.schema!}">
    [#if cls.cacheConcurrencyStrategy??]
    <cache usage="${cls.cacheConcurrencyStrategy}" region="${cls.cacheRegionName}"/>
    [/#if]
    <id name="id" unsaved-value="null">
      <generator class="org.beangle.orm.hibernate.id.TableSeqGenerator"/>
    </id>
    [#list cls.propertyIterator as pi]
    [#assign piv = pi.value/]
    [#--TO--ONE--]
    [#if generator.isToOne(piv)]
    <[#if piv.ignoreNotFound??]many-to-one[#else]one-to-one[/#if] name="${pi.name}" class="${piv.referencedEntityName}"[#rt/]
    [#list piv.columnIterator as ci] column="${ci.name}"[#if ci.unique] unique="true"[/#if] [#if !ci.nullable] not-null="true"[/#if][/#list][#t/]
    />[#lt/]
    [/#if]
[#t/]
  [#if generator.isSet(piv)]
  <set name="${pi.name}"[#if piv.inverse] inverse="true"[/#if] table="${piv.collectionTable.name}" [#if pi.cascade??]cascade="${pi.cascade}"[/#if]>
    <key [#list piv.key.columnIterator as pki]column="${pki.name}"[#if pki.nullable] not-null="true"[/#if][/#list]/>
  [#if generator.isOneToMany(piv.element)]
    <one-to-many class="${piv.element.referencedEntityName}"/>
  [/#if]
  [#if generator.isManyToMany(piv.element)]
    <many-to-many class="${piv.element.referencedEntityName}" [#list piv.element.columnIterator as ci] column="${ci.name}"[/#list]/>
  [/#if]
  </set>
  [/#if]
[#t/]
    [#if piv.columnSpan==1 && !generator.isToOne(piv)]
    <property name="${pi.name}" [#list piv.columnIterator as ci]column="${ci.name}"[#rt/]
    [#if ci.length!=255] length="${ci.length?c}"[/#if][#t/]
    [#if ci.unique] unique="true"[/#if][#if !ci.nullable] not-null="true"[/#if] [/#list][#t/]
    [#if !generator.isCustomType(piv.type)] type="${piv.typeName}"[/#if][#t/]
    [#if pi.metaAttributes??][#list pi.metaAttributes?keys as mak]${mak}="${pi.metaAttributes[mak]}" [/#list][/#if][#t/]
    >
  [#if generator.isCustomType(piv.type)]
  [#if generator.isEnumType(piv.type)]
    <type name="org.hibernate.type.EnumType">
      <param name="enumClass">${piv.type.returnedClass.name}</param>
    </type>
  [#else]
    <type name="${piv.type.class.name}">
    </type>
    [/#if]
    [/#if]
    </property>
    [#lt/]
    [/#if]
    [/#list]
</class>

[/#list]
</hibernate-mapping>
