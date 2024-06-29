/*
 Navicat Premium Data Transfer

 Source Server         : 总数据库 fuming
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 39.106.173.191:3306
 Source Schema         : carpool

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 03/05/2021 18:22:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for applyJoin
-- ----------------------------
DROP TABLE IF EXISTS `applyJoin`;
CREATE TABLE `applyJoin` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `childid` int unsigned NOT NULL COMMENT '孩子ID',
  `cooperateid` int unsigned NOT NULL COMMENT '订单ID',
  `state` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '申请状态 0正在申请 1 同意 2 拒绝 3 已取消',
  `driverid` int NOT NULL COMMENT '订单司机ID',
  `childAddr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '被接学生的地址',
  `parentid` int DEFAULT NULL COMMENT '代为申请的家长的ID',
  `dismistime` datetime NOT NULL COMMENT '放学时间',
  `destinationAddr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目的地址',
  `applyTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `hanldeTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车辆颜色',
  `carnumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车牌号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='车辆表';

-- ----------------------------
-- Table structure for child
-- ----------------------------
DROP TABLE IF EXISTS `child`;
CREATE TABLE `child` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(40) NOT NULL COMMENT '学生openid',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生姓名',
  `sex` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '学生性别',
  `stuclass` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生班级',
  `schooladdr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学校地址',
  `homeaddr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生家庭住址',
  `phone` varchar(11) NOT NULL COMMENT '学生手机号',
  `avatarUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='孩子表';

-- ----------------------------
-- Table structure for cooperate
-- ----------------------------
DROP TABLE IF EXISTS `cooperate`;
CREATE TABLE `cooperate` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '合作订单创建时间',
  `driverid` int unsigned NOT NULL COMMENT '司机ID',
  `state` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '司机接送状态',
  `maxstu` tinyint unsigned NOT NULL COMMENT '最多接学生数',
  `locate` varchar(255) NOT NULL DEFAULT '' COMMENT '司机位置',
  `locateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '司机定位时间',
  `carid` int unsigned NOT NULL DEFAULT '0' COMMENT '车辆ID',
  `departureTime` datetime DEFAULT NULL COMMENT '出发时间',
  `arriveTime` datetime DEFAULT NULL COMMENT '到学校时间',
  `leftTime` datetime DEFAULT NULL COMMENT '离开学校时间',
  `overTime` datetime DEFAULT NULL COMMENT '订单完成时间',
  `departure` varchar(255) DEFAULT NULL COMMENT '出发地',
  `destination` varchar(255) DEFAULT NULL COMMENT '目的地',
  `planArriveTime` datetime DEFAULT NULL COMMENT '计划到达时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='合作母表';

-- ----------------------------
-- Table structure for cooperateParentChild
-- ----------------------------
DROP TABLE IF EXISTS `cooperateParentChild`;
CREATE TABLE `cooperateParentChild` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `cooperateid` int unsigned NOT NULL COMMENT '合作订单ID',
  `parentid` int unsigned DEFAULT NULL COMMENT '家长ID',
  `childid` int unsigned NOT NULL COMMENT '学生ID',
  `childAddr` varchar(255) NOT NULL COMMENT '被接学生的地址',
  `state` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '接车状态',
  `dismistime` datetime NOT NULL COMMENT '放学时间',
  `cooperatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始合作时间',
  `destinationAddr` varchar(255) NOT NULL COMMENT '目的地址',
  `oncartime` datetime DEFAULT NULL COMMENT '上车时间',
  `overtime` datetime DEFAULT NULL COMMENT '到达或放弃合作时间',
  `childName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '孩子姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='合作子表';

-- ----------------------------
-- Table structure for driverCar
-- ----------------------------
DROP TABLE IF EXISTS `driverCar`;
CREATE TABLE `driverCar` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `driverid` int DEFAULT NULL COMMENT '司机ID',
  `carid` int DEFAULT NULL COMMENT '车辆ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='司机车辆关系表';

-- ----------------------------
-- Table structure for license
-- ----------------------------
DROP TABLE IF EXISTS `license`;
CREATE TABLE `license` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `driverid` int unsigned NOT NULL COMMENT '驾照持有者ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='驾驶证表';

-- ----------------------------
-- Table structure for parent
-- ----------------------------
DROP TABLE IF EXISTS `parent`;
CREATE TABLE `parent` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(40) NOT NULL COMMENT '家长openid',
  `name` varchar(32) NOT NULL COMMENT '家长姓名',
  `sex` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '家长性别',
  `phone` varchar(11) NOT NULL COMMENT '家长手机号',
  `idnumber` varchar(18) NOT NULL COMMENT '家长身份证号',
  `licenseid` int unsigned NOT NULL DEFAULT '0' COMMENT '家长驾照ID',
  `avatarUrl` varchar(255) NOT NULL COMMENT '用户头像地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='家长表';

-- ----------------------------
-- Table structure for parentChild
-- ----------------------------
DROP TABLE IF EXISTS `parentChild`;
CREATE TABLE `parentChild` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `parentid` int unsigned NOT NULL COMMENT '家长ID',
  `childid` int unsigned NOT NULL COMMENT '学生ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='家长孩子关系表';

-- ----------------------------
-- Table structure for unreceivedMessages
-- ----------------------------
DROP TABLE IF EXISTS `unreceivedMessages`;
CREATE TABLE `unreceivedMessages` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `fromUID` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息发送方openid',
  `toUID` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息接收方openid',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
  `data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `cooperateID` int NOT NULL COMMENT '订单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for verify
-- ----------------------------
DROP TABLE IF EXISTS `verify`;
CREATE TABLE `verify` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(40) DEFAULT NULL COMMENT '用户openid',
  `carNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '车牌号',
  `carColor` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '车辆颜色',
  `accept` tinyint unsigned DEFAULT '0' COMMENT '审核状态0待审核 1通过 2拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='爱心车主审核表';

-- ----------------------------
-- View structure for cooperateView
-- ----------------------------
DROP VIEW IF EXISTS `cooperateView`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cooperateView` AS select distinct `cooperate`.`id` AS `id`,`cooperate`.`datetime` AS `datetime`,`cooperate`.`state` AS `state`,`cooperate`.`driverid` AS `driverid`,`cooperate`.`maxstu` AS `maxstu`,`cooperate`.`locate` AS `locate`,`cooperate`.`locateTime` AS `locateTime`,`cooperate`.`carid` AS `carid`,`cooperate`.`departureTime` AS `departureTime`,`cooperate`.`arriveTime` AS `arriveTime`,`cooperate`.`leftTime` AS `leftTime`,`cooperate`.`overTime` AS `overTime`,`cooperate`.`departure` AS `departure`,`cooperate`.`destination` AS `destination`,`cooperate`.`planArriveTime` AS `planArriveTime`,(select count(0) from `cooperateParentChild` where (`cooperateParentChild`.`cooperateid` = `cooperate`.`id`)) AS `shouldPickStuNum`,(select count(0) from `cooperateParentChild` where ((`cooperateParentChild`.`cooperateid` = `cooperate`.`id`) and (`cooperateParentChild`.`state` = 1))) AS `stuNumAtCar`,(select `parent`.`name` from `parent` where (`parent`.`id` = `cooperate`.`driverid`)) AS `driverName`,(select `parent`.`openid` from `parent` where (`parent`.`id` = `cooperate`.`driverid`)) AS `driverOpenid`,(select `parent`.`avatarUrl` from `parent` where (`parent`.`id` = `cooperate`.`driverid`)) AS `driverAvatarUrl`,(select `parent`.`phone` from `parent` where (`parent`.`id` = `cooperate`.`driverid`)) AS `driverPhone`,(select `parent`.`sex` from `parent` where (`parent`.`id` = `cooperate`.`driverid`)) AS `driverSex`,(select `car`.`color` from `car` where (`car`.`id` = `cooperate`.`carid`)) AS `carColor`,(select `car`.`carnumber` from `car` where (`car`.`id` = `cooperate`.`carid`)) AS `carNumber` from `cooperate`;

-- ----------------------------
-- Triggers structure for table cooperate
-- ----------------------------
DROP TRIGGER IF EXISTS `updateTime`;
delimiter ;;
CREATE TRIGGER `updateTime` BEFORE UPDATE ON `cooperate` FOR EACH ROW IF (OLD.state != NEW.state) THEN
        IF (NEW.state = 1) THEN
        SET NEW.departureTime = CURRENT_TIMESTAMP;
        END IF;
        IF (NEW.state = 2) THEN
        SET NEW.arriveTime = CURRENT_TIMESTAMP;
        END IF;
        IF (NEW.state = 3) THEN
        SET NEW.leftTime = CURRENT_TIMESTAMP;
        END IF;
        IF (NEW.state = 4) THEN
        SET NEW.overTime = CURRENT_TIMESTAMP;
        END IF;
        END IF
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table cooperateParentChild
-- ----------------------------
DROP TRIGGER IF EXISTS `addChildName`;
delimiter ;;
CREATE TRIGGER `addChildName` BEFORE INSERT ON `cooperateParentChild` FOR EACH ROW SET NEW.childName=(SELECT name FROM child WHERE id=new.childid)
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
