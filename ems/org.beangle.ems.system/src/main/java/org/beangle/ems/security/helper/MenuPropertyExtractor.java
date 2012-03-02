/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.helper;

import org.beangle.ems.security.nav.Menu;
import org.beangle.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.util.i18n.TextResource;

/**
 * @author chaostone
 * @version $Id: ResourcePropertyExtractor.java Jul 17, 2011 9:41:20 PM chaostone $
 */
public class MenuPropertyExtractor extends DefaultPropertyExtractor {

	public MenuPropertyExtractor() {
		super();
	}

	public MenuPropertyExtractor(TextResource textResource) {
		super(textResource);
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		Menu menu = (Menu) target;
		if ("enabled".equals(property)) {
			return menu.isEnabled() ? "激活" : "冻结";
		} else if ("resources".equals(property)) {
			return getPropertyIn(menu.getResources(), "title");
		} else return super.getPropertyValue(target, property);
	}

}
