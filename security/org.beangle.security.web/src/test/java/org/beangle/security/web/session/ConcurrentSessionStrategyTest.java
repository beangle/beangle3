/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.MemSessionRegistry;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.session.category.CategoryPrincipal;
import org.beangle.security.web.session.category.LimitProfileProviderImpl;
import org.beangle.security.web.session.category.LimitProfile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class ConcurrentSessionStrategyTest {

	LimitProfileProviderImpl profileProvider;
	ConcurrentSessionStrategy controller;

	@BeforeClass
	public void setUp() {
		int profileNum = 10;
		Map<Object, LimitProfile> profiles = CollectUtils.newHashMap();
		for (int i = 0; i < profileNum; i++) {
			Object category = String.valueOf(i);
			profiles.put(category, new LimitProfile(category, 1000, 15, 1));
		}
		profileProvider = new LimitProfileProviderImpl();
		profileProvider.setProfiles(profiles);
		controller = new ConcurrentSessionStrategy();
		//controller.setProfileProvider(profileProvider);
	}

	// 保证loadProfile只进行一次
	@Test(threadPoolSize = 10, invocationCount = 20)
	public void testLoadProfiles() {
		//controller.loadProfilesWhenNecessary();
	}

	private class MockPrincipal implements CategoryPrincipal {
		final Object principal;
		Object category;

		private MockPrincipal(Object principal, Object category) {
			super();
			this.principal = principal;
			this.category = category;
		}

		public Object getPrincipal() {
			return principal;
		}

		public Object getCategory() {
			return category;
		}

		public void changeCategory(Object newCateogry) {
			category = newCateogry;
		}
	}

	@Test(enabled = false)
	public void testCalculateOnline() {
		int sessionNum = 100;
		int profileNum = 10;
		SessionRegistry registry = new MemSessionRegistry();
		for (int i = 0; i < sessionNum; i++) {
			String random = RandomStringUtils.randomAlphanumeric(21);
			Authentication authentication = new UsernamePasswordAuthentication(new MockPrincipal(
					random, String.valueOf(RandomUtils.nextInt(profileNum))), random);
			registry.register(random, authentication);
		}

//		ConcurrentSessionStrategy controller = new ConcurrentSessionStrategy();
//		controller.setProfileProvider(profileProvider);
//		controller.setSessionRegistry(registry);
//		controller.loadProfiles();
//		for (int i = 0; i < 5; i++) {
//			long start = System.currentTimeMillis();
//			controller.calculateOnline();
//			long elipse1 = System.currentTimeMillis() - start;
//			// start = System.currentTimeMillis();
//			// controller.reCalculateOnline();
//			// long elipse2 = System.currentTimeMillis() - start;
//			System.out.println(+sessionNum + "*" + profileNum + " consume:" + elipse1);
//		}
	}
}
