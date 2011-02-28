/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

/**
 * This class maps a type to names. Associations may be marked with a capacity.
 * Calling the get() method with a type and actual size n will return the
 * associated name with smallest capacity >= n, if available and an unmarked
 * default type otherwise. Eg, setting
 * 
 * <pre>
 * names.put(type, &quot;TEXT&quot;);
 * names.put(type, 255, &quot;VARCHAR($l)&quot;);
 * names.put(type, 65534, &quot;LONGVARCHAR($l)&quot;);
 * </pre>
 * 
 * will give you back the following:
 * 
 * <pre>
 *  names.get(type)         // --&gt; &quot;TEXT&quot; (default)
 *  names.get(type,    100) // --&gt; &quot;VARCHAR(100)&quot; (100 is in [0:255])
 *  names.get(type,   1000) // --&gt; &quot;LONGVARCHAR(1000)&quot; (1000 is in [256:65534])
 *  names.get(type, 100000) // --&gt; &quot;TEXT&quot; (default)
 * </pre>
 * 
 * On the other hand, simply putting
 * 
 * <pre>
 * names.put(type, &quot;VARCHAR($l)&quot;);
 * </pre>
 * 
 * would result in
 * 
 * <pre>
 *  names.get(type)        // --&gt; &quot;VARCHAR($l)&quot; (will cause trouble)
 *  names.get(type, 100)   // --&gt; &quot;VARCHAR(100)&quot;
 *  names.get(type, 10000) // --&gt; &quot;VARCHAR(10000)&quot;
 * </pre>
 * 
 * @author Christoph Beck
 */
public class TypeNames {

	private Map<Integer, Map<Integer, String>> weighted = CollectUtils.newHashMap();
	private Map<Integer, String> defaults = CollectUtils.newHashMap();

	/**
	 * get default type name for specified type
	 * 
	 * @param typecode
	 *            the type key
	 * @return the default type name associated with specified key
	 */
	public String get(int typecode) {
		String result = defaults.get(new Integer(typecode));
		if (result == null) throw new RuntimeException("No Dialect mapping for JDBC type: " + typecode);
		return result;
	}

	/**
	 * get type name for specified type and size
	 * 
	 * @param typecode
	 *            the type key
	 * @param size
	 *            the SQL length
	 * @param precision
	 *            the SQL precision
	 * @param scale
	 *            the SQL scale
	 * @return the associated name with smallest capacity >= size, if available
	 *         and the default type name otherwise
	 */
	public String get(int typecode, int size, int precision, int scale) {
		Map<Integer, String> map = weighted.get(Integer.valueOf(typecode));
		if (map != null && map.size() > 0) {
			// iterate entries ordered by capacity to find first fit
			for (Map.Entry<Integer, String> entry : map.entrySet()) {
				if (size <= ((Integer) entry.getKey()).intValue()) { return replace(
						(String) entry.getValue(), size, precision, scale); }
			}
		}
		return replace(get(typecode), size, precision, scale);
	}

	private static String replace(String type, int size, int precision, int scale) {
		type = StringUtils.replace(type, "$s", Integer.toString(scale));
		type = StringUtils.replace(type, "$l", Integer.toString(size));
		return StringUtils.replace(type, "$p", Integer.toString(precision));
	}

	/**
	 * set a type name for specified type key and capacity
	 * 
	 * @param typecode
	 *            the type key
	 */
	public void put(int typecode, int capacity, String value) {
		Map<Integer, String> map = weighted.get(new Integer(typecode));
		if (map == null) {// add new ordered map
			map = new TreeMap<Integer, String>();
			weighted.put(new Integer(typecode), map);
		}
		map.put(new Integer(capacity), value);
	}

	/**
	 * set a default type name for specified type key
	 * 
	 * @param typecode
	 *            the type key
	 */
	public void put(int typecode, String value) {
		defaults.put(new Integer(typecode), value);
	}
}
