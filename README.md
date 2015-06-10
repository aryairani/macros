# macros
Some macros I wanted.

## `SimpleSealedEnum`
```scala
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
```
Gives exhaustiveness checking:
```
[info] Compiling 1 Scala source to /Users/arya/Dropbox/code/arya/macros/target/scala-2.11/test-classes...
[warn] /Users/arya/Dropbox/code/arya/macros/src/test/scala/SimpleSealedEnumTest.scala:12: match may not be exhaustive.
[warn] It would fail on the following input: Child
[warn]   (Adult: Age) match {
[warn]         ^
[warn] one warning found
[info] Running test.SimpleSealedEnumTest 
List(Adult, Child)
```
