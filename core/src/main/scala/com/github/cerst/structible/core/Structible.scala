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

  final def apply[P, W](implicit structible: Structible[P, W]): Structible[P, W] = structible

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>can</u> expect messages of exception thrown by the provided
    * <i>_construct</i> function to be meaningful on their own. Otherwise, check out the overload with three
    * parameters.
    *
    * @param _construct NonFatal exceptions thrown by this function are caught and handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    *
    */
  final def structible[C, R](_construct: C => R, _destruct: R => C): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = _construct(c)

      override def constructEither(c: C): Either[String, R] = {
        try {
          Right(_construct(c))
        } catch {
          case NonFatal(cause) =>
            Left(getMessageNonNull(cause))
        }
      }

      override def constructOption(c: C): Option[R] = {
        try {
          Some(_construct(c))
        } catch {
          case NonFatal(_) =>
            None
        }
      }

      override def constructTry(c: C): Try[R] = Try(_construct(c))
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>cannot</u> expect messages of exception thrown by the provided
    * <i>_construct</i> function to be meaningful on their own. Otherwise, check out the overload with only
    * two parameters.
    *
    * @param _construct NonFatal exceptions thrown by this function are caught and handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rName      Name of the type <i>R</i> which is used to wrap/ enhance error messages produced by the provided
    *                   <i>_construct</i> function. If you prefer the name being derived via
    *                   [[scala.reflect.ClassTag ClassTag]], check out the respective overload.
    *
    */
  final def structible[C, R](_construct: C => R, _destruct: R => C, rName: String): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = {
        try {
          _construct(c)
        } catch {
          case NonFatal(cause) =>
            def msg = errorMsg(c, rName)

            throw new IllegalArgumentException(msg, cause)
        }
      }

      override def constructEither(c: C): Either[String, R] = {
        try {
          Right(_construct(c))
        } catch {
          case NonFatal(cause) =>
            def msg = errorMsg(c, rName, cause)

            Left(msg)
        }
      }

      override def constructOption(c: C): Option[R] = {
        try {
          Some(_construct(c))
        } catch {
          case NonFatal(_) =>
            None
        }
      }

      override def constructTry(c: C): Try[R] = {
        Try(_construct(c)) recoverWith {
          case cause =>
            def msg = errorMsg(c, rName)

            Failure(new IllegalArgumentException(msg, cause))
        }
      }
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>cannot</u> expect messages of exception thrown by the provided
    * <i>_construct</i> function to be meaningful on their own. Otherwise, check out the overload with only two
    * parameters.
    *
    * @param _construct NonFatal exceptions thrown by this function are caught and handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rClassTag  Used to derive the name of the type <i>R</i> which in turn is used to wrap/ enhance error
    *                   messages produced by the provided <i>_construct</i> function. If you prefer passing in the name
    *                   as [[java.lang.String String]], check out the respective overload.
    */
  final def structible[C, R](_construct: C => R, _destruct: R => C, rClassTag: ClassTag[R]): Structible[C, R] = {
    structible(_construct, _destruct, rClassTag.runtimeClass.getSimpleName)
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>can</u> expect <i>>Left</i>>-values returned by the provided
    * <i>_construct</i> function to be meaningful on their own. Otherwise, check out the overloads with three
    * parameters.
    *
    * @param _construct <i>>Left</i>-values returned by this function are handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    *
    */
  final def structibleEither[C, R](_construct: C => Either[String, R], _destruct: R => C): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = _construct(c) match {
        case Left(value) =>
          throw new IllegalArgumentException(value)
        case Right(value) =>
          value
      }

      override def constructEither(c: C): Either[String, R] = _construct(c)

      override def constructOption(c: C): Option[R] = _construct(c) match {
        case Left(_) =>
          None
        case Right(value) =>
          Some(value)
      }

      override def constructTry(c: C): Try[R] = _construct(c) match {
        case Left(error) =>
          Failure(new IllegalArgumentException(error))
        case Right(value) =>
          Success(value)
      }
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>cannot</u> expect <i>Left</i>-values returned by the provided
    * <i>_construct</i> function to be meaningful on their own. Otherwise, check out the overload with only two
    * parameters.
    *
    * @param _construct <i>>Left</i>-values returned by this function are handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rName      Name of the type <i>R</i> which is used to wrap/ enhance error messages produced by the provided
    *                   <i>_construct</i> function. If you prefer the name being derived via
    *                   [[scala.reflect.ClassTag ClassTag]], check out the respective overload.
    *
    */
  final def structibleEither[C, R](_construct: C => Either[String, R],
                                   _destruct: R => C,
                                   rName: String): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = _construct(c) match {
        case Left(reason) =>
          def msg = errorMsg(c, rName, reason)

          throw new IllegalArgumentException(msg)
        case Right(value) =>
          value
      }

      override def constructEither(c: C): Either[String, R] = _construct(c)

      override def constructOption(c: C): Option[R] = _construct(c) match {
        case Left(_) =>
          None
        case Right(value) =>
          Some(value)
      }

      override def constructTry(c: C): Try[R] = _construct(c) match {
        case Left(reason) =>
          def msg = errorMsg(c, rName, reason)

          Failure(new IllegalArgumentException(msg))
        case Right(value) =>
          Success(value)
      }
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>cannot</u> expect <i>Left</i>-values returned by the provided
    * <i>_construct</i> function to be meaningful on their own. Otherwise, check out the overload with only two
    * parameters.
    *
    * @param _construct NonFatal exceptions thrown by this function are caught and handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rClassTag  Used to derive the name of the type <i>R</i> which in turn is used to wrap/ enhance error
    *                   messages produced by the provided <i>_construct</i> function. If you prefer passing in the name
    *                   as [[java.lang.String String]], check out the respective overload.
    *
    */
  final def structibleEither[C, R](_construct: C => Either[String, R],
                                   _destruct: R => C,
                                   rClassTag: ClassTag[R]): Structible[C, R] = {
    structibleEither(_construct, _destruct, rClassTag.runtimeClass.getSimpleName)
  }

  /**
    * Creates a structible instance.<br/>
    *
    * @param _construct [[scala.None None]]-values returned by this function are handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rName      Name of the type <i>R</i> which is used to wrap/ enhance error messages in case of
    *                   [[scala.None None]] being returned by  provided <i>_construct</i> function.
    *
    */
  final def structibleOption[C, R](_construct: C => Option[R], _destruct: R => C, rName: String): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = _construct(c) match {
        case None =>
          def msg = errorMsg(c, rName)

          throw new IllegalArgumentException(msg)
        case Some(value) =>
          value
      }

      override def constructEither(c: C): Either[String, R] = _construct(c) match {
        case None =>
          def msg = errorMsg(c, rName)

          Left(msg)
        case Some(value) =>
          Right(value)
      }

      override def constructOption(c: C): Option[R] = _construct(c)

      override def constructTry(c: C): Try[R] = _construct(c) match {
        case None =>
          def msg = errorMsg(c, rName)

          Failure(new IllegalArgumentException(msg))
        case Some(value) =>
          Success(value)
      }
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>can</u> expect messages of exceptions of <i>Failures</i>
    * returned by the provided <i>_construct</i> function to be meaningful on their own. Otherwise, check out
    * the overloads with three parameters.
    *
    * @param _construct <i>>Failures</i> returned by this function are handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    *
    */
  final def structibleTry[C, R](_construct: C => Try[R], _destruct: R => C): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = _construct(c) match {
        case Failure(cause) =>
          throw new IllegalArgumentException(cause)
        case Success(value) =>
          value
      }

      override def constructEither(c: C): Either[String, R] = _construct(c) match {
        case Failure(cause) =>
          Left(getMessageNonNull(cause))
        case Success(value) =>
          Right(value)
      }

      override def constructOption(c: C): Option[R] = _construct(c) match {
        case Failure(_) =>
          None
        case Success(value) =>
          Some(value)
      }

      override def constructTry(c: C): Try[R] = _construct(c)
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>cannot</u> expect messages of exceptions of <i>>Failures</i>
    * returned by the provided <i>_construct</i> function to be meaningful on their own. Otherwise, check out
    * the overload with only two parameters.
    *
    * @param _construct <i>>Failures</i> returned by this function are handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rName      Name of the type <i>R</i> which is used to wrap/ enhance error messages produced by the provided
    *                   <i>_construct</i> function. If you prefer the name being derived via
    *                   [[scala.reflect.ClassTag ClassTag]], check out the respective overload.
    *
    */
  final def structibleTry[C, R](_construct: C => Try[R], _destruct: R => C, rName: String): Structible[C, R] = {
    new Structible[C, R] {
      override def destruct(r: R): C = _destruct(r)

      override def construct(c: C): R = _construct(c) match {
        case Failure(cause) =>
          def msg = errorMsg(c, rName)

          throw new IllegalArgumentException(msg, cause)
        case Success(value) =>
          value
      }

      override def constructEither(c: C): Either[String, R] = _construct(c) match {
        case Failure(cause) =>
          def msg = errorMsg(c, rName, cause)

          Left(msg)
        case Success(value) =>
          Right(value)
      }

      override def constructOption(c: C): Option[R] = _construct(c) match {
        case Failure(_) =>
          None
        case Success(value) =>
          Some(value)
      }

      override def constructTry(c: C): Try[R] = {
        _construct(c) recoverWith {
          case cause =>
            def msg = errorMsg(c, rName)

            Failure(new IllegalArgumentException(msg, cause))
        }
      }
    }
  }

  /**
    * Creates a structible instance.<br/>
    * Use this variant if and only if you <u>cannot</u> expect messages of exceptions of <i>Failure</i>'s
    * returned by the provided <i>_construct</i> function to be meaningful on their own. Otherwise, check out
    * the overload with only two parameters.
    *
    * @param _construct <i>>Failures</i> returned by this function are handled. Check the documentation of
    *                   the methods defined by [[com.github.cerst.structible.core.Constructible Constructible]] for
    *                   details.
    * @param rClassTag  Used to derive the name of the type <i>R</i> which in turn is used to wrap/ enhance error
    *                   messages produced by the provided <i>_construct</i> function. If you prefer passing in the name
    *                   as [[java.lang.String String]], check out the respective overload.
    *
    */
  final def structibleTry[C, R](_construct: C => Try[R],
                                _destruct: R => C,
                                rClassTag: ClassTag[R]): Structible[C, R] = {
    structibleTry(_construct, _destruct, rClassTag.runtimeClass.getSimpleName)
  }

  private final def errorMsg[C](c: C, rName: String): String = {
    s"Failed to construct '$rName' from '$c'"
  }

  private final def errorMsg[C](c: C, rName: String, cause: Throwable): String = {
    s"${errorMsg(c, rName)}. Cause: ${getMessageNonNull(cause)}"
  }

  private final def errorMsg[C](c: C, rName: String, reason: String): String = {
    s"${errorMsg(c, rName)}. Reason: $reason"
  }

  /**
    * Safe way to access [[java.lang.Throwable#getMessage()]] which may return null.
    */
  private final def getMessageNonNull(throwable: Throwable): String = {
    if (throwable.getMessage == null) "<exception.getMessage was null>" else throwable.getMessage
  }

}
