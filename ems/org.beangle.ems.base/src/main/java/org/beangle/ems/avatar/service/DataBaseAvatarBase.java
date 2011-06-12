/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.service;

import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.model.ByteArrayAvatar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class DataBaseAvatarBase extends AbstractAvatarBase {

	private String sql;

	private JdbcTemplate jdbcTemplate;

	public Avatar getAvatar(String name) {
		byte[] buf = getBytes(name);
		if (null == buf) {
			return null;
		} else {
			return new ByteArrayAvatar(name, "jpeg", buf);
		}
	}

	public boolean updateAvatar(String name, File avatar, String type) {
		return false;
	}

	public Page<String> getAvatarNames(PageLimit limit) {
		return null;
	}

	public String getDescription() {
		return "DataBaseAvatarBase";
	}

	protected byte[] getBytes(String name) {
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, name);
		Blob obj = null;
		if (rowSet.next()) {
			obj = (Blob) rowSet.getObject(1);
			try {
				return obj.getBytes(1L, (int) obj.length());
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setDataSource(DataSource dataSource) {
		if (null != dataSource) {
			jdbcTemplate = new JdbcTemplate(dataSource);
		} else {
			jdbcTemplate = null;
		}
	}
}
