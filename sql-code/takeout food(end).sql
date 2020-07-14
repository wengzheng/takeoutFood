/*
Navicat MySQL Data Transfer

Source Server         : Course
Source Server Version : 50647
Source Host           : localhost:3306
Source Database       : takeout food

Target Server Type    : MYSQL
Target Server Version : 50647
File Encoding         : 65001

Date: 2020-07-14 10:28:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `addressID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `nowaddress` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `phone` varchar(15) NOT NULL,
  PRIMARY KEY (`addressID`),
  KEY `FK_地址` (`userID`),
  CONSTRAINT `FK_地址` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='配送地址表';

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('3', '1', '281@gyt1232', '21buea', '1291276');
INSERT INTO `address` VALUES ('4', '1', 'qwiun@5612', 'oqwjn', '129371');
INSERT INTO `address` VALUES ('5', '1', '1212@?sc', '21223e', '12234334');
INSERT INTO `address` VALUES ('6', '1', '2918@hjweu12', 'hidbqaa', '1287236');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminID` int(11) NOT NULL,
  `adminaccount` varchar(30) NOT NULL,
  `adminname` varchar(30) NOT NULL,
  `adminpwd` varchar(30) NOT NULL,
  PRIMARY KEY (`adminID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='管理员信息';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'admin', '111111');
INSERT INTO `admin` VALUES ('2', 'asdf', 'wz', '123');

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `commodityID` int(11) NOT NULL,
  `commoditytypeID` int(11) DEFAULT NULL,
  `commodityname` varchar(30) NOT NULL,
  `price` double NOT NULL,
  `discount` double DEFAULT NULL,
  `evaluate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`commodityID`),
  KEY `FK_包含` (`commoditytypeID`),
  CONSTRAINT `FK_包含` FOREIGN KEY (`commoditytypeID`) REFERENCES `comoditytype` (`commoditytypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='商品';

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO `commodity` VALUES ('1', '1', 'apple', '1.5', null, null);
INSERT INTO `commodity` VALUES ('2', '2', 'banana1', '4', null, null);
INSERT INTO `commodity` VALUES ('3', '2', 'banana2', '6', null, null);

-- ----------------------------
-- Table structure for comoditytype
-- ----------------------------
DROP TABLE IF EXISTS `comoditytype`;
CREATE TABLE `comoditytype` (
  `commoditytypeID` int(11) NOT NULL,
  `shopID` int(11) DEFAULT NULL,
  `commoditytypename` varchar(30) NOT NULL,
  `commoditynum` int(11) NOT NULL,
  PRIMARY KEY (`commoditytypeID`),
  KEY `FK_生产` (`shopID`),
  CONSTRAINT `FK_生产` FOREIGN KEY (`shopID`) REFERENCES `shop` (`shopID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='商品类别';

-- ----------------------------
-- Records of comoditytype
-- ----------------------------
INSERT INTO `comoditytype` VALUES ('1', '1', 'apple', '1');
INSERT INTO `comoditytype` VALUES ('2', '1', 'banana', '2');

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `couponID` int(11) NOT NULL,
  `shopID` int(11) NOT NULL,
  `cdiscount` double NOT NULL,
  `cstarttime` datetime NOT NULL,
  `cendtime` datetime NOT NULL,
  `requarenum` int(11) NOT NULL,
  PRIMARY KEY (`couponID`),
  KEY `FK_设计` (`shopID`),
  CONSTRAINT `FK_设计` FOREIGN KEY (`shopID`) REFERENCES `shop` (`shopID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='优惠劵';

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES ('1', '1', '1.3', '2020-07-06 09:05:31', '2020-07-10 09:05:34', '3');
INSERT INTO `coupon` VALUES ('2', '1', '1.2', '2020-07-11 09:58:05', '2021-07-11 09:22:39', '6');
INSERT INTO `coupon` VALUES ('3', '1', '2', '2020-07-13 22:54:09', '2020-07-19 22:54:13', '8');

-- ----------------------------
-- Table structure for fullreductionplan
-- ----------------------------
DROP TABLE IF EXISTS `fullreductionplan`;
CREATE TABLE `fullreductionplan` (
  `fullreduceplanID` int(11) NOT NULL,
  `shopID` int(11) DEFAULT NULL,
  `fullreduceprice` double NOT NULL,
  `cdiscount` double NOT NULL,
  `withcoupon` tinyint(4) NOT NULL,
  PRIMARY KEY (`fullreduceplanID`),
  KEY `FK_设计2` (`shopID`),
  CONSTRAINT `FK_设计2` FOREIGN KEY (`shopID`) REFERENCES `shop` (`shopID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='满减方案';

-- ----------------------------
-- Records of fullreductionplan
-- ----------------------------
INSERT INTO `fullreductionplan` VALUES ('1', '1', '35', '10', '1');
INSERT INTO `fullreductionplan` VALUES ('2', '1', '50', '20', '1');
INSERT INTO `fullreductionplan` VALUES ('3', '1', '100', '30', '0');
INSERT INTO `fullreductionplan` VALUES ('4', '1', '110', '40', '0');
INSERT INTO `fullreductionplan` VALUES ('5', '1', '5', '1', '0');

-- ----------------------------
-- Table structure for givecoupon
-- ----------------------------
DROP TABLE IF EXISTS `givecoupon`;
CREATE TABLE `givecoupon` (
  `givecouponID` int(11) NOT NULL,
  `shopID` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `couponID` int(11) NOT NULL,
  `requarenum` int(11) NOT NULL,
  `numnowing` int(11) DEFAULT NULL,
  PRIMARY KEY (`givecouponID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='集单送券';

-- ----------------------------
-- Records of givecoupon
-- ----------------------------
INSERT INTO `givecoupon` VALUES ('1', '1', '1', '2', '6', '3');
INSERT INTO `givecoupon` VALUES ('2', '1', '1', '3', '8', '3');

-- ----------------------------
-- Table structure for incoupon
-- ----------------------------
DROP TABLE IF EXISTS `incoupon`;
CREATE TABLE `incoupon` (
  `incouponID` int(11) NOT NULL,
  `couponID` int(11) DEFAULT NULL,
  `userID` int(11) NOT NULL,
  `shopID` int(11) NOT NULL,
  `cdiscount` double NOT NULL,
  `cnum` bigint(20) NOT NULL,
  `cdeadline` datetime NOT NULL,
  PRIMARY KEY (`incouponID`),
  KEY `FK_参考2` (`couponID`),
  CONSTRAINT `FK_参考2` FOREIGN KEY (`couponID`) REFERENCES `coupon` (`couponID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='优惠劵持有';

-- ----------------------------
-- Records of incoupon
-- ----------------------------
INSERT INTO `incoupon` VALUES ('1', '2', '1', '1', '1.2', '0', '2021-07-11 09:22:39');
INSERT INTO `incoupon` VALUES ('2', '3', '1', '1', '2', '0', '2020-07-19 22:54:13');

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail` (
  `ordercommodityID` int(11) NOT NULL,
  `commodityID` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `cnum` bigint(20) DEFAULT NULL,
  `price` double NOT NULL,
  `cdiscount` double DEFAULT NULL,
  PRIMARY KEY (`ordercommodityID`),
  KEY `FK_补充` (`orderID`),
  KEY `FK_采购` (`commodityID`),
  CONSTRAINT `FK_补充` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  CONSTRAINT `FK_采购` FOREIGN KEY (`commodityID`) REFERENCES `commodity` (`commodityID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='订单详情';

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES ('1', '1', '1', null, '1.5', null);
INSERT INTO `orderdetail` VALUES ('2', '1', '1', null, '1.5', null);
INSERT INTO `orderdetail` VALUES ('3', '3', '2', null, '6', null);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `orderID` int(11) NOT NULL,
  `addressID` int(11) DEFAULT NULL,
  `riderID` int(11) DEFAULT NULL,
  `userID` int(11) NOT NULL,
  `shopID` int(11) NOT NULL,
  `fullreduceplanID` int(11) DEFAULT NULL,
  `couponID` int(11) DEFAULT NULL,
  `incouponID` int(11) DEFAULT NULL,
  `originalamount` double NOT NULL,
  `settlementamount` double DEFAULT NULL,
  `downtime` datetime NOT NULL,
  `requiretime` datetime DEFAULT NULL,
  `orderstate` varchar(30) DEFAULT NULL,
  `evaluation` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `FK_下单` (`userID`),
  KEY `FK_分配` (`addressID`),
  KEY `FK_委托` (`riderID`),
  CONSTRAINT `FK_下单` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  CONSTRAINT `FK_分配` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressID`),
  CONSTRAINT `FK_委托` FOREIGN KEY (`riderID`) REFERENCES `rider` (`riderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='商品订单';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '3', null, '1', '1', '0', null, null, '3', '3', '2020-07-13 22:58:41', '2020-07-19 22:54:13', 'Delivered', null);
INSERT INTO `orders` VALUES ('2', '4', null, '1', '1', '5', null, null, '6', '5', '2020-07-13 23:00:13', '2020-07-20 22:54:13', 'Not Received Order', null);
INSERT INTO `orders` VALUES ('3', '5', null, '1', '1', '0', null, null, '1.5', '1.5', '2020-07-13 23:13:44', '2020-07-21 22:54:13', 'Not Received Order', null);

-- ----------------------------
-- Table structure for rider
-- ----------------------------
DROP TABLE IF EXISTS `rider`;
CREATE TABLE `rider` (
  `riderID` int(11) NOT NULL,
  `adminID` int(11) DEFAULT NULL,
  `rideraccount` varchar(30) NOT NULL,
  `ridername` varchar(30) NOT NULL,
  `riderpwd` varchar(30) DEFAULT NULL,
  `rstarttime` datetime NOT NULL,
  `rIdtpye` varchar(20) NOT NULL,
  PRIMARY KEY (`riderID`),
  KEY `FK_管理` (`adminID`),
  CONSTRAINT `FK_管理` FOREIGN KEY (`adminID`) REFERENCES `admin` (`adminID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='骑手信息';

-- ----------------------------
-- Records of rider
-- ----------------------------
INSERT INTO `rider` VALUES ('1', '1', 'rider1', 'rname', '123', '2020-07-11 14:17:42', 'new');

-- ----------------------------
-- Table structure for ridesends
-- ----------------------------
DROP TABLE IF EXISTS `ridesends`;
CREATE TABLE `ridesends` (
  `sendID` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `riderID` int(11) DEFAULT NULL,
  `sendtime` datetime NOT NULL,
  `income` double DEFAULT NULL,
  PRIMARY KEY (`sendID`),
  KEY `FK_指派` (`orderID`),
  CONSTRAINT `FK_指派` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='骑手送单';

-- ----------------------------
-- Records of ridesends
-- ----------------------------
INSERT INTO `ridesends` VALUES ('1', '1', '1', '2020-07-19 22:54:13', '2');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shopID` int(11) NOT NULL,
  `adminID` int(11) DEFAULT NULL,
  `shopaccount` varchar(30) NOT NULL,
  `shopname` varchar(30) NOT NULL,
  `shoppwd` varchar(30) NOT NULL,
  `shopstar` int(11) DEFAULT NULL,
  `avgconsume` float DEFAULT NULL,
  `totalnum` int(11) DEFAULT NULL,
  PRIMARY KEY (`shopID`),
  KEY `FK_管理3` (`adminID`),
  CONSTRAINT `FK_管理3` FOREIGN KEY (`adminID`) REFERENCES `admin` (`adminID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='商家信息';

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1', '1', 'shop1', 'sname', '111', '2', null, null);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `adminID` int(11) DEFAULT NULL,
  `addressID` int(11) DEFAULT NULL,
  `useraccount` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `usex` varchar(4) NOT NULL,
  `userpwd` varchar(30) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL,
  `registertime` datetime NOT NULL,
  `VIP` tinyint(1) DEFAULT NULL,
  `VIPdeadline` datetime DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `FK_管理2` (`adminID`),
  CONSTRAINT `FK_管理2` FOREIGN KEY (`adminID`) REFERENCES `admin` (`adminID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户信息';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '1', null, 'user1', 'uname1', 'boy', '123', '1268234354', '12889347@8934474', 'china', '2020-07-10 08:52:23', null, null);
