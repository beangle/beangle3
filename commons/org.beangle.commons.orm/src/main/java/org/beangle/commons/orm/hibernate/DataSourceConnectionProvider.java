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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.util.JDBCExceptionReporter;

/**
 * @author chaostone
 * @version $Id: DataSourceConnectionProvider.java Feb 28, 2012 10:56:12 PM chaostone $
 */
public class DataSourceConnectionProvider implements ConnectionProvider {

  private DataSource dataSource;

  public void configure(Properties props) throws HibernateException {
    this.dataSource = SessionFactoryBean.getConfigTimeDataSource();
    // absolutely needs thread-bound DataSource to initialize
    if (this.dataSource == null) { throw new HibernateException(
        "No local DataSource found for configuration - "
            + "'dataSource' property must be set on SessionFactoryBean"); }
  }

  /**
   * Return the DataSource that this ConnectionProvider wraps.
   */
  public DataSource getDataSource() {
    return this.dataSource;
  }

  /**
   * This implementation delegates to the underlying DataSource.
   * 
   * @see javax.sql.DataSource#getConnection()
   */
  public Connection getConnection() throws SQLException {
    try {
      return this.dataSource.getConnection();
    } catch (SQLException ex) {
      JDBCExceptionReporter.logExceptions(ex);
      throw ex;
    }
  }

  /**
   * This implementation simply calls <code>Connection.close</code>.
   * 
   * @see java.sql.Connection#close()
   */
  public void closeConnection(Connection con) throws SQLException {
    try {
      con.close();
    } catch (SQLException ex) {
      JDBCExceptionReporter.logExceptions(ex);
      throw ex;
    }
  }

  /**
   * This implementation does nothing:
   * We're dealing with an externally managed DataSource.
   */
  public void close() {
  }

  /**
   * This implementation returns <code>false</code>: We cannot guarantee
   * to receive the same Connection within a transaction, not even when
   * dealing with a JNDI DataSource.
   */
  public boolean supportsAggressiveRelease() {
    return false;
  }

}
