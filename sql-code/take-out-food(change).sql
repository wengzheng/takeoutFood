/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/4 14:51:36                            */
/*==============================================================*/


drop table if exists 优惠劵;

drop table if exists 优惠劵持有;

drop table if exists 商品;

drop table if exists 商品类别;

drop table if exists 商品订单;

drop table if exists 商家信息;

drop table if exists 满减方案;

drop table if exists 用户信息;

drop table if exists 管理员信息;

drop table if exists 订单详情;

drop table if exists 配送地址表;

drop table if exists 集单送券;

drop table if exists 骑手信息;

drop table if exists 骑手送单;

/*==============================================================*/
/* Table: 优惠劵                                                   */
/*==============================================================*/
create table 优惠劵
(
   优惠券编号                int not null,
   员工编号                 int not null,
   优惠金额                 float(30) not null,
   起始日期                 timestamp not null,
   结束日期                 timestamp not null,
   集单要求数                int not null,
   primary key (优惠券编号)
);

alter table 优惠劵 comment '优惠劵';

/*==============================================================*/
/* Table: 优惠劵持有                                                 */
/*==============================================================*/
create table 优惠劵持有
(
   优惠劵持有编号              int not null,
   优惠券编号                int not null,
   用户编号                 int not null,
   所属商家编号               int not null,
   优惠金额                 float(30) not null,
   数量                   bigint not null,
   截止日期                 timestamp not null,
   primary key (优惠劵持有编号)
);

alter table 优惠劵持有 comment '优惠劵持有';

/*==============================================================*/
/* Table: 商品                                                    */
/*==============================================================*/
create table 商品
(
   商品编号                 int not null,
   分类编号                 int,
   商品类别编号               int not null,
   商品名                  varchar(30) not null,
   价格                   float(30) not null,
   优惠价格                 float(30) not null,
   primary key (商品编号)
);

alter table 商品 comment '商品';

/*==============================================================*/
/* Table: 商品类别                                                  */
/*==============================================================*/
create table 商品类别
(
   分类编号                 int not null,
   商家编号                 int,
   分类栏目名                varchar(30) not null,
   商品数量                 varchar(30) not null,
   primary key (分类编号)
);

alter table 商品类别 comment '商品类别';

/*==============================================================*/
/* Table: 商品订单                                                  */
/*==============================================================*/
create table 商品订单
(
   订单编号                 int not null,
   满减编号                 int not null,
   地址编号                 int not null,
   骑手编号                 int not null,
   用户编号                 int not null,
   优惠劵持有编号              int not null,
   商家编号                 int not null,
   原始金额                 float(30) not null,
   结算金额                 float(30) not null,
   优惠券编号                int not null,
   下单时间                 timestamp not null,
   要求送达时间               timestamp not null,
   配送地址编号               int not null,
   订单状态                 varchar(30) not null,
   primary key (订单编号)
);

alter table 商品订单 comment '商品订单';

/*==============================================================*/
/* Table: 商家信息                                                  */
/*==============================================================*/
create table 商家信息
(
   商家编号                 int not null,
   员工编号                 int,
   商家名                  varchar(30) not null,
   商家星级                 int not null,
   人均消费                 float(20) not null,
   总销量                  int not null,
   primary key (商家编号)
);

alter table 商家信息 comment '商家信息';

/*==============================================================*/
/* Table: 满减方案                                                  */
/*==============================================================*/
create table 满减方案
(
   满减编号                 int not null,
   员工编号                 int,
   满减金额                 float(30) not null,
   优惠金额                 float(30) not null,
   是否可与优惠券叠加            bool not null,
   primary key (满减编号)
);

alter table 满减方案 comment '满减方案';

/*==============================================================*/
/* Table: 用户信息                                                  */
/*==============================================================*/
create table 用户信息
(
   用户编号                 int not null,
   员工编号                 int,
   地址编号                 int,
   姓名                   varchar(30) not null,
   性别                   varchar(2) not null,
   用户密码                 varchar(30) not null,
   手机号码                 varchar(15) not null,
   邮箱                   varchar(30) not null,
   所在城市                 varchar(30) not null,
   注册时间                 timestamp not null,
   是否会员                 bool not null,
   会员截止日期               timestamp not null,
   primary key (用户编号)
);

alter table 用户信息 comment '用户信息';

/*==============================================================*/
/* Table: 管理员信息                                                 */
/*==============================================================*/
create table 管理员信息
(
   员工编号                 int not null,
   员工姓名                 varchar(30) not null,
   登录密码                 varchar(30) not null,
   primary key (员工编号)
);

alter table 管理员信息 comment '管理员信息';

