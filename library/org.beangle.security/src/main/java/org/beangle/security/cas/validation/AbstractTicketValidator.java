/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.validation;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import org.beangle.security.cas.CasConfig;
import org.beangle.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/** @author chaostone
 * @version $Id: Cas20ServiceTicketValidator.java Dec 27, 2011 6:06:21 PM chaostone $ */
public abstract class AbstractTicketValidator implements TicketValidator {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** Hostname verifier used when making an SSL request to the CAS server. */
	protected HostnameVerifier hostnameVerifier;

	private CasConfig config;

	/** A map containing custom parameters to pass to the validation url. */
	private Map<String, String> customParameters;

	private String encoding;

	/** Template method for ticket validators that need to provide additional parameters to the
	 * validation url.
	 * 
	 * @param urlParameters
	 *        the map containing the parameters. */
	protected void populateUrlAttributeMap(final Map<String, String> urlParameters) {
		// nothing to do
	}

	/** Constructs the URL to send the validation request to.
	 * 
	 * @param ticket
	 *        the ticket to be validated.
	 * @param serviceUrl
	 *        the service identifier.
	 * @return the fully constructed URL. */
	protected final String constructValidationUrl(final String ticket, final String serviceUrl) {
		final Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put("ticket", ticket);
		urlParameters.put("service", encodeUrl(serviceUrl));

		if (config.isRenew()) urlParameters.put("renew", "true");

		populateUrlAttributeMap(urlParameters);

		if (customParameters != null) urlParameters.putAll(customParameters);

		final String suffix = config.getValidateUri();
		final StringBuilder buffer = new StringBuilder(urlParameters.size() * 10
				+ config.getCasServer().length() + suffix.length() + 1);
		buffer.append(config.getCasServer()).append(suffix);

		int i = 0;
		for (Map.Entry<String, String> entry : urlParameters.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			if (value != null) {
				buffer.append(i++ == 0 ? "?" : "&");
				buffer.append(key);
				buffer.append("=");
				buffer.append(value);
			}
		}
		return buffer.toString();
	}

	/** Encodes a URL using the URLEncoder format.
	 * 
	 * @param url the url to encode.
	 * @return the encoded url, or the original url if "UTF-8" character encoding could not be
	 *         found. */
	protected final String encodeUrl(final String url) {
		if (url == null) { return null; }

		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			return url;
		}
	}

	/** Parses the response from the server into a CAS Assertion.
	 * 
	 * @param response
	 *        the response from the server, in any format.
	 * @return the CAS assertion if one could be parsed from the response.
	 * @throws TicketValidationException
	 *         if an Assertion could not be created. */
	protected abstract Assertion parseResponseFromServer(final String ticket, final String response)
			throws TicketValidationException;

	/** Contacts the CAS Server to retrieve the response for the ticket validation.
	 * 
	 * @param validationUrl
	 *        the url to send the validation request to.
	 * @param ticket
	 *        the ticket to validate.
	 * @return the response from the CAS server. */

	protected String retrieveResponseFromServer(URL validationUrl, String ticket){
		return HttpUtils.getResponseText(validationUrl, hostnameVerifier, getEncoding());
	}

	public Assertion validate(final String ticket, final String service) throws TicketValidationException {
		final String validationUrl = constructValidationUrl(ticket, service);
		logger.debug("Constructing validation url: " + validationUrl);
		try {
			logger.debug("Retrieving response from server.");
			final String serverResponse = retrieveResponseFromServer(new URL(validationUrl), ticket);

			if (serverResponse == null) { throw new TicketValidationException(
					"The CAS server returned no response."); }
			logger.debug("Server response: " + serverResponse);
			return parseResponseFromServer(ticket, serverResponse);
		} catch (final MalformedURLException e) {
			throw new TicketValidationException(e);
		}
	}

	/** Get an instance of an XML reader from the XMLReaderFactory.
	 * 
	 * @return the XMLReader. */
	public static XMLReader getXmlReader() {
		try {
			return XMLReaderFactory.createXMLReader();
		} catch (final SAXException e) {
			throw new RuntimeException("Unable to create XMLReader", e);
		}
	}

	/** Retrieve the text for a group of elements. Each text element is an entry
	 * in a list.
	 * <p>
	 * This method is currently optimized for the use case of two elements in a list.
	 * 
	 * @param xmlAsString
	 *        the xml response
	 * @param element
	 *        the element to look for
	 * @return the list of text from the elements. */
	public List<String> getTextForElements(final String xmlAsString, final String element) {
		final List<String> elements = new ArrayList<String>(2);
		final XMLReader reader = getXmlReader();

		final DefaultHandler handler = new DefaultHandler() {

			private boolean foundElement = false;

			private StringBuilder buffer = new StringBuilder();

			public void startElement(final String uri, final String localName, final String qName,
					final Attributes attributes) throws SAXException {
				if (localName.equals(element)) {
					this.foundElement = true;
				}
			}

			public void endElement(final String uri, final String localName, final String qName)
					throws SAXException {
				if (localName.equals(element)) {
					this.foundElement = false;
					elements.add(this.buffer.toString());
					this.buffer = new StringBuilder();
				}
			}

			public void characters(char[] ch, int start, int length) throws SAXException {
				if (this.foundElement) {
					this.buffer.append(ch, start, length);
				}
			}
		};

		reader.setContentHandler(handler);
		reader.setErrorHandler(handler);

		try {
			reader.parse(new InputSource(new StringReader(xmlAsString)));
		} catch (final Exception e) {
			logger.error("parse", e);
			return null;
		}

		return elements;
	}

	/** Retrieve the text for a specific element (when we know there is only
	 * one).
	 * 
	 * @param xmlAsString
	 *        the xml response
	 * @param element
	 *        the element to look for
	 * @return the text value of the element. */
	public String getTextForElement(final String xmlAsString, final String element) {
		final XMLReader reader = getXmlReader();
		final StringBuilder builder = new StringBuilder();

		final DefaultHandler handler = new DefaultHandler() {

			private boolean foundElement = false;

			public void startElement(final String uri, final String localName, final String qName,
					final Attributes attributes) throws SAXException {
				if (localName.equals(element)) {
					this.foundElement = true;
				}
			}

			public void endElement(final String uri, final String localName, final String qName)
					throws SAXException {
				if (localName.equals(element)) {
					this.foundElement = false;
				}
			}

			public void characters(char[] ch, int start, int length) throws SAXException {
				if (this.foundElement) {
					builder.append(ch, start, length);
				}
			}
		};

		reader.setContentHandler(handler);
		reader.setErrorHandler(handler);

		try {
			reader.parse(new InputSource(new StringReader(xmlAsString)));
		} catch (final Exception e) {
			logger.error("parse error", e);
			return null;
		}

		return builder.toString();
	}

	public final void setCustomParameters(final Map<String, String> customParameters) {
		this.customParameters = customParameters;
	}

	public final void setHostnameVerifier(final HostnameVerifier verifier) {
		this.hostnameVerifier = verifier;
	}

	public final void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	protected final String getEncoding() {
		return this.encoding;
	}

	public void setConfig(CasConfig config) {
		this.config = config;
	}
	
}
