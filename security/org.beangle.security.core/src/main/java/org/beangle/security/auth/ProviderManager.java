/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import java.util.Iterator;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class ProviderManager extends AbstractAuthenticationManager implements InitializingBean {

	protected final Logger logger = LoggerFactory.getLogger(ProviderManager.class);

	protected List<AuthenticationProvider> providers = CollectUtils.newArrayList();

	// protected SessionController sessionController;

	public void afterPropertiesSet() throws Exception {
		if (providers.isEmpty()) { throw new RuntimeException(
				"authentication provider list is empty"); }
		logger.info("providers:" + providers);
	}

	public Authentication doAuthentication(Authentication auth) throws AuthenticationException {
		Iterator<AuthenticationProvider> iter = getProviders().iterator();
		Class<? extends Authentication> toTest = auth.getClass();
		AuthenticationException lastException = null;

		while (iter.hasNext()) {
			AuthenticationProvider provider = iter.next();
			if (!provider.supports(toTest)) {
				continue;
			}
			Authentication result;
			try {
				result = provider.authenticate(auth);
				if (result != null) {
					copyDetails(auth, result);
					// sessionController.checkAuthenticationAllowed(result);
				}
			} catch (AuthenticationException ae) {
				lastException = ae;
				result = null;
			}
			if (lastException instanceof AccountStatusException) {
				// || lastException instanceof ConcurrentLoginException) {
				break;
			}

			if (null != result) {
				// sessionController.registerAuthentication(result);
				return result;
			}
		}

		if (lastException == null) {
			lastException = new ProviderNotFoundException("Provider not found!");
		}
		throw lastException;
	}

	private void copyDetails(Authentication source, Authentication dest) {
		if ((dest instanceof AbstractAuthentication) && (dest.getDetails() == null)) {
			AbstractAuthentication token = (AbstractAuthentication) dest;
			token.setDetails(source.getDetails());
		}
	}

	public List<AuthenticationProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<AuthenticationProvider> providers) {
		this.providers = providers;
	}

	// public void setSessionController(SessionController sessionController) {
	// this.sessionController = sessionController;
	// }
	//
	// public SessionController getSessionController() {
	// return sessionController;
	// }

}
