--对限制权限的模式引用换成对参数组的引用
insert into xtqx_xzcsz_t(id,zmc) select id,msm from xtqx_xzms_t;
insert into xtqx_xzcsz_cs_t(xzcszid,xzcsid) select xzmsid,mscsid from xtqx_xzms_cs_T;
drop table xtqx_xzms_cs_t;
update xtqx_xzms_t set xzcszid=id;
update xtqx_xzqx_yh_t set xzcszid=xzmsid;
update xtqx_xzqx_js_t set xzcszid=xzmsid;
update xtqx_xzqx_yhqx_t set xzcszid=xzmsid;
update xtqx_xzqx_jsqx_t set xzcszid=xzmsid;


--添加资源的可见范围字段，并更新部分数据
alter table xtqx_zy_T add column kjfw number(1);
update xtqx_zy_T set kjfw=2;
update xtqx_zy_t set kjfw=1 where zymc in('home');
update xtqx_zy_t set kjfw=0 where zymc in('login');
update xtqx_zy_t set zymc='group',zybt='用户组管理' where zymc='role';
update xtqx_mk_t set rkdz='group.do',mkbt='用户组管理'  where rkdz='role.do';

--将角色转移到用户组中，等删除了角色权限和模块以及数据权限后，再删除角色
insert into xtqx_yhz_t (id,yhzmc,yhzms,cjrid,yhlbid,cjsj,xgsj,sfky)
select id,jsmc,jsms,cjrid,yhlbid,cjsj,xgsj,sfky from xtqx_js_t;
updateSeqToTable('seq_xtqx_yhz_t');
drop sequence seq_xtqx_js_t;

--用户和角色的关联信息转移到用户和用户组上
insert into xtqx_yhz_yh_t (yhid,yhzid) select yhid,jsid from xtqx_yhjs_t;
drop table xtqx_yhjs_t;

--为有用户直接权限的用户创建同名的用户组
insert into xtqx_yhz_t(id,yhzmc,crjid,yhlbid,cjsj,xgsj,sfky)
select seq_xtqx_yhz_t.nextval,yh.yhmc,yh.cjrid,yh.mryhlbid,yh.cjsj,yh.xgsj,yh.zt>0  
from xtqx_yh_t yh where exists(select * from  xtqx_yhqx_t qx where yh.id=qx.yhid);

--同名用户组和用户关联
insert into xtqx_yhz_yh_t(yhid,yhzid)
select yh.id,yhz.id from xtqx_yh_t yh,xtqx_yhz_t yhz where yh.yhmc=yhz.yhzmc;

--将分级管理中的角色管理转移到用户组分级管理中
insert into xtqx_yhzgl_t(yhid,yhzid) select yhid,jsid from xtqx_fjgl_js_T;
drop table xtqx_fjgl_js_t;

--将角色模块权限转移到用户组权限上
insert into xtqx_yhzmk_T(id,yhzid,mkid) select id,jsid,mkid from xtqx_jsmk_T;
drop table  xtqx_jsmk_T;
drop sequence seq_xtqx_jsmk_t;
updateSeqToTable('seq_xtqx_yhzmk_t');

--转移用户模块权限到对应的同名组的模块权限上
insert into xtqx_yhzmk_T(id,yhzid,mkid) 
select seq_xtqx_yhzmk_T.nextval,(select yhz.id from xtqx_yhz_t yhz,xtqx_yh_T yh where yhz.yhzmc=yh.yhmc and yh.id=yhmk.yhid) as yhzid,yhmk.mkid 
from xtqx_yhmk_t yhmk;
drop table xtqx_yhmk_t;
drop sequence seq_xtqx_yhmk_t;

--将角色权限转移到用户组上
insert into xtqx_qx_t (id,yhzid,zyid)  select id,jsid,zyid from xtqx_jsqx_t;
drop sequence seq_xtqx_jsqx_t;
updateSeqToTable('seq_xtqx_qx_t');

