alter table se_groups add column code varchar2(32);
alter table se_groups add column dynamic boolean;
update se_groups set dynamic =false;

alter table se_group_profiles rename to se_group_session_profiles;

alter table se_menu_profiles add column group_id bigint;
update se_groups set code='1';
alter table se_groups drop column category_id;

select max(id)from se_groups 
select * from se
insert into se_groups (id,code,name,enabled,owner_id,parent_id,created_at,updated_at,dynamic,remark)
select 67,code,name||'67',enabled,owner_id,parent_id,created_at,updated_at,dynamic,remark from se_groups where id=1;

insert into se_groups (id,code,name,enabled,owner_id,parent_id,created_at,updated_at,dynamic,remark)
select 68,code,name||'68',enabled,owner_id,parent_id,created_at,updated_at,dynamic,remark from se_groups where id=2;

update se_menu_profiles set group_id=67 where group_id=1;
update se_menu_profiles set group_id=68 where group_id=2;

update se_authorities set group_id=67 where group_id=1;
update se_authorities set group_id=68 where group_id=2;

update se_group_members set  group_id=67 where group_id=1;
update se_group_members set group_id=68 where group_id=2;

update se_groups set name='匿名用户组',code='1', parent_id=null,remark='公开访问时需要' where id=1;
update se_groups set name='默认用户组',code='2',parent_id=null,remark='任何系统内的用户自动归在该组' where id=2;

alter table se_resources alter column scope set null;
alter table se_resources alter column NEED_PARAMS set null;

select next value for seq_SE_AUTHORITIES from SE_AUTHORITIES
select max(id) from SE_AUTHORITIES