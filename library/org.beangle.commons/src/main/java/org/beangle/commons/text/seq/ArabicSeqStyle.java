/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.seq;

public class ArabicSeqStyle implements SeqNumStyle {
	public String build(int seq) {
		return String.valueOf(seq);
	}
}
