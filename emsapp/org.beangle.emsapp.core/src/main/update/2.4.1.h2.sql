--remove table
drop table se_category_session_statuses  if exists;
drop table SE_ADMINUSERS  if exists;
drop table SE_ADMIN_USERS if exists;

alter table se_session_profiles add column name varchar(50) ;
update se_session_profiles set name='default_server';
alter table se_session_profiles alter column name set not null;

alter table SE_SESSION_ACTIVITIES rename to se_sessioninfo_logs;
alter table se_sessioninfo_logs alter column name rename to username;
alter table se_sessioninfo_logs drop column last_access_at ;
alter table se_sessioninfo_logs add column server_name varchar2(100);
alter table se_sessioninfo_logs alter column host rename to ip;
alter table se_sessioninfo_logs alter column login_at timestamp;
alter table se_sessioninfo_logs alter column logout_at timestamp;
--alter table se_sessioninfo_logs drop constraint primary key;
alter table se_sessioninfo_logs drop column id;
alter table se_sessioninfo_logs alter column sessionid rename to id;
alter table se_sessioninfo_logs add primary key (id);
update se_sessioninfo_logs set server_name='default_server';

--update se_sessioninfo_logs logs set logs.category=(select category.title from se_categories category where category.id=logs.category_id);
alter table se_sessioninfo_logs drop column category_id ;

