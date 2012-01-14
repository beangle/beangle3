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
 * but WITHOUT ANY WARRANTY without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.util.seq

import org.scalatest.FunSuite
import org.beangle.util.seq.SeqNumStyle._

class MultiLevelSeqGeneratorTest extends FunSuite{
  test("Generator MultiLevel Seq"){
    val sg= new MultiLevelSeqGenerator()
    sg.add(SeqPattern(Hanzi,"{1}"))
    sg.add(SeqPattern(Hanzi,"({2})"))
    sg.add(SeqPattern(Arabic, "{3}"))
    sg.add(SeqPattern(Arabic, "{3}.{4}"))
    sg.add(SeqPattern(Arabic, "{3}.{4}.{5}.{6}"))
    sg.add(SeqPattern(Arabic, "{3}.{4}.{5}.{6}.{7}"))
    sg.add(SeqPattern(Arabic, "{3}.{4}.{5}.{6}.{7}.{8}"))
    sg.add(SeqPattern(Arabic, "{3}.{4}.{5}.{6}.{7}.{8}.{9}"))

    assert("一"== sg.next(1))
    assert("(一)"== sg.next(2))
    assert("1"== sg.next(3))
    assert("1.1"== sg.next(4))
  }

  test("Hanzi Sequence Pattern"){
    val style=new HanziSeqStyle()
    assert("二百一十一"== style(211))
    assert("二百零一"== style(201))
    assert("三千零十一"== style(3011))
  }
}
