/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.example.ContractInfo;
import org.testng.annotations.Test;

/**
 * @author chaostone
 * @version $Id: LongIdObjectTest.java Aug 29, 2011 3:29:40 PM chaostone $
 */
@Test
public class LongIdObjectTest {

	public void testEquals() {
		ContractInfo info1 = new ContractInfo();
		ContractInfo info2 = new ContractInfo();
		ContractInfo info3 = info1;
		assertTrue(info1.equals(info3));
		assertFalse(info1.equals(info2));
		assertTrue(info1.hashCode() == info2.hashCode());

		Set<ContractInfo> infos = CollectUtils.newHashSet();
		infos.add(info1);
		infos.add(info2);
		assertEquals(infos.size(), 2);
		infos.remove(info3);
		assertEquals(infos.size(), 1);
	}
}
