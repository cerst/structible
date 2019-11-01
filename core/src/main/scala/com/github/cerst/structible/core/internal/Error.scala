package com.github.cerst.structible.core.internal

import com.github.cerst.structible.core.RName

import scala.util.{Failure, Try}

object Error {

  def failure[C, R](c: C, rName: RName, cause: Throwable): Try[R] = {
    val msg = errorMsg(c, rName, reason = getMessageNonNull(cause))
    Failure(new RuntimeException(msg, cause))
  }

  def failure[C, R](c: C, rName: RName, reason: String = ""): Try[R] = {
    val msg = errorMsg(c, rName, reason)
    Failure(new RuntimeException(msg))
  }

  def left[C, R](c: C, rName: RName, cause: Throwable): Either[String, R] = {
    val msg = errorMsg(c, rName, reason = getMessageNonNull(cause))
    Left(msg)
  }

  def left[C, R](c: C, rName: RName, reason: String = ""): Either[String, R] = {
    val msg = errorMsg(c, rName, reason)
    Left(msg)
  }

  def throwException[C, R](c: C, rName: RName, reason: String): Nothing = {
    val msg = errorMsg(c, rName, reason)
    throw new IllegalArgumentException(msg)
  }

  def throwException[C, R](c: C, rName: RName, cause: Throwable): Nothing = {
    val msg = errorMsg(c, rName, reason = "")
    throw new IllegalArgumentException(msg, cause)
  }

  private def errorMsg[C, R](c: C, rName: RName, reason: String): String = {
    val suffix = if (reason.nonEmpty) s" due to: $reason" else ""
    s"Failed to construct '${rName.value}' from '$c' $suffix"
  }

  /**
    * Safe way to access [[java.lang.Throwable#getMessage()]] which may return null.
    */
  private def getMessageNonNull(throwable: Throwable): String = {
    if (throwable.getMessage == null) "<exception.getMessage was null>" else throwable.getMessage
  }

}
