/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query.limit;

import static org.testng.Assert.assertNotNull;

import java.util.Iterator;

import org.beangle.collection.page.PageLimit;
import org.beangle.dao.query.LimitQuery;
import org.beangle.dao.query.builder.OqlBuilder;
import org.testng.annotations.Test;

@Test
public class TestQueryPage {

	public void testMove() throws Exception {
		OqlBuilder<String> query = OqlBuilder.from(String.class, "dd");
		query.limit(new PageLimit(1, 2));
		MockQueryPage page = new MockQueryPage((LimitQuery<String>) query.build());
		for (Iterator<String> iterator = page.iterator(); iterator.hasNext();) {
			String data = iterator.next();
			assertNotNull(data);
		}
	}
}
