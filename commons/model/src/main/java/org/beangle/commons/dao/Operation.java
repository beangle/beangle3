/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.dao;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Arrays;

/**
 * <p>
 * Operation class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: Operation.java Jul 25, 2011 2:21:27 PM chaostone $
 */
public class Operation {
  public static enum OperationType {
    SAVE_UPDATE, REMOVE
  }

  public final OperationType type;
  public final Object data;

  /**
   * <p>
   * Constructor for Operation.
   * </p>
   * 
   * @param type a {@link org.beangle.commons.dao.Operation.OperationType} object.
   * @param data a {@link java.lang.Object} object.
   */
  public Operation(OperationType type, Object data) {
    super();
    this.type = type;
    this.data = data;
  }

  /**
   * <p>
   * saveOrUpdate.
   * </p>
   * 
   * @param entities a {@link java.util.Collection} object.
   * @return a {@link org.beangle.commons.dao.Operation.Builder} object.
   */
  public static Builder saveOrUpdate(Collection<?> entities) {
    return new Builder().saveOrUpdate(entities);
  }

  /**
   * <p>
   * saveOrUpdate.
   * </p>
   * 
   * @param entities a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.Operation.Builder} object.
   */
  public static Builder saveOrUpdate(Object... entities) {
    return new Builder().saveOrUpdate(entities);
  }

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param entities a {@link java.util.Collection} object.
   * @return a {@link org.beangle.commons.dao.Operation.Builder} object.
   */
  public static Builder remove(Collection<?> entities) {
    return new Builder().remove(entities);
  }

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param entities a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.Operation.Builder} object.
   */
  public static Builder remove(Object... entities) {
    return new Builder().remove(entities);
  }

  public static class Builder {
    private List<Operation> operations = CollectUtils.newArrayList();

    public Builder saveOrUpdate(Collection<?> entities) {
      if (CollectUtils.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.SAVE_UPDATE, entity));
      }
      return this;
    }

    public Builder saveOrUpdate(Object... entities) {
      if (Arrays.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.SAVE_UPDATE, entity));
      }
      return this;
    }

    public Builder remove(Collection<?> entities) {
      if (CollectUtils.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.REMOVE, entity));
      }
      return this;
    }

    public Builder remove(Object... entities) {
      if (Arrays.isEmpty(entities)) { return this; }
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
