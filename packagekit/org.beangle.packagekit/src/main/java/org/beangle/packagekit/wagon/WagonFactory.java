/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.wagon;

public final class WagonFactory {

	static Wagon fileWagon = new FileWagon();
	static Wagon httpWagon = new HttpWagon();

	public static final Wagon getWagon(String alias) {
		if (alias.equals("file")) {
			return fileWagon;
		} else if (alias.equals("http")) {
			return httpWagon;
		} else {
			throw new WagonException(alias + " protocal not supported!");
		}
	}
}
