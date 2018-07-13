package io.cerst.structible

import scala.util.control.NonFatal

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Constructible[C, R] {

  def constructSafe(c: C): Either[String, R]

  def constructUnsafe(c: C): R

}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Destructible[C, R] {

  def destruct(r: R): C
}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Structible[C, R] extends Constructible[C, R] with Destructible[C, R]

object Structible {

  def apply[P, W](implicit structible: Structible[P, W]): Structible[P, W] =
    structible

  /**
    * Creates a structible based on the provided functions.<br/>
    * <i>constructSafe</i> is derived using <i>try constructUnsafe catch</i>
    */
  def instanceUnsafe[C, R](constrUnsafe: C => R,
                           destr: R => C): Structible[C, R] = {

    new Structible[C, R] {

      override def destruct(r: R): C = destr(r)

      override def constructSafe(c: C): Either[String, R] = {
        try {
          Right(constrUnsafe(c))
        } catch {
          case NonFatal(cause) =>
            Left(cause.getMessage)
        }
      }

      override def constructUnsafe(c: C): R = constrUnsafe(c)

    }
  }

  /**
    * Creates a structible based on the provided functions.<br/>
    * <i>constructUnsafe</i> is derived by throwing an <i>IllegalArgumentException</i> in case the former yields <i>Left</i>.
    */
  def instanceSafe[C, R](constrSafe: C => Either[String, R],
                         destr: R => C): Structible[C, R] = {

    new Structible[C, R] {
      override def destruct(r: R): C = destr(r)

      override def constructSafe(c: C): Either[String, R] = constrSafe(c)

      override def constructUnsafe(c: C): R = {
        constrSafe(c) fold (
          error => throw new IllegalArgumentException(error),
          identity
        )
      }
    }
  }

}
