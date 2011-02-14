/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import static org.testng.Assert.assertEquals;

import javax.mail.internet.MimeMessage;

import org.beangle.notification.notifiers.mail.AbstractMailNotifier;
import org.beangle.notification.notifiers.mail.DefaultMailNotifier;
import org.beangle.notification.notifiers.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

@Test
public class DefaultNotifictionTaskTest {

	DefaultNotificationTask task = new DefaultNotificationTask();
	GreenMail greenMail;

	@BeforeClass
	public void setUp() {
		greenMail = new GreenMail(ServerSetupTest.ALL);
		// uses test ports by default
		greenMail.start();
		greenMail.setUser("test1@localhost", "user1", "password");
		greenMail.setUser("test2@localhost", "user2", "password");
	}

	@AfterClass
	public void tearDown() {
		greenMail.stop();
	}

	public void testMail() {
		JavaMailSenderImpl mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
		mailSender.setHost("localhost");
		mailSender.setUsername("user1");
		mailSender.setPassword("password");
		mailSender.setPort(3025);

		AbstractMailNotifier notifier = new DefaultMailNotifier(mailSender);
		notifier.setFromMailbox("user1@localhost");
		notifier.setFromName("测试name");
		task.setNotifier(notifier);
		MailMessage mmc = new MailMessage("user2@localhost", "测试", "测试简单邮件发送机制");
		task.getMessageQueue().addMessage(mmc);
		task.send();
		MimeMessage[] msgs = greenMail.getReceivedMessages();
		assertEquals(1, msgs.length);
	}
}
