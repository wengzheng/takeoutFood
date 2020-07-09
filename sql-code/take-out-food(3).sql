/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/9 8:16:08                             */
/*==============================================================*/


drop table if exists address;

drop table if exists admin;

drop table if exists commodity;

drop table if exists comoditytype;

drop table if exists coupon;

drop table if exists fullreductionplan;

drop table if exists givecoupon;

drop table if exists incoupon;

drop table if exists orderdetail;

drop table if exists orders;

drop table if exists rider;

drop table if exists ridersends;

drop table if exists shop;

drop table if exists users;

/*==============================================================*/
/* Table: address                                               */
/*==============================================================*/
create table address
(
   addressID            int not null,
   userID               int not null,
   nowaddress           varchar(30) not null,
   username             varchar(30) not null,
   phone                varchar(15) not null,
   primary key (addressID)
);

alter table address comment '���͵�ַ��';

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   adminID              int not null,
   adminaccount         varchar(30) not null,
   adminname            varchar(30) not null,
   adminpwd             varchar(30) not null,
   primary key (adminID)
);

alter table admin comment '����Ա��Ϣ';

/*==============================================================*/
/* Table: commodity                                             */
/*==============================================================*/
create table commodity
(
   commodityID          int not null,
   commoditytypeID      int,
   commodityname        varchar(30) not null,
   price                float(30) not null,
   discount             float(30) not null,
   primary key (commodityID)
);

alter table commodity comment '��Ʒ';

/*==============================================================*/
/* Table: comoditytype                                          */
/*==============================================================*/
create table comoditytype
(
   commoditytypeID      int not null,
   shopID               int,
   commoditytypename    varchar(30) not null,
   commoditynum         varchar(30) not null,
   primary key (commoditytypeID)
);

alter table comoditytype comment '��Ʒ���';

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   couponID             int not null,
   shopID               int,
   cdiscount            float(30) not null,
   cstarttime           timestamp not null,
   cendtime             timestamp not null,
   requarenum           int not null,
   primary key (couponID)
);

alter table coupon comment '�Ż݄�';

/*==============================================================*/
/* Table: fullreductionplan                                     */
/*==============================================================*/
create table fullreductionplan
(
   fullreduceplanID     int not null,
   shopID               int,
   fullreduceprice      float(30) not null,
   cdiscount            float(30) not null,
   withcoupon           bool not null,
   primary key (fullreduceplanID)
);

alter table fullreductionplan comment '��������';

/*==============================================================*/
/* Table: givecoupon                                            */
/*==============================================================*/
create table givecoupon
(
   givecouponID         int not null,
   orderID              int,
   shopID               int not null,
   userID               int not null,
   couponID             int not null,
   requarenum           int not null,
   numnowing            int not null,
   primary key (givecouponID)
);

alter table givecoupon comment '������ȯ';

/*==============================================================*/
/* Table: incoupon                                              */
/*==============================================================*/
create table incoupon
(
   incouponID           int not null,
   couponID             int,
   userID               int not null,
   shopID               int not null,
   cdiscount            float(30) not null,
   cnum                 bigint not null,
   cdeadline            timestamp not null,
   primary key (incouponID)
);

alter table incoupon comment '�Ż݄�����';

/*==============================================================*/
/* Table: orderdetail                                           */
/*==============================================================*/
create table orderdetail
(
   ordercommodityID     int not null,
   commodityID          int not null,
   orderID              int not null,
   cnum                 bigint not null,
   price                float(30) not null,
   cdiscount            float(30) not null,
   primary key (ordercommodityID)
);

alter table orderdetail comment '��������';

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   orderID              int not null,
   addressID            int not null,
   riderID              int not null,
   userID               int not null,
   shopID               int not null,
   fullreduceplanID     int,
   incouponID           int,
   originalamount       float(30) not null,
   settlementamount     float(30) not null,
   couponID             int not null,
   downtime             timestamp not null,
   requiretime          timestamp not null,
   orderstate           varchar(30) not null,
   primary key (orderID)
);

alter table orders comment '��Ʒ����';

