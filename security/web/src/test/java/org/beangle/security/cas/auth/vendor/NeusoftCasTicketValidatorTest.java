/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.cas.auth.vendor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.beangle.commons.io.Files;
import org.beangle.security.cas.validation.Assertion;
import org.beangle.security.cas.validation.TicketValidationException;
import org.testng.annotations.Test;

@Test
public class NeusoftCasTicketValidatorTest {

  NeusoftCasTicketValidator validator = new NeusoftCasTicketValidator();

  public void testParseSuccess() throws TicketValidationException, IOException {
    File file = new File(NeusoftCasTicketValidatorTest.class.getResource("/neusoft-auth-success.xml")
        .getFile());
    String response = Files.readFileToString(file);
    Assertion assertion = validator.parseResponseFromServer("testticket", response);
    assertNotNull(assertion);
    assertEquals(assertion.getPrincipal(), "admin");
  }

  @Test(expectedExceptions = TicketValidationException.class)
  public void testParseFailure() throws TicketValidationException, IOException {
    File file = new File(NeusoftCasTicketValidatorTest.class.getResource("/neusoft-auth-failure.xml")
        .getFile());
    String response = Files.readFileToString(file);
    validator.parseResponseFromServer("ticket", response);
  }
}
