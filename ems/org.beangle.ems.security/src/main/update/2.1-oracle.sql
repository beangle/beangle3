	rename sys_groups_users  to sys_group_members;
	alter table sys_group_members add  member number(1);
	alter table sys_group_members add manager number(1) ;
	alter table sys_group_members add granter number(1);
	alter table sys_group_members add id number(19);
	alter table sys_group_members add updated_at date;
	alter table sys_group_members add created_at date;
	update  sys_group_members set member=1,manager=0,granter=0;
	update sys_group_members m set m.manager=1,m.granter=1 where exists(select  * from sys_groups_managers gm where gm.group_id=m.group_id and gm.user_id=m.user_id);
	update sys_group_members set id=rowum;
	alter table sys_group_members drop constraint SYS_C0021673;
	alter table sys_group_members add constraint SYS_C0021673 primary key(id);
	alter table sys_groups rename column creator_id to  owner_id;
	drop table sys_groups_managers;
	
	alter table sys_user_restrictions add pattern_id number(19);
	update sys_user_restrictions set pattern_id=param_group_id;
	alter table sys_user_restrictions drop column param_group_id;
	
	alter table sys_group_restrictions add pattern_id number(19);
	update sys_group_restrictions set pattern_id=param_group_id;
	alter table sys_group_restrictions drop column param_group_id;
	
	alter table sys_authority_restrictions add pattern_id number(19);
	update sys_authority_restrictions set pattern_id=param_group_id;
	alter table sys_authority_restrictions drop column param_group_id;
	
	alter table sys_menu_profiles add enabled number(1);
	update sys_menu_profiles set enabled=1;
	
	create table sys_resources_entities(resource_id number(19),entity_id number(19));
	alter table  sys_resources add  remark varchar2(200);
	update sys_resources set remark=description;
	alter table sys_resources drop column description;
	
	alter table sys_menus add  remark varchar2(200);
	alter table sys_menus add parent_id number(19);
	update sys_menus set remark=description;
	alter table sys_menus drop column description;
	update sys_menus m set m.parent_id=(select  n.id from sys_menus n where n.code= substr(m.code,1,length(m.code)-2));
	
	alter table sys_patterns rename to sys_restrict_patterns;
	alter table  sys_restrict_patterns add  remark varchar2(200);
	update sys_restrict_patterns set remark=description;
	alter table sys_restrict_patterns drop column description;
	
	alter table sys_restrict_patterns add entity_id number(19);
	update sys_restrict_patterns set entity_id=param_group_id;
	alter table sys_restrict_patterns drop column param_group_id;
	
	alter table sys_param_groups rename to sys_restrict_entities;
	alter table sys_restrict_entities add type varchar2(200);
	alter table sys_restrict_entities add  remark varchar2(200);
	
	insert into sys_resources_entities select p.resource_id,rp.entity_id from sys_resources_patterns p,sys_restrict_patterns rp where p.pattern_id=rp.id;
	drop table sys_resources_patterns;
	
	alter table sys_params rename to sys_restrict_fields;
	alter table sys_restrict_fields rename multi_value multiple;
	alter table sys_restrict_fields drop column id_property;
	alter table sys_restrict_fields drop column properties;
	alter table sys_restrict_fields add  remark varchar2(200);
	update sys_restrict_fields set remark=description;
	alter table sys_restrict_fields drop column description;
	
	rename sys_param_groups_params to sys_restrict_entities_fields;
	alter table sys_restrict_entities_fields add entity_id number(19) ;
	alter table sys_restrict_entities_fields add field_id number(19) ;
	update sys_restrict_entities_fields set entity_id=group_id,field_id=param_id;
	alter table sys_restrict_entities_fields drop column group_id cascade constraints;
	alter table sys_restrict_entities_fields drop column param_id;
	
	update sys_resources set name='/security/restrict-meta' where name='/security/pattern';
	delete from sys_menus_resources where resource_id in(select id from sys_resources where  name='security/param');
	delete from sys_resources_categories where resource_id in(select id from sys_resources where  name='security/param');
	delete from sys_resources_entities where resource_id in(select id from sys_resources where  name='security/param');
	delete from sys_authorities where resource_id in(select id from sys_resources where  name='security/param');
	delete from sys_resources where name='security/param';

	alter table sys_session_activities add os varchar2(40);
	alter table sys_session_activities add agent varchar2(40);