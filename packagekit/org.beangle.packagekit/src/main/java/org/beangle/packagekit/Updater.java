/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit;

import org.beangle.packagekit.internal.SimpleRepository;

public class Updater {

	Repository repository;

	public static void main(String[] args) {
		Repository repository = new SimpleRepository();
		repository.getProtocal();
	}

}
