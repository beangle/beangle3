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
package org.beangle.commons.lang.tuple;

import org.beangle.commons.lang.Objects;

public class Triple<X, Y, Z> {

  public final X _1;
  public final Y _2;
  public final Z _3;

  public Triple(X e1, Y e2, Z e3) {
    this._1 = e1;
    this._2 = e2;
    this._3 = e3;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
    result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
    result = prime * result + ((_3 == null) ? 0 : _3.hashCode());
    return result;
  }

  
  public X get_1() {
    return _1;
  }

  public Y get_2() {
    return _2;
  }

  public Z get_3() {
    return _3;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
    return Objects.equalsBuilder().add(_1, other._1).add(_2, other._2).add(_3, other._3).isEquals();
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("_1", _1).add("_2", _2).add("_3", _3).toString();
  }
}
