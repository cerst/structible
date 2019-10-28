/*
 * Copyright (c) 2018 Constantin Gerstberger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.cerst.structible.core

import com.github.cerst.structible.core.internal.{CheckConstraint, Error}

import scala.reflect.ClassTag
import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Constructible[C, R] {

  /**
    * Calls the <i>_construct<i> function passed-in to created this instance.<br/>
    * Throws an exception if and only if said function produced an error (such as an exception, a
    * [[scala.util.Failure Failure]], a [[scala.util.Left Left]] or a [[scala.None None]]).
    *
    */
  @throws[IllegalArgumentException]
  def construct(c: C): R

  /**
    * Calls the <i>_construct<i> function passed-in to created this instance.<br/>
    * Returns [[scala.util.Left Left]] if and only if said function produced an error (such as an exception, a
    * [[scala.util.Failure Failure]], a [[scala.util.Left Left]] or a [[scala.None None]]).
    *
    */
  def constructEither(c: C): Either[String, R]

  /**
    * Calls the <i>_construct<i> function passed-in to created this instance.<br/>
    * Returns [[scala.None None]] if and only if said function produced an error (such as an exception, a
    * [[scala.util.Failure Failure]], a [[scala.util.Left Left]] or a [[scala.None None]]).
    *
    */
  def constructOption(c: C): Option[R]

  /**
    * Calls the <i>_construct<i> function passed-in to created this instance.<br/>
    * Returns [[scala.util.Failure Failure]] if and only if said function produced an error (such as an exception, a
    * [[scala.util.Failure Failure]], a [[scala.util.Left Left]] or a [[scala.None None]]).
    *
    */
  def constructTry(c: C): Try[R]

}

trait Destructible[C, R] {

  /**
    * Calls the <i>_destruct</i> function passed-in to create this instance.<br/>
    * As said function is expected not to produce an error, no exception handling or the like is performed.
    */
  def destruct(r: R): C
}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Structible[C, R] extends Constructible[C, R] with Destructible[C, R]

object Structible {

  /**
    * Creates a structible instance.<br/>
    *
    * @param _construct <i>NonFatal</i> exceptions thrown by this function are caught and handled. Check the
    *                   documentation of the methods defined by
    *                   [[com.github.cerst.structible.core.Constructible Constructible]] for details.
    * @param _destruct  Expected to never throw an exception.
    * @param constraint Checked by the returned [[com.github.cerst.structible.core.Structible Structible]] instance
    *                   before invoking the provided <i>_construct</i> function.
    * @param hideC      Whether or not values of type <i>C</i> may appear in error messages produced by
    *                   <i>Structible</i> (i.e. does <b>not</b> effect error messages produced by the provided
    *                   <i>_construct</i> function.
    * @param rName      Name of the type <i>R</i> which is used to wrap/ enhance error messages produced by the provided
    *                   <i>_construct</i> function.
    *
    * @see [[com.github.cerst.structible.core.Constraint Constraint]]
    */
  def structible[C, R](_construct: C => R,
                       _destruct: R => C,
                       constraint: Constraint[C],
                       hideC: Boolean,
                       rName: RName): Structible[C, R] = new Structible[C, R] {

    override def destruct(r: R): C = _destruct(r)

    override def construct(c: C): R = {
      CheckConstraint(c, constraint) match {
        case None =>
          try {
            _construct(c)
          } catch {
            case NonFatal(cause) => Error.throwException(c, hideC, rName, cause)
          }

        case Some(value) =>
          Error.throwException(c, hideC, rName, reason = value)
      }
    }

    override def constructEither(c: C): Either[String, R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          try {
            Right(_construct(c))
          } catch {
            case NonFatal(cause) => Error.left(c, hideC, rName, cause)
          }

        case Some(value) =>
          Error.left(c, hideC, rName, reason = value)
      }
    }

