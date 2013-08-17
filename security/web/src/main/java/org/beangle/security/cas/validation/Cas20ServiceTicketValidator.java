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
package org.beangle.security.cas.validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.lang.Strings;

/**
 * @author chaostone
 * @version $Id: Cas20ServiceTicketValidator.java Dec 27, 2011 6:12:03 PM chaostone $
 */
public class Cas20ServiceTicketValidator extends AbstractTicketValidator {

  @Override
  protected Assertion parseResponseFromServer(final String ticket, String response)
      throws TicketValidationException {
    final String error = getTextForElement(response, "authenticationFailure");
    if (Strings.isNotBlank(error)) { throw new TicketValidationException(error); }
    final String principal = getTextForElement(response, "user");

    if (Strings.isEmpty(principal)) { throw new TicketValidationException(
        "No principal was found in the response from the CAS server."); }

    final Assertion assertion;
    final Map<String, Object> attributes = extractCustomAttributes(response);
    assertion = new AssertionBean(principal, ticket, attributes);
    customParseResponse(response, assertion);
    return assertion;
  }

  /**
   * Default attribute parsing of attributes that look like the following:
   * &lt;cas:attributes&gt;
   * &lt;cas:attribute1&gt;value&lt;/cas:attribute1&gt;
   * &lt;cas:attribute2&gt;value&lt;/cas:attribute2&gt;
   * &lt;/cas:attributes&gt;
   * <p>
   * This code is here merely for sample/demonstration purposes for those wishing to modify the CAS2
   * protocol. You'll probably want a more robust implementation or to use SAML 1.1
   * 
   * @param xml
   *          the XML to parse.
   * @return the map of attributes.
   */
  protected Map<String, Object> extractCustomAttributes(final String xml) {
    final int pos1 = xml.indexOf("<cas:attributes>");
    final int pos2 = xml.indexOf("</cas:attributes>");

    if (pos1 == -1) { return Collections.emptyMap(); }

    final String attributesText = xml.substring(pos1 + 16, pos2);

    final Map<String, Object> attributes = new HashMap<String, Object>();
    final BufferedReader br = new BufferedReader(new StringReader(attributesText));

    String line;
    final List<String> attributeNames = new ArrayList<String>();
    try {
      while ((line = br.readLine()) != null) {
        final String trimmedLine = line.trim();
        if (trimmedLine.length() > 0) {
          final int leftPos = trimmedLine.indexOf(":");
          final int rightPos = trimmedLine.indexOf(">");
          attributeNames.add(trimmedLine.substring(leftPos + 1, rightPos));
        }
      }
      br.close();
    } catch (final IOException e) {
      // ignore
    }

    for (final String name : attributeNames) {
      final List<String> values = getTextForElements(xml, name);

      if (values.size() == 1) {
        attributes.put(name, values.get(0));
      } else {
        attributes.put(name, values);
      }
    }

    return attributes;
  }

  /**
   * Template method if additional custom parsing (such as Proxying) needs to be done.
   * 
   * @param response
   *          the original response from the CAS server.
   * @param assertion
   *          the partially constructed assertion.
   * @throws TicketValidationException
   *           if there is a problem constructing the Assertion.
   */
  protected void customParseResponse(final String response, final Assertion assertion)
      throws TicketValidationException {
    // nothing to do
  }
}
