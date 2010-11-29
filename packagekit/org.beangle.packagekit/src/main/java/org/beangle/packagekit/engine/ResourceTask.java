/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine;

import org.beangle.packagekit.Resource;

public interface ResourceTask extends Task {

	public UpdateAction getAction();

	public Resource getResource();

	public void setResource(Resource resource);
}
