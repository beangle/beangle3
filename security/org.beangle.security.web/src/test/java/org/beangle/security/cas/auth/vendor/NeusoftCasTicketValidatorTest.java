/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth.vendor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.beangle.security.cas.validation.Assertion;
import org.beangle.security.cas.validation.TicketValidationException;
import org.testng.annotations.Test;

@Test
public class NeusoftCasTicketValidatorTest {

  NeusoftCasTicketValidator validator = new NeusoftCasTicketValidator();

  public void testParseSuccess() throws TicketValidationException, IOException {
    File file = new File(NeusoftCasTicketValidatorTest.class.getResource("/neusoft-auth-success.xml")
        .getFile());
    String response = FileUtils.readFileToString(file);
    Assertion assertion = validator.parseResponseFromServer("testticket", response);
    assertNotNull(assertion);
    assertEquals(assertion.getPrincipal(), "admin");
  }

  @Test(expectedExceptions = TicketValidationException.class)
  public void testParseFailure() throws TicketValidationException, IOException {
    File file = new File(NeusoftCasTicketValidatorTest.class.getResource("/neusoft-auth-failure.xml")
        .getFile());
    String response = FileUtils.readFileToString(file);
    validator.parseResponseFromServer("ticket", response);
  }
}
