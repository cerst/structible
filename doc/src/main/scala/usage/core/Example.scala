package usage.core

// #example
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._

final case class UserId private(value: Long) extends AnyVal

object UserId {

  private val structible: Structible[Long, UserId] =
    Structible.structible(new UserId(_), _.value, c >= 0, hideC = false)

  def apply(value: Long): UserId = structible.construct(value)
}
// #example

object ConstraintExample {

  c >= 0 && c <= 100 || c >= 200 && c <= 300
}

object NoClassTagExample {

  type UserId = Long

  object UserId {

    private val structible: Structible[Long, UserId] =
      Structible.structible(v => v, u => u, c >= 0, hideC = false)

    def apply(value: Long): UserId = structible.construct(value)
  }

}
