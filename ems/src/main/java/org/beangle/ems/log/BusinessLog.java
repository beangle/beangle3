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
package org.beangle.ems.log;

import java.util.Date;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.annotation.LogEntity;

/**
 * 业务日志
 *
 * @author chaostone
 * @version $Id: BusinessLog.java Jun 27, 2011 7:28:23 PM chaostone $
 */
@LogEntity
public interface BusinessLog extends Entity<Long> {

  /**
   * 操作人员
   */
  String getOperator();

  /**
   * 操作内容
   */
  String getOperation();

  /**
   * 操作资源
   */
  String getResource();

  /**
   * 操作时间
   */
  Date getOperateAt();

  /**
   * 操作地址
   */
  String getIp();

  /**
   * 操作的系统入口
   */
  String getEntry();

  /**
   * 客户端代理
   */
  String getAgent();

  /**
   * 详细内容
   */
  BusinessLogDetail getDetail();

}
