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
package org.beangle.struts1.support;

import java.util.Locale;

import org.apache.struts.util.MessageResources;
import org.beangle.commons.text.i18n.TextResource;

public class StrutsMessageResource implements TextResource {

  public StrutsMessageResource(Locale locale, MessageResources resources) {
    this.locale = locale;
    this.resources = resources;
  }

  @Override
  public String getText(String key) {
    return resources.getMessage(locale, key);
  }

  @Override
  public String getText(String key, String defaultValue, Object... obj) {
    return resources.getMessage(locale, key, obj);
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  protected MessageResources resources;
  protected Locale locale;
}
