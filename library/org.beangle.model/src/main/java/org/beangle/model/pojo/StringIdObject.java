/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.beangle.model.predicates.ValidEntityKeyPredicate;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class StringIdObject implements StringIdEntity {

	private static final long serialVersionUID = -6898498932182877104L;

	@Id
	@GeneratedValue(generator = "id_assigned")
	@GenericGenerator(name = "id_assigned", strategy = "assigned")
	protected String id;

	public StringIdObject() {
		super();
	}

	public StringIdObject(String id) {
		super();
		this.id = id;
	}

	public String getIdentifier() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isPersisted() {
		return ValidEntityKeyPredicate.INSTANCE.evaluate(id);
	}

	public boolean isTransient() {
		return !ValidEntityKeyPredicate.INSTANCE.evaluate(id);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-64900959, -454788261).append(this.id).toHashCode();
	}

	/**
	 * 比较id,如果任一方id是null,则不相等
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(final Object object) {
		if (!(object instanceof StringIdObject)) { return false; }
		StringIdObject rhs = (StringIdObject) object;
		if (null == getId() || null == rhs.getId()) { return false; }
		return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
	}

}
