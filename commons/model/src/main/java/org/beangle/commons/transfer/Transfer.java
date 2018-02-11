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
package org.beangle.commons.transfer;

import java.util.Locale;

import org.beangle.commons.transfer.io.TransferFormat;

/**
 * 导入导出数据转换器
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Transfer {

  /**
   * 启动转换
   * 
   * @param tr a {@link org.beangle.commons.transfer.TransferResult} object.
   */
  void transfer(TransferResult tr);

  /**
   * 转换一个对象
   */
  void transferItem();

  /**
   * 添加转换监听器
   * 
   * @param listener a {@link org.beangle.commons.transfer.TransferListener} object.
   * @return a {@link org.beangle.commons.transfer.Transfer} object.
   */
  Transfer addListener(TransferListener listener);

  /**
   * 转换数据的类型
   * 
   * @return a {@link java.lang.String} object.
   */
  TransferFormat getFormat();

  /**
   * 转换使用的locale
   * 
   * @return a {@link java.util.Locale} object.
   */
  Locale getLocale();

  /**
   * 转换数据的名称
   * 
   * @return a {@link java.lang.String} object.
   */
  String getDataName();

  /**
   * 得到转换过程中失败的个数
   * 
   * @return a int.
   */
  int getFail();

  /**
   * 得到转换过程中成功的个数
   * 
   * @return a int.
   */
  int getSuccess();

  /**
   * 查询正在转换的对象的次序号,从1开始
   * 
   * @return a int.
   */
  int getTranferIndex();

  /**
   * 返回方前正在转换成的对象
   * 
   * @return a {@link java.lang.Object} object.
   */
  Object getCurrent();
}