/*==============================================================*/
/* Table: rider                                                 */
/*==============================================================*/
create table rider
(
   riderID              int not null,
   adminID              int,
   rideraccount         varchar(30) not null,
   ridername            varchar(30) not null,
   riderpwd             varchar(30),
   rstarttime           timestamp not null,
   rIdtpye              varchar(20) not null,
   primary key (riderID)
);

alter table rider comment '������Ϣ';

/*==============================================================*/
/* Table: ridersends                                            */
/*==============================================================*/
create table ridersends
(
   sendID               int not null,
   orderID              int not null,
   riderID              int not null,
   sendtime             timestamp not null,
   evaluate             varchar(30) not null,
   primary key (sendID)
);

alter table ridersends comment '�����͵�';

/*==============================================================*/
/* Table: shop                                                  */
/*==============================================================*/
create table shop
(
   shopID               int not null,
   adminID              int,
   shopaccount          varchar(30) not null,
   shopname             varchar(30) not null,
   shoppwd              varchar(30),
   shopstar             int not null,
   avgconsume           float(20) not null,
   totalnum             int not null,
   primary key (shopID)
);

alter table shop comment '�̼���Ϣ';

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   userID               int not null,
   adminID              int,
   addressID            int,
   useraccount          varchar(30) not null,
   username             varchar(30) not null,
   usex                 varchar(2) not null,
   userpwd              varchar(30) not null,
   phone                varchar(15) not null,
   email                varchar(30) not null,
   address              varchar(30) not null,
   registertime         timestamp not null,
   VIP                  bool not null,
   VIPdeadline          timestamp not null,
   primary key (userID)
);

alter table users comment '�û���Ϣ';

alter table address add constraint FK_��ַ foreign key (userID)
      references users (userID) on delete restrict on update restrict;

alter table commodity add constraint FK_���� foreign key (commoditytypeID)
      references comoditytype (commoditytypeID) on delete restrict on update restrict;

alter table comoditytype add constraint FK_���� foreign key (shopID)
      references shop (shopID) on delete restrict on update restrict;

alter table coupon add constraint FK_��� foreign key (shopID)
      references shop (shopID) on delete restrict on update restrict;

alter table fullreductionplan add constraint FK_���2 foreign key (shopID)
      references shop (shopID) on delete restrict on update restrict;

alter table givecoupon add constraint FK_�õ� foreign key (orderID)
      references orders (orderID) on delete restrict on update restrict;

alter table incoupon add constraint FK_�ο�2 foreign key (couponID)
      references coupon (couponID) on delete restrict on update restrict;

alter table orderdetail add constraint FK_���� foreign key (orderID)
      references orders (orderID) on delete restrict on update restrict;

alter table orderdetail add constraint FK_�ɹ� foreign key (commodityID)
      references commodity (commodityID) on delete restrict on update restrict;

alter table orders add constraint FK_�µ� foreign key (userID)
      references users (userID) on delete restrict on update restrict;

alter table orders add constraint FK_�Ż� foreign key (fullreduceplanID)
      references fullreductionplan (fullreduceplanID) on delete restrict on update restrict;

alter table orders add constraint FK_�Ż�2 foreign key (incouponID)
      references incoupon (incouponID) on delete restrict on update restrict;

alter table orders add constraint FK_���� foreign key (addressID)
      references address (addressID) on delete restrict on update restrict;

alter table orders add constraint FK_ί�� foreign key (riderID)
      references rider (riderID) on delete restrict on update restrict;

alter table rider add constraint FK_���� foreign key (adminID)
      references admin (adminID) on delete restrict on update restrict;

alter table ridersends add constraint FK_ָ�� foreign key (orderID)
      references orders (orderID) on delete restrict on update restrict;

alter table shop add constraint FK_����3 foreign key (adminID)
      references admin (adminID) on delete restrict on update restrict;

alter table users add constraint FK_��ַ foreign key (addressID)
      references address (addressID) on delete restrict on update restrict;

alter table users add constraint FK_����2 foreign key (adminID)
      references admin (adminID) on delete restrict on update restrict;

