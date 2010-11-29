alter table sys_groups_users rename to sys_group_members;
alter table sys_group_members add column member bit ;
alter table sys_group_members add column manager bit ;
alter table sys_group_members add column granter bit; 
alter table sys_group_members add column id bigint; 
alter table sys_group_members add column updated_at timestamp;
alter table sys_group_members add column created_at timestamp;
update  sys_group_members set member=1,manager=0,granter=0;
update sys_group_members m set m.manager=1,m.granter=1 where exists(select  * from sys_groups_managers gm;
drop table sys_groups_managers;

where gm.group_id=m.group_id and gm.user_id=m.user_id)
update sys_group_members set member=1;
update sys_group_members set id=user_id+group_id;
alter table sys_groups add column owner_id bigint;
update sys_groups set owner_id=creator_id;

alter table sys_user_restrictions add column pattern_id bigint;
update sys_user_restrictions set pattern_id=param_group_id;
alter table sys_user_restrictions drop column param_group_id;

alter table sys_group_restrictions add column pattern_id bigint;
update sys_group_restrictions set pattern_id=param_group_id;
alter table sys_group_restrictions drop column param_group_id;

alter table sys_authority_restrictions add column pattern_id bigint;
update sys_authority_restrictions set pattern_id=param_group_id;
alter table sys_authority_restrictions drop column param_group_id;

alter table sys_menu_profiles add column enabled boolean;
update sys_menu_profiles set enabled=true;

create table sys_resources_objects(resource_id bigint,object_id bigint)
alter table  sys_resources add column  remark varchar(200)
update sys_resources set remark=description
alter table sys_resources drop column description

alter table sys_menus add column  remark varchar(200);
alter table sys_menus add parent_id bigint
update sys_menus set remark=description;
alter table sys_menus drop column description;
update sys_menus m set m.parent_id=(select  n.id from sys_menus n where n.code= substr(m.code,1,length(m.code)-2))

alter table sys_patterns rename to sys_restrict_patterns;
alter table  sys_restrict_patterns add column  remark varchar(200);
update sys_restrict_patterns set remark=description;
alter table sys_restrict_patterns drop column description;

alter table sys_restrict_patterns add column object_id bigint
update sys_restrict_patterns set object_id=param_group_id;
alter table sys_restrict_patterns drop column param_group_id


alter table sys_param_groups rename to sys_restrict_objects
alter table sys_restrict_objects add column type varchar(200);
alter table  sys_restrict_objects add column  remark varchar(200);

create table sys_resources_objects(resource_id bigint,object_id bigint)
insert into sys_resources_objects select p.resource_id,rp.object_id from sys_resources_patterns p,sys_restrict_patterns rp where p.pattern_id=rp.id;
drop table sys_resources_patterns


alter table sys_params rename to sys_restrict_fields
alter table sys_restrict_fields drop multi_value
alter table sys_restrict_fields drop id_property
alter table sys_restrict_fields drop properties
alter table sys_restrict_fields add column  remark varchar(200);
update sys_restrict_fields set remark=description;
alter table sys_restrict_fields drop column description;

alter table sys_param_groups_params rename to sys_restrict_objects_fields
alter table sys_restrict_objects_fields add column object_id bigint 
alter table sys_restrict_objects_fields add column field_id bigint 
update sys_restrict_objects_fields set object_id=group_id,field_id=param_id
alter table sys_restrict_objects_fields drop column group_id
alter table sys_restrict_objects_fields drop column param_id


update sys_resources set name='/security/restrict-meta' where name='/security/pattern';
delete from sys_menus_resources where resource_id in(select id from sys_resources where  name='security/param');
delete from sys_resources_categories where resource_id in(select id from sys_resources where  name='security/param');
delete from sys_resources_objects where resource_id in(select id from sys_resources where  name='security/param');
delete from sys_authorities where resource_id in(select id from sys_resources where  name='security/param');
delete from sys_resources where name='security/param';