--将角色权限上的数据限制转移到用户组权限限制
insert into  xtqx_xzqx_qx_T(id,qxid,xzcszid,sfky) select id,jsqxid,xzcszid,sfky from xtqx_xzqx_jsqx_T;
insert into xtqx_xzqx_qx_xzx_t(xzqxid,xznr,xzcsid) select xzqxid,xzcsid,xznr from xtqx_xzqx_jsqx_xzx_T;
drop table xtqx_xzqx_jsqx_xzx_T;
drop table xtqx_xzqx_jsqx_T;
drop sequence seq_xtqx_xzqx_jsqx_xzx_t;
drop sequence seq_xtqx_xzqx_jsqx_t;
updateSeqToTable('seq_xtqx_xzqx_qx_t');

--删除角色权限
drop table xtqx_jsqx_t;

--将角色上的数据限制转移到用户组上
insert into  xtqx_xzqx_yhz_T(id,yhzid,xzcszid,sfky) select id,jsid,xzcszid,sfky from xtqx_xzqx_js_T;
insert into xtqx_xzqx_yhz_xzx_t(xzqxid,xznr,xzcsid) select xzqxid,xzcsid,xznr from xtqx_xzqx_js_xzx_T;
drop table xtqx_xzqx_js_xzx_T;
drop table xtqx_xzqx_js_T;
drop sequence seq_xtqx_xzqx_js_xzx_t;
drop sequence seq_xtqx_xzqx_js_t;
updateSeqToTable('seq_xtqx_xzqx_js_t');

--删除角色
drop table xtqx_js_t;

create table tmpId( maxid number(19));

--将用户的直接权限转移到对应组的权限上
update tmpId set maxid=(select max(id) from xtqx_qx_t);
insert into xtqx_qx_t (id,yhzid,zyid)  select id+(select maxid from tmpId),(select yhz.id from xtqx_yhz_t yhz,xtqx_yh_T yh where yhz.yhzmc=yh.yhmc and yh.id=yhqx.yhid) as yhzid,zyid 
from xtqx_yhqx_t yhqx; 

insert into xtqx_xzqx_qx_t(id,sfky,qxid,xzcszid)
select seq_xtqx_xzqx_qx_t.nextval,sfky,yhqxid+(select maxid from tmpId),xzcszid from xtqx_xzqx_yhqx_t

insert into xtqx_xzqx_qx_xzx_t(xzqxid,xznr,xzcsid)
select xzqxid+(select maxid from tmpId),xznr,xzcsid from xtqx_xzqx_yhqx_xzx_t

drop table xtqx_xzqx_yhqx_xzx_t;
drop table xtqx_xzqx_yhqx_t;
drop table xtqx_yhqx_t;
drop sequence seq_xtqx_xzqx_yhqx_xzx_t;
drop sequence seq_xtqx_xzqx_yhqx_t;
drop sequence seq_xtqx_yhqx_t;

drop table tmpId;

--简化用户分级管理
drop table XTQX_FJGL_YH_T;

--增加系统会话配置记录
insert into xtqx_hhpz_t(id,zdrs,gqsj,zdhhs) values(1,2000,15,1);

--更新用户类别会话配置表
drop table xtqx_hhpz_yhlb_t;
drop sequence seq_xtqx_hhpz_yhlb_t;
rename  xtqx_yhlbpz_t to xtqx_hhpz_yhlb_t;
rename  seq_xtqx_yhlbpz_t to seq_xtqx_hhpz_yhlb_t;
alter table  xtqx_hhpz_yhlb_t add column zdhhs number(9);
alter table  xtqx_hhpz_yhlb_t add column hhpzid number(19);
update xtqx_yhlbpz_t set zdhhs=1,hhpzid=1;

--更名在线记录为会话记录
drop table xtqx_hhjl_t;
drop sequence seq_xtqx_hhjl_t;
rename  xtqx_zxjl_t to xtqx_hhjl_t;
rename  seq_xtqx_zxjl_t to seq_xtqx_hhjl_t;

--更名资源
update xtqx_zy_T set zymc='sessionActivity' where zymc='onlineRecord' ;
delete from xtqx_qx_T where zyid in(select id from xtqx_zy_T where zymc='management');
delete from xtqx_zy_t where zymc='management';

