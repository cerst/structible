package com.github.cerst.structible.core

import com.github.cerst.structible.core.DefaultConstraints._
import org.scalatest.{FreeSpec, Matchers}

final class ConstraintSpec extends FreeSpec with Matchers {

  "&& fails if this fails" in {
    val constraint = c > 0 && c < 10
    assertResult(List("_ > 0"))(constraint(0))
  }

  "&& fails if that fails" in {
    val constraint = c > 0 && c < 10
    assertResult(List("_ < 10"))(constraint(10))
  }

  "&& fails if this and that fail" in {
    val constraint = c < 10 && c < 100
    assertResult(List("_ < 10", "_ < 100"))(constraint(101))
  }

  "&& succeeds if this and that succeed" in {
    val constraint = c > 0 && c < 10
    assertResult(List.empty)(constraint(7))
  }

  "|| fails if this and that fail" in {
    val constraint = c < 10 || c < 100
    assertResult(List("_ < 10", "_ < 100"))(constraint(101))
  }

  "|| succeeds if this succeeds" in {
    val constraint = c < 0 || c > 10
    assertResult(List.empty)(constraint(-1))
  }

  "|| succeeds if that succeeds" in {
    val constraint = c < 0 || c > 10
    assertResult(List.empty)(constraint(11))
  }

}
