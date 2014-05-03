package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }
  
  test("balance: empty") {
    assert(balance("".toList))
  }
  
  test("balance: no parentheses") {
    assert(balance("this string has no parentheses".toList))
  }
  
  test("balance: all open") {
    assert(!balance("a(b(c(d(e".toList))
  }
  
  test("balance: all closed") {
    assert(!balance("v)w)x)y)z".toList))
  }
}
