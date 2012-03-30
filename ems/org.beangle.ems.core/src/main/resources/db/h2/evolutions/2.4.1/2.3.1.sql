alter table se_menus add indexno integer;
update se_menus a set indexno=substring(code,length(code)-1);
