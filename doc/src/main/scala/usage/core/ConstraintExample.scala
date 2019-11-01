package usage.core

// #example
import com.github.cerst.structible.core._
import com.github.cerst.structible.core.DefaultConstraints._

object ConstraintExample {

  final class UserId private (val value: Long) extends AnyVal

  object UserId {
    private final val MaxValue = Math.pow(2, 32).toLong

    private val structible: Structible[Long, UserId] =
      Structible.structible(new UserId(_), _.value, c >= 0L && c <= MaxValue)

    def apply(value: Long): UserId = structible.construct(value)
  }

}
// #example
