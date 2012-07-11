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
package org.beangle.commons.util.inflector

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.beangle.commons.util.inflector.lang.EnNounPluralizer

@RunWith(classOf[JUnitRunner])
class EnNounPluralizerTest extends FlatSpec with ShouldMatchers {

  val pluralizer = new EnNounPluralizer()

  "Pluralize fish " should "to fish" in {
    pluralizer.pluralize("fish") should be("fish")
    pluralizer.pluralize("fish") should be("fish")
  }

  "Pluralized special person " should " be people " in {
    pluralizer.pluralize("person") should be("people")
    pluralizer.pluralize("child") should be("children")
  }

  "Pluralized normal self " should " be selves " in {
    pluralizer.pluralize("yourself") should be("yourselves")
  }

}
