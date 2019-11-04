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

/**
  * A constraint such as 'greater than x' or 'matches x'.
  * <p/>
  * Constraints can be combined using the <i>&&</i> and <i>||</i> operators with <i>&&</i> having a higher precedence.
  * There is no <i>!</i> operator because constraint implement predefined error messages which arguably become the
  * harder to read the more (nested) levels of <i>!</i> occur in an expression. Beyond that, most operators should
  * have a an opposite one anyways.
  * <p/>
  * Example: c => c >= 0 && c <= 100 || c >= 200 && c <= 300
  * <p/>
  * Constraints are implemented as type classes. See
  * [[com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax ConstraintSyntax]] for available syntax and
  * [[com.github.cerst.structible.core.DefaultConstraints DefaultConstraints]] for available instances.
  */
trait Constraint[A] extends (A => List[String]) {

  def &&(that: Constraint[A]): Constraint[A] = (a: A) => apply(a) ++ that(a)

  def ||(that: Constraint[A]): Constraint[A] = (a: A) => {
    val thisViolated = this.apply(a)
    val thatViolated = that.apply(a)
    if (thisViolated.isEmpty || thatViolated.isEmpty) List.empty else thisViolated ++ thatViolated
  }

}
