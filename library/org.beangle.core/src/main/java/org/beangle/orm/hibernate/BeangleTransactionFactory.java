/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.hibernate;

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
	 * This was the case for Hibernate 3.0; Hibernate 3.1 changed it to "auto" (i.e.
	 * "after_statement" or "after_transaction"). However, for Spring's resource management (in
	 * particular for HibernateTransactionManager), "on_close" is the better default.
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
