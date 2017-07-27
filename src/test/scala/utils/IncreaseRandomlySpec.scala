package utils

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

/**
  * This is a ScalaCheck "property test" of the
  * `MathUtils.increaseRandomly` function.
  */
object IncreaseRandomlySpec extends Properties("IncreaseRandomlySpec") {

    property("increaseRandomly") = forAll { input: Int =>
        //println(s"input = $input")
        val result = MathUtils.increaseRandomly(input)
        result > input
    }


    // note: other `Int` generators that could be used
    val littleInts = Gen.choose(0, 99)
    val intsGreaterThan1 = Gen.choose(2, 10000)  //2147483647
    val nonZeroOneInts = Arbitrary.arbitrary[Int] suchThat (i => i != 0 && i != 1)

}

