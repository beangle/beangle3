--rename group table and sequence
alter table se_groups rename to se_roles;
alter table se_group_members rename to se_members;
alter table se_members alter column group_id rename to role_id;
--rename authority table and sequence
alter table se_authorities rename to se_permissions;
alter table se_permissions alter column group_id rename to role_id;
alter table se_menu_profiles alter column group_id rename to role_id;


alter table se_group_session_profiles drop constraint FKB63277379078CD9D;
alter table se_group_session_profiles drop column session_profile_id;
--drop table and sequence
drop table se_session_profiles;
--rename table and sequence
alter table se_group_session_profiles rename to se_session_profiles;
alter table se_session_profiles alter column group_id  rename  to role_id;
alter table se_sessioninfoes drop column SERVER_NAME;
alter table se_sessioninfo_logs drop column server_name;

create table se_role_profiles (id bigint not null, role_id bigint, primary key (id));
create table se_role_properties (id bigint not null, value varchar(1000), meta_id bigint not null, profile_id bigint not null, primary key (id));
create table se_user_profiles (id bigint not null, user_id bigint, primary key (id));
create table se_user_properties (id bigint not null, value varchar(1000), meta_id bigint not null, profile_id bigint not null, primary key (id));
alter table se_user_profiles add constraint FK60AB6EB1590D297D foreign key (user_id) references se_users;
alter table se_user_properties add constraint FKEE10BA3A285E8807 foreign key (profile_id) references se_user_profiles;
alter table se_user_properties add constraint FKEE10BA3A47BF7CD7 foreign key (meta_id) references se_property_metas;
alter table se_role_profiles add constraint FKB6327737F5DE1217 foreign key (role_id) references se_groups;
alter table se_role_properties add constraint FKFDF7B940D95AEF63 foreign key (profile_id) references se_group_profiles;
alter table se_role_properties add constraint FKFDF7B94047BF7CD7 foreign key (meta_id) references se_property_metas;
create sequence seq_se_user_profiles start with 1 increment by 1;
create sequence seq_se_user_properties start with 1 increment by 1;
create sequence seq_se_role_profiles start with 1 increment by 1;
create sequence seq_se_role_properties start with 1 increment by 1;
create sequence seq_se_property_metas start with 1 increment by 1;

drop table se_resources_entities;
drop table se_resources_categories;
