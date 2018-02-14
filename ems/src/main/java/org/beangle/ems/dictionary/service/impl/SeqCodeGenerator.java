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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.beangle.ems.dictionary.model.CodeScript;
import org.beangle.ems.dictionary.service.CodeFixture;

/**
 * @author chaostone
 * @version $Id: SeqCodeGenerator.java May 5, 2011 3:57:11 PM chaostone $
 */

public class SeqCodeGenerator extends ScriptCodeGenerator {

  public static final String SEQ = "seq";

  private EntityDao entityDao;

  public void setUp() throws Exception {
    // 事先注册seq变量
    interpreter.set("seq", SEQ);
    interpreter.set("dao", entityDao);
  }

  protected CodeScript getCodeScript(String entityName) {
    OqlBuilder<CodeScript> builder = OqlBuilder.from(CodeScript.class, "codeScript");
    builder.where("codeScript.codeClassName=:codeClassName", entityName);
    builder.cacheable();
    List<CodeScript> scripts = entityDao.search(builder);
    if (scripts.size() != 1) {
      return null;
    } else {
      return scripts.get(0);
    }
  }

  /**
   * 根据请求的实体，生成代码
   */
  @SuppressWarnings("unchecked")
  public String gen(CodeFixture fixture) {
    // 在必要时查找相应的生成脚本
    String script = fixture.getScript();
    CodeScript codeScript = null;
    if (null == script) {
      codeScript = getCodeScript(EntityUtils.getEntityClassName(fixture.getEntity().getClass()));
      if (null == codeScript) { return null; }
      script = codeScript.getScript();
      try {
        String code = (String) PropertyUtils.getProperty(fixture.getEntity(), codeScript.getAttr());
        if (isValidCode(code)) { return code; }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    int seqLength = -1;
    // 替换自动代码生成中的seq[x]
    if (Strings.contains(script, SEQ)) {
      seqLength = Numbers.toInt(Strings.substringBetween(script, SEQ + "[", "]"));

      script = Strings.replace(script, SEQ + "[" + Strings.substringBetween(script, SEQ + "[", "]") + "]",
          SEQ);
    }
    fixture.setScript(script);
    String code = super.gen(fixture);
    List<String> seqs = CollectUtils.newArrayList();
    if (-1 != seqLength) {
      try {
        OqlBuilder<?> builder = OqlBuilder.from(Class.forName(codeScript.getCodeClassName()), "entity");
        builder.select("select substr(entity." + codeScript.getAttr() + "," + (code.indexOf(SEQ) + 1) + ","
            + seqLength + ")");
        builder.where(" entity." + codeScript.getAttr() + " like :codeExample",
            Strings.replace(code, SEQ, "%"));
        builder.where("length(entity." + codeScript.getAttr() + ")="
            + (code.length() - SEQ.length() + seqLength));
        seqs = (List<String>) entityDao.search(builder);
        Collections.sort(seqs);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      synchronized (this) {
        int newSeqNo = 0;
        for (Iterator<String> iter = seqs.iterator(); iter.hasNext();) {
          String seqNo = iter.next();
          if (Numbers.toInt(seqNo) - newSeqNo >= 2) {
            break;
          } else {
            newSeqNo = Numbers.toInt(seqNo);
          }
        }
        newSeqNo++;
        String seqNo = String.valueOf(newSeqNo);
        if (0 != seqLength) {
          seqNo = Strings.repeat("0", seqLength - seqNo.length()) + newSeqNo;
        }
        code = Strings.replace(code, SEQ, seqNo);
      }
    }
    return code;
  }

  public String test(CodeFixture fixture, CodeScript codeScript) {
    try {
      Class<?> codeClass = Class.forName(codeScript.getCodeClassName());
      Entity<?> entity = (Entity<?>) codeClass.newInstance();
      PropertyUtils.getProperty(entity, codeScript.getAttr());
      if (null != fixture) {
        for (Iterator<?> iter = fixture.getParams().keySet().iterator(); iter.hasNext();) {
          String param = (String) iter.next();
          interpreter.set(param, fixture.getParams().get(param));
        }
        if (Strings.isNotEmpty(fixture.getScript())) {
          interpreter.eval(fixture.getScript());
        }
      }
      return gen(new CodeFixture(entity, codeScript.getScript()));
    } catch (Exception e) {
      return Throwables.getStackTrace(e);
    }
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }
}
