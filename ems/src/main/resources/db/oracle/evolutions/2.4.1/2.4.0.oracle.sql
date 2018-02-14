alter table se_menus drop column indexno;
update se_menus set  code =substr(code,1,2) ||'.'||substr(code,3,2)     where length(code)=4 and code not like '%.%';
update se_menus set  code =substr(code,1,2) ||'.'||substr(code,3,2) ||'.'||substr(code,5,2)   where length(code)=6 and code not like '%.%';
update se_menus set  code =substr(code,1,2) ||'.'||substr(code,3,2) ||'.'||substr(code,5,2) ||'.'||substr(code,7,2)   where length(code)=8 and code not like '%.%';

alter table se_users add  effective_at date;
alter table se_users add  invalid_at date;
alter table se_users add  password_expired_at date;
alter table se_users add  enabled number(1);

update se_users set enabled=1 where status>0;
update se_users set enabled=0 where status<=0;
alter table se_users drop column status;

update se_users set effective_at = created_at;
update se_users set effective_at =sysdate where effective_at is null; 
alter table se_users modify effective_at  not null;
alter table se_users modify enabled  not null;

alter table se_menus rename column eng_title to name;
alter table se_categories add  title varchar2(100);
update se_categories set title=name;
alter table se_categories modify title  not null;

alter table se_resources add need_params number(1);
update se_resources set need_params=0;
alter table se_resources modify need_params not null;

create table se_category_session_stats
(
   id number(19) primary key,
   category varchar2(255) not null,
   server_name varchar2(255) not null,
   stat_at date not null,
   capacity number(9) not null,
   on_line number(9) not null,
   inactive_interval number(9) not null,
   user_max_sessions number(9) not null
   
);

create sequence seq_se_category_session_stats;