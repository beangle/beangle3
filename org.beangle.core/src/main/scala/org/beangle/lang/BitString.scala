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
 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.lang

object BitString {
  def apply(str: String): BitString = {
    new BitString(java.lang.Long.parseLong(str,2));
  }

  def apply(value: Long): BitString = {
    new BitString(value);
  }
  def add(first: String, second: String): String = BitString(first).and(BitString(second)).toString

  def or(first: String, second: String): String = BitString(first).or(BitString(second)).toString
}

class BitString(val value: Long) {

  val str: String = value.toBinaryString

  def length = str.length

  def and(other: BitString): BitString = new BitString(other.value & this.value)

  def or(other: BitString): BitString = new BitString(other.value | this.value)

  override def toString = str

  def toString(maxLength: Int) = "0" * (maxLength - str.length) + str
}
