package com.github.cerst.structible.core

import org.scalatest.{FreeSpec, Matchers}

final class PackageSpec extends FreeSpec with Matchers {

  "regularSimpleName should work" in {
    val actual = RName[PackageSpec]
    val expected = RName("PackageSpec")

    assert(actual == expected)
  }

  "companionSimpleName should work" in {
    val actual = companionToRName(PackageSpec)
    val expected = RName("PackageSpec")

    assert(actual == expected)
  }

}

// needed for testing
object PackageSpec
