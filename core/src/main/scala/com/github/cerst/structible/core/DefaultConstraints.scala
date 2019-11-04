package com.github.cerst.structible.core

import com.github.cerst.structible.core.constraint.instances._

/**
  * Aggregates all constraint type implementations for import convenience.
  */
object DefaultConstraints
    extends BigDecimalConstraints
    with BigIntConstraints
    with ByteConstraints
    with DoubleConstraints
    with DurationConstraints
    with FloatConstraints
    with InstantConstraints
    with IntConstraints
    with JavaDurationConstraints
    with LocalDateTimeConstraints
    with LongConstraints
    with OffsetDateTimeConstraints
    with ShortConstraints
    with StringConstraints
    with ZonedDateTimeConstraints
