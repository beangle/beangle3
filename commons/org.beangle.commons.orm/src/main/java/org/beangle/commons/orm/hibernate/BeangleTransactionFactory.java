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

import org.hibernate.ConnectionReleaseMode;
import org.hibernate.Transaction;
import org.hibernate.jdbc.JDBCContext;
import org.hibernate.transaction.JDBCTransaction;
import org.hibernate.transaction.TransactionFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author chaostone
 * @version $Id: BeangleTransactionFactory.java Feb 28, 2012 10:29:04 PM chaostone $
 */
public class BeangleTransactionFactory implements TransactionFactory {

  /**
   * Sets connection release mode "on_close" as default.
   * <p>
   * This was the case for Hibernate 3.0; Hibernate 3.1 changed it to "auto" (i.e. "after_statement"
   * or "after_transaction"). However, for Spring's resource management (in particular for
   * HibernateTransactionManager), "on_close" is the better default.
   */
  public ConnectionReleaseMode getDefaultReleaseMode() {
    return ConnectionReleaseMode.ON_CLOSE;
  }

  public Transaction createTransaction(JDBCContext jdbcContext, Context transactionContext) {
    return new JDBCTransaction(jdbcContext, transactionContext);
  }

  public void configure(Properties props) {
  }

  public boolean isTransactionManagerRequired() {
    return false;
  }

  public boolean areCallbacksLocalToHibernateTransactions() {
    return true;
  }

  public boolean isTransactionInProgress(JDBCContext jdbcContext, Context transactionContext,
      Transaction transaction) {

    return (transaction != null && transaction.isActive())
        || TransactionSynchronizationManager.isActualTransactionActive();
  }

}
