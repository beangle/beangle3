/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity;

import static org.testng.Assert.assertNotNull;

import org.beangle.model.example.ContractInfo;
import org.testng.annotations.Test;

@Test
public class ModelTest {

	public void test() {
		ContractInfo entity = Model.newInstance(ContractInfo.class);
		assertNotNull(entity);
	}
}
