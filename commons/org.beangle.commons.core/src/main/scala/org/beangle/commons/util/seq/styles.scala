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
package org.beangle.commons.util.seq

object SeqNumStyle {
  val Hanzi = new HanziSeqStyle()
  val Arabic = new ArabicSeqStyle()
}

trait SeqNumStyle {
  def build(seq: Int): String;
}

class ArabicSeqStyle extends SeqNumStyle {
  def build(seq: Int) = seq.toString
}

object HanziSeqStyle {
  val Max = 99999
  val Names = Array("零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十")
  val Priorities = Array("十", "百", "千", "万")
}

import HanziSeqStyle._

class HanziSeqStyle extends SeqNumStyle {
  def build(seq: Int): String = if (seq > Max) throw new RuntimeException("seq greate than " + Max) else buildText(seq.toString)

  def apply(seq: Int): String = build(seq)

  private def buildText(seq: String): String = {
    val sb = new StringBuilder()
    for (i <- 0 until seq.length) {
      val c = seq(i)
      sb ++= (if (c - '0' > 0) {
        Names(c - '0') + priority(seq.length - i)
      } else {
        Names(c - '0')
      })
    }
    sb.result.replaceAll("零一十", "零十").replaceAll("零零", "零")
  }

  private def priority(index: Int): String = {
    if (index < 2) "" else Priorities(index - 2)
  }

}
