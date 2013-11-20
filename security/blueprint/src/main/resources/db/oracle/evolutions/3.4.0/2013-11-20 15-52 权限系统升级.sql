--删除角色的动态组属性
alter table se_roles drop column dynamic;

--重新命名属性元信息
rename se_profile_fields to se_fields;

--复制se_data_types的子段到se_fields
alter table se_fields add type_name varchar2(100);
alter table se_fields add key_name varchar2(20);
alter table se_fields add properties varchar2(100);

update se_fields f set (type_name,key_name,properties)=(select d.type_name,d.key_name,d.properties from se_data_types d where d.id=f.type_id);

alter table se_fields modify type_name not null;
alter table se_fields drop column type_id;

--删除se_data_types表
alter table se_data_fields add type_name varchar2(100);
update se_data_fields a set a.type_name =(select d.type_name from se_data_types d where d.id=a.type_id);
alter table se_data_fields modify type_name not null;

alter table se_data_fields drop column type_id;
drop table se_data_types;

--合并se_roles和se_role_profiles
alter table se_role_properties modify value not null;
alter table se_role_properties add role_id number(10);

update se_role_properties a set a.role_id=(select b.role_id from se_role_profiles b where b.id=a.profile_id);
alter table se_role_properties modify role_id not null;
alter table se_role_properties drop column profile_id;
drop table se_role_profiles;

