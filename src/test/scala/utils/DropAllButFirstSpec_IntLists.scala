package utils

import org.scalacheck.Gen.const
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

/**
 * These are different algorithms to generate a `List[Int]` (technically
 * they generate a `Gen[List[Int]]`.
 */
object GenIntSeq {

    // (1) generate lists that contain the numbers 1 through 5
    val g1to5: Gen[List[Int]] = Gen.containerOf[List,Int](Gen.choose(1, 5))

    // (2) generate lists using a frequency, where 2s occur four times as often
    // as the numbers 1, 3, 4 and 5
    val favorTwos: Gen[Int] = Gen.frequency(
        (1, 1),
        (4, 2),
        (1, 3),
        (1, 4),
        (1, 5)
    )
    val genMostlyTwos: Gen[List[Int]] = Gen.containerOf[List,Int](favorTwos)

    // (3) another way to try to control how many 2s are in each list.
    // it improves on what's shown on this page:
    // https://stackoverflow.com/questions/38922414/how-can-i-define-a-scalacheck-generator-that-produces-a-subset-of-a-sequences-e
    val littleList: List[Int] = scala.util.Random.shuffle(List(1,2,3,4,5,6,2,7,8,9))
    val littleListGen: Gen[List[Int]] = Gen.someOf(littleList).map(_.toList)

}


object DropAllButFirstSpec_IntLists extends Properties("DropAllButFirstSpec") {

    val NUM_TO_DROP = 2

    /**
      * when testing, try these different generators to see how they work:
      *
      *     GenSeq.g1to5
      *     GenSeq.genMostlyTwos
      *     GenSeq.littleListGen
      *
      * the first two generators create long lists, so you may want to use code like this
      * at the beginning of the test:
      *
      *     val input2 = input.take(10)
      *
      * and then use `input2` in the tests.
      *
      */

    // this is the default `List[Int]` generator:
    // property("dropAllButFirstIntLists") = forAll { input: List[Int] =>

    property("dropAllButFirstIntLists") = forAll(GenIntSeq.g1to5) { input: List[Int] =>

        // for debugging
        //println("")
        println(s"input: $input")

        // run `dropAllButFirst`
        val result = ListUtils.dropAllButFirst(input, 2)

        // start the tests
        val numMatches = input.count(_ == NUM_TO_DROP)
        if (numMatches == 0) {
            /**
              * OBSERVATION 1: If the element you want to drop is not in the input list,
              * the resulting list should be the same as the input list
              */
            input == result
        } else if (numMatches == 1) {
            /**
              * OBSERVATION 2: If only one occurrence of the element you want to drop
              * is in the input list, the resulting list should be the
              * same as the input list
              */
            input == result
        } else {
            /**
              * OBSERVATION 3: If more than one occurrence of the element you want
              * to drop is in the input list, then: (a) the first element should
              * remain in its original position; (b) all other occurrences should
              * be dropped; (c) all other elements in the list should be as they were.
              */

            // (a) “the first element should remain in its original position.”
            val element1PositionOriginal = input.indexOf(NUM_TO_DROP)
            val element1PositionFinal = result.indexOf(NUM_TO_DROP)

            // (b) “all other occurrences should be dropped.” it's enough to
            // test that there is only one occurrence in `result`
            val numOccurrencesInResult = result.count(_ == NUM_TO_DROP)

            // (c) “all other elements in the list should be as they were.”
            val locationOfFirstOccurrenceInInput = input.indexOf(NUM_TO_DROP)
            val (inputBefore, inputAfter) = input.splitAt(locationOfFirstOccurrenceInInput)
            // splitAt retains the 'split' element as the first element of the "after" list.
            // therefore, only look at the tail of the "after" list.
            val inputAfterTail = inputAfter.tail
            val inputAfterFiltered = inputAfterTail.filter(_ != NUM_TO_DROP)
            //println(s"locationOfFirstOccurrenceInInput = $locationOfFirstOccurrenceInInput")
            //println(s"inputBefore        = $inputBefore")
            //println(s"inputAfterFiltered = $inputAfterFiltered")

            val locationOfFirstOccurrenceInResult = result.indexOf(NUM_TO_DROP)
            val (resultBefore, resultAfter) = result.splitAt(locationOfFirstOccurrenceInResult)
            val resultAfterTail = resultAfter.tail
            //println(s"locationOfFirstOccurrenceInResult = $locationOfFirstOccurrenceInResult")
            //println(s"resultBefore     = $resultBefore")
            //println(s"resultAfterTail  = $resultAfterTail")

            // run all of the “Observation 3” property tests
            (
                element1PositionOriginal == element1PositionFinal    &&       // property 3.a
                numOccurrencesInResult == 1                          &&       // property 3.b
                inputBefore == resultBefore                          &&       // property 3.c
                inputAfterFiltered == resultAfterTail
            )

        }
    }

}









