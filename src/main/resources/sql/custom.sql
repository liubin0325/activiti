/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.17-log : Database - activiti_demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`activiti_demo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `activiti_demo`;

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

/*Data for the table `act_custom_form` */

insert  into `act_custom_form`(`form_id`,`form_name`,`form_type`,`order_id`,`content`,`remark`,`operator`,`operatedate`) values ('b124c01e75594ce9975aa5ee5440582c','测试表单','0',1,'<table align=\"center\" data-sort=\"sortDisabled\"><tbody><tr class=\"firstRow\"><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">管理单位</td><td width=\"215\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px; -ms-word-break: break-all;\" rowspan=\"1\" colspan=\"3\"><input name=\"dept_showname\" id=\"dept_showname\" type=\"text\" size=\"87\" maxlength=\"100\" value=\"{单行文本：管理单位}\" param_namechn=\"管理单位\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">热力站</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"local_stat1\" id=\"local_stat1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：热力站}\" param_namechn=\"热力站\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">小区</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"local_xq1\" id=\"local_xq1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：小区}\" param_namechn=\"小区\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">大楼</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"lh1\" id=\"lh1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：大楼}\" param_namechn=\"大楼\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">用户地址</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"address1\" id=\"address1\" type=\"text\" size=\"20\" maxlength=\"100\" value=\"{单行文本：用户地址}\" param_namechn=\"用户地址\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">收费性质</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"hotwise\" id=\"hotwise\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：收费性质}\" param_namechn=\"收费性质\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">销户状态</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"isdest\" id=\"isdest\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：销户状态}\" param_namechn=\"销户状态\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">当季欠费</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"isdjqf1\" id=\"isdjqf1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：当季欠费}\" param_namechn=\"当季欠费\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">历史欠费</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"islsqf\" id=\"islsqf\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：历史欠费}\" param_namechn=\"历史欠费\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">发送人</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"userid\" id=\"userid\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：发送人}\" param_namechn=\"发送人\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">截止日期</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"operatedate\" id=\"operatedate\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：发送截止日期}\" param_namechn=\"发送截止日期\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border: 0px currentColor; border-image: none; color: rgb(133, 133, 136); position: relative;\"><span style=\"top: 32px; width: 85px; height: 26px; line-height: 26px; display: block; position: absolute; background-color: rgb(240, 240, 243);\">用户编号</span>\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td width=\"708\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\" rowspan=\"1\" colspan=\"3\"><textarea name=\"housecode\" id=\"housecode\" rows=\"6\" cols=\"91\" param_namechn=\"用户编号\" datadefault=\"\" myplugins=\"textarea\">{多行文本：用户编号}</textarea></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border: 0px currentColor; border-image: none; color: rgb(133, 133, 136); position: relative;\"><span style=\"top: 60px; width: 85px; height: 26px; line-height: 26px; display: block; position: absolute; background-color: rgb(240, 240, 243);\">短信内容</span>\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td width=\"708\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px; -ms-word-break: break-all;\" rowspan=\"1\" colspan=\"3\"><textarea name=\"smsremark\" id=\"smsremark\" rows=\"10\" cols=\"91\" param_namechn=\"短信内容\" datadefault=\"\" myplugins=\"textarea\">{多行文本：短信内容}</textarea></td></tr></tbody></table>',NULL,NULL,NULL);

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

/*Data for the table `act_custom_form_data` */

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

/*Data for the table `act_custom_form_field` */

insert  into `act_custom_form_field`(`field_id`,`form_id`,`param_name`,`param_namechn`,`datadefault`,`datatype`,`text_size`,`text_rows`,`datavalue`,`content`,`myplugins`,`order_id`,`islist`) values ('053130b8349e979f32b1220a77f46070','b124c01e75594ce9975aa5ee5440582c','dept_showname','管理单位','','text','87','100','','<input name=\"dept_showname\" id=\"dept_showname\" type=\"text\" size=\"87\" maxlength=\"100\" value=\"{单行文本：管理单位}\" param_namechn=\"管理单位\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,0,'\0'),('13c4e343596b462a2ca8f24ad0e58592','b124c01e75594ce9975aa5ee5440582c','lh1','大楼','','text','20','20','','<input name=\"lh1\" id=\"lh1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：大楼}\" param_namechn=\"大楼\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,3,'\0'),('37bde41272bcf281a11c803d062cfc27','b124c01e75594ce9975aa5ee5440582c','operatedate','发送截止日期','','text','20','20','','<input name=\"operatedate\" id=\"operatedate\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：发送截止日期}\" param_namechn=\"发送截止日期\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,10,'\0'),('422938f8e803604a26533146b02cadaa','b124c01e75594ce9975aa5ee5440582c','userid','发送人','','text','20','20','','<input name=\"userid\" id=\"userid\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：发送人}\" param_namechn=\"发送人\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,9,'\0'),('4366c4bb3ea6d070809bba898954dd08','b124c01e75594ce9975aa5ee5440582c','hotwise','收费性质','','text','20','20','','<input name=\"hotwise\" id=\"hotwise\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：收费性质}\" param_namechn=\"收费性质\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,5,'\0'),('5100020a530569407d32372a0239007c','b124c01e75594ce9975aa5ee5440582c','local_stat1','热力站','','text','20','20','','<input name=\"local_stat1\" id=\"local_stat1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：热力站}\" param_namechn=\"热力站\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,1,'\0'),('79da965f5428d711eb76e9b98e8c3ca5','b124c01e75594ce9975aa5ee5440582c','address1','用户地址','','text','20','100','','<input name=\"address1\" id=\"address1\" type=\"text\" size=\"20\" maxlength=\"100\" value=\"{单行文本：用户地址}\" param_namechn=\"用户地址\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,4,'\0'),('806e5d63729b8a659685c6b0279b65dc','b124c01e75594ce9975aa5ee5440582c','isdest','销户状态','','text','20','20','','<input name=\"isdest\" id=\"isdest\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：销户状态}\" param_namechn=\"销户状态\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,6,'\0'),('80899a293cd957b8f779ace9d578da44','b124c01e75594ce9975aa5ee5440582c','local_xq1','小区','','text','20','20','','<input name=\"local_xq1\" id=\"local_xq1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：小区}\" param_namechn=\"小区\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,2,'\0'),('9450e9c48651a8f33ac79d80a24831ed','b124c01e75594ce9975aa5ee5440582c','housecode','用户编号','','','91','6','','<textarea name=\"housecode\" id=\"housecode\" rows=\"6\" cols=\"91\" param_namechn=\"用户编号\" datadefault=\"\" myplugins=\"textarea\">{多行文本：用户编号}</textarea>',NULL,11,'\0'),('c86e6c4859de62e35ea5a666d1efa4c4','b124c01e75594ce9975aa5ee5440582c','isdjqf1','当季欠费','','text','20','20','','<input name=\"isdjqf1\" id=\"isdjqf1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：当季欠费}\" param_namechn=\"当季欠费\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,7,'\0'),('cd4ec9ad3d8fceea6024c288507dbab3','b124c01e75594ce9975aa5ee5440582c','smsremark','短信内容','','','91','10','','<textarea name=\"smsremark\" id=\"smsremark\" rows=\"10\" cols=\"91\" param_namechn=\"短信内容\" datadefault=\"\" myplugins=\"textarea\">{多行文本：短信内容}</textarea>',NULL,12,'\0'),('e32d5cec2221d2c8c9b64e1794b2ed25','b124c01e75594ce9975aa5ee5440582c','islsqf','历史欠费','','text','20','20','','<input name=\"islsqf\" id=\"islsqf\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：历史欠费}\" param_namechn=\"历史欠费\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/>',NULL,8,'\0');

/*Table structure for table `act_custom_model_form` */

DROP TABLE IF EXISTS `act_custom_model_form`;

CREATE TABLE `act_custom_model_form` (
  `model_id` varchar(64) NOT NULL COMMENT '流程模型ID',
  `form_id` varchar(32) NOT NULL COMMENT '表单ID',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `act_custom_model_form` */

insert  into `act_custom_model_form`(`model_id`,`form_id`) values ('1','b124c01e75594ce9975aa5ee5440582c');

/*Table structure for table `act_custom_node_form` */

DROP TABLE IF EXISTS `act_custom_node_form`;

CREATE TABLE `act_custom_node_form` (
  `field_id` varchar(32) NOT NULL COMMENT '字段ID',
  `model_id` varchar(32) NOT NULL COMMENT '模型ID',
  `node_key` varchar(32) NOT NULL COMMENT '节点KEy',
  `isvisible` bit(1) NOT NULL DEFAULT b'0',
  `isedit` bit(1) NOT NULL DEFAULT b'0',
  `isnotnull` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`field_id`,`model_id`,`node_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `act_custom_node_form` */

insert  into `act_custom_node_form`(`field_id`,`model_id`,`node_key`,`isvisible`,`isedit`,`isnotnull`) values ('053130b8349e979f32b1220a77f46070','1','hx_start','','','\0'),('13c4e343596b462a2ca8f24ad0e58592','1','hx_start','','','\0'),('37bde41272bcf281a11c803d062cfc27','1','hx_start','','','\0'),('422938f8e803604a26533146b02cadaa','1','hx_start','','','\0'),('4366c4bb3ea6d070809bba898954dd08','1','hx_start','','','\0'),('5100020a530569407d32372a0239007c','1','hx_start','','','\0'),('79da965f5428d711eb76e9b98e8c3ca5','1','hx_start','','','\0'),('806e5d63729b8a659685c6b0279b65dc','1','hx_start','','','\0'),('80899a293cd957b8f779ace9d578da44','1','hx_start','','','\0'),('9450e9c48651a8f33ac79d80a24831ed','1','hx_start','','','\0'),('c86e6c4859de62e35ea5a666d1efa4c4','1','hx_start','','','\0'),('cd4ec9ad3d8fceea6024c288507dbab3','1','hx_start','','','\0'),('e32d5cec2221d2c8c9b64e1794b2ed25','1','hx_start','','','\0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
