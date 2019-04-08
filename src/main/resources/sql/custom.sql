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
  `procdef_id` varchar(64) NOT NULL COMMENT '流程定义ID',
  `business_key` varchar(64) NOT NULL COMMENT '流程Key',
  `form_id` varchar(32) NOT NULL COMMENT '表单ID',
  `data` text NOT NULL COMMENT '表单数据',
  `list_data` text COMMENT '列表数据',
  `data_extra` text COMMENT '扩展数据',
  PRIMARY KEY (`procdef_id`,`business_key`)
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

/*Table structure for table `act_custom_model_callback` */

DROP TABLE IF EXISTS `act_custom_model_callback`;

CREATE TABLE `act_custom_model_callback` (
  `model_id` varchar(64) NOT NULL,
  `callback` varchar(128) NOT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_model_deployment` */

DROP TABLE IF EXISTS `act_custom_model_deployment`;

CREATE TABLE `act_custom_model_deployment` (
  `deployment_id` varchar(64) NOT NULL,
  `model_id` varchar(64) NOT NULL,
  PRIMARY KEY (`deployment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_model_form` */

DROP TABLE IF EXISTS `act_custom_model_form`;

CREATE TABLE `act_custom_model_form` (
  `model_id` varchar(64) NOT NULL COMMENT '流程模型ID',
  `form_id` varchar(32) NOT NULL COMMENT '表单ID',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `act_custom_node_form` */

DROP TABLE IF EXISTS `act_custom_node_form`;

CREATE TABLE `act_custom_node_form` (
  `field_id` varchar(32) NOT NULL COMMENT '字段ID',
  `model_id` varchar(64) NOT NULL COMMENT '模型ID',
  `node_key` varchar(32) NOT NULL COMMENT '节点KEy',
  `isvisible` bit(1) NOT NULL DEFAULT b'0',
  `isedit` bit(1) NOT NULL DEFAULT b'0',
  `isnotnull` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`field_id`,`model_id`,`node_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
