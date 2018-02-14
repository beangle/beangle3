--should rename sequence
alter table se_permissions rename to se_func_permissions;

alter table se_func_permissions add column actions varchar2(100);
alter table se_func_permissions add column effective_at date;
alter table se_func_permissions add column invalid_at date;
alter table se_func_permissions add column remark varchar2(100);
alter table se_func_permissions add column restrictions varchar2(200);

--rename function resource and sequence 
alter table se_resources rename to se_func_resources;
alter table se_menus_resources alter column resource_id rename to func_resource_id;
alter table se_func_resources add column actions varchar2(50);


create table se_data_resources (id bigint not null, actions varchar(100), enabled boolean not null, name varchar(100) not null unique, remark varchar(100), title varchar(100) not null, primary key (id));

insert into se_data_resources (id,name,title ,remark ,enabled )
select id,case when type is null then name else type end,name,remark,true from se_restrict_entities ;

create table se_data_permissions (id bigint not null, actions varchar(100), attrs varchar(300), effective_at timestamp, filters varchar(500), invalid_at timestamp, remark varchar(100), restrictions varchar(500), func_resource_id bigint, resource_id bigint not null, role_id bigint, primary key (id));

insert into se_data_permissions (id,resource_id , filters  , effective_at,remark )
select id,entity_id,content,sysdate,remark from se_restrict_patterns;

--should rename sequence
alter table se_property_metas  rename to se_data_fields;

