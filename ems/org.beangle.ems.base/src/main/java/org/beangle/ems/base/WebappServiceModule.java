/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.base;

import org.beangle.ems.base.avatar.service.FileSystemAvatarBase;
import org.beangle.ems.base.config.service.DaoPropertyConfigProvider;
import org.beangle.ems.base.io.ClasspathDocLoader;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.web.io.SplitStreamDownloader;

public class WebappServiceModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(FileSystemAvatarBase.class);
		bind(DaoPropertyConfigProvider.class, ClasspathDocLoader.class).shortName();
		bind("streamDownloader", SplitStreamDownloader.class);
	}

}
