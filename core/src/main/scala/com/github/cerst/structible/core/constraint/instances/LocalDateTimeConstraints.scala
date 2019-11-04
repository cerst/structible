package com.github.cerst.structible.core.constraint.instances

import java.time.LocalDateTime

import com.github.cerst.structible.core.constraint.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

trait LocalDateTimeConstraints {

  implicit val lessThanForLocalDateTime: LessThan[LocalDateTime] = x => { y =>
    if (y.compareTo(x) < 0) List.empty else List(s"_ < $x")
  }

  implicit val lessThanOrEqualForLocalDateTime: LessThanOrEqual[LocalDateTime] = x => { y =>
    if (y.compareTo(x) <= 0) List.empty else List(s"_ <= $x")
  }

  implicit val greaterThanForLocalDateTime: GreaterThan[LocalDateTime] = x => { y =>
    if (y.compareTo(x) > 0) List.empty else List(s"_ > $x")
  }

  implicit val greaterThanForOrEqualLocalDateTime: GreaterThanOrEqual[LocalDateTime] = x => { y =>
    if (y.compareTo(x) >= 0) List.empty else List(s"_ >= $x")
  }

}

object LocalDateTimeConstraints extends LocalDateTimeConstraints
