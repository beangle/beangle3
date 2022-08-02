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

public class HourMinuteType implements UserType {

  /** @Override */
  public int[] sqlTypes() {
    return new int[] { Types.SMALLINT };
  }

  /** @Override */
  public Class returnedClass() {
    return HourMinute.class;
  }

  /** @Override */
  public boolean equals(Object x, Object y) {
    if (x instanceof HourMinute && y instanceof HourMinute) {
      return ((HourMinute) x).value == ((HourMinute) y).value;
    } else {
      return false;
    }
  }

  /** @Override */
  public int hashCode(Object x) {
    return x.hashCode();
  }

  /** @Override */
  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
      throws HibernateException, SQLException {
    short value = resultSet.getShort(names[0]);
    if (resultSet.wasNull()) return null;
    else return new HourMinute(value);
  }

  /** @Override */
  public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      statement.setNull(index, Types.SMALLINT);
    } else {
      statement.setShort(index, ((HourMinute) value).value);
    }
  }

  /** @Override */
  public Object deepCopy(Object value) {
    return value;
  }

  /** @Override */
  public boolean isMutable() {
    return false;
  }

  /** @Override */
  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  /** @Override */
  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  /** @Override */
  public Object replace(Object original, Object target, Object owner) {
    return original;
  }
}
