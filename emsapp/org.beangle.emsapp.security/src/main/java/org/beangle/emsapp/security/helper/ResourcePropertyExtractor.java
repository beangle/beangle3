/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.helper;

import org.beangle.commons.text.TextResource;
import org.beangle.ems.security.Resource;
import org.beangle.model.transfer.exporter.DefaultPropertyExtractor;

/**
 * @author chaostone
 * @version $Id: ResourcePropertyExtractor.java Jul 17, 2011 9:41:20 PM chaostone $
 */
public class ResourcePropertyExtractor extends DefaultPropertyExtractor {

	public ResourcePropertyExtractor() {
		super();
	}

	public ResourcePropertyExtractor(TextResource textResource) {
		super(textResource);
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		Resource resource = (Resource) target;
		if ("enabled".equals(property)) {
			return resource.isEnabled() ? "激活" : "冻结";
		} else if ("scope".equals(property)) {
			String scope = "公开";
			if (1 == resource.getScope()) scope = "公有";
			else if (2 == resource.getScope()) scope = "私有";
			return scope;
		} else if ("categories".equals(property)) {
			return getPropertyIn(resource.getCategories(), "title");
		} else return super.getPropertyValue(target, property);
	}

}