insert into sys_user_categories (id,name) select id,lbmc from xtqx_yhlb_t;
insert into sys_users(id,name,fullname,password,status,mail,admin,creator_id,created_at,updated_at,remark,default_category_id)
select id,yhmc,yhxm,mm,zt,email,0,cjrid,cjsj,xgsj,bz,mryhlbid from xtqx_yh_T;

insert into  sys_users_categories(user_id,category_id) select yhid,lbid from xtqx_yh_lb_T;

insert into sys_groups(id,name,remark,creator_id,category_id,created_at,updated_at,enabled,parent_id) select id,yhzmc,yhzms,cjrid,yhlbid,cjsj,xgsj,sfky,sjzid from xtqx_yhz_t;

insert into sys_groups_users(group_id,user_id) select yhzid,yhid from xtqx_yhz_yh_t;
insert into sys_groups_managers (group_id,user_id) select yhzid,yhid from xtqx_yhzgl_t;

insert into sys_resources(id,name,title,description,enabled,scope) select id,zymc,zybt,zyjj,sfky,kjfw from xtqx_zy_T;
insert into  sys_resources_categories (resource_id,category_id) select zyid,lbid from xtqx_zy_lb_T;

insert into sys_menu_profiles(id,name,category_id)select id,mkpzmc,yhlbid from xtqx_mkpz_T;
insert into sys_menus(id,profile_id,code,title,eng_title,description,entry,enabled ) select id,mkpzid,mkdm,mkbt,mkbt_en,mkjj,rkdz,sfky from xtqx_mk_T;
insert into sys_menus_resources(menu_id,resource_id)select mkid,zyid from xtqx_mk_zy_T;


insert into sys_params( id,name,type,description,multi_value,source,id_property,properties)  select id,csm,cslx,csms,sfdz,yylx,zsx,btsx from xtqx_xzcs_t;
insert into sys_param_groups(id,name)select id,zmc from xtqx_xzcsz_t;
insert into sys_param_groups_params(group_id,param_id) select xzcszid,xzcsid from xtqx_xzcsz_cs_T;
insert into sys_patterns(id,content,description,param_group_id)select id,msnr,msms,xzcszid from xtqx_xzms_T;
insert into sys_resources_patterns(resource_id,pattern_id)select zyid,xzmsid from xtqx_zy_xzms_t;
insert into  sys_menu_authorities (id,group_id,menu_id)select id,yhzid,mkid from xtqx_yhzmk_t;
insert into sys_authorities (id,group_id,resource_id)select id,yhzid,zyid from xtqx_qx_t;

insert into sys_session_profiles(id,capacity,inactive_interval,user_max_sessions) select id,zdrs,gqsj,zdhhs from xtqx_hhpz_T;
insert into sys_category_profiles(id,category_id,capacity,inactive_interval,user_max_sessions,session_profile_id)select id,yhlbid,zdrs,gqsj,zdhhs,hhpzid from xtqx_hhpz_yhlb_t;
insert into  sys_session_activities(id,sessionid,name,fullname,host,login_at,logout_at,last_access_at,online_time,
category_id,remark)select id,hhid,yhmc,yhxm,ip,dlsj,tcsj,zhfwsj,zxsj,yhlbid,bz from xtqx_hhjl_T;

insert into sys_user_restrictions(id,enabled,holder_id,param_group_id)select id,sfky,yhid,xzcszid from xtqx_xzqx_yh_t;
insert into sys_user_restrictions_items (restriction_id,param_id,content) select xzqxid,xzcsid,xznr from xtqx_xzqx_yh_xzx_t;

insert into sys_group_restrictions(id,enabled,holder_id,param_group_id)select id,sfky,yhzid,xzcszid from xtqx_xzqx_yhz_t;
insert into sys_group_restrictions_items (restriction_id,param_id,content) select xzqxid,xzcsid,xznr from xtqx_xzqx_yhz_xzx_t;

insert into sys_authority_restrictions(id,enabled,holder_id,param_group_id)
select id,sfky,qxid,xzcszid from xtqx_xzqx_qx_t;
insert into sys_authority_restrictions_items (restriction_id,param_id,content) 
select xzqxid,xzcsid,xznr from xtqx_xzqx_qx_xzx_t;


