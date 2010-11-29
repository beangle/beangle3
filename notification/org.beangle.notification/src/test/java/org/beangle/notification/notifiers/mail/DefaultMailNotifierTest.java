/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.testng.annotations.Test;

//$Id:DefaultMailNotifierTest.java Mar 22, 2009 1:24:04 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultMailNotifierTest {
	private boolean online = false;

	@Test
	public void testGmail() throws Exception {
		JavaMailSenderImpl mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("eams.demon");
		mailSender.setPassword("eamsadmin");
		mailSender.setPort(465);
		mailSender.setProtocol("smtp");
		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
		mailSender.getJavaMailProperties().put("mail.smtp.port", new Integer(465));
		mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
		mailSender.getJavaMailProperties().put("mail.smtp.socketFactory.port", new Integer(465));
		mailSender.getJavaMailProperties().put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		mailSender.getJavaMailProperties().put("mail.smtp.socketFactory.fallback", "false");

		MailMessage mmc = new MailMessage("eams.demon@gmail.com", "测试", "测试简单邮件发送机制");
		DefaultMailNotifier mailNotifier = new DefaultMailNotifier();
		mailNotifier.setJavaMailSender(mailSender);
		mailNotifier.setFromMailbox("eams.demon@gmail.com");
		mailNotifier.setFromName("测试name");
		if (online) mailNotifier.sendMessage(mmc);
	}

	@Test
	public void testSimple() throws Exception {
		JavaMailSenderImpl mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
		mailSender.setHost("mail.shufe.edu.cn");
		mailSender.setUsername("infocms");
		mailSender.setPassword("shufejw");
		mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");

		MailMessage mmc = new MailMessage("infocms@mail.shufe.edu.cn", "测试", "测试简单邮件发送机制");
		DefaultMailNotifier mailNotifier = new DefaultMailNotifier();
		mailNotifier.setJavaMailSender(mailSender);
		mailNotifier.setFromMailbox("infocms@mail.shufe.edu.cn");
		mailNotifier.setFromName("测试name");
		if (online) mailNotifier.sendMessage(mmc);
	}
}
