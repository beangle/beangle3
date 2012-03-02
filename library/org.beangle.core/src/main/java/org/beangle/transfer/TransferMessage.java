/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 转换消息
 * 
 * @author chaostone
 */
public class TransferMessage {

	public static final String ERROR_ATTRS = "error.transfer.attrs";

	public static final String ERROR_ATTRS_EXPORT = "error.transfer.attrs.export";

	/**
	 * 转换数据的序号
	 */
	int index;

	/**
	 * 消息内容
	 */
	String message;

	/**
	 * 消息中使用的对应值
	 */
	List<Object> values = new ArrayList<Object>();

	public TransferMessage(int index, String message, Object value) {
		this.index = index;
		this.message = message;
		this.values.add(value);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("index", this.index)
				.append("message", this.message).append("values", this.values).toString();
	}

}
