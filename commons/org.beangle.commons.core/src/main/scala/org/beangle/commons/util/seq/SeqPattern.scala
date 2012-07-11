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

import org.beangle.commons.lang.Predef._
import scala.collection.mutable._

class MultiLevelSeqGenerator {
  val patterns = new HashMap[Int, SeqPattern]()

  def getPattern(level: Int): SeqPattern = patterns.get(level).get

  def next(level: Int): String = getPattern(level).next

  def add(pattern: SeqPattern) {
    pattern.generator = this
    patterns.put(pattern.level, pattern)
  }

  def reset(level: Int) = getPattern(level).reset()
}

object SeqPattern {
  def apply(style: SeqNumStyle, pattern: String): SeqPattern = new SeqPattern(style, pattern)
}

class SeqPattern(style: SeqNumStyle, pattern: String) {
  var params = new ListBuffer[Int]()
  var level: Int = 0
  var seq: Int = 0
  var generator: MultiLevelSeqGenerator = _

  private def initParams() {
    var remainder = pattern;
    while (remainder.length > 0) {
      val p = remainder.substringBetween("{", "}")
      if (p.isDigits) params += p.toInt
      remainder = remainder.substringAfter("{" + p + "}")
    }
    params = params.sortWith((a, b) => a < b)
    this.level = params.last
    params.remove(params.length - 1)
  }

  initParams()

  def curSeqText = style.build(seq)

  def next: String = {
    seq += 1
    var text = pattern
    for (paramLevel <- params) {
      text = text.replace("{" + paramLevel + "}", generator.getPattern(paramLevel).curSeqText)
    }
    text.replace("{" + level + "}", style.build(seq))
  }

  def reset() = seq = 0;
}
