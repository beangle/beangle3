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
package org.beangle.struts2.view.template;

import java.util.Stack;

/**
 * ui主体栈
 * 
 * @author chaostone
 * @version $Id: ThemeStack.java Jul 28, 2011 12:04:52 PM chaostone $
 */
public class ThemeStack {

  private Stack<Theme> themes = new Stack<Theme>();

  public Theme push(Theme item) {
    return themes.push(item);
  }

  public Theme pop() {
    return themes.pop();
  }

  public Theme peek() {
    return themes.peek();
  }

  public boolean isEmpty() {
    return themes.isEmpty();
  }

}
