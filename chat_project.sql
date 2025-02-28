/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1-mysql8.0
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : chat_project

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2025-02-28 16:17:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `friend_id` int NOT NULL AUTO_INCREMENT COMMENT '好友表的主键id',
  `user_id` int NOT NULL COMMENT '用户id  和用户表的user_id是外键关系',
  `user_friend_id` int NOT NULL COMMENT '用户的某个好友id 和用户表的user_id是外键关系',
  `add_time` datetime DEFAULT NULL COMMENT '添加好友的时间',
  PRIMARY KEY (`friend_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of friends
-- ----------------------------
INSERT INTO `friends` VALUES ('1', '1', '1', '2022-07-14 23:12:53');
INSERT INTO `friends` VALUES ('2', '1', '2', '2022-07-14 23:13:02');
INSERT INTO `friends` VALUES ('3', '2', '1', '2022-07-14 23:13:13');
INSERT INTO `friends` VALUES ('4', '2', '2', '2022-07-14 23:13:21');
INSERT INTO `friends` VALUES ('5', '3', '3', '2022-07-15 00:00:00');
INSERT INTO `friends` VALUES ('6', '4', '4', '2022-07-15 00:00:00');
INSERT INTO `friends` VALUES ('7', '5', '5', '2022-07-15 00:00:00');
INSERT INTO `friends` VALUES ('8', '2', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('9', '3', '2', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('10', '1', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('11', '5', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('12', '1', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('13', '5', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('14', '1', '2', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('15', '2', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('16', '1', '2', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('17', '2', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('18', '6', '6', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('19', '6', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('20', '1', '6', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('21', '6', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('22', '5', '6', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('23', '2', '6', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('24', '6', '2', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('25', '2', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('26', '5', '2', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('27', '4', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('28', '1', '4', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('29', '3', '1', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('30', '1', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('31', '3', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('32', '5', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('33', '3', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('34', '5', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('35', '3', '5', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('36', '5', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('37', '4', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('38', '3', '4', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('39', '4', '3', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('40', '3', '4', '2022-07-18 00:00:00');
INSERT INTO `friends` VALUES ('41', '7', '7', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('42', '8', '8', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('43', '9', '9', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('44', '10', '10', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('45', '7', '3', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('46', '3', '7', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('47', '7', '5', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('48', '5', '7', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('49', '7', '9', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('50', '9', '7', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('51', '7', '1', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('52', '1', '7', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('53', '2', '8', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('54', '8', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('55', '2', '8', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('56', '8', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('57', '1', '3', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('58', '3', '1', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('59', '1', '10', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('60', '10', '1', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('61', '1', '10', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('62', '10', '1', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('63', '2', '10', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('64', '10', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('65', '2', '10', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('66', '10', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('67', '2', '9', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('68', '9', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('69', '2', '7', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('70', '7', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('71', '2', '4', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('72', '4', '2', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('73', '11', '3', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('74', '3', '11', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('75', '11', '4', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('76', '4', '11', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('77', '11', '1', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('78', '1', '11', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('79', '11', '9', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('80', '9', '11', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('81', '12', '11', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('82', '11', '12', '2022-07-19 00:00:00');
INSERT INTO `friends` VALUES ('83', '11', '7', '2022-07-22 00:00:00');
INSERT INTO `friends` VALUES ('84', '7', '11', '2022-07-22 00:00:00');
INSERT INTO `friends` VALUES ('85', '12', '4', '2022-07-22 00:00:00');
INSERT INTO `friends` VALUES ('86', '4', '12', '2022-07-22 00:00:00');
INSERT INTO `friends` VALUES ('87', '12', '9', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('88', '9', '12', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('89', '12', '8', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('90', '8', '12', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('91', '13', '13', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('92', '14', '14', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('93', '15', '15', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('94', '15', '9', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('95', '9', '15', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('96', '15', '3', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('97', '3', '15', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('98', '15', '6', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('99', '6', '15', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('100', '15', '11', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('101', '11', '15', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('102', '16', '16', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('103', '16', '3', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('104', '3', '16', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('105', '16', '9', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('106', '9', '16', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('107', '16', '11', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('108', '11', '16', '2022-07-23 00:00:00');
INSERT INTO `friends` VALUES ('109', '17', '17', '2025-02-28 00:00:00');
INSERT INTO `friends` VALUES ('110', '17', '1', '2025-02-28 00:00:00');
INSERT INTO `friends` VALUES ('111', '1', '17', '2025-02-28 00:00:00');

-- ----------------------------
-- Table structure for friend_message
-- ----------------------------
DROP TABLE IF EXISTS `friend_message`;
CREATE TABLE `friend_message` (
  `friend_message_id` int NOT NULL AUTO_INCREMENT COMMENT '好友消息id',
  `sender_id` int NOT NULL COMMENT '发送用户id  和用户表的user_id是外键关系',
  `receiver_id` int NOT NULL COMMENT '消息接受用户的id 和用户表的user_id是外键关系',
  `message` varchar(200) DEFAULT NULL COMMENT '发送的消息，默认200个字符',
  `send_time` datetime DEFAULT NULL COMMENT '发送消息的时间',
  PRIMARY KEY (`friend_message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of friend_message
-- ----------------------------
INSERT INTO `friend_message` VALUES ('1', '1', '1', '你好，我自己', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('2', '1', '2', '你好，李四', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('3', '2', '1', '你好张三', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('4', '1', '5', '你是谁', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('5', '1', '5', '好不好', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('6', '1', '5', '欸有什么', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('7', '6', '1', '你好', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('8', '4', '1', '', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('9', '4', '1', '', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('10', '2', '1', '你好', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('11', '1', '2', '年后', '2022-07-18 00:00:00');
INSERT INTO `friend_message` VALUES ('12', '10', '10', '5858', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('13', '10', '10', '58585', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('14', '12', '11', '你好', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('15', '12', '11', '发送一个个文件，路径为：chatfiles/0b3da78150d7fb8169b207ff5169ddc8.jpeg', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('16', '11', '3', '48', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('17', '11', '12', '发送了一个文件，路径为：chatfiles/7.jpeg', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('18', '12', '11', '发送一个个文件，路径为：chatfiles/6.jpeg', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('19', '11', '12', '发送了一个文件，路径为：chatfiles/8.jpeg', '2022-07-19 00:00:00');
INSERT INTO `friend_message` VALUES ('20', '11', '3', '就', '2022-07-22 00:00:00');
INSERT INTO `friend_message` VALUES ('21', '11', '3', '发送了一个文件，路径为：chatfiles/0b3da78150d7fb8169b207ff5169ddc8.jpeg', '2022-07-22 00:00:00');
INSERT INTO `friend_message` VALUES ('22', '11', '12', '急急急急急急', '2022-07-22 00:00:00');
INSERT INTO `friend_message` VALUES ('23', '12', '4', '的护额大户', '2022-07-22 00:00:00');
INSERT INTO `friend_message` VALUES ('24', '12', '11', '二点', '2022-07-22 00:00:00');
INSERT INTO `friend_message` VALUES ('25', '11', '12', '无所谓', '2022-07-22 00:00:00');
INSERT INTO `friend_message` VALUES ('26', '11', '12', '你好', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('27', '12', '11', '你也好', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('28', '12', '11', 'hello', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('29', '11', '12', '乱七八糟', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('30', '15', '11', '你好', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('31', '15', '11', '发送了一个文件，路径为：chatfiles/7.jpeg', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('32', '11', '15', '你是谁', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('33', '15', '11', '你爸爸', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('34', '16', '11', '你好', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('35', '16', '11', '发送了一个文件，路径为：chatfiles/9.jpeg', '2022-07-23 00:00:00');
INSERT INTO `friend_message` VALUES ('36', '16', '11', '的额的', '2022-07-23 00:00:00');

-- ----------------------------
-- Table structure for group_info
-- ----------------------------
DROP TABLE IF EXISTS `group_info`;
CREATE TABLE `group_info` (
  `group_id` int NOT NULL AUTO_INCREMENT COMMENT '群聊id',
  `group_number` varchar(20) NOT NULL COMMENT '群的号码',
  `group_name` varchar(20) NOT NULL COMMENT '群名',
  `group_leader_id` int NOT NULL COMMENT '群主id 和用户表的user_id是外键关系',
  `group_intro` varchar(200) DEFAULT '暂无群介绍' COMMENT '群的简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_number` (`group_number`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of group_info
-- ----------------------------
INSERT INTO `group_info` VALUES ('12', '95963', 'xiexie', '1', '暂无群介绍', '2022-07-22 10:56:53');
INSERT INTO `group_info` VALUES ('21', '78896', '852', '12', '请输入群简介', '2022-07-23 00:00:00');
INSERT INTO `group_info` VALUES ('22', '18441', '看i几乎共同繁荣的', '12', '看i聚合页共同繁荣', '2022-07-23 00:00:00');
INSERT INTO `group_info` VALUES ('23', '23201', '公司', '12', '恶的', '2022-07-23 00:00:00');
INSERT INTO `group_info` VALUES ('24', '29487', '恶的', '12', '请问群简介', '2022-07-23 00:00:00');
INSERT INTO `group_info` VALUES ('25', '31379', '250', '12', '250聚集', '2022-07-23 00:00:00');

-- ----------------------------
-- Table structure for group_member
-- ----------------------------
DROP TABLE IF EXISTS `group_member`;
CREATE TABLE `group_member` (
  `group_member_id` int NOT NULL AUTO_INCREMENT COMMENT '群聊成员主键id',
  `group_id` int NOT NULL COMMENT '群id 和group_info表的group_id字段外键关系',
  `member_id` int NOT NULL COMMENT '群成员id 和用户表的user_id是外键关系',
  `add_time` datetime DEFAULT NULL COMMENT '加入群聊的时间',
  PRIMARY KEY (`group_member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of group_member
-- ----------------------------
INSERT INTO `group_member` VALUES ('27', '21', '12', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('28', '22', '12', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('29', '21', '11', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('30', '22', '11', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('31', '12', '12', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('32', '23', '12', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('33', '24', '12', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('34', '25', '12', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('35', '25', '11', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('36', '25', '1', '2022-07-23 00:00:00');
INSERT INTO `group_member` VALUES ('40', '23', '16', '2022-07-23 00:00:00');

-- ----------------------------
-- Table structure for group_message
-- ----------------------------
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_message` (
  `group_message_id` int NOT NULL AUTO_INCREMENT COMMENT '群聊消息主键id',
  `group_id` int NOT NULL COMMENT '群id 和group_info表的group_id字段外键关系',
  `member_id` int NOT NULL COMMENT '群成员id 和用户表的user_id是外键关系',
  `message` varchar(200) NOT NULL COMMENT '群成员发送的消息',
  `send_time` datetime DEFAULT NULL COMMENT '群成员发送本条消息的时间',
  PRIMARY KEY (`group_message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of group_message
-- ----------------------------
INSERT INTO `group_message` VALUES ('1', '1', '11', 'wsw', '2022-07-21 23:22:42');
INSERT INTO `group_message` VALUES ('3', '1', '11', '1212得得', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('4', '1', '11', '发送了一个文件，路径为：chatfiles/6.jpeg', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('5', '11', '12', '大家好', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('6', '11', '11', '你们好', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('7', '15', '11', '五十五十', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('8', '2', '11', 'i及hi和', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('9', '11', '12', '的额', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('10', '11', '12', '充电电池', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('11', '11', '12', '彻底的调查', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('12', '11', '12', '的CDC', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('14', '17', '11', '8488', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('15', '17', '11', '发送了一个文件，路径为：chatfiles/9.jpeg', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('16', '11', '12', '非人非', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('17', '16', '11', '恶的', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('18', '12', '12', '饿的的', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('19', '12', '11', '得得得', '2022-07-22 00:00:00');
INSERT INTO `group_message` VALUES ('20', '12', '12', '急急急', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('21', '12', '11', '得得士刁手', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('22', '20', '11', '一个', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('23', '25', '11', '大家好', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('24', '25', '1', '250们大家好', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('25', '25', '12', '欢迎新250进群', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('26', '25', '15', '大家好', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('27', '25', '15', '发送了一个文件，路径为：chatfiles/6.jpeg', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('28', '23', '16', '你好', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('29', '25', '16', '开心不', '2022-07-23 00:00:00');
INSERT INTO `group_message` VALUES ('30', '27', '11', '及间谍u电话', '2022-07-23 00:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nickname` varchar(20) NOT NULL COMMENT '用户的昵称',
  `username` varchar(20) NOT NULL COMMENT '用户的账号',
  `password` varchar(20) NOT NULL COMMENT '用户的密码',
  `sign` varchar(50) DEFAULT '暂无个性签名' COMMENT '用户的个性签名',
  `sex` char(5) DEFAULT '男' COMMENT '性别 默认为男',
  `header` varchar(200) DEFAULT NULL COMMENT '头像图片的地址',
  `add_time` datetime DEFAULT NULL COMMENT '账号的创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zs', '123', '123', '暂无个性签名', '男', 'images//man.png', '2022-07-14 23:11:36');
INSERT INTO `user` VALUES ('2', 'ls', '124', '124', '暂无个性签名', '女', 'images//woman.png', '2022-07-14 23:12:39');
INSERT INTO `user` VALUES ('3', '我是你的神', '351676', '159', '请输入你的个性签名', '男', 'images/man.png', '2022-07-15 00:00:00');
INSERT INTO `user` VALUES ('4', '喜喜喜', '577582', '1234', '请输入你的个性签名', '男', 'images/man.png', '2022-07-15 00:00:00');
INSERT INTO `user` VALUES ('5', '4848', '084218', '456', '484849595', '女', 'images/1657898895099.png', '2022-07-15 00:00:00');
INSERT INTO `user` VALUES ('6', '98', '301728', '159', '请输入你的个性签名', '女', 'images/1658152453538.png', '2022-07-18 00:00:00');
INSERT INTO `user` VALUES ('7', '2626', '700848', '123', '请输入你的个性签名', '男', 'images/man.png', '2022-07-19 00:00:00');
INSERT INTO `user` VALUES ('8', '23', '587520', '123', '请输入你的个性签名', '男', 'images/man.png', '2022-07-19 00:00:00');
INSERT INTO `user` VALUES ('9', '我是', '831972', '123', '请输入你的个性签名', '男', 'images/man.png', '2022-07-19 00:00:00');
INSERT INTO `user` VALUES ('10', '為', '232246', '123', '请输入你的个性签名', '男', 'images/man.png', '2022-07-19 00:00:00');
INSERT INTO `user` VALUES ('11', '1', '1', '11', '暂无个性签名', '男', 'images/man.png', '2022-07-19 00:00:00');
INSERT INTO `user` VALUES ('12', '2', '2', '2', '暂无个性签名', '男', 'images/man.png', '2022-07-19 00:00:00');
INSERT INTO `user` VALUES ('13', '恶的', '884226', '1', '1的底层', '女', 'images/1658535577038.png', '2022-07-23 00:00:00');
INSERT INTO `user` VALUES ('14', '空间和广泛的', '298998', '1', '请输入你的个性签名', '女', 'images/woman.png', '2022-07-23 00:00:00');
INSERT INTO `user` VALUES ('15', '3', '621337', '3', 'assassinated', '女', 'images/1658547516526.png', '2022-07-23 00:00:00');
INSERT INTO `user` VALUES ('16', '4', '642522', '4', '非人非', '女', 'images/1658548986532.png', '2022-07-23 00:00:00');
INSERT INTO `user` VALUES ('17', 'ewdff', '924852', '123', '而一个和', '女', 'images/woman.png', '2025-02-28 00:00:00');
