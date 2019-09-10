package com.github.cerst.structible.core

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
