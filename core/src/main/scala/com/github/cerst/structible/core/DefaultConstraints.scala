package com.github.cerst.structible.core

import com.github.cerst.factories.constraints._
import com.github.cerst.structible.core.constraint.instances.{BigIntConstraints, ByteConstraints, DoubleConstraints, DurationConstraints, FloatConstraints, InstantConstraints, IntConstraints, JavaDurationConstraints, LongConstraints, OffsetDateTimeConstraints, ShortConstraints, StringConstraints, ZonedDateTimeConstraints}

/**
  * Aggregates all constraint type implementations for import convenience.
  */
object DefaultConstraints
    extends BigDecimalConstraints
    with BigIntConstraints
    with DoubleConstraints
    with DurationConstraints
    with FloatConstraints
    with InstantConstraints
    with IntConstraints
    with JavaDurationConstraints
    with LongConstraints
    with OffsetDateTimeConstraints
    with ByteConstraints
    with ShortConstraints
    with StringConstraints
    with ZonedDateTimeConstraints
