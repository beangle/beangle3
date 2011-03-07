/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.ds;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.lang.Validate;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoolingDataSourceFactory {

	private static final Logger logger = LoggerFactory.getLogger(PoolingDataSourceFactory.class);
	protected String url;
	protected String username;
	protected String password;

	public PoolingDataSourceFactory() {
		super();
	}

	public PoolingDataSourceFactory(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public PoolingDataSourceFactory(String driverClassName, String url, String username, String password) {
		super();
		setDriverClassName(driverClassName);
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public void setDriverClassName(String driverClassName) {
		Validate.notEmpty(driverClassName, "Property 'driverClassName' must not be empty");
		String driverClassNameToUse = driverClassName.trim();
		try {
			Class.forName(driverClassNameToUse);
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Could not load JDBC driver class [" + driverClassNameToUse + "]", ex);
		}
		logger.info("Loaded JDBC driver: {}", driverClassNameToUse);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DataSource getObject() {
		GenericObjectPool.Config config=new GenericObjectPool.Config();
		config.maxActive=16;
		ObjectPool connectionPool = new GenericObjectPool(null,config);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, username, password);
		new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
		return new PoolingDataSource(connectionPool);
	}

	public Class<?> getObjectType() {
		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
