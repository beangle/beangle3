comment on table ems_code_categories is '代码分类';
comment on column ems_code_categories.name is '类别名称';
comment on column ems_code_categories.parent_id is '上级类别ID';
comment on column ems_code_categories.id is '非业务主键';

comment on table ems_code_metas is '登记系统使用的基础代码';
comment on column ems_code_metas.name is '代码名称';
comment on column ems_code_metas.title is '中文名称';
comment on column ems_code_metas.class_name is '类名';
comment on column ems_code_metas.category_id is '所在分类ID';
comment on column ems_code_metas.id is '非业务主键';

comment on table ems_code_scripts is '系统编码规则';
comment on column ems_code_scripts.code_name is '编码对象';
comment on column ems_code_scripts.attr is '编码属性';
comment on column ems_code_scripts.code_class_name is '编码对象的类名';
comment on column ems_code_scripts.script is '编码脚本';
comment on column ems_code_scripts.description is '编码简要描述';
comment on column ems_code_scripts.created_at is '创建时间';
comment on column ems_code_scripts.updated_at is '最后修改时间';
comment on column ems_code_scripts.id is '非业务主键';

comment on table ems_property_config_items is '系统配置项';
comment on column ems_property_config_items.name is '名称';
comment on column ems_property_config_items.value is '值';
comment on column ems_property_config_items.description is '描述';
comment on column ems_property_config_items.type is '类型';
comment on column ems_property_config_items.id is '非业务主键';

comment on table ems_rule_parameters is '规则参数';
comment on column ems_rule_parameters.rule_id is '业务规则ID';
comment on column ems_rule_parameters.name is '参数名称';
comment on column ems_rule_parameters.type is '参数类型';
comment on column ems_rule_parameters.title is '参数标题';
comment on column ems_rule_parameters.description is '参数描述';
comment on column ems_rule_parameters.parent_id is '上级参数ID';
comment on column ems_rule_parameters.id is '非业务主键';

comment on table ems_rule_config_params is '规则参数配置';
comment on column ems_rule_config_params.config_id is '标准-规则ID';
comment on column ems_rule_config_params.param_id is '规则参数ID';
comment on column ems_rule_config_params.value is '参数值';
comment on column ems_rule_config_params.id is '非业务主键';

comment on table ems_rule_configs is '规则配置';
comment on column ems_rule_configs.rule_id is '业务规则ID';
comment on column ems_rule_configs.enabled is '是否启用';
comment on column ems_rule_configs.created_at is '创建时间';
comment on column ems_rule_configs.updated_at is '最后修改时间';
comment on column ems_rule_configs.id is '非业务主键';

comment on table ems_rules is '规则';
comment on column ems_rules.name is '规则名称';
comment on column ems_rules.business is '适用业务';
comment on column ems_rules.description is '规则描述';
comment on column ems_rules.factory is '规则管理容器';
comment on column ems_rules.service_name is '规则服务名';
comment on column ems_rules.enabled is '是否启用';
comment on column ems_rules.created_at is '创建时间';
comment on column ems_rules.updated_at is '最后修改时间';
comment on column ems_rules.id is '非业务主键';

comment on table ems_entity_metas is '实体元信息';
comment on column ems_entity_metas.name is '实体名称';
comment on column ems_entity_metas.comments is '实体别名';
comment on column ems_entity_metas.remark is '实体备注';
comment on column ems_entity_metas.id is '非业务主键';

comment on table ems_property_metas is '属性元数据实现';
comment on column ems_property_metas.meta_id is '所属元数据ID';
comment on column ems_property_metas.name is '属性名称';
comment on column ems_property_metas.type is '类型';
comment on column ems_property_metas.comments is '属性说明';
comment on column ems_property_metas.remark is '备注';
comment on column ems_property_metas.id is '非业务主键';

comment on table se_restrictions is '资源访问限制';
comment on column se_restrictions.content is '限制内容';
comment on column se_restrictions.entity_id is '限制实体ID';
comment on column se_restrictions.resrc is '适用资源(可用正则表达式)';
comment on column se_restrictions.group_id is '适用用户组ID';
comment on column se_restrictions.user_id is '适用用户ID';
comment on column se_restrictions.enabled is '是否启用';
comment on column se_restrictions.remark is '备注说明';
comment on column se_restrictions.id is '非业务主键';

