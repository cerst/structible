# Avro4s

$name.avro4s$ is available for Scala 2.12 and 2.13.

It provides derivations for:

* _Encoder_
* _Decoder_
* _SchemaFor_
* _BicoderWithSchemaFor_ (see below)

These allow you to (de-) serialize value classes as if they were plain values.
 
Make sure to familiarize yourself with @ref:[creating _structible_ instances](../index.md) before looking at the examples below.

## Dependency

@@dependency[sbt,Maven,Gradle] {
    group="$group$"
    artifact="$name.avro4s$"
    version="$version$"
}


## Example (Individual)
You can derive _Encoder_, _Decoder_ and _SchemaFor_ individually like this:

@@snip [IndividualExample.scala]($root$/src/main/scala/usage/avro4s/IndividualExample.scala) { #example }


## Example (Compact)

Given that you commonly need all three of them, _structible-avro4s_ provides a custom trait to derive everything in one line:

@@snip [CompactExample.scala]($root$/src/main/scala/usage/avro4s/CompactExample.scala) { #example }
