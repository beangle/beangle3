/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata.impl;

import java.io.InputStream;
import java.util.Properties;

import org.beangle.commons.dao.metadata.EntityContext;
import org.beangle.commons.dao.metadata.Model;
import org.beangle.commons.dao.metadata.ModelBuilder;
import org.beangle.commons.dao.metadata.Populator;

/**
 * <p>
 * DefaultModelBuilder class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DefaultModelBuilder implements ModelBuilder {

  private static final String POPULATOR_PRO = "model.populatorClass";

  private static final String CONTEXT_PRO = "model.contextClass";

  /**
   * 初始化默认对象
   */
  public void build() {
    try {
      Properties props = new Properties();
      InputStream is = AbstractEntityContext.class
          .getResourceAsStream("/org/beangle/commons/dao/metadata/model-default.properties");
      if (null != is) {
        props.load(is);
      }
      is = AbstractEntityContext.class.getResourceAsStream("/model.properties");
      if (null != is) {
        props.load(is);
      }
      if (null == Model.getContext()) {
        Class<?> contextClass = Class.forName((String) props.get(CONTEXT_PRO));
        Model.setContext((EntityContext) contextClass.newInstance());
      }
      if (null == Model.getPopulator()) {
        Class<?> populatorClass = Class.forName((String) props.get(POPULATOR_PRO));
        Model.setPopulator((Populator) populatorClass.newInstance());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <p>
   * getModel.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.metadata.Model} object.
   */
  public Model getModel() {
    return Model.getInstance();
  }

}