comment on table se_restrict_entities is '数据限制实体';
comment on column se_restrict_entities.name is '名称';
comment on column se_restrict_entities.type is '类型';
comment on column se_restrict_entities.remark is '备注';
comment on column se_restrict_entities.id is '非业务主键';

comment on table se_session_profiles is '用户组会话配置';
comment on column se_session_profiles.group_id is '用户组ID';
comment on column se_session_profiles.capacity is '最大在线人数';
comment on column se_session_profiles.user_max_sessions is '单用户的同时最大会话数';
comment on column se_session_profiles.inactive_interval is '不操作过期时间(以分为单位)';
comment on column se_session_profiles.id is '非业务主键';

comment on table se_authorities is '系统授权实体';
comment on column se_authorities.group_id is '用户组ID';
comment on column se_authorities.resource_id is '权限实体中的模块ID';
comment on column se_authorities.id is '非业务主键';

comment on table se_resources is '系统资源';
comment on column se_resources.name is '模块名字';
comment on column se_resources.title is '模块标题';
comment on column se_resources.remark is '简单描述';
comment on column se_resources.scope is '资源访问范围';
comment on column se_resources.enabled is '模块是否可用';
comment on column se_resources.entry is '是否为入口';
comment on column se_resources.id is '非业务主键';

comment on table se_users is '系统用户';
comment on column se_users.name is '名称';
comment on column se_users.fullname is '用户姓名';
comment on column se_users.password is '用户密文';
comment on column se_users.mail is '用户联系email';
comment on column se_users.creator_id is '创建人ID';
comment on column se_users.effect_at is '账户生效日期';
comment on column se_users.invalid_at is '账户失效日期';
comment on column se_users.password_expired_at is '密码失效日期';
comment on column se_users.enabled is '是否启用';
comment on column se_users.remark is '备注';
comment on column se_users.created_at is '创建时间';
comment on column se_users.updated_at is '最后修改时间';
comment on column se_users.id is '非业务主键';

comment on table se_groups is '用户组信息';
comment on column se_groups.name is '名称';
comment on column se_groups.parent_id is '父级组ID';
comment on column se_groups.owner_id is '创建人ID';
comment on column se_groups.remark is '备注';
comment on column se_groups.enabled is '是否启用';
comment on column se_groups.dynamic is '动态组';
comment on column se_groups.created_at is '创建时间';
comment on column se_groups.updated_at is '最后修改时间';
comment on column se_groups.code is '代码';
comment on column se_groups.id is '非业务主键';

comment on table se_group_members is '用户组成员关系';
comment on column se_group_members.group_id is '用户组ID';
comment on column se_group_members.user_id is '用户ID';
comment on column se_group_members.member is '用户是否是该组的成员';
comment on column se_group_members.granter is '用户是否能将该组授权给他人';
comment on column se_group_members.manager is '用户是否是该组的管理者';
comment on column se_group_members.created_at is '创建时间';
comment on column se_group_members.updated_at is '最后修改时间';
comment on column se_group_members.id is '非业务主键';

comment on table se_menus is '系统菜单';
comment on column se_menus.name is '菜单名称';
comment on column se_menus.title is '菜单标题';
comment on column se_menus.entry is '菜单入口';
comment on column se_menus.remark is '菜单备注';

comment on table se_menus_resources is '系统菜单-引用资源集合';
comment on column se_menus.enabled is '是否启用';
comment on column se_menus.profile_id is '菜单配置ID';
comment on column se_menus.parent_id is '父级菜单ID';
comment on column se_menus.code is '代码';
comment on column se_menus.id is '非业务主键';

comment on table se_menu_profiles is '菜单配置';
comment on column se_menu_profiles.name is '菜单配置名称';
comment on column se_menu_profiles.group_id is '用户组ID';
comment on column se_menu_profiles.enabled is '是否启用';
comment on column se_menu_profiles.id is '非业务主键';

comment on table se_property_metas is '用户属性元信息';
comment on column se_property_metas.name is '名称';
comment on column se_property_metas.key_name is '关键字名称';
comment on column se_property_metas.property_names is '其它属性名(逗号隔开)';
comment on column se_property_metas.value_type is '类型';
comment on column se_property_metas.remark is '备注';
comment on column se_property_metas.source is '数据提供描述';
comment on column se_property_metas.multiple is '能够提供多值';
comment on column se_property_metas.required is '是否必填项';
comment on column se_property_metas.id is '非业务主键';

