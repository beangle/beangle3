/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist;

import java.util.Collection;

import org.beangle.collection.CollectUtils;
import org.testng.annotations.Test;

public class EntityDaoTest {

	@Test
	public void testFoo() {
		Collection<Object> aa = CollectUtils.newArrayList();
		new EntityDaoTest().saveOrUpdate(aa);
	}

	public void saveOrUpdate(Object a) {

	}
}
