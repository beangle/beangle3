/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.metadata;

import static org.testng.Assert.assertNotNull;

import org.beangle.dao.metadata.Model;
import org.beangle.dao.example.ContractInfo;
import org.testng.annotations.Test;

@Test
public class ModelTest {

	public void test() {
		ContractInfo entity = Model.newInstance(ContractInfo.class);
		assertNotNull(entity);
	}
}
