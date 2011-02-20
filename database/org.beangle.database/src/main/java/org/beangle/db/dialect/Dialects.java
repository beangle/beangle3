/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

public class Dialects {

	public static final String HSQL = "HSQL";

	public static final String H2 = "H2";

	private static final Map<String, Constructor<? extends Dialect>> constructors = CollectUtils.newHashMap();

	static {
		register(DB2Dialect.class);
		register(DerbyDialect.class);
		register(H2Dialect.class);
		register(HSQLDialect.class);
		register(OracleDialect.class);
		register(PostgreSQLDialect.class);
		register(SQLServer2005Dialect.class);
	}

	public static Dialect getDialect(String dialectName) {
		Constructor<? extends Dialect> con = constructors.get(dialectName);
		if (null == con) {
			throw new RuntimeException(dialectName + " not supported");
		} else {
			try {
				return con.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("cannot construct a instance of " + dialectName, e);
			}
		}
	}

	public static void register(Class<? extends Dialect> clazz) {
		String name = StringUtils.substringBefore(clazz.getSimpleName(), "Dialect");
		register(name, clazz);
	}

	public static void register(String shortname, Class<? extends Dialect> clazz) {
		Constructor<? extends Dialect> con = null;
		try {
			con = clazz.getConstructor();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		constructors.put(shortname, con);
	}
}
