/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.seq;

public class NbspGenerator {

	public String generator(int repeat) {
		String repeater = "&nbsp;";
		StringBuilder returnval = new StringBuilder();
		for (int i = 0; i < repeat; i++) {
			returnval.append(repeater);
		}
		return returnval.toString();
	}
}
