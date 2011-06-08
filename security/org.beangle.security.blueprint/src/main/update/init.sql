insert into se_categories(id,name) values(1,'default');
insert into se_users(id,created_at,updated_at,fullname,name,mail,password,status,creator_id,default_category_id)
values(1,current date,current date,'admin','admin','admin@beangle.org','c4ca4238a0b923820dcc509a6f75849b',1,null,1);
insert into se_users_categories(user_id,category_id) values(1,1)
insert into se_adminusers(id,user_id,created_at,updated_at) values(1,1,current date,current date);