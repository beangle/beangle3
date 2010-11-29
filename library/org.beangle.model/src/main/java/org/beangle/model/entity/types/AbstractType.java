/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.types;

import org.beangle.model.entity.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractType implements Type {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractType.class);

	public boolean isCollectionType() {
		return false;
	}

	public boolean isComponentType() {
		return false;
	}

	public boolean isEntityType() {
		return false;
	}

	public Type getPropertyType(String property) {
		return null;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Type)) { return false; }
		return getName().equals(((Type) obj).getName());
	}

	public int hashCode() {
		return getName().hashCode();
	}

	public String toString() {
		return getName();
	}

	public abstract String getName();

	public abstract Class<?> getReturnedClass();

	public Object newInstance() {
		try {
			return getReturnedClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
