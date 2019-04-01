package com.hx.activiti.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestMain {
    public static void main(String[] args){
        String html = "<table align=\"center\" data-sort=\"sortDisabled\"><tbody><tr class=\"firstRow\"><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">管理单位</td><td width=\"215\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px; -ms-word-break: break-all;\" rowspan=\"1\" colspan=\"3\"><input name=\"dept_showname\" id=\"dept_showname\" type=\"text\" size=\"87\" maxlength=\"100\" value=\"{单行文本：管理单位}\" param_namechn=\"管理单位\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">热力站</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"local_stat1\" id=\"local_stat1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：热力站}\" param_namechn=\"热力站\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">小区</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"local_xq1\" id=\"local_xq1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：小区}\" param_namechn=\"小区\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">大楼</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"lh1\" id=\"lh1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：大楼}\" param_namechn=\"大楼\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">用户地址</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"address1\" id=\"address1\" type=\"text\" size=\"20\" maxlength=\"100\" value=\"{单行文本：用户地址}\" param_namechn=\"用户地址\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">收费性质</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"hotwise\" id=\"hotwise\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：收费性质}\" param_namechn=\"收费性质\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">销户状态</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"isdest\" id=\"isdest\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：销户状态}\" param_namechn=\"销户状态\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">当季欠费</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"isdjqf1\" id=\"isdjqf1\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：当季欠费}\" param_namechn=\"当季欠费\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">历史欠费</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"islsqf\" id=\"islsqf\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：历史欠费}\" param_namechn=\"历史欠费\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">发送人</td><td width=\"80\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"userid\" id=\"userid\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：发送人}\" param_namechn=\"发送人\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border-width: 0px 0px 2px; border-style: none none solid; border-color: currentColor currentColor rgb(255, 255, 255); border-image: none; color: rgb(133, 133, 136); -ms-word-break: break-all; background-color: rgb(240, 240, 243);\">截止日期</td><td width=\"100\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\"><input name=\"operatedate\" id=\"operatedate\" type=\"text\" size=\"20\" maxlength=\"20\" value=\"{单行文本：发送截止日期}\" param_namechn=\"发送截止日期\" datadefault=\"\" datatype=\"text\" myplugins=\"text\"/></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border: 0px currentColor; border-image: none; color: rgb(133, 133, 136); position: relative;\"><span style=\"top: 32px; width: 85px; height: 26px; line-height: 26px; display: block; position: absolute; background-color: rgb(240, 240, 243);\">用户编号</span>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td width=\"708\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px;\" rowspan=\"1\" colspan=\"3\"><textarea name=\"housecode\" id=\"housecode\" rows=\"6\" cols=\"91\" param_namechn=\"用户编号\" datadefault=\"\" myplugins=\"textarea\">{多行文本：用户编号}</textarea></td></tr><tr><td width=\"70\" align=\"center\" valign=\"middle\" style=\"border: 0px currentColor; border-image: none; color: rgb(133, 133, 136); position: relative;\"><span style=\"top: 60px; width: 85px; height: 26px; line-height: 26px; display: block; position: absolute; background-color: rgb(240, 240, 243);\">短信内容</span>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td width=\"708\" valign=\"top\" style=\"border: 0px currentColor; border-image: none; padding-left: 3px; -ms-word-break: break-all;\" rowspan=\"1\" colspan=\"3\"><textarea name=\"smsremark\" id=\"smsremark\" rows=\"10\" cols=\"91\" param_namechn=\"短信内容\" datadefault=\"\" myplugins=\"textarea\">{多行文本：短信内容}</textarea></td></tr></tbody></table>";

        Document doc = Jsoup.parse(html);

//        Element element = doc.getElementById("sqdw");
//        element.attr("value","test");

        System.out.println(doc.html());
    }
}
