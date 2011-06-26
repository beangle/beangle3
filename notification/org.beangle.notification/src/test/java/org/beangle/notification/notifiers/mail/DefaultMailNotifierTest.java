/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.notifiers.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.testng.annotations.Test;

@Test
public class DefaultMailNotifierTest {
	private boolean online = false;

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

		MailMessage mmc = new MailMessage("测试", "测试简单邮件发送机制", "eams.demon@gmail.com","gjloverfly@163.com","chaostone.duan@gmail.com");
		DefaultMailNotifier<MailMessage> mailNotifier = new DefaultMailNotifier<MailMessage>();
		mailNotifier.setMailSender(mailSender);
		mailNotifier.setFrom("测试name<eams.demon@gmail.com>");
		if (online) mailNotifier.deliver(mmc);
	}

	public void testSimple() throws Exception {
		JavaMailSenderImpl mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
		mailSender.setHost("mail.shufe.edu.cn");
		mailSender.setUsername("infocms");
		mailSender.setPassword("shufejw");
		mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");

		MailMessage mmc = new MailMessage("测试", "测试简单邮件发送机制", "infocms@mail.shufe.edu.cn");
		DefaultMailNotifier<MailMessage> mailNotifier = new DefaultMailNotifier<MailMessage>();
		mailNotifier.setMailSender(mailSender);
		mailNotifier.setFrom("<测试name>infocms@mail.shufe.edu.cn");
		if (online) mailNotifier.deliver(mmc);
	}

}
