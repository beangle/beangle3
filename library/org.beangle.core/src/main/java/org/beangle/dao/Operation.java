/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.beangle.collection.CollectUtils;

/**
 * @author chaostone
 * @version $Id: Operation.java Jul 25, 2011 2:21:27 PM chaostone $
 */
public class Operation {
	public static enum OperationType {
		SAVE_UPDATE, REMOVE
	}

	public final OperationType type;
	public final Object data;

	public Operation(OperationType type, Object data) {
		super();
		this.type = type;
		this.data = data;
	}

	public static Builder saveOrUpdate(Collection<?> entities) {
		return new Builder().saveOrUpdate(entities);
	}

	public static Builder saveOrUpdate(Object... entities) {
		return new Builder().saveOrUpdate(entities);
	}

	public static Builder remove(Collection<?> entities) {
		return new Builder().remove(entities);
	}

	public static Builder remove(Object... entities) {
		return new Builder().remove(entities);
	}

	public static class Builder {
		private List<Operation> operations = CollectUtils.newArrayList();

		public Builder saveOrUpdate(Collection<?> entities) {
			if (CollectionUtils.isEmpty(entities)) { return this; }
			for (Object entity : entities) {
				if (null != entity) operations.add(new Operation(OperationType.SAVE_UPDATE, entity));
			}
			return this;
		}

		public Builder saveOrUpdate(Object... entities) {
			if (ArrayUtils.isEmpty(entities)) { return this; }
			for (Object entity : entities) {
				if (null != entity) operations.add(new Operation(OperationType.SAVE_UPDATE, entity));
			}
			return this;
		}

		public Builder remove(Collection<?> entities) {
			if (CollectionUtils.isEmpty(entities)) { return this; }
			for (Object entity : entities) {
				if (null != entity) operations.add(new Operation(OperationType.REMOVE, entity));
			}
			return this;
		}

		public Builder remove(Object... entities) {
			if (ArrayUtils.isEmpty(entities)) { return this; }
			for (Object entity : entities) {
				if (null != entity) operations.add(new Operation(OperationType.REMOVE, entity));
			}
			return this;
		}

		public List<Operation> build() {
			return operations;
		}
	}

}
