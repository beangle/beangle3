/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.captcha.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Image extends AbstractCaptcha {

  private String imageStyle="width:70px; height:22px;cursor: pointer;vertical-align:top;";
  
  private String inputStyle="width:45px;height:16px";

  public Image(ValueStack stack) {
    super(stack);
  }

  public String getImageStyle() {
    return imageStyle;
  }

  public void setImageStyle(String imageStyle) {
    this.imageStyle = imageStyle;
  }

  public String getInputStyle() {
    return inputStyle;
  }

  public void setInputStyle(String inputStyle) {
    this.inputStyle = inputStyle;
  }

}
