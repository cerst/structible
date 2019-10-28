package com.github.cerst.structible.core

/**
  * A constraint such as 'greater than x' or 'matches x'.<p/>
  * Constraints can be combined using the <i>&&</i> and <i>||</i> operators (having the usual precedence). There is no
  * implementation of the <i>!</i> operator because constraint implementation predefined error messages which arguably
  * become the harder to read the more (nested) levels of <i>!</i> occur in an expression.<p/>
  * Example: c => c >= 0 && c <= 100 || c >= 200 && c <= 300
  *
  */
trait Constraint[A] extends (A => List[String]){

  def &&(that: Constraint[A]): Constraint[A] = (a: A) => apply(a) ++ that(a)

  def ||(that: Constraint[A]): Constraint[A] = (a: A) => {
    val thisViolated = this.apply(a)
    val thatViolated = that.apply(a)
    if(thisViolated.isEmpty || thatViolated.isEmpty) List.empty else thisViolated ++ thatViolated
  }

  // don't implement '!' because we cannot provide a good error message (except for '!(...)' which arguably is hard to read
  // there should always be an inverse constraint anyways

}
