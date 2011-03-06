/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.Adminuser;
import org.beangle.security.blueprint.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "org.beangle.security.blueprint.Adminuser")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdminuserBean extends LongIdTimeObject implements Adminuser {
	private static final long serialVersionUID = 6491945157573106200L;

	@NotNull
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
