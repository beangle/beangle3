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
package org.beangle.orm.hibernate.id;

import static org.hibernate.id.PersistentIdentifierGenerator.CATALOG;
import static org.hibernate.id.PersistentIdentifierGenerator.TABLE;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.mapping.Table;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class AutoIncrementGenerator implements IdentifierGenerator, Configurable {

  IdFunctor functor = null;

  public void configure(Type t, Properties params, Dialect dialect) throws MappingException {
    String entityName = params.getProperty(IdentifierGenerator.ENTITY_NAME);
    String schema = null;
    if (null != entityName && null != TableSeqGenerator.namingStrategy)
      schema = TableSeqGenerator.namingStrategy.getSchema(entityName);

    String tableName = Table.qualify(dialect.quote(params.getProperty(CATALOG)), dialect.quote(schema),
        dialect.quote(params.getProperty(TABLE)));
    functor = new IdFunctor(tableName, (t instanceof LongType));
  }

  public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
    try {
      return functor.generate(session, object);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new HibernateException(e.getMessage());
    }
  }
}

class IdFunctor {
  String tableName;
  boolean isLong = true;
  static String sql = "{? = call next_id(?)}";

  public IdFunctor(String tableName, boolean isLong) {
    super();
    this.tableName = tableName;
    this.isLong = isLong;
  }

  public java.io.Serializable generate(SessionImplementor session, Object obj) throws SQLException {
    JdbcCoordinator jdbc = session.getTransactionCoordinator().getJdbcCoordinator();
    CallableStatement st = (CallableStatement) jdbc.getStatementPreparer().prepareStatement(sql, true);
    try {
      st.registerOutParameter(1, java.sql.Types.BIGINT);
      st.setString(2, tableName);
      st.execute();
      Number id = st.getLong(1);
      if (!isLong) id = Integer.valueOf(id.intValue());
      System.out.println("generator a new id :" + id + " for " + tableName);
      return id;
    } finally {
      jdbc.release(st);
    }
  }
}
