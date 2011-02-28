/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence;

import static org.testng.Assert.assertEquals;

import org.beangle.db.sequence.impl.DefaultSequenceNamePattern;
import org.testng.annotations.Test;

@Test
public class SequenceNamePatternTest {

	public void testGetTableName() {
		DefaultSequenceNamePattern pattern = new DefaultSequenceNamePattern();
		assertEquals("SYS_USERS_T", pattern.getTableName("SEQ_SYS_USERS_T"));
	}
}
