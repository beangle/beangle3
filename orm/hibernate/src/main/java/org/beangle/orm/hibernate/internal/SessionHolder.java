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
package org.beangle.orm.hibernate.internal;

import org.beangle.commons.lang.Assert;
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
    Assert.notNull(session, "Session must not be null");
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
