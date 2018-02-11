--sequence 
alter table se_data_fields rename to se_profile_fields;

create table se_data_types (id bigint primary key,name varchar2(50),key_name varchar2(20),properties varchar2(100),type_name varchar2(100));

insert into se_data_types(id,name,key_name,properties,type_name)
select id,name,key_name,property_names,value_type from se_profile_fields;

create sequence seq_se_data_types;

alter table se_profile_fields add column type_id bigint;
update se_profile_fields a set a.type_id=a.id;
alter table se_profile_fields drop column value_type;
alter table se_profile_fields drop column key_name;
alter table se_profile_fields drop column property_names;

alter table se_profile_fields alter column remark rename to title;

create table se_data_fields (id bigint primary key,name varchar2(50),title varchar2(50),type_id bigint,resource_id bigint);
create sequence seq_se_data_fields;

alter table  se_user_properties alter column meta_id rename to field_id;
alter table  se_role_properties alter column meta_id rename to field_id;


alter table se_session_stats drop column server_name;

update se_func_resources  set name='/security/nav/menu' where name='/security/menu' ;
update se_menus set entry='/security/nav/menu' where entry='/security/menu';

update se_func_resources set name='/security/nav/profile' where  name='/security/menu-profile';
update se_menus set entry='/security/nav/profile' where entry='/security/menu-profile' ;

update se_func_resources set  name='/security/nav/index' where name='/security/menu-nav' ;
update se_menus set  entry='/security/nav/index' where entry='/security/menu-nav' ;

update se_func_resources set name='/security/session/monitor' where name='/security/monitor' ;
update se_menus set  entry='/security/session/monitor' where entry='/security/monitor';

update se_func_resources set  name='/security/session/log' where name='/security/sessioninfo-log' ;
update se_menus set  entry='/security/session/log' where  entry='/security/sessioninfo-log';


update se_func_resources set name='/security/permission' where  name='/security/authority' ;
update se_menus set entry='/security/permission' where  entry='/security/authority';

update se_func_resources set name='/security/role' where  name='/security/group' ;
update se_menus set entry='/security/role' where  entry='/security/group';

update se_func_resources set name='/security/data/profile' where  name='/security/restriction' ;
update se_menus set entry='/security/data/profile' where  entry='/security/restriction';


alter table se_session_stats drop column user_max_sessions ;
alter table se_session_stats drop column INACTIVE_INTERVAL;