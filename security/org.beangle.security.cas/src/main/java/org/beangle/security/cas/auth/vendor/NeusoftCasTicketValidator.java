/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth.vendor;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Map;

import org.beangle.web.util.HttpUtils;
import org.jasig.cas.client.proxy.Cas20ProxyRetriever;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyRetriever;
import org.jasig.cas.client.validation.AbstractUrlBasedTicketValidator;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Neusoft custom cas implemention.
 * 
 * @author chaostone
 */
public class NeusoftCasTicketValidator extends AbstractUrlBasedTicketValidator {
	/** The CAS 2.0 protocol proxy callback url. */
	private String proxyCallbackUrl;

	/** The storage location of the proxy granting tickets. */
	private ProxyGrantingTicketStorage proxyGrantingTicketStorage;

	/** Implementation of the proxy retriever. */
	private ProxyRetriever proxyRetriever;

	public NeusoftCasTicketValidator(String casServerUrlPrefix) {
		super(casServerUrlPrefix);
		this.proxyRetriever = new Cas20ProxyRetriever(casServerUrlPrefix);
	}

	@Override
	protected Assertion parseResponseFromServer(String response) throws TicketValidationException {
		XMLReader r;
		try {
			r = XMLReaderFactory.createXMLReader();
			r.setFeature("http://xml.org/sax/features/namespaces", false);
			ServiceXmlHandler handler = new ServiceXmlHandler();
			r.setContentHandler(handler);
			r.parse(new InputSource(new StringReader(response)));
			return handler.getAssertion(proxyGrantingTicketStorage, proxyRetriever);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getUrlSuffix() {
		return "serviceValidate";
	}

	public void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
		this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
	}

	public void setProxyRetriever(ProxyRetriever proxyRetriever) {
		this.proxyRetriever = proxyRetriever;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void populateUrlAttributeMap(final Map urlParameters) {
		urlParameters.put("pgtUrl", encodeUrl(this.proxyCallbackUrl));
		urlParameters.put("checkAlive", "true");
	}

	public String getProxyCallbackUrl() {
		return proxyCallbackUrl;
	}

	public void setProxyCallbackUrl(String proxyCallbackUrl) {
		this.proxyCallbackUrl = proxyCallbackUrl;
	}

	public ProxyGrantingTicketStorage getProxyGrantingTicketStorage() {
		return proxyGrantingTicketStorage;
	}

	public ProxyRetriever getProxyRetriever() {
		return proxyRetriever;
	}

	/**
	 * Retrieves the response from the server by opening a connection and merely reading the
	 * response.
	 */
	protected final String retrieveResponseFromServer(final URL validationUrl, final String ticket) {
		return HttpUtils.getResponseText(validationUrl, "GBK");
	}

}
