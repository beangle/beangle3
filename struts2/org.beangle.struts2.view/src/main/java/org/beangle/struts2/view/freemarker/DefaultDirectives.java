/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.struts2.view.freemarker;

import org.beangle.commons.lang.annotation.Beta;
import org.beangle.struts2.view.component.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Experimental new freemarker directive api.
 * 
 * @author chaostone
 * @since 3.0.1
 */
@Beta
public class DefaultDirectives {

  public static class Toolbar extends ComponentDirective {

    public Toolbar(ValueStack stack) {
      super(stack);
    }

    @Override
    protected Component getComponent() {
      return new org.beangle.struts2.view.component.Toolbar(stack);
    }

  }

}
