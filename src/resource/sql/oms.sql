#############################################################
#                  MODEL       : oms1.0                     #
#                  AUTHOR      : xiejun                     #
#############################################################

delimiter ;

set names utf8;
SET FOREIGN_KEY_CHECKS = 0; 

###########  创建数据库 ##############
drop database if exists oms;
create database oms character set utf8;

############### 建表 ################
use oms;

#department 部门表
drop table if exists department;
create table `department`
(
	#部门id
	id int auto_increment,
	#部门名称
	name varchar(255) not null default "",
	#主键
	primary key (`id`)
)ENGINE=InnoDB default charset=utf8;
insert into department(name) values ("系统运营部");
insert into department(name) values ("云端开发部");
insert into department(name) values ("终端开发部");
insert into department(name) values ("系统测试部");
insert into department(name) values ("产品设计部");

#系统管理员相关表
drop table if exists manager;
drop table if exists manager_role;
drop table if exists permission;

#管理员角色表
create table `manager_role`
(
	#角色ID
	idx int auto_increment,
	#角色名
	name char(32) not null,
	#管理组状态 0 可使用 1 不可用 2 已删除
	status tinyint not null default 0,
	#权限 (权限具体与各个菜单项的ID对应，拥有该菜单ID串则具有该操作权限，ID间用"/"竖杠隔开)
	permission text not null,
	#主键
	primary key (`idx`)
)ENGINE=InnoDB default charset=utf8;
insert into manager_role(name, permission) values('系统管理员','1000/1100/1200/1300/1400/1500');
insert into manager_role(name, permission) values('运营总监','2000/2100/2200/2300/3000/3100/3200/3300/3400/4000/4100/4200/4300/4400/5000/5100/5200/5300/6000/6100/6200/6300/6400/6500');
insert into manager_role(name, permission) values('渠道管理','3000/3100/3200/3300/3400/3500');
insert into manager_role(name, permission) values('运营管理','5000/5100/5200/5300');
insert into manager_role(name, permission) values('资源管理','6000/6100/6200/6300/6400/6500');
insert into manager_role(name, permission) values('数据分析','4000/4100/4200/4300/4400');

