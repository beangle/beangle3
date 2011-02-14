/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth.vendor;

import org.beangle.security.cas.CasConfig;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyRetriever;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.FactoryBean;

public class NeusoftCasTicketValidatorFactory implements FactoryBean<TicketValidator>{

	private CasConfig config;

	/** The CAS 2.0 protocol proxy callback url. */
	private String proxyCallbackUrl;

	/** The storage location of the proxy granting tickets. */
	private ProxyGrantingTicketStorage proxyGrantingTicketStorage;

	/** Implementation of the proxy retriever. */
	private ProxyRetriever proxyRetriever;

	public TicketValidator getObject() throws Exception {
		NeusoftCasTicketValidator validator = new NeusoftCasTicketValidator(config.getCasServer());
		if (null != proxyGrantingTicketStorage) {
			validator.setProxyGrantingTicketStorage(proxyGrantingTicketStorage);
		}
		if (null != config.getProxyReceptor()) {
			validator.setProxyCallbackUrl(config.getProxyCallbackUrl());
		}
		if (null != proxyCallbackUrl) {
			validator.setProxyCallbackUrl(proxyCallbackUrl);
		}
		if (null != proxyRetriever) {
			validator.setProxyRetriever(proxyRetriever);
		}
		validator.setRenew(config.isRenew());
		return validator;
	}

	public Class<?> getObjectType() {
		return TicketValidator.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setConfig(CasConfig config) {
		this.config = config;
	}

	public void setProxyCallbackUrl(String proxyCallbackUrl) {
		this.proxyCallbackUrl = proxyCallbackUrl;
	}

	public void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
		this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
	}

	public void setProxyRetriever(ProxyRetriever proxyRetriever) {
		this.proxyRetriever = proxyRetriever;
	}
}
