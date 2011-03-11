/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.avatar;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.webapp.avatar.action.BoardAction;
import org.beangle.webapp.avatar.action.MyAction;
import org.beangle.webapp.avatar.action.MyUploadAction;
import org.beangle.webapp.avatar.action.UserAction;
import org.beangle.webapp.avatar.service.FileSystemAvatarBase;

public class AvatarModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(BoardAction.class, MyAction.class, MyUploadAction.class, UserAction.class).in(Scope.PROTOTYPE);
		bind(FileSystemAvatarBase.class);
	}

}
