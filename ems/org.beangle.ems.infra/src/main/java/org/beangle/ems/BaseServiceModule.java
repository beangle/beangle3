/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems;

import org.beangle.ems.avatar.service.FileSystemAvatarBase;
import org.beangle.ems.config.service.DaoPropertyConfigProvider;
import org.beangle.ems.dictionary.service.impl.BaseCodeServiceImpl;
import org.beangle.ems.dictionary.service.impl.SeqCodeGenerator;
import org.beangle.ems.io.ClasspathDocLoader;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.web.io.SplitStreamDownloader;

public class BaseServiceModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(FileSystemAvatarBase.class);
		bind(DaoPropertyConfigProvider.class, ClasspathDocLoader.class).shortName();
		bind("streamDownloader", SplitStreamDownloader.class);

		bind("baseCodeService", BaseCodeServiceImpl.class);
		bind(SeqCodeGenerator.class);
	}

}
