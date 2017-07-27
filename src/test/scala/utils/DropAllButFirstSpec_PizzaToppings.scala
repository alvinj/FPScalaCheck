package utils

import org.scalacheck.Gen.const
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}


/**
  * the "model" classes for these tests
  */
sealed trait Topping
case object BlackOlives extends Topping
case object Cheese extends Topping
case object Mushrooms extends Topping
case object Pepperoni extends Topping
case object Sausage extends Topping


object GenToppingsSeq {

    // generate a list of toppings
    val genBO = const(BlackOlives)
    val genCh = const(Cheese)
    val genMu = const(Mushrooms)
    val genPe = const(Pepperoni)
    val genSa = const(Sausage)

    def genTopping: Gen[Topping] = Gen.oneOf(genBO, genCh, genMu, genPe, genSa)
    val genToppings: Gen[List[Topping]] = Gen.containerOf[List,Topping](genTopping)

}


object DropAllButFirstSpec_PizzaToppings extends Properties("DropAllButFirstSpec_PizzaToppings") {

    /**
      * TODO in the real world you'd refactor this test and the
      * `DropAllButFirstSpec_IntLists`, as they use the exact same
      * algorithm to test two different types of lists.
      */
    property("dropAllButFirst_Toppings") = forAll(GenToppingsSeq.genToppings) { input: List[Topping] =>

        // debugging
        //println(s"\ninput = $input")
        val TOPPING_TO_MATCH = Pepperoni

        // run `dropAllButFirst`
        val result = ListUtils.dropAllButFirst(input, TOPPING_TO_MATCH)

        // start the tests
        val numMatches = input.count(_ == TOPPING_TO_MATCH)
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
            val element1PositionOriginal = input.indexOf(TOPPING_TO_MATCH)
            val element1PositionFinal = result.indexOf(TOPPING_TO_MATCH)

            // (b) “all other occurrences should be dropped.” it's enough to
            // test that there is only one occurrence in `result`
            val numOccurrencesInResult = result.count(_ == TOPPING_TO_MATCH)

            // (c) “all other elements in the list should be as they were.”
            val locationOfFirstOccurrenceInInput = input.indexOf(TOPPING_TO_MATCH)
            val (inputBefore, inputAfter) = input.splitAt(locationOfFirstOccurrenceInInput)
            // splitAt retains the 'split' element as the first element of the "after" list.
            // therefore, only look at the tail of the "after" list.
            val inputAfterTail = inputAfter.tail
            val inputAfterFiltered = inputAfterTail.filter(_ != TOPPING_TO_MATCH)
            //println(s"locationOfFirstOccurrenceInInput = $locationOfFirstOccurrenceInInput")
            //println(s"inputBefore        = $inputBefore")
            //println(s"inputAfterFiltered = $inputAfterFiltered")

            val locationOfFirstOccurrenceInResult = result.indexOf(TOPPING_TO_MATCH)
            val (resultBefore, resultAfter) = result.splitAt(locationOfFirstOccurrenceInResult)
            val resultAfterTail = resultAfter.tail
            //println(s"locationOfFirstOccurrenceInResult = $locationOfFirstOccurrenceInResult")
            //println(s"resultBefore     = $resultBefore")
            //println(s"resultAfterTail  = $resultAfterTail")

            // run all of the “Observation 3” property tests
            (
                element1PositionOriginal == element1PositionFinal   &&   // property 3.a
                numOccurrencesInResult == 1                         &&   // property 3.b
                inputBefore == resultBefore                         &&   // property 3.c
                inputAfterFiltered == resultAfterTail
            )

        }

    }



}
