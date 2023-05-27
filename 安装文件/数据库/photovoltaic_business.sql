/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : photovoltaic_business

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 27/10/2022 08:46:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for compensation
-- ----------------------------
DROP TABLE IF EXISTS `compensation`;
CREATE TABLE `compensation`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `subsidy_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '追退补编号',
  `house_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '户号',
  `house_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '户名',
  `time_begin` date NULL DEFAULT NULL COMMENT '开始日期',
  `time_finish` date NULL DEFAULT NULL COMMENT '结束日期',
  `error_fee` double(16, 2) NULL DEFAULT NULL COMMENT '错误单价',
  `right_fee` double(16, 2) NULL DEFAULT NULL COMMENT '正确单价',
  `generating_quantity` double(16, 2) NULL DEFAULT NULL COMMENT '总发电量',
  `compensation_fee` double(16, 2) NULL DEFAULT NULL COMMENT '总追补金额',
  `subsidy_time` date NULL DEFAULT NULL COMMENT '追补时间',
  `subsidy_fee` double(16, 2) NULL DEFAULT NULL COMMENT '追补金额',
  `subsidy_total_fee` double(16, 2) NULL DEFAULT NULL COMMENT '累计已追补金额',
  `subsidy_rest_fee` double(16, 2) NULL DEFAULT NULL COMMENT '还需追补金额',
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '该数据创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '该数据修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for documents
-- ----------------------------
DROP TABLE IF EXISTS `documents`;
CREATE TABLE `documents`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `document_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `document_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `document_time` datetime(0) NULL DEFAULT NULL,
  `document_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for grid_connect
-- ----------------------------
DROP TABLE IF EXISTS `grid_connect`;
CREATE TABLE `grid_connect`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `house_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '户号',
  `add_apply` datetime(0) NULL DEFAULT NULL COMMENT '并网申请',
  `add_survey` datetime(0) NULL DEFAULT NULL COMMENT '现场勘察',
  `add_confirm` datetime(0) NULL DEFAULT NULL COMMENT '接入方案设计及确认',
  `add_record` datetime(0) NULL DEFAULT NULL COMMENT '项目备案',
  `connect_apply` datetime(0) NULL DEFAULT NULL COMMENT '并网验收与调试申请',
  `connect_device` datetime(0) NULL DEFAULT NULL COMMENT '安装计量装置',
  `connect_sign` datetime(0) NULL DEFAULT NULL COMMENT '签署合同及协议',
  `connect_check` datetime(0) NULL DEFAULT NULL COMMENT '并网验收与调试',
  `is_complete` tinyint(0) NULL DEFAULT NULL COMMENT '是否并网运行',
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `files` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `process1_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `process7_number` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `process1_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `process7_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `process2_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process3_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process4_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process5_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process6_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for house_info
-- ----------------------------
DROP TABLE IF EXISTS `house_info`;
CREATE TABLE `house_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `house_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '户号',
  `house_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '户名',
  `house_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `house_owner` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋产权人',
  `contact_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `add_reason` tinyint(0) NULL DEFAULT NULL COMMENT '新建用户原因',
  `house_type` tinyint(0) NULL DEFAULT NULL COMMENT '安装处房屋情况',
  `total_floor` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '总楼层',
  `house_floor` tinyint(0) NULL DEFAULT NULL COMMENT '房屋楼层',
  `installation_type` tinyint(0) NULL DEFAULT NULL COMMENT '安装方式',
  `power_supply` tinyint(0) NULL DEFAULT NULL COMMENT '供电电压',
  `internet_capacity` double(16, 2) NULL DEFAULT NULL COMMENT '光伏上网容量',
  `connection_time` datetime(0) NULL DEFAULT NULL COMMENT '光伏并网时间',
  `powered_devices` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主要用电设备',
  `consumption_mode` tinyint(0) NULL DEFAULT NULL COMMENT '发电消纳方式',
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '该数据创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '该数据修改时间',
  `ext_field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subsidy
-- ----------------------------
DROP TABLE IF EXISTS `subsidy`;
CREATE TABLE `subsidy`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `house_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `house_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目名称',
  `time` date NULL DEFAULT NULL COMMENT '电费年月',
  `subsidy_standard` double(16, 2) NULL DEFAULT NULL COMMENT '补助标准',
  `generating_quantity` double(16, 3) NULL DEFAULT NULL COMMENT '发电量',
  `network_price` double(16, 2) NULL DEFAULT NULL COMMENT '上网电价',
  `network_quantity` double(16, 3) NULL DEFAULT NULL COMMENT '上网电量',
  `payable_amount` double(16, 2) NULL DEFAULT NULL COMMENT '应付购电费',
  `subsidy_amount` double(16, 2) NULL DEFAULT NULL COMMENT '中央补助金额',
  `total_amount` double(16, 2) NULL DEFAULT NULL COMMENT '总应付金额',
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '该数据创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '该数据更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9973 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
