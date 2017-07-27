package utils

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

/**
  * This is a series of ScalaTest unit tests that test the
  * `ListUtils.dropFirstMatch` function. It helps to show
  * a contrast between ScalaCheck and ScalaTest.
  */
class DropFirstMatchTests extends FunSuite with BeforeAndAfter {

    val list1      = List(1)
    val list123    = List(1, 2, 3)
    val list123123 = List(1, 2, 3, 1, 2, 3)

    test("drop 1 from list1") {
        val xs = ListUtils.dropFirstMatch(list1, 1)
        assert(xs.size == 0)
        assert(xs == List())
    }

    test("drop 1 from list123") {
        val xs = ListUtils.dropFirstMatch(list123, 1)
        assert(xs.size == 2)
        assert(xs == List(2, 3))
    }

    test("drop 2 from list123") {
        val xs = ListUtils.dropFirstMatch(list123, 2)
        assert(xs.size == 2)
        assert(xs == List(1, 3))
    }

    test("drop 3 from list123") {
        val xs = ListUtils.dropFirstMatch(list123, 3)
        assert(xs.size == 2)
        assert(xs == List(1, 2))
    }

    test("drop 17 from list123") {
        val xs = ListUtils.dropFirstMatch(list123, 17)
        assert(xs.size == 3)
        assert(xs == List(1, 2, 3))
    }

    test("drop 1 from list123123") {
        val xs = ListUtils.dropFirstMatch(list123123, 1)
        assert(xs.size == 5)
        assert(xs == List(2, 3, 1, 2, 3))
    }


}
