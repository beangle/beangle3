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
package org.beangle.ems.log.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.beangle.commons.entity.annotation.LogEntity;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.ems.log.BusinessLog;
import org.beangle.ems.log.BusinessLogDetail;

/**
 * 业务日志明细
 *
 * @author chaostone
 * @version $Id: BusinessLogDetailBean.java Aug 1, 2011 3:16:29 PM chaostone $
 */
@Entity(name = "org.beangle.ems.log.BusinessLogDetail")
@LogEntity
public class BusinessLogDetailBean extends LongIdObject implements BusinessLogDetail {

  private static final long serialVersionUID = 8792899149257213752L;

  /** 操作参数 */
  @Lob
  private String content;

  /** 操作日志 */
  @ManyToOne(fetch = FetchType.LAZY)
  private BusinessLog log;

  public BusinessLogDetailBean() {
    super();
  }

  public BusinessLogDetailBean(BusinessLog log, String content) {
    super();
    this.content = content;
    this.log = log;
  }

  public String getContent() {
    return content;
  }

  public void setcontent(String content) {
    this.content = content;
  }

  public BusinessLog getLog() {
    return log;
  }

  public void setLog(BusinessLog log) {
    this.log = log;
  }

}
