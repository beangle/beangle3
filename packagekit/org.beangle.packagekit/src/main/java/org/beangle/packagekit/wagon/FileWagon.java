/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.wagon;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWagon implements Wagon {
	
	private static final Logger logger = LoggerFactory.getLogger(FileWagon.class);

	public void transfer(String from, String to) {
		try {
			FileUtils.copyFile(new File(from), new File(to));
			logger.info("complete transfer package from {} to {}", from, to);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WagonException("failure transfer url" + from + " to " + to);
		}
	}

}