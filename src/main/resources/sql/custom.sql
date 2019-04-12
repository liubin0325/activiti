/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.17-log : Database - activiti
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `act_custom_form` */

DROP TABLE IF EXISTS `act_custom_form`;

CREATE TABLE `act_custom_form` (
  `form_id` varchar(32) NOT NULL COMMENT '表单ID',
  `form_name` varchar(100) NOT NULL COMMENT '表单名称',
  `form_type` varchar(32) DEFAULT NULL COMMENT '表单类型',
  `order_id` smallint(6) NOT NULL DEFAULT '0' COMMENT '排序',
  `content` text COMMENT '表单HTML',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人',
  `operatedate` date DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_form_data` */

DROP TABLE IF EXISTS `act_custom_form_data`;

CREATE TABLE `act_custom_form_data` (
  `procinst_id` varchar(64) NOT NULL COMMENT '流程实例ID',
  `business_key` varchar(64) NOT NULL COMMENT '流程Key',
  `form_id` varchar(32) NOT NULL COMMENT '表单ID',
  `data` text NOT NULL COMMENT '表单数据',
  `data_extra` text COMMENT '扩展数据',
  `list_count` int(11) NOT NULL DEFAULT '0' COMMENT '列表数据长度',
  PRIMARY KEY (`procinst_id`,`business_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_form_field` */

DROP TABLE IF EXISTS `act_custom_form_field`;

CREATE TABLE `act_custom_form_field` (
  `field_id` varchar(32) NOT NULL COMMENT '字段ID id+name md5',
  `form_id` varchar(32) NOT NULL COMMENT '表单ID',
  `param_name` varchar(64) NOT NULL,
  `param_namechn` varchar(64) DEFAULT NULL,
  `datadefault` varchar(64) DEFAULT NULL,
  `datatype` varchar(32) DEFAULT NULL,
  `text_size` varchar(4000) DEFAULT NULL,
  `text_rows` varchar(4000) DEFAULT NULL,
  `datavalue` varchar(4000) DEFAULT NULL,
  `content` text,
  `myplugins` varchar(20) DEFAULT NULL,
  `order_id` int(12) NOT NULL DEFAULT '0',
  `islist` bit(1) NOT NULL,
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_form_list_data` */

DROP TABLE IF EXISTS `act_custom_form_list_data`;

CREATE TABLE `act_custom_form_list_data` (
  `procinst_id` varchar(64) NOT NULL COMMENT '流程实例ID',
  `business_key` varchar(64) NOT NULL COMMENT '流程key',
  `list_order` int(11) DEFAULT NULL COMMENT '列表数据排序',
  `list_data` text COMMENT '列表行数据',
  PRIMARY KEY (`procinst_id`,`business_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_form_node` */

DROP TABLE IF EXISTS `act_custom_form_node`;

CREATE TABLE `act_custom_form_node` (
  `field_id` varchar(32) NOT NULL COMMENT '字段ID',
  `model_id` varchar(64) NOT NULL COMMENT '模型ID',
  `node_key` varchar(32) NOT NULL COMMENT '节点KEy',
  `isvisible` bit(1) NOT NULL DEFAULT b'0',
  `isedit` bit(1) NOT NULL DEFAULT b'0',
  `isnotnull` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`field_id`,`model_id`,`node_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_model_deployment` */

DROP TABLE IF EXISTS `act_custom_model_deployment`;

CREATE TABLE `act_custom_model_deployment` (
  `deployment_id` varchar(64) NOT NULL,
  `model_id` varchar(64) NOT NULL,
  PRIMARY KEY (`deployment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_model_extra` */

DROP TABLE IF EXISTS `act_custom_model_extra`;

CREATE TABLE `act_custom_model_extra` (
  `model_id` varchar(64) NOT NULL,
  `form_id` varchar(32) NOT NULL,
  `callback` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_procinst_extra` */

DROP TABLE IF EXISTS `act_custom_procinst_extra`;

CREATE TABLE `act_custom_procinst_extra` (
  `procinst_id` varchar(64) NOT NULL COMMENT '流程实例ID',
  `business_key` varchar(64) NOT NULL COMMENT '流程key',
  `model_id` varchar(64) DEFAULT NULL COMMENT '模板ID',
  `inst_key` varchar(256) DEFAULT NULL COMMENT '实例关键字（内部） 用于判断业务是否重复提交，空则不判断',
  `keyvalue` varchar(256) DEFAULT NULL COMMENT '流程的关键字数据 用户可见的，可查询的',
  `proc_content` varchar(4000) DEFAULT NULL COMMENT '流程关键信息，用户显示给用户的',
  `apply_user` varchar(64) DEFAULT NULL COMMENT '流程提交人',
  `apply_time` datetime DEFAULT NULL COMMENT '流程提交时间',
  `pre_task_user` varchar(64) DEFAULT NULL COMMENT '上一个办理人',
  `pre_task_time` datetime DEFAULT NULL COMMENT '上一个办理时间',
  `dept_id` varchar(64) DEFAULT NULL COMMENT '用户所在部门ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '0未完成；1已完成；2已终止；3未完成，已有人接收；5已完成，后作废；6已完成，已发送其他接口系统；7已完成，发送其他接口系统失败',
  PRIMARY KEY (`procinst_id`,`business_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
