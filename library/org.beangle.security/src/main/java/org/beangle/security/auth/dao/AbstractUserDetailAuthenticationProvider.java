/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth.dao;

import org.beangle.security.auth.AccountExpiredException;
import org.beangle.security.auth.AuthenticationProvider;
import org.beangle.security.auth.CredentialsExpiredException;
import org.beangle.security.auth.DisabledException;
import org.beangle.security.auth.LockedException;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailChecker;
import org.beangle.security.core.userdetail.UsernameNotFoundException;

public abstract class AbstractUserDetailAuthenticationProvider implements AuthenticationProvider {
	private boolean forcePrincipalAsString = false;
	private UserDetailChecker preAuthenticationChecker = new DefaultPreAuthenticationChecker();
	private UserDetailChecker postAuthenticationChecker = new DefaultPostAuthenticationChecker();

	protected abstract void additionalAuthenticationChecks(UserDetail userDetails,
			Authentication authentication) throws AuthenticationException;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication
				.getName();

		UserDetail user = retrieveUser(username, authentication);

		if (null == user) { throw new UsernameNotFoundException(); }
		preAuthenticationChecker.check(user);

		additionalAuthenticationChecks(user, authentication);

		postAuthenticationChecker.check(user);

		Object principalToReturn = user;

		if (forcePrincipalAsString) {
			principalToReturn = user.getUsername();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetail user) {
		UsernamePasswordAuthentication result = new UsernamePasswordAuthentication(principal,
				authentication.getCredentials(), user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	protected void doAfterPropertiesSet() throws Exception {
	}

	public boolean isForcePrincipalAsString() {
		return forcePrincipalAsString;
	}

	/**
	 * Allows subclasses to actually retrieve the <code>UserDetails</code> from
	 * an implementation-specific location, with the option of throwing an
	 * <code>AuthenticationException</code> immediately if the presented
	 * credentials are incorrect (this is especially useful if it is necessary
	 * to bind to a resource as the user in order to obtain or generate a <code>UserDetails</code>).
	 * <p>
	 * Subclasses are not required to perform any caching, as the
	 * <code>AbstractUserDetailsAuthenticationProvider</code> will by default cache the
	 * <code>UserDetails</code>. The caching of <code>UserDetails</code> does present additional
	 * complexity as this means subsequent requests that rely on the cache will need to still have
	 * their credentials validated, even if the correctness of credentials was assured by subclasses
	 * adopting a binding-based strategy in this method. Accordingly it is important that subclasses
	 * either disable caching (if they want to ensure that this method is the only method that is
	 * capable of authenticating a request, as no <code>UserDetails</code> will ever be cached) or
	 * ensure subclasses implement
	 * {@link #additionalAuthenticationChecks(UserDetail, UsernamePasswordAuthentication)} to
	 * compare the credentials of a cached <code>UserDetails</code> with subsequent authentication
	 * requests.
	 * </p>
	 * <p>
	 * Most of the time subclasses will not perform credentials inspection in this method, instead
	 * performing it in
	 * {@link #additionalAuthenticationChecks(UserDetail, UsernamePasswordAuthentication)} so that
	 * code related to credentials validation need not be duplicated across two methods.
	 * </p>
	 * 
	 * @param username
	 *            The username to retrieve
	 * @param authentication
	 *            The authentication request, which subclasses <em>may</em> need
	 *            to perform a binding-based retrieval of the <code>UserDetails</code>
	 * @return the user information (never <code>null</code> - instead an
	 *         exception should the thrown)
	 * @throws AuthenticationException
	 *             if the credentials could not be validated (generally a
	 *             <code>BadCredentialsException</code>, an
	 *             <code>AuthenticationServiceException</code> or
	 *             <code>UsernameNotFoundException</code>)
	 */
	protected abstract UserDetail retrieveUser(String username, Authentication authentication)
			throws AuthenticationException;

	public void setForcePrincipalAsString(boolean forcePrincipalAsString) {
		this.forcePrincipalAsString = forcePrincipalAsString;
	}

	public boolean supports(Class<? extends Authentication> authentication) {
		return (UsernamePasswordAuthentication.class.isAssignableFrom(authentication));
	}

	protected UserDetailChecker getPreAuthenticationChecks() {
		return preAuthenticationChecker;
	}

	/**
	 * Sets the policy will be used to verify the status of the loaded <tt>UserDetails</tt>
	 * <em>before</em> validation of the credentials takes
	 * place.
	 * 
	 * @param preAuthenticationChecks
	 *            strategy to be invoked prior to authentication.
	 */
	public void setPreAuthenticationChecks(UserDetailChecker preAuthenticationChecks) {
		this.preAuthenticationChecker = preAuthenticationChecks;
	}

	protected UserDetailChecker getPostAuthenticationChecks() {
		return postAuthenticationChecker;
	}

	public void setPostAuthenticationChecks(UserDetailChecker postAuthenticationChecks) {
		this.postAuthenticationChecker = postAuthenticationChecks;
	}

	private class DefaultPreAuthenticationChecker implements UserDetailChecker {
		public void check(UserDetail user) {
			if (user.isAccountLocked()) { throw new LockedException(null, user); }

			if (!user.isEnabled()) { throw new DisabledException(null, user); }

			if (user.isAccountExpired()) { throw new AccountExpiredException(null, user); }
		}
	}

	private class DefaultPostAuthenticationChecker implements UserDetailChecker {
		public void check(UserDetail user) {
			if (user.isCredentialsExpired()) { throw new CredentialsExpiredException(null, user); }
		}
	}

}
