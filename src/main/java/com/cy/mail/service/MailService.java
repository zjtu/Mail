package com.cy.mail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 发送简单邮件
     * @param from 发送者
     * @param to 收件人
     * @param cc 抄送人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String from,String to,String cc,String subject,String content){
        SimpleMailMessage simpMsg=new SimpleMailMessage();
        simpMsg.setFrom(from);
        simpMsg.setTo(to);
        simpMsg.setCc(cc);
        simpMsg.setSubject(subject);
        simpMsg.setText(content);
        javaMailSender.send(simpMsg);
    }

    /**
     * 发送带附件的邮件
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param file
     */
    public void sendAttachFileMail(String from, String to, String subject, String content, File file){
        try {
            MimeMessage message= javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(),file);
            javaMailSender.send(message);

        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    /**
     * 发送带图片资源的邮件
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param srcPath
     * @param resIds
     */
    public void sendMailWithImg(String from,String to,String subject,String content,String[] srcPath,String[] resIds){
        if (srcPath.length!=resIds.length){
            System.out.println("发送失败");
            return;
        }
        try {
            MimeMessage message= javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            for (int i = 0; i < srcPath.length; i++) {
                FileSystemResource res=new FileSystemResource(new File(srcPath[i]));
                helper.addInline(resIds[i],res);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }
    }


    public void sendHtmlMail(String from,String to,String subject,String content){
        try{
            MimeMessage message= javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            javaMailSender.send(message);
        }catch (MessagingException e) {
            System.out.println("发送失败");
        }
    }






}
