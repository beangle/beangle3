alter table se_menus drop column indexno;
update se_menus set  code =substring(code,1,2) ||'.'||substring(code,3,2)     where length(code)=4 and code not like '%.%';
update se_menus set  code =substring(code,1,2) ||'.'||substring(code,3,2) ||'.'||substring(code,5,2)   where length(code)=6 and code not like '%.%';
update se_menus set  code =substring(code,1,2) ||'.'||substring(code,3,2) ||'.'||substring(code,5,2) ||'.'||substring(code,7,2)   where length(code)=8 and code not like '%.%';

alter table se_users add column effective_at datetime;
alter table se_users add column invalid_at datetime;
alter table se_users add column password_expired_at datetime;
alter table se_users add column enabled bool;

update se_users set enabled=status>0;
alter table se_users drop column status;

update se_users set effective_at = created_at;
update se_users set effective_at =current timestamp where effective_at is null; 
alter table se_users alter column effective_at set not null;
alter table se_users alter column enabled set not null;
