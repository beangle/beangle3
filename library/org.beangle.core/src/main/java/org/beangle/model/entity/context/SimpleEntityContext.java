/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.beangle.model.entity.Type;
import org.beangle.model.entity.types.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleEntityContext extends AbstractEntityContext {

	private static final Logger logger = LoggerFactory.getLogger(SimpleEntityContext.class);

	public SimpleEntityContext() {
		Properties props = new Properties();
		try {
			InputStream is = AbstractEntityContext.class.getResourceAsStream("/model.properties");
			if (null != is) {
				props.load(is);
			}
		} catch (IOException e) {
			logger.error("read error model.properties");
		}

		if (!props.isEmpty()) {
			logger.info("Using model.properties initialize Entity Context.");
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				EntityType entityType = new EntityType();
				entityType.setEntityName(key);
				try {
					entityType.setEntityClass(Class.forName(value));
				} catch (ClassNotFoundException e) {
					logger.error(value + " was not correct class name", e);
				}
				entityType.setPropertyTypes(new HashMap<String, Type>());
				entityTypes.put(key, entityType);
			}
		}
	}
}
