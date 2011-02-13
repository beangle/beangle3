/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.InitializingBean;

/**
 * Stores properties related to this CAS service.
 * <p>
 * Each web application capable of processing CAS tickets is known as a service. This class stores
 * the properties that are relevant to the local CAS service, being the application that is being
 * secured by Beangle Security.
 * 
 * @author chaostone
 * @version $Id: ServiceProperties.java $
 */
public class CasConfig implements InitializingBean {

	private String casServer;

	private String localServer;

	private boolean renew = false;

	private boolean encode = true;

	private String artifactName = "ticket";

	private String proxyReceptor;

	private String loginUri = "/login";

	public CasConfig() {
		super();
	}

	public CasConfig(String casServer, String localServer) {
		super();
		this.casServer = casServer;
		this.localServer = localServer;
	}

	public void afterPropertiesSet() throws Exception {
		Validate.notEmpty(this.casServer, "cas server must be specified.");
		Validate.isTrue(!this.casServer.endsWith("/"), "cas server should not end with /");
		Validate.notEmpty(this.localServer, "local server must be specified.");
		Validate.notEmpty(this.loginUri, "loginUri must be specified. like /login");
		Validate.notEmpty(this.artifactName, "artifact name  must be specified.etc. ticket");
	}

	public String getCasServer() {
		return casServer;
	}

	public void setCasServer(String casServer) {
		this.casServer = casServer;
	}

	public String getLocalServer() {
		return localServer;
	}

	public void setLocalServer(String localServer) {
		this.localServer = localServer;
	}

	/**
	 * The enterprise-wide CAS login URL. Usually something like
	 * <code>https://www.mycompany.com/cas/login</code>.
	 * 
	 * @return the enterprise-wide CAS login URL
	 */
	public String getLoginUrl() {
		return casServer + loginUri;
	}

	public String getProxyCallbackUrl() {
		if (null == proxyReceptor) { return null; }
		StringBuilder buffer = new StringBuilder();
		if (!localServer.startsWith("https://") && !localServer.startsWith("http://")) {
			buffer.append("http://");
		}

		buffer.append(localServer);
		buffer.append(proxyReceptor);
		return buffer.toString();
	}

	/**
	 * Indicates whether the <code>renew</code> parameter should be sent to the
	 * CAS login URL and CAS validation URL.
	 * <p>
	 * If <code>true</code>, it will force CAS to authenticate the user again (even if the user has
	 * previously authenticated). During ticket validation it will require the ticket was generated
	 * as a consequence of an explicit login. High security applications would probably set this to
	 * <code>true</code>. Defaults to <code>false</code>, providing automated single sign on.
	 * 
	 * @return whether to send the <code>renew</code> parameter to CAS
	 */
	public boolean isRenew() {
		return renew;
	}

	public void setRenew(boolean renew) {
		this.renew = renew;
	}

	public boolean isEncode() {
		return encode;
	}

	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	public String getProxyReceptor() {
		return proxyReceptor;
	}

	public void setProxyReceptor(String proxyReceptor) {
		this.proxyReceptor = proxyReceptor;
	}

	public String getLoginUri() {
		return loginUri;
	}

	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

}
