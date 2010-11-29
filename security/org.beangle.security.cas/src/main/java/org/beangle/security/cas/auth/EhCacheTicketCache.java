/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * Caches tickets using a Spring IoC defined <A
 * HREF="http://ehcache.sourceforge.net">EHCACHE</a>.
 * 
 * @author chaostone
 */
public class EhCacheTicketCache implements StatelessTicketCache, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(EhCacheTicketCache.class);

	private Ehcache cache;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(cache, "cache mandatory");
	}

	public CasAuthentication get(String serviceTicket) {
		Element element = null;
		try {
			element = cache.get(serviceTicket);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Cache hit: " + (element != null) + "; service ticket: " + serviceTicket);
		}

		if (element == null) {
			return null;
		} else {
			return (CasAuthentication) element.getValue();
		}
	}

	public Ehcache getCache() {
		return cache;
	}

	public void put(CasAuthentication token) {
		Element element = new Element(token.getCredentials().toString(), token);
		logger.debug("Cache put: {}", element.getKey());
		cache.put(element);
	}

	public void remove(CasAuthentication token) {
		logger.debug("Cache remove: {}", token.getCredentials().toString());
		this.remove(token.getCredentials().toString());
	}

	public void remove(String serviceTicket) {
		cache.remove(serviceTicket);
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}
}
