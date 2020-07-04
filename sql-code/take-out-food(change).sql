/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/4 14:51:36                            */
/*==============================================================*/


drop table if exists �Ż݄�;

drop table if exists �Ż݄�����;

drop table if exists ��Ʒ;

drop table if exists ��Ʒ���;

drop table if exists ��Ʒ����;

drop table if exists �̼���Ϣ;

drop table if exists ��������;

drop table if exists �û���Ϣ;

drop table if exists ����Ա��Ϣ;

drop table if exists ��������;

drop table if exists ���͵�ַ��;

drop table if exists ������ȯ;

drop table if exists ������Ϣ;

drop table if exists �����͵�;

/*==============================================================*/
/* Table: �Ż݄�                                                   */
/*==============================================================*/
create table �Ż݄�
(
   �Ż�ȯ���                int not null,
   Ա�����                 int not null,
   �Żݽ��                 float(30) not null,
   ��ʼ����                 timestamp not null,
   ��������                 timestamp not null,
   ����Ҫ����                int not null,
   primary key (�Ż�ȯ���)
);

alter table �Ż݄� comment '�Ż݄�';

/*==============================================================*/
/* Table: �Ż݄�����                                                 */
/*==============================================================*/
create table �Ż݄�����
(
   �Ż݄����б��              int not null,
   �Ż�ȯ���                int not null,
   �û����                 int not null,
   �����̼ұ��               int not null,
   �Żݽ��                 float(30) not null,
   ����                   bigint not null,
   ��ֹ����                 timestamp not null,
   primary key (�Ż݄����б��)
);

alter table �Ż݄����� comment '�Ż݄�����';

/*==============================================================*/
/* Table: ��Ʒ                                                    */
/*==============================================================*/
create table ��Ʒ
(
   ��Ʒ���                 int not null,
   ������                 int,
   ��Ʒ�����               int not null,
   ��Ʒ��                  varchar(30) not null,
   �۸�                   float(30) not null,
   �Żݼ۸�                 float(30) not null,
   primary key (��Ʒ���)
);

alter table ��Ʒ comment '��Ʒ';

/*==============================================================*/
/* Table: ��Ʒ���                                                  */
/*==============================================================*/
create table ��Ʒ���
(
   ������                 int not null,
   �̼ұ��                 int,
   ������Ŀ��                varchar(30) not null,
   ��Ʒ����                 varchar(30) not null,
   primary key (������)
);

alter table ��Ʒ��� comment '��Ʒ���';

/*==============================================================*/
/* Table: ��Ʒ����                                                  */
/*==============================================================*/
create table ��Ʒ����
(
   �������                 int not null,
   �������                 int not null,
   ��ַ���                 int not null,
   ���ֱ��                 int not null,
   �û����                 int not null,
   �Ż݄����б��              int not null,
   �̼ұ��                 int not null,
   ԭʼ���                 float(30) not null,
   ������                 float(30) not null,
   �Ż�ȯ���                int not null,
   �µ�ʱ��                 timestamp not null,
   Ҫ���ʹ�ʱ��               timestamp not null,
   ���͵�ַ���               int not null,
   ����״̬                 varchar(30) not null,
   primary key (�������)
);

alter table ��Ʒ���� comment '��Ʒ����';

/*==============================================================*/
/* Table: �̼���Ϣ                                                  */
/*==============================================================*/
create table �̼���Ϣ
(
   �̼ұ��                 int not null,
   Ա�����                 int,
   �̼���                  varchar(30) not null,
   �̼��Ǽ�                 int not null,
   �˾�����                 float(20) not null,
   ������                  int not null,
   primary key (�̼ұ��)
);

alter table �̼���Ϣ comment '�̼���Ϣ';

/*==============================================================*/
/* Table: ��������                                                  */
/*==============================================================*/
create table ��������
(
   �������                 int not null,
   Ա�����                 int,
   �������                 float(30) not null,
   �Żݽ��                 float(30) not null,
   �Ƿ�����Ż�ȯ����            bool not null,
   primary key (�������)
);

alter table �������� comment '��������';

/*==============================================================*/
/* Table: �û���Ϣ                                                  */
/*==============================================================*/
create table �û���Ϣ
(
   �û����                 int not null,
   Ա�����                 int,
   ��ַ���                 int,
   ����                   varchar(30) not null,
   �Ա�                   varchar(2) not null,
   �û�����                 varchar(30) not null,
   �ֻ�����                 varchar(15) not null,
   ����                   varchar(30) not null,
   ���ڳ���                 varchar(30) not null,
   ע��ʱ��                 timestamp not null,
   �Ƿ��Ա                 bool not null,
   ��Ա��ֹ����               timestamp not null,
   primary key (�û����)
);

alter table �û���Ϣ comment '�û���Ϣ';

/*==============================================================*/
/* Table: ����Ա��Ϣ                                                 */
/*==============================================================*/
create table ����Ա��Ϣ
(
   Ա�����                 int not null,
   Ա������                 varchar(30) not null,
   ��¼����                 varchar(30) not null,
   primary key (Ա�����)
);

alter table ����Ա��Ϣ comment '����Ա��Ϣ';

