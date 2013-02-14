/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.notification.mail;

import org.testng.annotations.Test;

@Test
public class DefaultMailNotifierTest {
  private boolean online = false;

  public void testGmail() throws Exception {
    JavaMailSender mailSender = new JavaMailSender();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setUsername("eams.demon");
    mailSender.setPassword("xxxxxs");
    mailSender.setPort(465);
    mailSender.setProtocol("smtp");
    mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
    mailSender.getJavaMailProperties().put("mail.smtp.port", new Integer(465));
    mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
    mailSender.getJavaMailProperties().put("mail.smtp.socketFactory.port", new Integer(465));
    mailSender.getJavaMailProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    mailSender.getJavaMailProperties().put("mail.smtp.socketFactory.fallback", "false");

    MailMessage mmc = new MailMessage("测试", "测试简单邮件发送机制", "duantihua@163.com");
    DefaultMailNotifier<MailMessage> mailNotifier = new DefaultMailNotifier<MailMessage>(mailSender);
    mailNotifier.setFrom("段体华<duantihua@gmail.com>");
    if (online) mailNotifier.deliver(mmc);
  }

  public void testSimple() throws Exception {
    JavaMailSender mailSender = new JavaMailSender();
    mailSender.setHost("mail.shufe.edu.cn");
    mailSender.setUsername("infocms");
    mailSender.setPassword("xxxx");
    mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");

    MailMessage mmc = new MailMessage("测试", "测试简单邮件发送机制", "infocms@mail.shufe.edu.cn");
    DefaultMailNotifier<MailMessage> mailNotifier = new DefaultMailNotifier<MailMessage>();
    mailNotifier.setMailSender(mailSender);
    mailNotifier.setFrom("<测试name>infocms@mail.shufe.edu.cn");
    if (online) mailNotifier.deliver(mmc);
  }

}
