/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
