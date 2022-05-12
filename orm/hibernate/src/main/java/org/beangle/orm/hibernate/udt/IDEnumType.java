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

import org.beangle.commons.lang.IDEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.LoggableUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class IDEnumType implements EnhancedUserType, DynamicParameterizedType, LoggableUserType, Serializable {
  private Class<? extends IDEnum> enumClass;

  @Override
  public String objectToSQLString(Object value) {
    return String.valueOf(((IDEnum) value).getId());
  }

  @Override
  public String toXMLString(Object value) {
    return String.valueOf(((IDEnum) value).getId());
  }

  @Override
  public Object fromXMLString(String xmlValue) {
    return null;
  }

  @Override
  public String toLoggableString(Object value, SessionFactoryImplementor factory) {
    return value.toString();
  }

  @Override
  public void setParameterValues(Properties parameters) {
    final ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
    enumClass = reader.getReturnedClass().asSubclass(IDEnum.class);
  }

  @Override
  public int[] sqlTypes() {
    return new int[]{Types.INTEGER};
  }

  @Override
  public Class<? extends IDEnum> returnedClass() {
    return enumClass;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    return x == y;
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return x == null ? 0 : x.hashCode();
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
    final int id = rs.getInt(names[0]);
    if (rs.wasNull()) {
      return null;
    }
    for (IDEnum value : returnedClass().getEnumConstants()) {
      if (id == value.getId()) {
        return value;
      }
    }
    throw new IllegalStateException("unknown enum  " + returnedClass().getName() + " id:" + id);
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, Types.INTEGER);
    } else {
      st.setInt(index, ((IDEnum) value).getId());
    }
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return cached;
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }
}
