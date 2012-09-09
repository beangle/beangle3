/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.mail;

import java.util.List;

import javax.mail.internet.InternetAddress;

import junit.framework.Assert;

import org.beangle.notification.mail.MimeUtils;
import org.testng.annotations.Test;

@Test
public class MimeUtilsTest {

  public void testParseAddress() throws Exception {
    String me = "段体华<duantihua@gmail.com>,程序员<programer@gmail.com>";
    List<InternetAddress> adds = MimeUtils.parseAddress(me, "UTF-8");
    int i = 0;
    for (InternetAddress add : adds) {
      if (i == 0) {
        Assert.assertEquals("段体华", add.getPersonal());
      } else {
        Assert.assertEquals("程序员", add.getPersonal());
      }
      i++;
    }
  }
}
