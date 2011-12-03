package org.beangle.lang
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.testing.SUnit.Test

class BitStringTest extends FlatSpec with ShouldMatchers {

  "bit string" should "have correct value and length." in {
    BitString("1111").value should be(15)
    BitString("1111").length should be(4)
    BitString(15).value should be(15)
    BitString(15).length should be(4)
  }
  
}