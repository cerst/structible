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
