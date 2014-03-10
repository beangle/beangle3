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
package org.beangle.struts2.captcha.action;

import org.beangle.struts2.action.ActionSupport;
import org.beangle.struts2.annotation.Result;
import org.beangle.struts2.annotation.Results;
import org.beangle.struts2.captcha.service.CaptchaProvider;

/**
 * Generator Jcaptcha image.
 * 
 * @author chaostone
 */
@Results({ @Result(name = "index", type = "captchaImage") })
public class ImageAction extends ActionSupport {

  private CaptchaProvider captchaProvider;

  /**
   * Holds the byte[] for JPEG encoded Captcha Image.
   */
  @Override
  public String index() throws Exception {
    put("captchaImage", captchaProvider.generater(getRequest()).toByteArray());
    return forward();
  }

  public void setCaptchaProvider(CaptchaProvider captchaProvider) {
    this.captchaProvider = captchaProvider;
  }

}
