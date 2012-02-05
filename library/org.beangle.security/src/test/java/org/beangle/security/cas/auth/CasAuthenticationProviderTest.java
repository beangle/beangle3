/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.cas.validation.Assertion;
import org.beangle.security.cas.validation.AssertionBean;
import org.beangle.security.cas.validation.TicketValidationException;
import org.beangle.security.cas.validation.TicketValidator;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.security.core.userdetail.DefaultUserDetailBean;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;
import org.beangle.security.core.userdetail.UsernameNotFoundException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class CasAuthenticationProviderTest {
	CasAuthenticationProvider cap;
	MockStatelessTicketCache cache;

	@BeforeMethod
	public void setUp() throws Exception {
		cap = new CasAuthenticationProvider();
		cap.setUserDetailService(new MockAuthoritiesPopulator());
		cap.setKey("qwerty");
		cache = new MockStatelessTicketCache();
		cap.setStatelessTicketCache(cache);
		cap.setTicketValidator(new MockTicketValidator(true));
		cap.init();
	}

	public void supportsRequiredTokens() {
		assertFalse(cap.supports(UsernamePasswordAuthentication.class));
		assertTrue(cap.supports(CasAuthentication.class));
	}

	@Test
	public void statefulAuthenticationIsSuccessful() throws Exception {
		CasAuthentication token = new CasAuthentication(CasAuthentication.STATEFUL_ID, "ST-123", null);
		token.setDetails("details");
		Authentication result = cap.authenticate(token);

		// Confirm ST-123 was NOT added to the cache
		assertTrue(cache.get("ST-456") == null);
		if (!(result instanceof CasAuthentication)) {
			fail("Should have returned a CasAuthentication");
		}
		CasAuthentication casResult = (CasAuthentication) result;
		assertEquals(makeUserDetailsFromAuthoritiesPopulator(), casResult.getPrincipal());
		assertEquals("ST-123", casResult.getCredentials());
		assertTrue(casResult.getAuthorities().contains(new GrantedAuthorityBean("ROLE_A")));
		assertTrue(casResult.getAuthorities().contains(new GrantedAuthorityBean("ROLE_B")));
		assertEquals(cap.getKey().hashCode(), casResult.getKeyHash());
		assertEquals("details", casResult.getDetails());

		// Now confirm the CasAuthentication is automatically re-accepted.
		// To ensure TicketValidator not called again, set it to deliver an
		// exception...
		cap.setTicketValidator(new MockTicketValidator(false));

		Authentication laterResult = cap.authenticate(result);
		assertEquals(result, laterResult);
	}

	@Test
	public void statelessAuthenticationIsSuccessful() throws Exception {
		CasAuthentication token = new CasAuthentication(CasAuthentication.STATELESS_ID, "ST-456", null);
		token.setDetails("details");
		Authentication result = cap.authenticate(token);
		// Confirm ST-456 was added to the cache
		assertTrue(cache.get("ST-456") != null);

		if (!(result instanceof CasAuthentication)) {
			fail("Should have returned a CasAuthentication");
		}

		assertEquals(makeUserDetailsFromAuthoritiesPopulator(), result.getPrincipal());
		assertEquals("ST-456", result.getCredentials());
		assertEquals("details", result.getDetails());

		// Now try to authenticate again. To ensure TicketValidator not
		// called again, set it to deliver an exception...
		cap.setTicketValidator(new MockTicketValidator(false));

		// Previously created UsernamePasswordAuthenticationToken is OK
		Authentication newResult = cap.authenticate(token);
		assertEquals(makeUserDetailsFromAuthoritiesPopulator(), newResult.getPrincipal());
		assertEquals("ST-456", newResult.getCredentials());
	}

	private class MockAuthoritiesPopulator implements UserDetailService {
		public UserDetail loadDetail(final String token) throws UsernameNotFoundException {
			return makeUserDetailsFromAuthoritiesPopulator();
		}
	}

	private UserDetail makeUserDetailsFromAuthoritiesPopulator() {
		return new DefaultUserDetailBean("user", "password", GrantedAuthorityBean.build("ROLE_A", "ROLE_B"));
	}

	private class MockStatelessTicketCache implements StatelessTicketCache {
		private Map<String, CasAuthentication> cache = new HashMap<String, CasAuthentication>();

		public CasAuthentication get(String serviceTicket) {
			return cache.get(serviceTicket);
		}

		public void put(CasAuthentication token) {
			cache.put(token.getCredentials().toString(), token);
		}

		public void remove(CasAuthentication token) {
			throw new UnsupportedOperationException("mock method not implemented");
		}

		public void remove(String serviceTicket) {
			throw new UnsupportedOperationException("mock method not implemented");
		}
	}

	private class MockTicketValidator implements TicketValidator {
		private boolean returnTicket;

		public MockTicketValidator(boolean returnTicket) {
			this.returnTicket = returnTicket;
		}

		public Assertion validate(final String ticket, final String service) throws TicketValidationException {
			if (returnTicket) { return new AssertionBean("rod",ticket); }
			throw new BadCredentialsException("As requested from mock");
		}
	}
}
