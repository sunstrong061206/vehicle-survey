/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : vehicle_survey

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 02/11/2021 10:13:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for assignlog
-- ----------------------------
DROP TABLE IF EXISTS `assignlog`;
CREATE TABLE `assignlog`  (
  `assginId` int NOT NULL AUTO_INCREMENT,
  `assignLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `assignTime` timestamp NOT NULL,
  `assignPoint` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `assignMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageTime` timestamp NOT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetTime` timestamp NULL DEFAULT NULL,
  `result` tinyint NULL DEFAULT NULL COMMENT '0表示审批未通过，1表示审批通过',
  `resultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL COMMENT '0表示此单失效，1表示此单生效',
  PRIMARY KEY (`assginId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of assignlog
-- ----------------------------

-- ----------------------------
-- Table structure for drivelog
-- ----------------------------
DROP TABLE IF EXISTS `drivelog`;
CREATE TABLE `drivelog`  (
  `driveId` int NOT NULL AUTO_INCREMENT,
  `assignId` int NOT NULL COMMENT '对应的派车单编号',
  `driveLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `driveBegin` timestamp NOT NULL,
  `driveEnd` timestamp NULL DEFAULT NULL,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `surveyRoute` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '勘查路径',
  PRIMARY KEY (`driveId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of drivelog
-- ----------------------------

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` tinyint NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `facedata` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `boosNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` tinyint NOT NULL COMMENT '1表示就职中，0表示未就职',
  `pwd` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------

-- ----------------------------
-- Table structure for lendlog
-- ----------------------------
DROP TABLE IF EXISTS `lendlog`;
CREATE TABLE `lendlog`  (
  `lendId` int NOT NULL AUTO_INCREMENT,
  `lendLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendDrivecardImg` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendTime` timestamp NOT NULL,
  `lendDays` int NOT NULL,
  `lendMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manage1No` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manage1LendTime` timestamp NULL DEFAULT NULL,
  `manage2No` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manage2LendTime` timestamp NOT NULL,
  `manage2LendResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过，2表示申报',
  `manage2LendResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetLendTime` timestamp NULL DEFAULT NULL,
  `vetLendResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `vetLendResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `returnTime` timestamp NULL DEFAULT NULL,
  `returnImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片数组',
  `manage2ReturnTime` timestamp NULL DEFAULT NULL,
  `manage2ReturnResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过，2表示申报',
  `manage2ReturnResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetReturnTime` timestamp NULL DEFAULT NULL,
  `vetReturnResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `vetReturnResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL COMMENT '0表示此单失效，1表示此单生效',
  PRIMARY KEY (`lendId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lendlog
-- ----------------------------

-- ----------------------------
-- Table structure for protectlog
-- ----------------------------
DROP TABLE IF EXISTS `protectlog`;
CREATE TABLE `protectlog`  (
  `protectId` int NOT NULL AUTO_INCREMENT,
  `protectLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `protectTime` timestamp NOT NULL,
  `protectMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageTime` timestamp NOT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetTime` timestamp NULL DEFAULT NULL,
  `result` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `resultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL COMMENT '0表示此单失效，1表示此单生效',
  PRIMARY KEY (`protectId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of protectlog
-- ----------------------------

-- ----------------------------
-- Table structure for punishlog
-- ----------------------------
DROP TABLE IF EXISTS `punishlog`;
CREATE TABLE `punishlog`  (
  `punishId` int NOT NULL AUTO_INCREMENT,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accidentMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accidentTime` timestamp NOT NULL,
  `accidentPoint` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `punishMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `punishAmount` double NOT NULL,
  `payStatus` tinyint NOT NULL COMMENT '0表示未缴款，1表示已缴款',
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`punishId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of punishlog
-- ----------------------------

-- ----------------------------
-- Table structure for repairlog
-- ----------------------------
DROP TABLE IF EXISTS `repairlog`;
CREATE TABLE `repairlog`  (
  `repairId` int NOT NULL AUTO_INCREMENT,
  `repairLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `repairTime` timestamp NOT NULL,
  `repairImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片数组',
  `repairMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `surveyTime` timestamp NULL DEFAULT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manageTime` timestamp NULL DEFAULT NULL,
  `manageResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `manageResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetTime` timestamp NULL DEFAULT NULL,
  `vetResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `vetResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL COMMENT '0表示此单失效，1表示此单生效',
  PRIMARY KEY (`repairId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of repairlog
-- ----------------------------

-- ----------------------------
-- Table structure for test_assignlog
-- ----------------------------
DROP TABLE IF EXISTS `test_assignlog`;
CREATE TABLE `test_assignlog`  (
  `assignId` int NOT NULL AUTO_INCREMENT,
  `assignLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `assignTime` timestamp NOT NULL,
  `assignPoint` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `assignMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageTime` timestamp NOT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetTime` timestamp NULL DEFAULT NULL,
  `result` tinyint NULL DEFAULT NULL COMMENT '-1表示审批未通过，1表示审批通过',
  `resultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL COMMENT '默认为0，失效为-1，有效为1',
  `revokeTime` timestamp NULL DEFAULT NULL,
  `damagedImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `damagedMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `damagedLevel` int NULL DEFAULT NULL,
  PRIMARY KEY (`assignId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_assignlog
-- ----------------------------
INSERT INTO `test_assignlog` VALUES (5, '豫A123JK3', '2021-09-29 08:00:00', 'G3食堂', '', 'sv123456', 'ad238292', '2021-09-29 21:08:14', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (6, '豫A123JK0', '2021-09-29 08:00:00', '', '', 'sv123456', 'ad238292', '2021-09-29 21:25:56', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (7, '豫A123JK6', '2021-11-05 22:32:09', '人民大会堂', '恐怖袭击', 'sv123456', 'ad238292', '2021-10-01 22:32:40', '0', '2021-10-01 21:59:13', 1, '审批通过', 1, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (8, '豫A123JK0', '2021-10-09 08:00:00', 'sdfdsf', 'dfdsf', 'sv123456', 'ad238292', '2021-10-09 18:12:08', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (9, '豫A123JK0', '2021-10-09 08:00:00', 'sdsad', 'dsadasd', 'sv123456', 'ad238292', '2021-10-09 18:13:02', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (10, '豫A123JK0', '2021-10-09 08:00:00', 'sd', 'adasdsad', 'sv123456', 'ad238292', '2021-10-09 18:13:30', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (11, '豫A123JK3', '2021-10-22 08:00:00', 'gg', '', 'sv123456', 'ad238292', '2021-10-22 00:05:27', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (12, '津HSB555', '2021-10-22 08:00:00', 'B312', '三个老师开火', 'sv123456', 'ad123456', '2021-10-22 00:08:20', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (14, '豫B123JK0', '2021-10-26 08:00:00', '定海', '', 'sv123456', 'ad238292', '2021-10-26 16:50:17', 'vt123456', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_assignlog` VALUES (15, '豫B123JK0', '2021-10-28 08:00:00', '中国计量大学', '地震', 'sv123456', 'ad238292', '2021-10-28 20:59:25', 'vt123456', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for test_drivelog
-- ----------------------------
DROP TABLE IF EXISTS `test_drivelog`;
CREATE TABLE `test_drivelog`  (
  `driveId` int NOT NULL,
  `assignId` int NOT NULL COMMENT '对应的派车单编号',
  `driveLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` timestamp NOT NULL,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `placeX` decimal(65, 6) NULL DEFAULT NULL,
  `placeY` decimal(65, 6) NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_drivelog
-- ----------------------------
INSERT INTO `test_drivelog` VALUES (5, 5, '豫A123JK3', '2021-10-05 23:49:34', 'sv123456', 30.265550, 120.153600, '110.png');
INSERT INTO `test_drivelog` VALUES (5, 5, '豫A123JK3', '2021-10-05 23:49:35', 'sv123456', 30.266000, 120.154000, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-09 21:50:08', 'sv123456', 30.267000, 120.305230, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-09 21:50:13', 'sv123456', 30.270000, 120.205230, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-09 21:50:18', 'sv123456', 30.257270, 120.205230, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-09 21:50:23', 'sv123456', 31.257270, 120.205230, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-09 21:50:28', 'sv123456', 30.257270, 120.205230, '112112112.jpg');
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-09 21:50:30', 'sv123456', 30.257270, 120.205230, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-25 17:24:32', 'sv123456', 30.274150, 120.155150, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-25 17:24:49', 'sv123456', 30.274150, 120.155150, NULL);
INSERT INTO `test_drivelog` VALUES (7, 7, '豫A123JK6', '2021-10-25 20:15:43', 'sv123456', 30.257270, 120.205230, NULL);

-- ----------------------------
-- Table structure for test_employee
-- ----------------------------
DROP TABLE IF EXISTS `test_employee`;
CREATE TABLE `test_employee`  (
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` tinyint NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `facedata` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bossNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` tinyint NOT NULL COMMENT '1表示就职中，-1表示未就职，2表示出车中，3表示请假中',
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_employee
-- ----------------------------
INSERT INTO `test_employee` VALUES ('ad000000', '孙斯壮', 18, '11011011011', 'manage', '0', 'sunstrong.jpg', 'undefined', '0', 1, '123123');
INSERT INTO `test_employee` VALUES ('ad123456', '张东奇', 20, '13967217344', 'manage', '0', '0', 'oxucR6UX8wJ0DH_Y6G4LPLBiCDJU', '0', 1, '123123');
INSERT INTO `test_employee` VALUES ('ad238292', '何宇堂', 25, '18972729937', 'manage', '0', '0', 'oxucR6UX8wJ0DH_Y6G4LPLBiCDJU', 'ad123456', -1, '123123');
INSERT INTO `test_employee` VALUES ('sv123456', '李联曦', 21, '13762572224', 'survey', '0', '0', 'oxucR6UX8wJ0DH_Y6G4LPLBiCDJU', 'ad123456', 1, '123123');
INSERT INTO `test_employee` VALUES ('vt123456', '孙哲华', 22, '13567392290', 'vet', '0', '0', NULL, '0', 1, NULL);
INSERT INTO `test_employee` VALUES ('vt658392', '郑钧元', 24, '13783920028', 'vet', '0', 'zjy.jpg', 'oBcaO5Hfd8p-x6I2KSWco23UsXuU', '0', 1, '123123');

-- ----------------------------
-- Table structure for test_lendlog
-- ----------------------------
DROP TABLE IF EXISTS `test_lendlog`;
CREATE TABLE `test_lendlog`  (
  `lendId` int NOT NULL AUTO_INCREMENT,
  `lendLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendDrivecardImg` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lendTime` timestamp NOT NULL,
  `lendDays` int NOT NULL,
  `lendMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manageLendTime` timestamp NULL DEFAULT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetLendTime` timestamp NULL DEFAULT NULL,
  `vetLendResult` tinyint NULL DEFAULT NULL,
  `vetLendResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `returnTime` timestamp NULL DEFAULT NULL,
  `returnImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归还照片的数组',
  `manageReturnTime` timestamp NULL DEFAULT NULL,
  `vetReturnTime` timestamp NULL DEFAULT NULL,
  `vetReturnResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `vetReturnResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL,
  `revokeTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`lendId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 119 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_lendlog
-- ----------------------------
INSERT INTO `test_lendlog` VALUES (112, '豫A123JK0', '张三', '19157702894', '1143095194_1632901362833.jpg;1256577390_1632901362836.jpg', '2021-07-29 15:28:32', 18, '服务外包', 'ad23829', '2021-09-29 15:28:33', '0', NULL, NULL, NULL, '2021-10-25 08:00:00', '2073423192_1635151605178.png', '2021-10-25 16:46:45', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_lendlog` VALUES (116, '豫A123JK0', '李丽芳', '13567332987', '26733497_1633862729679.jpeg', '2021-10-10 08:00:00', 1, 'dasdsdadasdsd', 'ad23829', '2021-10-10 18:45:30', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_lendlog` VALUES (117, '津HSB555', '赵丽华', '18753529973', '187366598_1634563042119.png', '2021-10-18 08:00:00', 1, 'dfds', 'ad123456', '2021-10-18 21:17:22', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_lendlog` VALUES (119, '津HSB555', '吴七', '13967217344', '952568713_1635150789404.png', '2021-10-25 08:00:00', 1, '？？？', 'ad123456', '2021-10-25 16:33:09', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for test_protectlog
-- ----------------------------
DROP TABLE IF EXISTS `test_protectlog`;
CREATE TABLE `test_protectlog`  (
  `protectId` int NOT NULL AUTO_INCREMENT,
  `protectLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `protectTime` timestamp NULL DEFAULT NULL,
  `protectMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manageTime` timestamp NOT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetTime` timestamp NULL DEFAULT NULL,
  `result` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `resultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL,
  `revokeTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`protectId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_protectlog
-- ----------------------------
INSERT INTO `test_protectlog` VALUES (13, '豫A123JK2', '2021-10-08 23:43:52', '保养测试', 'ad238292', '2021-10-01 23:43:53', '0', '2021-10-01 23:48:16', 1, '多多保养', 1, NULL);
INSERT INTO `test_protectlog` VALUES (26, '津HSB555', '2021-10-25 08:00:00', '车很旧了，想维修一下', 'ad123456', '2021-10-25 20:30:54', '0', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_protectlog` VALUES (28, '[object Object]', '2021-10-26 08:00:00', '缺油', 'ad238292', '2021-10-26 16:50:30', 'vt123456', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_protectlog` VALUES (29, '[object Object]', '2021-10-26 08:00:00', '近期较好', 'ad123456', '2021-10-26 19:57:48', 'vt123456', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for test_punishlog
-- ----------------------------
DROP TABLE IF EXISTS `test_punishlog`;
CREATE TABLE `test_punishlog`  (
  `punishId` int NOT NULL AUTO_INCREMENT,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accidentMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accidentTime` timestamp NOT NULL,
  `accidentPoint` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `punishMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `punishAmount` double NOT NULL,
  `payStatus` tinyint NOT NULL COMMENT '0表示未缴款，1表示已缴款',
  `license` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`punishId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_punishlog
-- ----------------------------
INSERT INTO `test_punishlog` VALUES (1, 'sv123456', '闯红灯', '2021-09-01 01:52:17', '杭州西湖', '罚款100元', 100, 1, '豪车');
INSERT INTO `test_punishlog` VALUES (2, 'sv123456', '闯红灯', '2021-10-13 13:59:08', '杭州西湖边', '罚款20元', 20, 0, '豪车');
INSERT INTO `test_punishlog` VALUES (3, 'sv123456', '加塞', '2021-10-16 14:02:28', '杭州下沙文泽路', '罚款10元', 10, 1, '豪车');

-- ----------------------------
-- Table structure for test_repairlog
-- ----------------------------
DROP TABLE IF EXISTS `test_repairlog`;
CREATE TABLE `test_repairlog`  (
  `repairId` int NOT NULL AUTO_INCREMENT,
  `repairLicense` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `repairTime` timestamp NOT NULL,
  `repairImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片数组',
  `repairMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `surveyNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `surveyTime` timestamp NULL DEFAULT NULL,
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manageTime` timestamp NULL DEFAULT NULL,
  `manageResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `manageResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vetTime` timestamp NULL DEFAULT NULL,
  `vetResult` tinyint NULL DEFAULT NULL COMMENT '0表示未通过，1表示通过',
  `vetResultMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effectStatus` tinyint NULL DEFAULT NULL,
  `revokeTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`repairId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_repairlog
-- ----------------------------
INSERT INTO `test_repairlog` VALUES (4, '豫A123JK5', '2021-09-25 17:30:43', '931739027_1632573635692.jpg;2146566414_1632573635701.jpg;410147384_1632573635705.ico', '车很旧了', 'sv123456', '2021-09-25 17:30:43', 'ad123456', '2021-10-25 16:38:25', 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_repairlog` VALUES (5, '豫A123JK6', '2021-10-02 09:54:30', '137540148_1633139801850.jpeg;123312610_1633139801857.jpg', '引擎盖不好了，维修一下', 'sv123456', '2021-09-02 09:54:30', 'ad123456', '2021-10-25 17:07:50', 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_repairlog` VALUES (6, '豫A123JK7', '2021-10-02 09:54:30', 'https://img.coolcr.cn/2021/10/28/8a61813460932.jpeg;133234042_1633139832915.jpg', '后备箱被撞了', 'sv123456', '2021-10-08 21:41:02', 'ad123456', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_repairlog` VALUES (41, '豫A123JK0', '2021-10-10 08:00:00', '1910765277_1633850489347.jpg', '车门坏了', 'sv123456', '2021-10-10 15:21:29', 'ad123456', '2021-10-25 20:17:29', 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `test_repairlog` VALUES (43, '豫B123JK2', '2021-10-25 08:00:00', '1396251929_1635165670901.png', '车窗坏了', 'sv123456', '2021-10-25 20:41:11', 'ad23829', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for test_vehicle
-- ----------------------------
DROP TABLE IF EXISTS `test_vehicle`;
CREATE TABLE `test_vehicle`  (
  `license` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车牌号',
  `status` tinyint NOT NULL COMMENT '-1表示废弃，1表示待用，0表示出行中，2表示维修中，3表示保养中，4表示出借中',
  `begin` timestamp NOT NULL COMMENT '添加车辆的时间',
  `end` timestamp NULL DEFAULT NULL COMMENT '废弃车辆的时间',
  `type` tinyint NOT NULL COMMENT '0表示自动挡，1表示手动挡',
  `peopleNum` tinyint NOT NULL COMMENT '车辆的最大载人数',
  `emission` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车辆的排量，需写明单位',
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车辆归属的管理员工号',
  `img` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆的照片',
  `price` double NOT NULL,
  `lastProtectTime` timestamp NOT NULL,
  `endMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`license`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_vehicle
-- ----------------------------
INSERT INTO `test_vehicle` VALUES ('津HSB555', 1, '2021-09-25 16:45:26', NULL, 1, 5, '2.0L', 'ad123456', '1048272003_1632559525793.jpeg', 120000, '2021-09-25 16:45:26', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK0', 1, '2021-09-22 20:42:25', NULL, 6, 6, '6', 'ad123456', '1406286107_1632290825115.jpg', 6, '2021-09-22 20:42:25', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK1', 2, '2021-09-22 20:51:57', NULL, 1, 1, '1', 'ad123456', '', 1, '2021-09-22 20:51:57', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK2', 2, '2021-09-22 20:49:54', NULL, 1, 1, '1', 'ad123456', '', 1, '2021-09-22 20:49:54', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK3', 3, '2021-09-22 20:48:17', NULL, 1, 1, '1', 'ad123456', '', 1, '2021-09-22 20:48:17', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK4', 0, '2021-09-22 20:19:58', NULL, 22, 1, '11', 'ad123456', '', 22, '2021-09-22 20:19:58', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK5', 0, '2021-09-22 14:07:06', NULL, 123, 123, '1.2L', 'ad123456', '1406286107_1632290825115.jpg', 123, '2021-09-22 14:07:06', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK6', 2, '2021-09-22 14:48:34', NULL, 2, 22, '2', 'ad123456', '1088996788_1632293313305.jpg', 2, '2021-09-22 14:48:34', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK7', 2, '2021-09-22 20:55:09', NULL, 2, 2, '2', 'ad123456', '', 2, '2021-09-22 20:55:09', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK8', 3, '2021-09-22 20:45:20', NULL, 4, 5, '5', 'ad123456', '', 5, '2021-09-22 20:45:20', NULL);
INSERT INTO `test_vehicle` VALUES ('豫A123JK9', 1, '2021-09-22 20:33:11', NULL, 1, 65, '6', 'ad123456', '', 1, '2021-09-22 20:33:11', NULL);
INSERT INTO `test_vehicle` VALUES ('豫B123JK0', 0, '2021-10-24 14:45:04', NULL, 0, 0, '', 'ad238292', NULL, 0, '2021-10-24 15:34:22', NULL);
INSERT INTO `test_vehicle` VALUES ('豫B123JK1', 3, '2021-08-23 13:28:18', NULL, 0, 0, '0', 'ad123456', '1048272003_1632559525793.jpeg', 98888, '2021-08-23 13:28:18', NULL);
INSERT INTO `test_vehicle` VALUES ('豫B123JK2', 1, '2021-07-24 14:45:04', NULL, 0, 0, '', 'ad238292', NULL, 222, '2021-07-24 14:45:04', NULL);
INSERT INTO `test_vehicle` VALUES ('豫B123JKL', -1, '2021-07-24 14:45:04', '2021-07-24 14:46:05', 0, 50, '1.2L', 'ad123456', '1406286107_1632290825115.jpg', 30000, '2021-07-24 14:45:04', '损坏');

-- ----------------------------
-- Table structure for vehicle
-- ----------------------------
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE `vehicle`  (
  `license` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT NULL COMMENT '1表示待用，0表示出行中，2表示维修中，3表示保养中，4表示出借中',
  `begin` timestamp NULL DEFAULT NULL,
  `type` tinyint NULL DEFAULT NULL COMMENT '0表示汽油，1表示电动',
  `peopleNum` tinyint NULL DEFAULT NULL,
  `emission` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '带单位',
  `manageNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `lastProtectTime` timestamp NULL DEFAULT NULL,
  `end` timestamp NULL DEFAULT NULL,
  `endMsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vehicle
-- ----------------------------
INSERT INTO `vehicle` VALUES ('鲁NE23A2', 1, NULL, 0, 5, '2.0T', NULL, NULL, 106000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('苏E82L84', 1, NULL, 0, 5, '1.5L', NULL, NULL, 51900, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('京N8P8F8', 1, NULL, 0, 6, '2.5L', NULL, NULL, 187000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('沪KR9888', 1, NULL, 1, 4, '1.2L', NULL, NULL, 52000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙FQT976', 1, NULL, 0, 5, '1.6L', NULL, NULL, 81700, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('苏EM86B8', 1, NULL, 0, 5, '1.4L', NULL, NULL, 110800, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('豫A6A6DF', 1, NULL, 0, 6, '2.2T', NULL, NULL, 201800, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('湘ABN597', 1, NULL, 0, 6, '2.0T', NULL, NULL, 227800, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('晋HBJ731', 1, NULL, 1, 4, '1.1L', NULL, NULL, 94200, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙CS8417', 1, NULL, 0, 6, '2.4L', NULL, NULL, 205000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('川C2JF99', 1, NULL, 0, 6, '2.3T', NULL, NULL, 259000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('渝B8V359', 1, NULL, 1, 5, '1.8L', NULL, NULL, 78000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('沪X7W470', 1, NULL, 1, 5, '2.0L', NULL, NULL, 99000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙F6T083', 1, NULL, 0, 5, '1.7L', NULL, NULL, 65200, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙A6FK29', 1, NULL, 0, 4, '1.7L', NULL, NULL, 48900, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙BM6469', 1, NULL, 0, 5, '2.2L', NULL, NULL, 89600, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙A3FR59', 1, NULL, 0, 5, '2.0T', NULL, NULL, 135400, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙AG9678', 1, NULL, 1, 5, '2.1T', NULL, NULL, 140000, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙A7B280', 1, NULL, 0, 6, '2.1T', NULL, NULL, 277800, NULL, NULL, NULL);
INSERT INTO `vehicle` VALUES ('浙CQJ906', 1, NULL, 0, 6, '2.5L', NULL, NULL, 216500, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
