package io.cerst.structible

import scala.util.control.NonFatal

trait Constructible[P, W] {

  def constructSafe(p: P): Either[String, W]

  def constructUnsafe(p: P): W

}

trait Destructible[W, P] {

  def destruct(w: W): P
}

trait Structible[P, W] extends Constructible[P, W] with Destructible[W, P]

object Structible {

  def summon[P,W](implicit structible: Structible[P,W]): Structible[P, W] = structible

  def apply[P, W](_constructUnsafe: P => W,
                  _destruct: W => P): Structible[P, W] = {

    new Structible[P, W] {

      override def constructSafe(p: P): Either[String, W] = {
        try {
          Right(_constructUnsafe(p))
        } catch {
          case NonFatal(cause) =>
            Left(cause.getMessage)
        }
      }

      override def constructUnsafe(p: P): W = _constructUnsafe(p)

      override def destruct(w: W): P = _destruct(w)
    }
  }
}