comment on table se_group_profiles is '用户组属性配置';
comment on column se_group_profiles.group_id is '用户组ID';
comment on column se_group_profiles.id is '非业务主键';

comment on table se_group_properties is '用户组属性';
comment on column se_group_properties.value is '值';
comment on column se_group_properties.meta_id is '属性元ID';
comment on column se_group_properties.profile_id is '用户组属性配置ID';
comment on column se_group_properties.id is '非业务主键';

comment on table se_user_properties is '数据限制域';
comment on column se_user_properties.value is '值';
comment on column se_user_properties.meta_id is '属性元ID';
comment on column se_user_properties.profile_id is '用户属性配置ID';
comment on column se_user_properties.id is '非业务主键';

comment on table se_user_profiles is '用户配置';
comment on column se_user_profiles.user_id is '用户ID';
comment on column se_user_profiles.id is '非业务主键';

comment on table ems_business_logs is '业务日志实现';
comment on column ems_business_logs.operator is '操作用户';
comment on column ems_business_logs.operation is '操作内容';
comment on column ems_business_logs.resrc is '操作资源';
comment on column ems_business_logs.entry is '操作资源';
comment on column ems_business_logs.operate_at is '操作时间';
comment on column ems_business_logs.ip is '操作IP';
comment on column ems_business_logs.agent is '操作客户端代理';
comment on column ems_business_logs.detail_id is '操作明细ID';
comment on column ems_business_logs.id is '非业务主键';

comment on table ems_business_log_details is '业务日志明细';
comment on column ems_business_log_details.content is '操作参数';
comment on column ems_business_log_details.log_id is '操作日志ID';
comment on column ems_business_log_details.id is '非业务主键';

comment on table se_sessioninfoes is '活动会话信息';
comment on column se_sessioninfoes.username is '系统登录用户';
comment on column se_sessioninfoes.fullname is '用户真实姓名';
comment on column se_sessioninfoes.category is '用户分类';
comment on column se_sessioninfoes.ip is '登录IP';
comment on column se_sessioninfoes.os is 'OS';
comment on column se_sessioninfoes.agent is 'agent';
comment on column se_sessioninfoes.login_at is '登录时间';
comment on column se_sessioninfoes.expired_at is '过期时间';
comment on column se_sessioninfoes.last_access_at is '最后访问时间';
comment on column se_sessioninfoes.remark is '备注';
comment on column se_sessioninfoes.id is '非业务主键';

comment on table se_sessioninfo_logs is '登录和退出日志';
comment on column se_sessioninfo_logs.username is '系统登录用户';
comment on column se_sessioninfo_logs.fullname is '用户真实姓名';
comment on column se_sessioninfo_logs.ip is '登录IP';
comment on column se_sessioninfo_logs.os is 'OS';
comment on column se_sessioninfo_logs.agent is 'agent';
comment on column se_sessioninfo_logs.login_at is '登录时间';
comment on column se_sessioninfo_logs.online_time is '在线时间';
comment on column se_sessioninfo_logs.logout_at is '退出时间';
comment on column se_sessioninfo_logs.remark is '备注';
comment on column se_sessioninfo_logs.id is '非业务主键';

comment on table se_session_stats is '分类会话计数状态';
comment on column se_session_stats.stat_at is '统计时间戳';
comment on column se_session_stats.category is '用户分类';
comment on column se_session_stats.capacity is '最大容量';
comment on column se_session_stats.on_line is '实际在线';
comment on column se_session_stats.inactive_interval is '过期时间';
comment on column se_session_stats.user_max_sessions is '单用户最大会话数';
comment on column se_session_stats.id is '非业务主键';

comment on table se_access_logs is '访问日志';
comment on column se_access_logs.sessionid is '会话ID';
comment on column se_access_logs.resrc is '资源';
comment on column se_access_logs.begin_at is '开始时间';
comment on column se_access_logs.end_at is '结束时间';
comment on column se_access_logs.username is '用户名';
comment on column se_access_logs.id is '非业务主键';