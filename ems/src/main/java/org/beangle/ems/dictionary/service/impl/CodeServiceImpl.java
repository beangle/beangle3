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
package org.beangle.ems.dictionary.service.impl;

import java.sql.Date;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.pojo.Code;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.service.CodeService;

/**
 * @author chaostone
 * @version $Id: CodeServiceImpl.java May 5, 2011 3:33:07 PM chaostone $
 */
public class CodeServiceImpl extends BaseServiceImpl implements CodeService {

  public <T extends Code<Integer>> T getCode(Class<T> codeClass, String code) {
    OqlBuilder<T> builder = OqlBuilder.from(codeClass, "Code").where("Code.code=:code", code);
    List<T> rs = entityDao.search(builder);
    if (!rs.isEmpty()) return rs.get(0);
    else return null;
  }

  public <T extends Code<Integer>> List<T> getCodes(Class<T> codeClass) {
    OqlBuilder<T> builder = OqlBuilder.from(codeClass, "Code").where(
        "Code.effectiveAt <= :now and (Code.invalidAt is null or Code.invalidAt >= :now)",
        new java.util.Date());
    builder.orderBy("Code.code");
    return entityDao.search(builder);
  }

  public <T extends Code<Integer>> T getCode(Class<T> codeClass, Integer codeId) {
    return entityDao.get(codeClass, codeId);
  }

  public <T extends Code<Integer>> List<T> getCodes(Class<T> type, Integer... ids) {
    OqlBuilder<T> builder = OqlBuilder.from(type, "Code").where("Code.id in(:ids)", ids);
    return entityDao.search(builder);
  }

  @SuppressWarnings("unchecked")
  public Class<? extends Code<Integer>> getCodeType(String name) {
    OqlBuilder<CodeMeta> builder = OqlBuilder.from(CodeMeta.class, "coder");
    builder.where("coder.name=:name or coder.enName=:name", name);
    List<CodeMeta> coders = entityDao.search(builder);
    try {
      if (1 != coders.size()) return null;
      else return (Class<? extends Code<Integer>>) Class.forName(coders.get(0).getClassName());
    } catch (ClassNotFoundException e) {
      logger.error("Code " + name + "type not found", e);
      throw new RuntimeException(e);
    }
  }

  public void removeCodes(Class<? extends Code<Integer>> codeClass, Integer... codeIds) {
    entityDao.remove(entityDao.get(codeClass, codeIds));
  }

  public void saveOrUpdate(Code<Integer> code) {
    code.setUpdatedAt(new Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(code);
  }
}
