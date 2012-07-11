/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth.vendor;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.cas.validation.Assertion;
import org.beangle.security.cas.validation.AssertionBean;
import org.beangle.security.cas.validation.TicketValidationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ServiceXmlHandler extends DefaultHandler {

  public ServiceXmlHandler() {
    currentText = new StringBuffer();
  }

  public void startElement(String ns, String ln, String qn, Attributes a) {
    currentText = new StringBuffer();
    if (qn.equals("sso:authenticationSuccess")) authenticationSuccess = true;
    else if (qn.equals("sso:authenticationFailure")) {
      authenticationSuccess = false;
      errorCode = a.getValue("code");
      if (errorCode != null) errorCode = errorCode.trim();
    } else if (qn.equals("sso:attribute")) {
      String name = a.getValue("name");
      String val = a.getValue("value");
      userMap.put(name, val);
    }
    if (authenticationSuccess) {
      if (qn.equals("sso:proxies")) proxyFragment = true;
      if (qn.equals("sso:checkAliveTicket")) checkAliveFragment = true;
    }
  }

  public void characters(char ch[], int start, int length) {
    currentText.append(ch, start, length);
  }

  public void endElement(String ns, String ln, String qn) throws SAXException {
    if (authenticationSuccess) {
      if (qn.equals("sso:user")) user = currentText.toString().trim();
      if (qn.equals("sso:proxyGrantingTicket")) pgtIou = currentText.toString().trim();
    } else if (qn.equals("sso:authenticationFailure")) errorMessage = currentText.toString().trim();
    if (qn.equals("sso:proxies")) proxyFragment = false;
    else if (proxyFragment && qn.equals("sso:proxy")) proxyList.add(currentText.toString().trim());
    if (qn.equals("sso:checkAliveTicket")) {
      checkAliveFragment = false;
      caKey = currentText.toString().trim();
    }
  }

  protected static final String AUTHENTICATION_SUCCESS = "sso:authenticationSuccess";
  protected static final String AUTHENTICATION_FAILURE = "sso:authenticationFailure";
  protected static final String PROXY_GRANTING_TICKET = "sso:proxyGrantingTicket";
  protected static final String USER = "sso:user";
  protected static final String ATTRIBUTES = "sso:attributes";
  protected static final String ATTRIBUTE = "sso:attribute";
  protected static final String CHECKALIVE = "sso:checkAliveTicket";
  protected static final String PROXIES = "sso:proxies";
  protected static final String PROXY = "sso:proxy";
  protected StringBuffer currentText;
  protected boolean authenticationSuccess = false;
  protected String netid;
  private Map<String, Object> userMap = CollectUtils.newHashMap();
  protected String errorCode;
  protected String errorMessage;
  protected String pgtIou;
  protected String user;
  protected List<String> proxyList = CollectUtils.newArrayList();
  protected boolean proxyFragment = false;
  protected boolean checkAliveFragment;
  protected String caKey;

  protected Assertion getAssertion() throws TicketValidationException {
    if (authenticationSuccess) return new AssertionBean(user, caKey, userMap);
    else throw new TicketValidationException(errorCode + ":" + errorMessage);
  }

}
