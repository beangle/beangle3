/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.beangle.security.cas.validation.AbstractTicketValidator;
import org.beangle.security.cas.validation.Assertion;
import org.beangle.security.cas.validation.TicketValidationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Neusoft custom cas implemention.
 * 
 * @author chaostone
 */
public class NeusoftCasTicketValidator extends AbstractTicketValidator {

  public NeusoftCasTicketValidator() {
    super();
    setEncoding("GBK");
  }

  @Override
  protected Assertion parseResponseFromServer(final String ticket, String response)
      throws TicketValidationException {
    XMLReader r;
    try {
      r = XMLReaderFactory.createXMLReader();
      r.setFeature("http://xml.org/sax/features/namespaces", false);
      ServiceXmlHandler handler = new ServiceXmlHandler();
      r.setContentHandler(handler);
      r.parse(new InputSource(new StringReader(response)));
      return handler.getAssertion();
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected final void populateUrlAttributeMap(final Map<String, String> urlParameters) {
    urlParameters.put("checkAlive", "true");
  }

}
