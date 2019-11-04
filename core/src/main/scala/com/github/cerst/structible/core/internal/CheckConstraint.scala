package com.github.cerst.structible.core.internal

import com.github.cerst.structible.core.Constraint

object CheckConstraint {

  def apply[C, R](c: C, constraint: Constraint[C]): Option[String] = {
    val violations = constraint(c)
    if (violations.nonEmpty) {
      def formattedViolations = violations.mkString("[ ", ", ", " ]")

      Some(s"violated constraints: $formattedViolations")
    } else {
      None
    }
  }

}
