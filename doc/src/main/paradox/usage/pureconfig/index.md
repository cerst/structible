# Pureconfig

$name.pureconfig$ is available for Scala 2.12 and 2.13.

It provides derivations for

* _ConfigConvert_
* _ConfigReader_
* _ConfigWriter_

Make sure to familiarize yourself with @ref:[creating _structible_ instances](../index.md) before looking at the examples below.

## Dependency

@@dependency[sbt,Maven,Gradle] {
    group="$group$"
    artifact="$name.pureconfig$"
    version="$version$"
}

## Example (Individual)
You can derive _ConfigReader_ and _ConfigWriter_ individually like this (most of the time, only _ConfigReader_ is required):

@@snip [IndividualExample.scala]($root$/src/main/scala/usage/pureconfig/IndividualExample.scala) { #example }

## Example (Compact)
Whenever you need both of them, you can also create a _ConfigConvert_ instance in their place.

@@snip [CompactExample.scala]($root$/src/main/scala/usage/pureconfig/CompactExample.scala) { #example }
