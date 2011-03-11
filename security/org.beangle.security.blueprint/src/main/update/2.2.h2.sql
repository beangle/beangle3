alter table sys_admin_users  rename to sys_adminusers;
alter table sys_user_categories rename to sys_categories;
alter table SYS_RESOURCES_ENTITIES alter column  entity_id rename to restrict_entity_id;
alter table SYS_RESTRICT_ENTITIES_FIELDS  alter column field_id rename to restrict_field_id;
alter table SYS_RESTRICT_ENTITIES_FIELDS  alter column entity_id rename to restrict_entity_id;
alter table SYS_RESTRICT_FIELDS  add column key_name varchar(20);
alter table SYS_RESTRICT_FIELDS  add column property_names varchar(100);

alter table SYS_AUTHORITY_RESTRICTIONS rename SYS_AUTH_RESTRICTIONS;

alter table SYS_AUTH_RESTRICTIONS_ITEMS  alter column  param_id rename  to field_id;
alter table SYS_user_RESTRICTIONS_ITEMS  alter column  param_id rename  to field_id;
alter table SYS_group_RESTRICTIONS_ITEMS alter column param_id rename  to field_id;