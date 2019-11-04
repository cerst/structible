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

package com.github.cerst.structible.core.constraint.syntax

import com.github.cerst.structible.core.Constraint

import scala.annotation.implicitNotFound
import scala.util.matching.Regex

/**
  * Base trait for implementing constraints which require the <u>length</u> of a value to be less than a given threshold.
  */
@implicitNotFound("No 'LengthLessThan' (_.length < x) implementation found for ${A}")
trait LengthLessThan[A] extends (Int => Constraint[A])

/**
  * Base trait for implementing constraints which require the <u>length</u> of a value to be less than <b>or</b> equal
  * to a given threshold.
  */
@implicitNotFound("No 'LengthLessThanOrEqual' (_.length <= x) implementation found for ${A}")
trait LengthLessThanOrEqual[A] extends (Int => Constraint[A])

/**
  * Base trait for implementing constraints which require the <u>length</u> of a value to be greater than a given threshold.
  */
@implicitNotFound("No 'LengthGreaterThan' (_.length > x) implementation found for ${A}")
trait LengthGreaterThan[A] extends (Int => Constraint[A])

/**
  * Base trait for implementing constraints which require the <u>length</u> of a value to be greater than <b>or</b>
  * equal to a given threshold.
  */
@implicitNotFound("No 'LengthGreaterThanOrEqual' (_.length >= x) implementation found for ${A}")
trait LengthGreaterThanOrEqual[A] extends (Int => Constraint[A])

/**
  * Base trait for implementing constraints which require a value to be less than a given threshold.
  */
@implicitNotFound("No 'LessThan' (_ < x) implementation found for ${A}")
trait LessThan[A] extends (A => Constraint[A])

/**
  * Base trait for implementing constraints which require a value to be less than <b>or</b> equal to a given threshold.
  */
@implicitNotFound("No 'LessThanOrEqual' (_ <= x) implementation found for ${A}")
trait LessThanOrEqual[A] extends (A => Constraint[A])

/**
  * Base trait for implementing constraints which require a value to be greater than a given threshold.
  */
@implicitNotFound("No 'GreaterThan' (_ > x) implementation found for ${A}")
trait GreaterThan[A] extends (A => Constraint[A])

/**
  * Base trait for implementing constraints which require a value to be greater than <b>or</b> equal to a given threshold.
  */
@implicitNotFound("No 'GreaterThanOrEqual' (_ >= x) implementation found for ${A}")
trait GreaterThanOrEqual[A] extends (A => Constraint[A])

/**
  * Base trait for implementing constraints which require a value to match a regular expression.
  */
@implicitNotFound("No 'Matches' (_ matches x) implementation found for ${A}")
trait Matches[A] extends (Regex => Constraint[A])

/**
  * Base trait for implementing constraints which require a value to be non-empty.
  */
@implicitNotFound("No 'NonEmpty' (_.nonEmpty) implementation found for ${A}")
trait NonEmpty[A] extends (Unit => Constraint[A])

/**
  * Contains the syntax (i.e. operators etc.) for specifying constraints of a factory method.<br/>
  * The term <i>syntax</i> refers to the fact that all methods don't contain any implementation beyond delegating
  * to a respective (implicitly provided) type class.
  */
final object ConstraintSyntax {

  final object length {

    /**
      * Requires the <u>length</u> of the value to be less than <i>x</i>.
      */
    def <[A](x: Int)(implicit ev: LengthLessThan[A]): Constraint[A] = ev(x)

    /**
      * Requires the <u>length</u> of the value to be less than <b>or</b> equal to <i>x</i>.
      */
    def <=[A](x: Int)(implicit ev: LengthLessThanOrEqual[A]): Constraint[A] = ev(x)

    /**
      * Requires the <u>length</u>of the value to be greater than <i>x</i>.
      */
    def >[A](x: Int)(implicit ev: LengthGreaterThan[A]): Constraint[A] = ev(x)

    /**
      * Requires the <u>length</u> of the value to be greater than <b>or</b> equal to <i>x</i>.
      */
    def >=[A](x: Int)(implicit ev: LengthGreaterThanOrEqual[A]): Constraint[A] = ev(x)

  }

  /**
    * Requires the value to be less than <i>x</i>.
    */
  def <[A](x: A)(implicit ev: LessThan[A]): Constraint[A] = ev(x)

  /**
    * Requires the value to be less than <b>or</b> equal to <i>x</i>.
    */
  def <=[A](x: A)(implicit ev: LessThanOrEqual[A]): Constraint[A] = ev(x)

  /**
    * Requires the value to be greater than <i>x</i>.
    */
  def >[A](x: A)(implicit ev: GreaterThan[A]): Constraint[A] = ev(x)

  /**
    * Requires the value to be greater than <b>or</b> equal to <i>x</i>.
    */
  def >=[A](x: A)(implicit ev: GreaterThanOrEqual[A]): Constraint[A] = ev(x)

  /**
    * Requires the value to match <i>x</i>.
    */
  def matches[A](x: Regex)(implicit ev: Matches[A]): Constraint[A] = ev(x)

  /**
    * Requires the value to be non-empty.
    */
  def nonEmpty[A](implicit ev: NonEmpty[A]): Constraint[A] = ev(())

  /**
    * Requires nothing.
    */
  def unconstrained[A]: Constraint[A] = _ => List.empty

}
