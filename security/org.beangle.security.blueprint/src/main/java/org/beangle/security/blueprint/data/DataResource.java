/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data;

import org.beangle.security.blueprint.Resource;

/**
 * 数据资源
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface DataResource extends Resource {

  /** 允许对数据的CRUD操作 */
  /** 创建 */
  public static final String CreateAction = "insert";

  /** 读取 */
  public static final String ReadAction = "select";

  /** 删除 */
  public static final String DeleteAction = "delete";

  /** 更新 */
  public static final String UpdateAction = "update";

  /** 写操作(包括读以外的其他操作) */
  public static final String WriteAction = CreateAction + "," + UpdateAction + "," + DeleteAction;
  
}
