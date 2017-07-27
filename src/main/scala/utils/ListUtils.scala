package utils

object ListUtils {

    /**
      * drops the first element in the list `ls` that matches
      * `value`, then returns all other elements in the sequence.
      */
    def dropFirstMatch[A](ls: Seq[A], value: A): Seq[A] = {
        val index = ls.indexOf(value) //index is -1 if there is no match
        if (index < 0) {
            ls
        } else if (index == 0) {
            ls.tail
        } else {
            // splitAt keeps the matching element in the second group
            val (a, b) = ls.splitAt(index)
            a ++ b.tail
        }
    }

    /**
      * Drop every `dropee` from the list except for the first occurrence
      * of that element. Example:
      *
      * {{{
      * List(1,2,3,2,4,2,5) becomes List(1,2,3,4,5) when dropee=2
      * }}}
      *
      * @note This function is not tail-recursive, so it will blow up on large lists.
      *
      * @param list The list that contains duplicate elements you want to drop.
      * @param dropee The element you want to drop.
      * @tparam A The type of the element in the list.
      * @return A list where all `dropee` elements after the first occurrence
      *         have been dropped.
      */
    def dropAllButFirst[A](list: List[A], dropee: A): List[A] = {
        dropAllButFirst(list, dropee, 0)
    }

    /**
      * @param list The list that contains duplicate elements you want to drop.
      * @param dropee The element you want to drop.
      * @param foundCount
      * @tparam A
      * @return
      */
    private def dropAllButFirst[A](list: List[A], dropee: A, foundCount: Int): List[A] = list match {

        case Nil => Nil

        case x :: xs if x == dropee => {
            if (foundCount == 0) {
                // this is the first occurrence; keep it
                x :: dropAllButFirst(xs, dropee, foundCount+1)
            } else {
                // not the first occurrence, drop it
                dropAllButFirst(xs, dropee, foundCount+1)
            }
        }

        // `x` does not match `dropee`, keep looking
        case x :: xs => {
            x :: dropAllButFirst(xs, dropee, foundCount)
        }

    }

}