#管理员表
create table `manager`
(
	#操作员ID
	id int auto_increment,
	#姓名
	name char(64) not null,
	#账号(可关联邮箱)
	account char(64) not null default "",
	#初始密码
	password char(16) not null default "",
	#密码期限
	password_limit_utc bigint not null default 0,
	#创建密码时间
	password_create_utc bigint not null default 0,
	#邮箱账号
	email char(64),
	#是否关联	0 未关联 1已关联
	relate_flag tinyint not null default 0,
	#管理员角色ID
	role_id int not null,
	#部门ID
	department_id int not null default 1,
	#使用状态 0 可使用 1 冻结 2 不可用 3 已删除
	status tinyint not null default 0,
	#头像图片url
	headset_url char(64),
	#登录时间
	login_utc bigint not null default 0,
	#主键
	primary key (`id`),
	#索引
	key (role_id),
	key (department_id),
	#外键约束-级联删除
	foreign key (`role_id`) references `manager_role` (`idx`) on delete cascade on update cascade,
	foreign key (`department_id`) references `department` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;
insert into manager(name,account, role_id, password,department_id) values('美家秀','admin',1,'broadin',1);

#权限表
create table `permission`
(
	#id
	id int unsigned,
	#描述
	description varchar(64) not null,
	#主键
	primary key (`id`)
)ENGINE=InnoDB default charset=utf8;
insert into permission(id,description) values (1000, '系统管理权限');
insert into permission(id,description) values (1100, '管理员列表');
insert into permission(id,description) values (1200, '部门列表');
insert into permission(id,description) values (1300, '权限设置');
insert into permission(id,description) values (1400, '管理员回收站');
insert into permission(id,description) values (1500, '事件邮件设置');

insert into permission(id,description) values (2000, '用户管理权限');
insert into permission(id,description) values (2100, '用户列表');
insert into permission(id,description) values (2200, '新建用户');
insert into permission(id,description) values (2300, '用户回收站');

insert into permission(id,description) values (3000, '渠道管理权限');
insert into permission(id,description) values (3100, '渠道列表');
insert into permission(id,description) values (3200, '渠道版本管理');
insert into permission(id,description) values (3300, '新增渠道');
insert into permission(id,description) values (3400, '渠道回收站');

insert into permission(id,description) values (4000, '数据分析权限');
insert into permission(id,description) values (4100, '基本数据分析');
insert into permission(id,description) values (4200, '渠道数据');
insert into permission(id,description) values (4300, '用户活跃');
insert into permission(id,description) values (4400, '用户留存');

insert into permission(id,description) values (5000, '运营管理权限');
insert into permission(id,description) values (5100, '相册推荐');
insert into permission(id,description) values (5200, '活动管理');
insert into permission(id,description) values (5300, '广告管理');

insert into permission(id,description) values (6000, '资源管理权限');
insert into permission(id,description) values (6100, '模版分类');
insert into permission(id,description) values (6200, '模版列表');
insert into permission(id,description) values (6300, '装饰品分类');
insert into permission(id,description) values (6400, '装饰品列表');
insert into permission(id,description) values (6500, '音乐列表');

#管理员回收站表
drop table if exists `recycle`;
create table `recycle`
(
	#自增索引
	idx int unsigned auto_increment,
	#类型 0 用户 1 用户组
	type tinyint not null,
	#对应被删除的对象ID
	ref_id int not null,
	#对应管理组角色
	manage_role char(64),
	#执行删除操作者
	operate_name char(64) not null default "",
	#被删除的用户或用户组姓名
	name text,
	#删除时间
	del_utc bigint,
	#主键
	primary key (`idx`)
)ENGINE=InnoDB default charset=utf8;

#管理员操作日志表
drop table if exists `operate_log`;
create table `operate_log`
(
	#自增索引
	idx bigint unsigned auto_increment,
	#操作员id
	manager_id int not null references manager(id),
	#操作时间
	utc bigint,
	#描述
	description text,
	#主键
	primary key (`idx`)
)ENGINE=InnoDB default charset=utf8;

#图片表，图片单独建表管理
drop table if exists image;
create table `image`
(
	#图片ID(首字母+md5, 首字母有意义，J-表示JPEG图片， P-PNG图片， B-BMP图片)
	id char(36)	not null,
	#图片数据
	image mediumblob not null,
	#主键
	primary key (`id`)
)ENGINE=InnoDB default charset=utf8;

#相册饰品道具分组表
drop table if exists decorate;
drop table if exists decorate_group;
#decorate_group表
create table `decorate_group`
(
	#自增主键
	id char(36) not null,
	#名称
	name varchar(128) not null default "",
	#图标地址
	icon_url varchar(255) not null default "",
	#详细描述
	detail text not null,
	#创建时间
	create_utc bigint not null default 0,
	primary key (id)
)ENGINE=InnoDB default charset=utf8;

#相册饰品道具表
#decorate表
create table `decorate`
(
	#主键
	id char(36) not null,
	#所属道具组
	group_id char(36) not null default "",
	#图片地址
	url varchar(255) not null default "",
	#名称
	name varchar(128) not null default  "",
	#宽度
	width smallint	not null default 0,
	#高度
	height smallint not null default 0,
	#添加时间
	create_utc bigint not null default 0,
	#主键
	primary key (id),
	#索引
	key (`group_id`),
	#外键
	foreign key (`group_id`) references `decorate_group` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#主题相册模板分组表
drop table if exists album_template;
drop table if exists template_group;
#template_group表
create table `template_group`
(
	#自增主键
	id char(36) not null,
	#名称
	name varchar(128) not null default "",
	#图标地址
	icon_url varchar(255) not null default "",
	#详细描述
	detail text not null,
	#创建时间
	create_utc bigint not null default 0,
	primary key (id)
)ENGINE=InnoDB default charset=utf8;

#主题相册模板表
#album_template表
create table `album_template`
(
	#主键
	id char(36)  not null,
	#所属模版分组
	group_id char(36) not null,
	#模版名
	name varchar(36) not null default "",
	#模版简介
	introduction text not null,
	#模版封面图URL
	cover varchar(255) not null default "",
	#创建时间
	create_utc	bigint 	not null default 0,
	#最后修改时间（属性发生变化）
	last_modify_utc	bigint 	not null default 0,
	#主键
	primary key (id),
	#索引
	key (`name`),
	key (`group_id`),
	#外键
	foreign key (`group_id`) references `template_group` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#command表
drop table if exists command;
create table `command`
(
	#自增主键
	id int unsigned	auto_increment,
	#命令
	cmd char(64) not null,
	#参数1 一般就是表ID
	arg0 varchar(50),
	#参数2
	arg1 varchar(128),
	#参数3
	arg2 text,
	#主键
	primary key (id)
)ENGINE=InnoDB default charset=utf8;

#媒资表
drop table if exists media;
#media表
create table `media`
(
	#主键
	id char(64) not null,
	#类型（见media_type表）
	type tinyint not null default 0,
	#内容
	content text,
	#描述
	description text,
	#缩略图
	thumbnail varchar(255) not null default "",
	#资源（图片）宽度
	width int not null default 0,
	#资源（图片）高度
	height int not null default 0,
	#字节长度(单位byte)
	byte_len bigint default 0,
	#时间长度(单位秒)
	time_len int default 0,
	#创建者
	creater_id char(16) not null default "",
	#最后修改者
	last_mod_userid char(16) not null default "", 
	#创建时间（如果是时间轴相册，创建时间相同即认为是同一条目内容）
	create_utc bigint not null default 0,
	#修改时间
	modify_utc bigint not null default 0,	
	#主键
	primary key (id),
	#索引
	key (`creater_id`),
	key (`create_utc`)
)ENGINE=InnoDB default charset=utf8;

#媒资类型表
drop table if exists media_type;
#media_type表
create table `media_type`
(
	#id
	id tinyint not null,
	#描述
	description varchar(32) not null default "",
	primary key (id)
)ENGINE=InnoDB default charset=utf8;
insert into media_type(id, description) values(0, "Photo"); #照片
insert into media_type(id, description) values(1, "Video"); #视频
insert into media_type(id, description) values(2, "Audio"); #音频
insert into media_type(id, description) values(3, "Xml");#xml文件
insert into media_type(id, description) values(4, "Text"); #文本

drop table if exists channel_cover;
drop table if exists channel_user;
drop table if exists channel_install;
drop table if exists channel_contacts;
drop table if exists user_relation;
drop table if exists user_club;
drop table if exists club;
drop table if exists album_media;
drop table if exists album;
drop table if exists user;
drop table if exists channel;

#user表
#用户基本信息
create table `user`
(	
	#主键 
	id char(16)	not null,
	#账户名（手机号）
	account	char(32) not null,
	#密码
	password varchar(32) not null default "",
	#用户头像URL
	portrait varchar(255) not null default "",
	#背景图像URL
	bkgimage varchar(255) not null default "",
	#注册终端类型
	register_term_type varchar(20) not null default "",
	#开通状态 0-禁用 1-启用 
	enable char not null default 0, 
	#当前所活跃的群
	active_club_id char(16) not null default "",
	#用户昵称
	nickname varchar(128) not null default "",
	#真实姓名
	realname varchar(128) not null default "",
	#email
	email varchar(128) not null default "",
	#住址
	addr varchar(255) not null default "",
	#电话
	phone varchar(16) not null default "",
	#国家
	country varchar(128) not null default "",
	#邮编
	postcode varchar(16) not null default "",
	#生日
	birthday varchar(10) not null default "",
	#当前在线终端数
	online_termcnt int unsigned not null default 0,
	#账户余额(单位分)
	balances int not null default 0,
	#用户级别（0-普通用户， 1-VIP用户）
	level char not null default 0,
	#被添加为好友的权限（0-任何人， 1-需要通过验证，2-回答问题，3-拒绝所有人）
	friend_permission char not null default 0,
	#添加好友问题
	friend_question	varchar(255)	not null default "",
	#添加好友问题答案
	friend_answer varchar(255) not null default "",
	#渠道id
	channel_id char(16) not null default 0,
	#注册时间
	register_utc bigint not null	 default 0,
	#激活时间（即enable置为1的时间）
	enable_utc bigint not null default 0,
	#有效期至（为0表示无有效期限制，永远可用）
	validto_utc bigint not null default 0,
	#最后更改时间
	last_modify_utc bigint not null default 0,
	#主键
	primary key (id),
	#索引
	key (`channel_id`),
	#唯一索引
	UNIQUE(account)
)ENGINE=InnoDB default charset=utf8;
#默认用户
insert into user(id,account,password,realname,enable,enable_utc) values ('UlNUVVZXzMzMzMzM','13888888888','123456','meijiashow',1,unix_timestamp()*1000);

#club用户群/组/家庭表
#家庭基本信息
create table club
(
  	#自定义主键
	id char(16) not null,
  	#群头像URL
  	portrait char(255) not null default "",
	#群昵称
  	nickname char(128) not null default "",
	#群简介
  	introduction char(255) not null default "",
	#背景图像URL
	bkgimage char(255) not null default "",
	#申请加入的权限（0-需要通过验证 1-任何人可加入 2-拒绝任何人加入）
	join_permission	char not null default 0,
	#允许的最大人数（0-无限制）
	max_member_cnt int not null default 0,
	#创建时间
	create_utc bigint not null default 0,
	#创建者用户id
	creater_id varchar(16) not null default "",
	#最后一次更改的用户id
	last_modify_userid varchar(16) not null default "",
	#最后更改时间
	last_modify_utc bigint not null default 0,
  	primary key (id),
  	#索引
  	key (`creater_id`)
)default charset=utf8;

#用户和群关系表
#user_club表
create table user_club
(
	#自增主键
	idx int unsigned auto_increment, 
	#群id，外键，连接到club
	club_id char(16) 	not null references club(id),
	#成员id，外键，连接到user
	user_id	char(16) not null references user(id),
	#用户和群的状态关系（0-正在申请加入 1-群正式成员 2-群回收站成员）
	club_user_status char not null default 0,
	#用户在群里的昵称（群名片）
	user_nickname char(128) not null default "",
	#加入时间
	join_utc bigint not null default 0,
	#最后更新时间
	last_modify_utc bigint not null default 0,
	#主键
	primary key (idx),
	#索引
	key (`user_id`),
	key (`club_id`),
	#外键约束-级联删除
	foreign key (`user_id`) references `user` (`id`) on delete cascade on update cascade,
	foreign key (`club_id`) references `club` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#用户与用户关系表
#user_relation表
create table `user_relation`
(
	#自增主键
	idx int unsigned auto_increment,
	#主用户id，外键，连接到user
	host_id char(16) not null references user (id),
	#对应的客用户id，外键，连接到user
	guest_id 	char(16) not null references user (id),
	#用户和用户的状态关系（0-正在申请加为好友 1-正式好友 2-黑名单）
	guest_status tinyint not null default 0,
	#主用户给客用户取的备注名
	guest_markname char(128) not null default "",
	#最后更新时间
	last_modify_utc bigint not null default 0,
	#主键
	primary key (idx),
 	#索引
	key (`host_id`),
	key (`guest_id`),
	#外键约束-级联删除
	foreign key (`host_id`) references `user` (`id`) on delete cascade on update cascade,
	foreign key (`guest_id`) references `user` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#相册属性表
#album表
create table `album`
(
	#主键
	id char(36) not null,
	#相册名
	name varchar(64) not null,
	#相册简介
	introduction text	not null,
	#相册类型（0-普通相册 1-主题相册 2-时间轴相册）
	type char not null default 0,
	#相册所属的群（家庭），外键，链接到club表
	club_id char(16) not null,
	#模板id（主题相册）
	template_id char(16) not null,
	#相册封面图URL
	cover varchar(255) not null default "",
	#相册音频介绍文件URL
	sound_url varchar(255) not null default "",
	#状态(0-正常，1-正在合成，2-合成失败) 
	status char not null default 0,
	#相册权限（0-所有人可访问 1-只有创建者可访问（不可见） 2-输入密码可访问 3-回答问题）
	permission char not null default 0,
	#照片数量
	photo_count int not null default 0,
	#视频数量
	video_count int not null default 0,
	#密码
	password varchar(16) not null default "",
	#问题
	question varchar(128)	not null default "",
	#答案
	answer varchar(128)	not null default "",
	#备注
	remark varchar(255) not null default "",
	#创建者id
	creater_id char(16)	not null,
	#创建时间
	create_utc bigint not null default 0,
	#最后修改时间（属性发生变化）
	last_modify_utc	bigint not null default 0,
	#最后修改者id
	last_mod_userid	char(16) not null	default "",
	#最后上传媒资时间
	last_upload_utc	bigint not null default 0,
	#主键
	primary key (id),
	#索引
	key (`name`),
	key (`creater_id`),
	key (`club_id`)
)ENGINE=InnoDB default charset=utf8;

#相册媒资表
#album_media表
create table `album_media`
(
	#自增主键
	id char(64) not null,
	#所属相册 ，外键，链接到album
	album_id char(36) 	not null default "",
	#media_id，外键，链接到media
	media_id char(64) not null default "",
	#媒资状态（主题相册特有，0-正常 1-暂存。注意，如果用户保存了相册，该条媒资应该被删除，即保存成功后清除暂存）
  	status char not null default 0,
  	#标题
  	title varchar(64) not null default "",
  	#描述
  	detail varchar(255) not null default "",
	#发表者id
	user_id char(16) not null default "",
	#发表者IP地址
	user_ip char(16) not null default "",
	#最后修改时间
	last_modify_utc bigint not null default 0,
	#最后修改者
	last_mod_userid char(16) not null default "",
	#最后修改者IP地址
	last_mod_userip varchar(16) not null default "",
	#发表的utc
	utc	bigint not null default 0,
	#发表经纬度
	longitude_latitude  char(16) default "",
	#阅读数
	read_count int not null default 0,
	#分享次数
	share_count int not null default 0,
	#转发次数
	forward_count int not null default 0,
	#主键
	primary key (id),
	#索引
	key (`album_id`),
	key (`user_id`),
	key (`media_id`),
	#外键约束-级联删除
	foreign key (`album_id`) references `album` (`id`) on delete cascade on update cascade,
	foreign key (`user_id`) references `user` (`id`) on delete cascade on update cascade,
	foreign key (`media_id`) references `media` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#channel 渠道表
create table `channel`
(
	#渠道id
	id char(64) not null,
	#渠道名称
	name char(64) not null default "",
	#渠道公司全名
	company_name varchar(255) not null default "",
	#渠道公司地址
	address varchar(255) not null default "",
	#渠道logo key码
	logo_key char(36) not null default "",
	#渠道图片地址
	logo_url text,
	#渠道状态	0正常 1 删除(至回收站)
	status tinyint not null default 0,
	#渠道介绍
	description text,
	#主键
	primary key (`id`)
)ENGINE=InnoDB default charset=utf8;

#渠道覆盖数表
create table `channel_cover`
(
	#覆盖表id
	id int unsigned auto_increment,
	#渠道ID
	channel_id char(64),
	#智能电视覆盖用户数
	smarttv_cover int(11) default 0,
	#机顶盒覆盖用户数
	stb_cover int(11) default 0,
	#广电双模覆盖用户数
	dvb_cover int(11) default 0,
	#应用商店覆盖用户数
	appstore_cover int(11) default 0,
	#年份
	year int(4) default 0,
	#月份
	month int(2) default 0,
	#主键
	primary key (`id`),
	#外键
	foreign key (`channel_id`) references `channel` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#渠道新增注册用户数表
create table `channel_user`
(
	#序号id
	id int unsigned auto_increment,
	#渠道ID
	channel_id char(64),
	#智能电视新增用户数
	smarttv_user int(11) default 0,
	#机顶盒新增用户数
	stb_user int(11) default 0,
	#广电双新增盖用户数
	dvb_user int(11) default 0,
	#应用商店新增用户数
	appstore_user int(11) default 0,
	#其他手持设备新增用户数
	other_user int(11) default 0,
	#年份
	year int(4) default 0,
	#月份
	month int(2) default 0,
	#主键
	primary key (`id`),
	#外键
	foreign key (`channel_id`) references `channel` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#渠道安装数表
create table `channel_install`
(
	#序号id
	id int unsigned auto_increment,
	#渠道ID
	channel_id char(64),
	#智能电视安装数
	smarttv_install int(11) default 0,
	#机顶盒安装数
	stb_install int(11) default 0,
	#广电双模安装数
	dvb_install int(11) default 0,
	#应用商店安装数
	appstore_install int(11) default 0,
	#其他手持设备安装数
	other_install int(11) default 0,
	#年份
	year int(4) default 0,
	#月份
	month int(2) default 0,
	#主键
	primary key (`id`),
	#外键
	foreign key (`channel_id`) references `channel` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#渠道联系人表
create table `channel_contacts`
(
	#联系人id
	id int unsigned auto_increment,
	#渠道ID
	channel_id char(64),
	#联系人姓名
	name char(36) not null default "",
	#联系人部门
	department char(36),
	#联系人职务
	job_position char(36),
	#手机
	mobile char(16),
	#邮箱
	email char(64),
	#电话
	phone char(32),
	#主键
	primary key (`id`),
	#外键
	foreign key (`channel_id`) references `channel` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#终端表
drop table if exists terminal; 
#terminal表
create table `terminal` 
( 
	#id 终端唯一标识字串 
	id char(64)	not null, 
	#所属用户
	user_id char(32) not null default "",
	#IP地址, 初始可以为空，程序跑起来再更新此字段 
	ip varchar(20) not null default "", 
	#MAC地址，初始可以为空，登录时上报
	mac varchar(20) not null default "", 
	#终端类型，详解见terminal_type表 
	type tinyint not null, 
	#软件版本号 
	soft_ver varchar(32) not null default "", 
	#硬件版本号 
	hard_ver varchar(32) not null default "", 
	#在线状态(0-不在线 1-在线) 
	online char	not null default 0,  
	#登录时间 
	login_utc bigint not null default 0, 
	#终端所在地区 
	addr varchar(128) not null default "", 
	#渠道
	channel_id char(16) not null default 0,
	#主键
	primary key (id),
	#索引
	key (`channel_id`)
)ENGINE=InnoDB default charset=utf8;

#终端类型表， 宏定义
drop table if exists terminal_type;
#teminal_type表
create table `terminal_type`
(
	#id
	id tinyint not null,
	#描述
	description varchar(32) not null default "",
	primary key (id)
)ENGINE=InnoDB default charset=utf8;
insert into terminal_type(id, description) values(0, "UNKOWN"); #未知
insert into terminal_type(id, description) values(1, "STB"); #机顶盒
insert into terminal_type(id, description) values(2, "Web-Common"); #WEB公版
insert into terminal_type(id, description) values(3, "Android-Phone"); #Android手机
insert into terminal_type(id, description) values(4, "Android-Pad"); #Android Pad
insert into terminal_type(id, description) values(5, "iOS-Phone"); #苹果手机
insert into terminal_type(id, description) values(6, "iOS-Pad"); #苹果平板
insert into terminal_type(id, description) values(7, "PC"); #个人电脑
insert into terminal_type(id, description) values(8, "SMART-TV"); #智能电视
insert into terminal_type(id, description) values(9, "BROADCAST-DOUBLEMODE"); #广电双模
insert into terminal_type(id, description) values(10, "APPSTORE"); #应用商店

#系统消息表
drop table if exists sys_notify;
#sys_notify表
create table `sys_notify`
(
	#主键
	id char(32) not null,
	#消息类型（见sys_notify_type表）
	type tinyint not null default 0,
	#消息文本
	content	text not null,
	#消息创建时间
	create_utc bigint not null default 0,
	#主键
	primary key (id)
)ENGINE=InnoDB default charset=utf8;

#系统消息类型表， 宏定义
drop table if exists sys_notify_type;
#sys_notify_type表
create table `sys_notify_type`
(
	#id
	id tinyint not null,
	#描述
	description varchar(32) not null default "",
	primary key (id)
)ENGINE=InnoDB default charset=utf8;
insert into sys_notify_type(id, description) values(0, "未定义");
insert into sys_notify_type(id, description) values(1, "申请加为好友");
insert into sys_notify_type(id, description) values(2, "好友申请通过");
insert into sys_notify_type(id, description) values(3, "好友申请被拒绝"); 
insert into sys_notify_type(id, description) values(4, "被请求加为好友"); 
insert into sys_notify_type(id, description) values(5, "申请加入群"); 
insert into sys_notify_type(id, description) values(6, "加入群申请通过"); 
insert into sys_notify_type(id, description) values(7, "加入群申请被拒绝"); 
insert into sys_notify_type(id, description) values(8, "邀请加入群"); 
insert into sys_notify_type(id, description) values(9, "应邀加入群成功"); 

#系统消息记录表
drop table if exists sys_notify_action;
#sys_notify_action表
create table `sys_notify_action`
(
	#自增主键
	idx int unsigned auto_increment,
	#系统消息id
	sys_notify_id char(32) not null,  
	#消息来源者id
	from_id char(16) not null default "",
	#接收者id
	to_id	char(16)  not null default "",
	#目标群id
	club_id char(16) not null default "",
	#接收者的消息状态（0-未读 1-已阅 2-已删除）
	receive_status char	not null default 0,
	#消息成功送达时间
	expiry_utc bigint not null default 0,
	#主键
	primary key (idx),
	#索引
	key (`sys_notify_id`),
	key (`from_id`),
	key (`to_id`),
	key (`club_id`),
	#外键约束-级联删除
	foreign key (`sys_notify_id`) references `sys_notify` (`id`) on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;

#原始日志表
drop table if exists l_log;
create table `l_log`
(
	#自增索引
	idx bigint unsigned auto_increment,
	#deviceId 安装应用的设备ID
	device_id varchar(255),
	#clientType 客户端类型	0未知
	client_type char(10),
	#partnerCode 渠道商编码
	partner_code varchar(100),
	#familyId 家庭账户ID
	family_id bigint,
	#systemType 操作系统类型 0未知
	system_type varchar(100),
	#logKey 日志标识码
	log_key varchar(100),
	#logLabel 事件描述标签
	log_label varchar(100),
	#eventType log类型，针对终端动作(0-未知 1-应用, 2-相册访问，3-事件，4-错误)
	event_type tinyint not null default 0,
	#subEventype 子类型 类型编号(11-init-应用启动，12-exit-应用退出, 13-应用安装 21-普通相册，22-主题相册，23-时间轴相册，31-account.register-注册，32-account.login-登陆，33-点击应用)
	subevent_type tinyint not null default 0,
	#utc app动作时间
	utc bigint,
	#主键
	primary key ( idx )
)ENGINE=InnoDB default charset=utf8;
