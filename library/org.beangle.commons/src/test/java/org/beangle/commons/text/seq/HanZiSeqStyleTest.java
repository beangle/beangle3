/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.seq;

import static org.testng.Assert.assertEquals;

import org.beangle.commons.text.seq.HanZiSeqStyle;
import org.testng.annotations.Test;

public class HanZiSeqStyleTest {

	@Test
	public void testBuildText() {
		HanZiSeqStyle style = new HanZiSeqStyle();
		assertEquals("二百一十一", style.build(211));
		assertEquals("二百零一", style.build(201));
		assertEquals("三千零十一", style.build(3011));
	}
}