/*==============================================================*/
/* Table: 订单详情                                                  */
/*==============================================================*/
create table 订单详情
(
   订单商品编号               int not null,
   商品编号                 int not null,
   订单编号                 int not null,
   数量                   bigint not null,
   价格                   float(30) not null,
   优惠金额                 float(30) not null,
   primary key (订单商品编号)
);

alter table 订单详情 comment '订单详情';

/*==============================================================*/
/* Table: 配送地址表                                                 */
/*==============================================================*/
create table 配送地址表
(
   地址编号                 int not null,
   用户编号                 int not null,
   地址                   varchar(30) not null,
   联系人                  varchar(30) not null,
   电话                   varchar(15) not null,
   primary key (地址编号)
);

alter table 配送地址表 comment '配送地址表';

/*==============================================================*/
/* Table: 集单送券                                                  */
/*==============================================================*/
create table 集单送券
(
   集单编号                 int not null,
   订单编号                 int not null,
   商家编号                 int not null,
   用户编号                 int not null,
   优惠券编号                int not null,
   集单要求数                int not null,
   已订单数                 int not null,
   primary key (集单编号)
);

alter table 集单送券 comment '集单送券';

/*==============================================================*/
/* Table: 骑手信息                                                  */
/*==============================================================*/
create table 骑手信息
(
   骑手编号                 int not null,
   员工编号                 int,
   骑手姓名                 varchar(30) not null,
   入职日期                 timestamp not null,
   骑手身份                 varchar(20) not null,
   primary key (骑手编号)
);

alter table 骑手信息 comment '骑手信息';

/*==============================================================*/
/* Table: 骑手送单                                                  */
/*==============================================================*/
create table 骑手送单
(
   送单编号                 int not null,
   订单编号                 int,
   骑手编号                 int not null,
   所接订单编号               int not null,
   时间                   timestamp not null,
   用户评价                 varchar(30) not null,
   primary key (送单编号)
);

alter table 骑手送单 comment '骑手送单';

alter table 优惠劵 add constraint FK_设计 foreign key (员工编号)
      references 管理员信息 (员工编号) on delete restrict on update restrict;

alter table 优惠劵持有 add constraint FK_参考2 foreign key (优惠券编号)
      references 优惠劵 (优惠券编号) on delete restrict on update restrict;

alter table 商品 add constraint FK_包含 foreign key (分类编号)
      references 商品类别 (分类编号) on delete restrict on update restrict;

alter table 商品类别 add constraint FK_生产 foreign key (商家编号)
      references 商家信息 (商家编号) on delete restrict on update restrict;

alter table 商品订单 add constraint FK_下单 foreign key (用户编号)
      references 用户信息 (用户编号) on delete restrict on update restrict;

alter table 商品订单 add constraint FK_优惠 foreign key (满减编号)
      references 满减方案 (满减编号) on delete restrict on update restrict;

alter table 商品订单 add constraint FK_优惠2 foreign key (优惠劵持有编号)
      references 优惠劵持有 (优惠劵持有编号) on delete restrict on update restrict;

alter table 商品订单 add constraint FK_分配 foreign key (地址编号)
      references 配送地址表 (地址编号) on delete restrict on update restrict;

alter table 商品订单 add constraint FK_委托 foreign key (骑手编号)
      references 骑手信息 (骑手编号) on delete restrict on update restrict;

alter table 商家信息 add constraint FK_管理3 foreign key (员工编号)
      references 管理员信息 (员工编号) on delete restrict on update restrict;

alter table 满减方案 add constraint FK_设计2 foreign key (员工编号)
      references 管理员信息 (员工编号) on delete restrict on update restrict;

alter table 用户信息 add constraint FK_所在地 foreign key (地址编号)
      references 配送地址表 (地址编号) on delete restrict on update restrict;

alter table 用户信息 add constraint FK_管理2 foreign key (员工编号)
      references 管理员信息 (员工编号) on delete restrict on update restrict;

alter table 订单详情 add constraint FK_采购 foreign key (商品编号)
      references 商品 (商品编号) on delete restrict on update restrict;

alter table 订单详情 add constraint FK_补充 foreign key (订单编号)
      references 商品订单 (订单编号) on delete restrict on update restrict;

alter table 配送地址表 add constraint FK_地址 foreign key (用户编号)
      references 用户信息 (用户编号) on delete restrict on update restrict;

alter table 集单送券 add constraint FK_得到 foreign key (订单编号)
      references 商品订单 (订单编号) on delete restrict on update restrict;

alter table 骑手信息 add constraint FK_管理 foreign key (员工编号)
      references 管理员信息 (员工编号) on delete restrict on update restrict;

alter table 骑手送单 add constraint FK_指派 foreign key (订单编号)
      references 商品订单 (订单编号) on delete restrict on update restrict;

