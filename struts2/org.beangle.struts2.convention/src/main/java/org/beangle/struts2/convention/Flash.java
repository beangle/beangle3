/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;

public class Flash implements Map<Object, Object>, Serializable {

	private static final long serialVersionUID = -5292283953338410228L;
	public static final String MESSAGES = "messages";
	public static final String ERRORS = "errors";

	/**
	 * current request
	 */
	public final Map<Object, Object> now = CollectUtils.newHashMap();

	/**
	 * next request
	 */
	public final Map<Object, Object> next = CollectUtils.newHashMap();

	/**
	 * return now and session saved
	 * 
	 * @return
	 */
	public Set<Object> keySet() {
		return now.keySet();
	}

	/**
	 * return now and session saved value
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		return now.get(key);
	}

	/**
	 * put value to next
	 * 
	 * @param key
	 * @param value
	 */
	public Object put(Object key, Object value) {
		return next.put(key, value);
	}

	public void putAll(Map<? extends Object, ? extends Object> values) {
		next.putAll(values);
	}

	void keep(String key) {
		next.put(key, now.get(key));
	}

	void keep() {
		next.putAll(now);
	}

	public void nextToNow() {
		now.clear();
		now.putAll(next);
		next.clear();
	}

	public void clear() {
		now.clear();
	}

	public boolean containsKey(Object key) {
		return now.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return now.containsValue(value);
	}

	public Set<Map.Entry<Object, Object>> entrySet() {
		return now.entrySet();
	}

	public boolean isEmpty() {
		return now.isEmpty();
	}

	public Object remove(Object key) {
		return now.remove(key);
	}

	public int size() {
		return now.size();
	}

	public Collection<Object> values() {
		return now.values();
	}

	/**
	 * 添加消息到下一次请求
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) next.get(Flash.MESSAGES);
		if (null == messages) {
			messages = CollectUtils.newArrayList();
			put(Flash.MESSAGES, messages);
		}
		messages.add(message);
	}

	/**
	 * 添加错误消息到下一次请求
	 * 
	 * @param message
	 */
	public void addError(String error) {
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) next.get(Flash.ERRORS);
		if (null == errors) {
			errors = CollectUtils.newArrayList();
			put(Flash.ERRORS, errors);
		}
		errors.add(error);
	}

	/**
	 * 添加消息到本次请求
	 * 
	 * @param message
	 */
	public void addMessageNow(String message) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) now.get(Flash.MESSAGES);
		if (null == messages) {
			messages = CollectUtils.newArrayList();
			now.put(Flash.MESSAGES, messages);
		}
		messages.add(message);
	}
	/**
	 * 添加错误到本次请求
	 * 
	 * @param message
	 */
	public void addErrorNow(String message) {
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) now.get(Flash.ERRORS);
		if (null == errors) {
			errors = CollectUtils.newArrayList();
			now.put(Flash.ERRORS, errors);
		}
		errors.add(message);
	}
}
