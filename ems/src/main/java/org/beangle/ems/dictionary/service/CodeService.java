/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.dictionary.service;

import java.util.List;

import org.beangle.commons.entity.pojo.Code;

/**
 * @author chaostone
 * @version $Id: CodeService.java May 4, 2011 7:49:02 PM chaostone $
 */
public interface CodeService {
  /**
   * 依据code的类型和主键查找,无效时返回null.
   *
   * @param type
   * @param codeId
   */
  <T extends Code<Integer>> T getCode(Class<T> type, Integer codeId);

  /**
   * 依据code的类型和代码查找,无效时返回null.
   *
   * @param type
   * @param code
   */
  <T extends Code<Integer>> T getCode(Class<T> type, String code);

  /**
   * 返回现有的有效使用的代码
   *
   * @param type
   */
  <T extends Code<Integer>> List<T> getCodes(Class<T> type);

  /**
   * 查询指定id的基础代码
   *
   * @param type
   * @param ids
   */
  <T extends Code<Integer>> List<T> getCodes(Class<T> type, Integer... ids);

  /**
   * 查找指定名称基础代码
   *
   * @param name
   */
  Class<? extends Code<Integer>> getCodeType(String name);

  /**
   * 新增代码 如果新的代码已经存在，则抛出异常.
   *
   * @param code
   */
  void saveOrUpdate(Code<Integer> code);

  /**
   * 删除基础代码
   *
   * @param codeClass
   * @param codeIds
   */
  void removeCodes(Class<? extends Code<Integer>> codeClass, Integer... codeIds);
}
