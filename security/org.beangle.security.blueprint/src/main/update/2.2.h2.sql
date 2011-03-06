alter table sys_admin_users  rename to sys_adminusers;
alter table sys_user_categories rename to sys_categories;
alter table SYS_RESOURCES_ENTITIES alter column  entity_id rename to restrict_entity_id