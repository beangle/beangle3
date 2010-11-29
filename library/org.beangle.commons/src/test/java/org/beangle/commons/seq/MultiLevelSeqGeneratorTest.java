/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.seq;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class MultiLevelSeqGeneratorTest {
	@Test
	public void testGenerator() {
		MultiLevelSeqGenerator sg = new MultiLevelSeqGenerator();
		sg.add(new SeqPattern(SeqNumStyle.HANZI, "{1}"));
		sg.add(new SeqPattern(SeqNumStyle.HANZI, "({2})"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}.{4}"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}.{4}.{5}"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}.{4}.{5}.{6}"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}.{4}.{5}.{6}.{7}"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}.{4}.{5}.{6}.{7}.{8}"));
		sg.add(new SeqPattern(SeqNumStyle.ARABIC, "{3}.{4}.{5}.{6}.{7}.{8}.{9}"));

		assertEquals("一", sg.getSytle(1).next());
		assertEquals("(一)", sg.getSytle(2).next());
		assertEquals("1", sg.getSytle(3).next());
		assertEquals("1.1", sg.getSytle(4).next());
	}
}
