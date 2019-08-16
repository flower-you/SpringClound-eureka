use delta_tools;

-- ************** shop-client ******************

-- 用户表
drop table if exists t_user;
create table t_user (
id int primary key auto_increment,
username  varchar(20) unique,
password varchar(56) not null,
photoname varchar(150),
sex boolean,
age int,
phone VARCHAR(15)-- 电话
);

-- 图书属性表
drop table if exists t_book;
CREATE TABLE t_book(
id int PRIMARY KEY auto_increment,
NAME VARCHAR(50),
price FLOAT,-- 原价
auth  VARCHAR(30), -- 作者
img VARCHAR(40), -- 图书的图片名称UUID
rebate NUMERIC(3,2), -- 0.xx保留两位
stock INT,
publisher VARCHAR(50), -- 出版社名称
publishdate DATE, -- 出版日期
pages INT, -- 页数
size VARCHAR(10), -- 开本
printtimes INT, -- 印次
versions INT, -- 版次
brief TEXT, -- 内容简介
content VARCHAR(4000), -- 目录,必须一显示格式良好的HTMl文本保存
onlinetime DATE  -- 上架时间
);

-- 购物车表
-- 每个用户都有一个购物车
drop table if exists t_shopcar;
create table t_shopcar(
uid int,
bid int,
num int,-- 同种商品的数量
`status` tinyint(4) default 1,-- 1:正常，0：禁用，-1：删除
create_time timestamp default current_timestamp,-- 记录创建时间，时间戳，在创建新纪录时吧这个字段值设置为当前时间，但以后修改时不再更新它
update_time timestamp on update current_timestamp,-- 记录更新时间，时间戳,在创建新纪录时吧这个字段值设为0，但以后修改的时候更新为当前的时间戳
-- foreign key(uid) references t_user(id),
foreign key (uid) references t_user(id)  on update cascade on delete cascade,
foreign key(bid) references t_book(id)
);

create index uid_bid_num on t_shopcar(uid, bid, num);


select * from t_book;


drop table if exists shop;

create table shop(
id bigint unsigned primary key auto_increment,
`name` varchar(150),
price float, -- 原价
`number` varchar(50), -- 编号
img VARCHAR(40), -- 图书的图片名称UUID
rebate numeric(3,2), -- 折扣  0.xx保留两位
stock int unsigned, -- 库存
`describe` varchar(200),
-- product_time datetime default current_timestamp,-- 生产时间
gmt_create datetime default current_timestamp,
gmt_modified datetime on update current_timestamp
);



--  *****************  schedule ******************

DROP TABLE IF EXISTS `quartz_sample`;
CREATE TABLE `quartz_sample`  (
  `id` int unsigned primary KEY auto_increment,
  `name` varchar(50) unique,
  `service_name` varchar(70) comment '所属服务',
  `type` tinyint default 0 comment '0集群 1单节点',
  `cron` varchar(30),
  `status` tinyint default 1 comment '0 启动 1未启动',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
);
INSERT INTO `quartz_sample`(`name`,`service_name`,`type`,`cron`,`status`) VALUES ('clear_book_cache','cache_client',0,'0 0 2 * * *',1);

-- update `cron` set `cron` = '0/5 * * * * *' where `cron_id` = 1;
-- update `cron` set `cron` = '0 */5 * * * *' where `cron_id` = 1;
-- explain  select * FROM `cron` where `cron_id` = 1;
-- select * from `cron`;
drop table if exists quartz_job;
create table `quartz_job`(
 id bigint unsigned primary key auto_increment,
job_name varchar(255) default null comment '定时任务名称',
job_group varchar(255) default null comment '所属服务',
`job_status` tinyint DEFAULT 0 COMMENT '是否开启 0未开启 1已启动',
cron_expression varchar(255) not null comment 'cron表达式',
job_type tinyint DEFAULT 0 comment '任务类型 0：集群 1：单节点' ,
-- is_concurrent varchar(255) DEFAULT NULL COMMENT '是否需要顺序执行',
-- `method_name` varchar(255) NOT NULL COMMENT '方法名',
`create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

insert into quartz_job(job_name,job_group,job_status,cron_expression,job_type) values('removeBookCache','cache-client',0,'0 0 2 * * *',0);
select * from quartz_job;

drop table if exists quartz_job_record;
CREATE TABLE `quartz_job_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `flag` tinyint DEFAULT 0 COMMENT '执行结果(1成功0失败)',
  `state` tinyint DEFAULT 0 COMMENT '执行状态（0执行中,1执行结束）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`state`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2552705 DEFAULT CHARSET=utf8;

insert into  quartz_job_record(name) values('removeBoookCache');

-- drop table timed_task,task_sample,timed_task_msg;

desc cron;
-- drop table cron;

select * from quartz_job;