/*==============================================================*/
/* Table: ��������                                                  */
/*==============================================================*/
create table ��������
(
   ������Ʒ���               int not null,
   ��Ʒ���                 int not null,
   �������                 int not null,
   ����                   bigint not null,
   �۸�                   float(30) not null,
   �Żݽ��                 float(30) not null,
   primary key (������Ʒ���)
);

alter table �������� comment '��������';

/*==============================================================*/
/* Table: ���͵�ַ��                                                 */
/*==============================================================*/
create table ���͵�ַ��
(
   ��ַ���                 int not null,
   �û����                 int not null,
   ��ַ                   varchar(30) not null,
   ��ϵ��                  varchar(30) not null,
   �绰                   varchar(15) not null,
   primary key (��ַ���)
);

alter table ���͵�ַ�� comment '���͵�ַ��';

/*==============================================================*/
/* Table: ������ȯ                                                  */
/*==============================================================*/
create table ������ȯ
(
   �������                 int not null,
   �������                 int not null,
   �̼ұ��                 int not null,
   �û����                 int not null,
   �Ż�ȯ���                int not null,
   ����Ҫ����                int not null,
   �Ѷ�����                 int not null,
   primary key (�������)
);

alter table ������ȯ comment '������ȯ';

/*==============================================================*/
/* Table: ������Ϣ                                                  */
/*==============================================================*/
create table ������Ϣ
(
   ���ֱ��                 int not null,
   Ա�����                 int,
   ��������                 varchar(30) not null,
   ��ְ����                 timestamp not null,
   �������                 varchar(20) not null,
   primary key (���ֱ��)
);

alter table ������Ϣ comment '������Ϣ';

/*==============================================================*/
/* Table: �����͵�                                                  */
/*==============================================================*/
create table �����͵�
(
   �͵����                 int not null,
   �������                 int,
   ���ֱ��                 int not null,
   ���Ӷ������               int not null,
   ʱ��                   timestamp not null,
   �û�����                 varchar(30) not null,
   primary key (�͵����)
);

alter table �����͵� comment '�����͵�';

alter table �Ż݄� add constraint FK_��� foreign key (Ա�����)
      references ����Ա��Ϣ (Ա�����) on delete restrict on update restrict;

alter table �Ż݄����� add constraint FK_�ο�2 foreign key (�Ż�ȯ���)
      references �Ż݄� (�Ż�ȯ���) on delete restrict on update restrict;

alter table ��Ʒ add constraint FK_���� foreign key (������)
      references ��Ʒ��� (������) on delete restrict on update restrict;

alter table ��Ʒ��� add constraint FK_���� foreign key (�̼ұ��)
      references �̼���Ϣ (�̼ұ��) on delete restrict on update restrict;

alter table ��Ʒ���� add constraint FK_�µ� foreign key (�û����)
      references �û���Ϣ (�û����) on delete restrict on update restrict;

alter table ��Ʒ���� add constraint FK_�Ż� foreign key (�������)
      references �������� (�������) on delete restrict on update restrict;

alter table ��Ʒ���� add constraint FK_�Ż�2 foreign key (�Ż݄����б��)
      references �Ż݄����� (�Ż݄����б��) on delete restrict on update restrict;

alter table ��Ʒ���� add constraint FK_���� foreign key (��ַ���)
      references ���͵�ַ�� (��ַ���) on delete restrict on update restrict;

alter table ��Ʒ���� add constraint FK_ί�� foreign key (���ֱ��)
      references ������Ϣ (���ֱ��) on delete restrict on update restrict;

alter table �̼���Ϣ add constraint FK_����3 foreign key (Ա�����)
      references ����Ա��Ϣ (Ա�����) on delete restrict on update restrict;

alter table �������� add constraint FK_���2 foreign key (Ա�����)
      references ����Ա��Ϣ (Ա�����) on delete restrict on update restrict;

alter table �û���Ϣ add constraint FK_���ڵ� foreign key (��ַ���)
      references ���͵�ַ�� (��ַ���) on delete restrict on update restrict;

alter table �û���Ϣ add constraint FK_����2 foreign key (Ա�����)
      references ����Ա��Ϣ (Ա�����) on delete restrict on update restrict;

alter table �������� add constraint FK_�ɹ� foreign key (��Ʒ���)
      references ��Ʒ (��Ʒ���) on delete restrict on update restrict;

alter table �������� add constraint FK_���� foreign key (�������)
      references ��Ʒ���� (�������) on delete restrict on update restrict;

alter table ���͵�ַ�� add constraint FK_��ַ foreign key (�û����)
      references �û���Ϣ (�û����) on delete restrict on update restrict;

alter table ������ȯ add constraint FK_�õ� foreign key (�������)
      references ��Ʒ���� (�������) on delete restrict on update restrict;

alter table ������Ϣ add constraint FK_���� foreign key (Ա�����)
      references ����Ա��Ϣ (Ա�����) on delete restrict on update restrict;

alter table �����͵� add constraint FK_ָ�� foreign key (�������)
      references ��Ʒ���� (�������) on delete restrict on update restrict;

