/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.captcha.service;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * Captcha 提供者
 * @author chaostone
 *
 */
public interface CaptchaProvider {

  /**
   * 生成验证字节流
   * @param request
   * @return null if non local provider.
   */
  ByteArrayOutputStream generater(HttpServletRequest request);

  /**
   * 验证用户输入
   * @param request
   * @param response
   * @return
   */
  boolean verify(HttpServletRequest request,String response);

}
