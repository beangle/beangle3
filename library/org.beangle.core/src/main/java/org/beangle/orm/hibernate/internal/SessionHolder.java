/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.hibernate.internal;

import org.apache.commons.lang.Validate;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * @author chaostone
 * @version $Id: SessionHolder.java Feb 27, 2012 10:24:03 PM chaostone $
 */
public class SessionHolder extends ResourceHolderSupport {
	
	private final Session session;

	private Transaction transaction;

	private FlushMode previousFlushMode;

	public SessionHolder(Session session) {
		Validate.notNull(session, "Session must not be null");
		this.session = session;
	}

	public Session getSession() {
		return this.session;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setPreviousFlushMode(FlushMode previousFlushMode) {
		this.previousFlushMode = previousFlushMode;
	}

	public FlushMode getPreviousFlushMode() {
		return this.previousFlushMode;
	}

	@Override
	public void clear() {
		super.clear();
		this.transaction = null;
		this.previousFlushMode = null;
	}

}
