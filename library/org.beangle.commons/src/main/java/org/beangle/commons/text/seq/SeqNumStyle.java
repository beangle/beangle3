/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.seq;

public interface SeqNumStyle {
	/** 中文数字 */
	public static final SeqNumStyle HANZI = new HanZiSeqStyle();

	/** 数字 */
	public static final SeqNumStyle ARABIC = new ArabicSeqStyle();

	public String build(int seq);
}
