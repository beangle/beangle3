/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.orm.hibernate;

import java.util.Properties;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.orm.TableNamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 按照表明进行命名序列<br>
 * 依据命名模式进行，默认模式{table}_seq<br>
 * 该生成器可以
 * 
 * <pre>
 * 1)具有较好的数据库移植性，支持没有sequence的数据库。
 * 2)可以通过设置优化起进行优化
 * 3)可以按照表名进行自动命名序列名，模式{table}_seq
 * </pre>
 * 
 * @author chaostone
 */
public class TableSeqGenerator extends SequenceStyleGenerator {
  private static final Logger logger = LoggerFactory.getLogger(TableSeqGenerator.class);

  /** Updated by OverrideConfiguration.secondPass */
  public static TableNamingStrategy namingStrategy;

  // 表名、字段名、序列长度
  private static final int MaxLength = 30;
  /** 序列命名模式 */
  private String sequencePattern = "seq_{table}";

  /**
   * If the parameters do not contain a {@link SequenceGenerator#SEQUENCE} name, we assign one
   * based on the table name.
   */
  public void configure(Type type, Properties params, Dialect dialect) {
    if (Strings.isEmpty(params.getProperty(SEQUENCE_PARAM))) {
      String tableName = params.getProperty(PersistentIdentifierGenerator.TABLE);
      String pk = params.getProperty(PersistentIdentifierGenerator.PK);
      if (null != tableName) {
        String seqName = Strings.replace(sequencePattern, "{table}", tableName);
        seqName = Strings.replace(seqName, "{pk}", pk);
        if (seqName.length() > MaxLength) {
          logger.error("{}'s length >=30, wouldn't be supported in some db!", seqName);
        }
        String entityName = params.getProperty(IdentifierGenerator.ENTITY_NAME);
        if (null != entityName && null != namingStrategy) {
          String schema = namingStrategy.getSchema(entityName);
          if (null != schema) seqName = schema + "." + seqName;
        }
        params.setProperty(SEQUENCE_PARAM, seqName);
      }
    }
    super.configure(type, params, dialect);
  }

  public String getSequencePattern() {
    return sequencePattern;
  }

  public void setSequencePattern(String sequencePattern) {
    this.sequencePattern = sequencePattern;
  }

}
