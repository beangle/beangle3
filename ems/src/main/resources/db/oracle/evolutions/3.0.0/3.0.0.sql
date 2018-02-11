
--convert category to group
insert into se_groups(id,owner_id,enabled,name,created_at,remark,category_id,updated_at,parent_id)
select -10-a.id,1,1,a.name,sysdate,a.title,3,sysdate,null from se_categories a;

update se_groups  g set g.parent_id=(select pg.id from se_groups pg where 0-pg.id-10=g.category_id);

alter table se_groups add code varchar2(30);
update se_groups set code =id;
alter table se_groups modify  code  not null;

--drop category in group and resources 
alter table se_groups drop column category_id;
drop table se_resources_categories;

-- alter menu_profiles
alter table se_menu_profiles add group_id number(19);
update  se_menu_profiles set group_id = -10 - category_id;
alter table se_menu_profiles drop column category_id;

-- add user to default new groups
insert into se_group_members(id,group_id,user_id,manager,granter,member,created_at,updated_at)
select seq_se_group_members.nextval,-10-uc.category_id,u.id,0,0,1,sysdate,sysdate from se_users u ,se_users_categories uc where uc.user_id=u.id and 
not exists(select * from se_group_members gm where gm.user_id=u.id);

drop table se_users_categories;
drop table se_user_categories;
--alter table se_users drop constraint FK205BA2163AD67A6D;
alter table se_users drop column default_category_id;


-- alter se_category_profiles
alter table se_category_profiles add  group_id number(19);
update  se_category_profiles set group_id = -10 - category_id;
alter table se_category_profiles drop column category_id;
rename se_category_profiles  to se_group_profiles;
rename seq_se_category_profiles  to seq_se_group_profiles;

rename se_category_session_stats  to se_session_stats;
rename seq_se_category_session_stats  to seq_se_session_stats;

-- final drop se_categories
drop table se_categories;

update se_groups  set parent_id =null where id=parent_id;
update se_groups  a set a.code = (select b.code||'.'||a.code from se_groups b where b.id=a.parent_id) 
where exists(select b.code from se_groups b where b.id=a.parent_id);

drop table sys_parameters;

alter table sys_business_logs rename column operater to operator;
alter table se_resources rename column need_params  to entry;

--------------------
--rename group table and sequence
rename se_groups  to se_roles;
rename seq_se_groups  to seq_se_roles;
rename se_group_members  to se_members;
rename seq_se_group_members  to seq_se_members;
alter table se_members rename column group_id to role_id;
--rename authority table and sequence
rename se_authorities  to se_permissions;
rename seq_se_authorities  to seq_se_permissions;
alter table se_permissions rename column group_id to role_id;
alter table se_menu_profiles rename column group_id to role_id;

alter table se_group_profiles drop column session_profile_id;
--drop table and sequence
drop table se_session_profiles;
drop sequence seq_se_session_profiles; 
--rename table and sequence
rename se_group_profiles to se_session_profiles;
rename seq_se_group_profiles to seq_se_session_profiles;

alter table se_session_profiles rename column group_id   to role_id;
alter table se_sessioninfoes drop column SERVER_NAME;
alter table se_sessioninfo_logs drop column server_name;


rename  se_restrict_fields to se_data_fields;
rename  seq_se_restrict_fields to seq_se_data_fields;

create table se_role_profiles (id number(19) not null, role_id number(19), primary key (id));
create table se_role_properties (id number(19) not null, value varchar(1000), meta_id number(19) not null, profile_id number(19) not null, primary key (id));
create table se_user_profiles (id number(19) not null, user_id number(19), primary key (id));
create table se_user_properties (id number(19) not null, value varchar(1000), meta_id number(19) not null, profile_id number(19) not null, primary key (id));
alter table se_user_profiles add constraint FK60AB6EB1590D297D foreign key (user_id) references se_users;
alter table se_user_properties add constraint FKEE10BA3A285E8807 foreign key (profile_id) references se_user_profiles;
alter table se_user_properties add constraint FKEE10BA3A47BF7CD7 foreign key (meta_id) references se_data_fields;
alter table se_role_profiles add constraint FKB6327737F5DE1217 foreign key (role_id) references se_roles;
alter table se_role_properties add constraint FKFDF7B940D95AEF63 foreign key (profile_id) references se_role_profiles;
alter table se_role_properties add constraint FKFDF7B94047BF7CD7 foreign key (meta_id) references se_data_fields;
create sequence seq_se_user_profiles start with 1 increment by 1;
create sequence seq_se_user_properties start with 1 increment by 1;
create sequence seq_se_role_profiles start with 1 increment by 1;
create sequence seq_se_role_properties start with 1 increment by 1;

----
drop table se_resources_entities;

rename se_resources to se_func_resources;
alter table se_func_resources add actions varchar2(100);
rename seq_se_resources to seq_se_func_resources;

alter table se_menus_resources rename column resource_id to func_resource_id;

rename se_permissions to se_func_permissions;
rename seq_se_permissions to seq_se_func_permissions;
alter table se_func_permissions add RESTRICTIONS varchar2(100);
alter table se_func_permissions add remark varchar2(100);
alter table se_func_permissions add invalid_at date;
alter table se_func_permissions add effective_at date;
alter table se_func_permissions add actions varchar2(100);

rename se_restrict_entities to se_data_resources;
rename seq_se_restrict_entities to seq_se_data_resources;

rename se_restrict_patterns to se_data_permissions;
rename seq_se_restrict_patterns to seq_se_data_permissions;

alter table se_data_resources add title varchar2(100);
update se_data_resources set title=name;
alter table se_data_resources add enabled number(1) default 1;
alter table se_data_resources add attrs varchar2(200);
alter table se_data_resources add actions varchar2(100);

alter table se_data_fields rename column type to value_type;
alter table se_data_fields add REQUIRED number(1) default 0;

alter table se_data_permissions add role_id number(19);
alter table se_data_permissions rename column entity_id to  resource_id;
alter table se_data_permissions add func_resource_id number(19);
alter table se_data_permissions add restrictions varchar2(300);
alter table se_data_permissions add invalid_at date;
alter table se_data_permissions add effective_at date;
alter table se_data_permissions rename column content to  filters;
alter table se_data_permissions add attrs varchar2(100);
alter table se_data_permissions add actions varchar2(50);

update se_roles set code = replace(code,'-','');
