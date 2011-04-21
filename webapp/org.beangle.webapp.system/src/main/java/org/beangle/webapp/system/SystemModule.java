/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.system;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.web.io.SplitStreamDownloader;
import org.beangle.webapp.staticfile.service.ClasspathDocLoader;
import org.beangle.webapp.system.action.FileAction;
import org.beangle.webapp.system.action.InfoAction;
import org.beangle.webapp.system.action.PropertyAction;
import org.beangle.webapp.system.service.DaoPropertyConfigProvider;

public final class SystemModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(FileAction.class, InfoAction.class, PropertyAction.class).in(Scope.PROTOTYPE);
		bind(DaoPropertyConfigProvider.class, ClasspathDocLoader.class).shortName();
		bind("streamDownloader", SplitStreamDownloader.class);
	}

}
