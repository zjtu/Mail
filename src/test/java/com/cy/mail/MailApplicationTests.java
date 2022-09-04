package com.cy.mail;

import com.cy.mail.entity.User;
import com.cy.mail.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.StringWriter;

@SpringBootTest
class MailApplicationTests {

    @Autowired
    MailService mailService;

    @Test
    void contextLoads() {
    }

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("1064432866@qq.com", "2655342589@qq.com", "1064432866@qq.com", "邮件主题", "邮件内容");
    }


    @Test
    public void sendAttachFileMail() {
        mailService.sendAttachFileMail("1064432866@qq.com", "2655342589@qq.com", "测试邮件主题", "测试邮件内容", new File(""));
    }

    @Test
    public void sendMailWithImg() {
        mailService.sendMailWithImg("1064432866@qq.com", "2655342589@qq.com", "邮件主题(图片)", "<div>hello,这是一封带图片资源的邮件:" + "图片1:<div><img src='cid:p01/></div>" + "图片2:<div><img src='cid:p02/></div>" + "</div>", new String[]{"C:\\Users\\10644\\Downloads\\p1.jpg", "C:\\Users\\10644\\Downloads\\p2.jpg"}, new String[]{"p01", "p02"});
    }


    @Test
    public void sendHtmlMail() {
        try {
            Configuration configuration = new Configuration(Configuration.getVersion());
            ClassLoader loader = MailApplication.class.getClassLoader();
            configuration.setClassLoaderForTemplateLoading(loader, "ftl");
            Template template = configuration.getTemplate("mailtemplate.ftl");
            StringWriter mail = new StringWriter();
            User user = new User();
            user.setUsername("江南没有雨");
            user.setGender("男");
            template.process(user, mail);
            mailService.sendHtmlMail("1064432866@qq.com", "2655342589@qq.com", "测试邮件主题", mail.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
