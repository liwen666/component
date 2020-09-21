package com.temp.common.mail;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
public class SendmailUtil {    
        public static void main(String[] args) throws AddressException,            MessagingException, IOException {       
  Properties properties = new Properties();
//  properties.put("mail.transport.protocol", "smtp");// 连接协议        
//  properties.put("mail.smtp.host", "smtp.qq.com");// 主机名        
//  properties.put("mail.smtp.port", "587");// 端口号        
//  properties.put("mail.smtp.auth", "true");        
//  properties.put("mail.smtp.ssl.enable", "true");//设置是否使用ssl安全连接  ---一般都使用        
//  properties.put("mail.debug", "true");//设置是否显示debug信息  true 会在控制台显示相关信息    
        	
        	InputStream  in = new FileInputStream("D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\mail\\email.properties");
        	properties.load(in);
        	
//得到回话对象        
Session session = Session.getInstance(properties);        
// 获取邮件对象        
Message message = new MimeMessage(session);        
//设置发件人邮箱地址       
 message.setFrom(new InternetAddress("1438131288@qq.com"));       
 //设置收件人地址        
 
 message.setRecipients(                RecipientType.TO,                new InternetAddress[] { new InternetAddress("534093268@qq.com") });       
 //设置邮件标题        
message.setSubject("这是第一封Java邮件");        
//设置邮件内容        
message.setText("内容为： JRXJava发送来的邮件。");
 //得到邮差对象        
Transport transport = session.getTransport();        
//连接自己的邮箱账户        
transport.connect("1438131288@qq.com", "orocyfwemhjvgdea");//密码为刚才得到的授权码
//transport.connect("smtp.qq.com",465, "1438131288@qq.com",  "orocyfwemhjvgdea");
//发送邮件        
transport.sendMessage(message, message.getAllRecipients());    
}
 }