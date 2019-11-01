package usage.core

// #example
import com.github.cerst.structible.core._

object BasicExample {

  final class UserId private (val value: Long) extends AnyVal

  object UserId {

    private val structible: Structible[Long, UserId] =
      Structible.structible(new UserId(_), _.value, c.unconstrained)

    def apply(value: Long): UserId = structible.construct(value)

  }

}
// #example
