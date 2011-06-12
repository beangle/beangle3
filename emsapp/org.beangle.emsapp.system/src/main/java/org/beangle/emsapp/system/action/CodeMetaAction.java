/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.system.action;

import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.struts2.action.EntityDrivenAction;

/**
 * @author chaostone
 * @version $Id: CodeMetaAction.java May 5, 2011 4:09:06 PM chaostone $
 */
public class CodeMetaAction extends EntityDrivenAction {

	@Override
	protected String getEntityName() {
		return CodeMeta.class.getName();
	}

}