    override def constructOption(c: C): Option[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          try {
            Some(_construct(c))
          } catch {
            case NonFatal(_) => None
          }

        case Some(_) =>
          None
      }
    }

    override def constructTry(c: C): Try[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          Try {
            construct(c)
          } recoverWith {
            case NonFatal(cause) => Error.failure(c, hideC, rName, cause)
          }

        case Some(value) =>
          Error.failure(c, hideC, rName, reason = value)
      }
    }

  }

  def structible[C, R: ClassTag](_construct: C => R,
                                 _destruct: R => C,
                                 constraint: Constraint[C],
                                 hideC: Boolean): Structible[C, R] = {
    structible(_construct, _destruct, constraint, hideC, rName = RName[R])
  }

  def structibleEither[C, R](_construct: C => Either[String, R],
                             _destruct: R => C,
                             constraint: Constraint[C],
                             hideC: Boolean,
                             rName: RName): Structible[C, R] = new Structible[C, R] {

    override def destruct(r: R): C = _destruct(r)

    override def construct(c: C): R = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Left(value) => Error.throwException(c, hideC, rName, reason = value)
            case Right(r)    => r
          }

        case Some(value) =>
          Error.throwException(c, hideC, rName, reason = value)
      }
    }

    override def constructEither(c: C): Either[String, R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Left(value) => Error.left(c, hideC, rName, reason = value)
            case Right(r)    => Right(r)
          }

        case Some(value) =>
          Error.left(c, hideC, rName, reason = value)
      }
    }

    override def constructOption(c: C): Option[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Left(_)  => None
            case Right(r) => Some(r)
          }

        case Some(_) =>
          None
      }
    }

    override def constructTry(c: C): Try[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Left(value) => Error.failure(c, hideC, rName, reason = value)
            case Right(r)    => Success(r)
          }

        case Some(value) =>
          Error.failure(c, hideC, rName, reason = value)
      }
    }

  }

  def structibleEither[C, R: ClassTag](_construct: C => Either[String, R],
                                       _destruct: R => C,
                                       constraint: Constraint[C],
                                       hideC: Boolean): Structible[C, R] = {
    structibleEither(_construct, _destruct, constraint, hideC, rName = RName[R])
  }

  def structibleOption[C, R](_construct: C => Option[R],
                             _destruct: R => C,
                             constraint: Constraint[C],
                             hideC: Boolean,
                             rName: RName): Structible[C, R] = new Structible[C, R] {

    override def destruct(r: R): C = _destruct(r)

    override def construct(c: C): R = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case None    => Error.throwException(c, hideC, rName, reason = "")
            case Some(r) => r
          }

        case Some(value) =>
          Error.throwException(c, hideC, rName, reason = value)
      }
    }

    override def constructEither(c: C): Either[String, R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case None    => Error.left(c, hideC, rName)
            case Some(r) => Right(r)
          }

        case Some(value) =>
          Error.left(c, hideC, rName, reason = value)
      }
    }

    override def constructOption(c: C): Option[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c)

        case Some(_) =>
          None
      }
    }

    override def constructTry(c: C): Try[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          Error.failure(c, hideC, rName)

        case Some(value) =>
          Error.failure(c, hideC, rName, reason = value)
      }
    }
  }

  def structibleOption[C, R: ClassTag](_construct: C => Option[R],
                                       _destruct: R => C,
                                       constraint: Constraint[C],
                                       hideC: Boolean): Structible[C, R] = {
    structibleOption(_construct, _destruct, constraint, hideC, rName = RName[R])
  }

  def structibleTry[C, R](_construct: C => Try[R],
                          _destruct: R => C,
                          constraint: Constraint[C],
                          hideC: Boolean,
                          rName: RName): Structible[C, R] = new Structible[C, R] {

    override def destruct(r: R): C = _destruct(r)

    override def construct(c: C): R = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Failure(cause) => Error.throwException(c, hideC, rName, cause)
            case Success(r)     => r
          }

        case Some(value) =>
          Error.throwException(c, hideC, rName, reason = value)
      }
    }

    override def constructEither(c: C): Either[String, R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Failure(cause) => Error.left(c, hideC, rName, cause)
            case Success(r)     => Right(r)
          }
        case Some(value) =>
          Error.left(c, hideC, rName, reason = value)
      }
    }

    override def constructOption(c: C): Option[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Failure(_) => None
            case Success(r) => Some(r)
          }

        case Some(_) =>
          None
      }
    }

    override def constructTry(c: C): Try[R] = {
      CheckConstraint(c, constraint) match {
        case None =>
          _construct(c) match {
            case Failure(cause) => Error.failure(c, hideC, rName, cause)
            case Success(r)     => Success(r)
          }

        case Some(value) =>
          Error.failure(c, hideC, rName, reason = value)
      }
    }
  }

  def structibleTry[C, R: ClassTag](_construct: C => Try[R],
                                    _destruct: R => C,
                                    constraint: Constraint[C],
                                    hideC: Boolean): Structible[C, R] = {
    structibleTry(_construct, _destruct, constraint, hideC, rName = RName[R])
  }

}
