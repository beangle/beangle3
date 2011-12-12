/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.lang;

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
