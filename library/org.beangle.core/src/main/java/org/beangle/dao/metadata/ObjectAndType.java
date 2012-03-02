/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.metadata;

/**
 * 对象和类型
 * 
 * @author chaostone
 */
public class ObjectAndType {

	private Object obj;
	private Type type;

	public ObjectAndType(Object obj, Type type) {
		super();
		this.obj = obj;
		this.type = type;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
