/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp;

import org.beangle.emsapp.avatar.service.FileSystemAvatarBase;
import org.beangle.emsapp.config.service.DaoPropertyConfigProvider;
import org.beangle.emsapp.dictionary.service.impl.BaseCodeServiceImpl;
import org.beangle.emsapp.dictionary.service.impl.SeqCodeGenerator;
import org.beangle.emsapp.io.ClasspathDocLoader;
import org.beangle.emsapp.log.service.BusinessEventLogger;
import org.beangle.spring.config.AbstractBindModule;
import org.beangle.web.io.SplitStreamDownloader;

public class BaseServiceModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(FileSystemAvatarBase.class);
		bind(DaoPropertyConfigProvider.class, ClasspathDocLoader.class).shortName();
		bind("streamDownloader", SplitStreamDownloader.class);

		bind("baseCodeService", BaseCodeServiceImpl.class);
		bind(SeqCodeGenerator.class);
		bind(BusinessEventLogger.class);
	}

}
