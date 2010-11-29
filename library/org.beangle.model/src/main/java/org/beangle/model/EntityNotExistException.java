/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model;

/**
 * 删除Pojo时，没有对应的对象异常.
 * 
 * @author chaostone 2005-9-15
 */
public class EntityNotExistException extends RuntimeException {
	private static final long serialVersionUID = 3136311427259768845L;

	public EntityNotExistException(final String message) {
		super(message);
	}
}
