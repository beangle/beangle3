/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.service;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.web.io.SplitStreamDownloader;
import org.beangle.webapp.service.avatar.FileSystemAvatarBase;
import org.beangle.webapp.service.config.DaoPropertyConfigProvider;
import org.beangle.webapp.service.io.ClasspathDocLoader;

public class ServiceModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(FileSystemAvatarBase.class);
		bind(DaoPropertyConfigProvider.class, ClasspathDocLoader.class).shortName();
		bind("streamDownloader", SplitStreamDownloader.class);
	}

}
