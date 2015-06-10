package test

import net.arya.macros.SimpleSealedEnum

@SimpleSealedEnum('Adult, 'Child)
trait Age

object SimpleSealedEnumTest extends App {
  import Age._
  println(values)

  (Adult: Age) match {
    case Adult ⇒ "ok"
  }
}
