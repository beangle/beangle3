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
package org.beangle.orm.hibernate.udt;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class WeekStateType implements UserType {

  public int[] sqlTypes() {
    return new int[] { Types.BIGINT };
  }

  public Class returnedClass() {
    return WeekState.class;
  }

  public boolean equals(Object x, Object y) {
    if (x instanceof WeekState && y instanceof WeekState) {
      try {
        return ((WeekState) x).value == ((WeekState) y).value;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  public int hashCode(Object x) {
    return x.hashCode();
  }

  public Object deepCopy(Object value) {
    return value;
  }

  public boolean isMutable() {
    return false;
  }

  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  public Object replace(Object original, Object target, Object owner) {
    return original;
  }

  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
      throws HibernateException, SQLException {
    Long value = resultSet.getLong(names[0]);
    if (resultSet.wasNull()) return null;
    else return new WeekState(value);
  }

  public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      statement.setNull(index, Types.BIGINT);
    } else {
      statement.setLong(index, ((WeekState) value).value);
    }
  }
}
