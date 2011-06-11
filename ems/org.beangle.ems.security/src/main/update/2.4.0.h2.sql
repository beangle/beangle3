alter table se_menus drop column indexno;
update se_menus set code=code   where length(code)=2 and code not like '%.%';
update  se_menus set  code =substring(code,1,2) ||'.'||cast(substring(code,3,2)     where length(code)=4 and code not like '%.%';
update  se_menus set  code =substring(code,1,2) ||'.'||cast(substring(code,3,2) ||'.'||cast(substring(code,5,2)   where length(code)=6 and code not like '%.%';
update  se_menus set  code =substring(code,1,2) ||'.'||cast(substring(code,3,2) ||'.'||cast(substring(code,5,2) ||'.'||cast(substring(code,7,2)   where length(code)=8 and code not like '%.%';