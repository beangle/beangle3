--convert category to group
insert into se_groups(id,owner_id,enabled,name,created_at,remark,category_id,updated_at,parent_id)
select -10-a.id,1,1,a.name,current time,a.title,3,current time,null from se_categories a;

update se_groups  g set g.parent_id=(select pg.id from se_groups pg where 0-pg.id-10=g.category_id);
alter table se_groups drop column category_id;

alter table se_groups add column code varchar2(30);
update se_groups set code =id;
alter table se_groups alter column code  set not null;

drop table se_resources_categories;

-- alter menu_profiles
alter table se_menu_profiles add column group_id bigint;
update  se_menu_profiles set group_id = -10 - category_id;
--alter table se_menu_profiles drop constraint FK97C88D8937C1F66;
alter table se_menu_profiles drop column category_id;

-- add user to default new groups
insert into se_group_members(id,group_id,user_id,manager,granter,member,created_at,updated_at)
select next value for seq_se_group_members,-10-uc.category_id,u.id,0,0,1,current time,current time from se_users u ,se_users_categories uc where uc.user_id=u.id and 
not exists(select * from se_group_members gm where gm.user_id=u.id);

drop table se_users_categories;
drop table se_user_categories;
--alter table se_users drop constraint FK205BA2163AD67A6D;
alter table se_users drop column default_category_id;


-- alter se_category_profiles
alter table se_category_profiles add column group_id bigint;
update  se_category_profiles set group_id = -10 - category_id;
--alter table se_category_profiles drop constraint FKD2B84D1971A29F6B;
alter table se_category_profiles drop column category_id;
alter table se_category_profiles rename to se_group_profiles;

alter table se_category_session_stats rename to se_session_stats;
drop sequence seq_se_category_session_stats ;
create sequence seq_se_session_stats;

-- final drop se_categories
drop table se_categories;

update se_groups  set parent_id =null where id=parent_id;
update se_groups  a set a.code = (select b.code from se_groups b where b.id=a.parent_id)||'.'||a.code where exists(select b.code from se_groups b where b.id=a.parent_id)

drop table sys_parameters;

alter table sys_business_logs alter column resource rename to resrc;
alter table sys_business_logs alter column operater rename to operator;

alter table se_resources alter column need_params rename to entry;
