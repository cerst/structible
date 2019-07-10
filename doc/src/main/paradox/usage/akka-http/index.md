# Akka-Http

$name.akka-http$ is available for Scala 2.12 and 2.13.

It provides derivations for:

* _PathMatcher_
* _Unmarshaller_


## Dependency

@@dependency[sbt,Maven,Gradle] {
    group="$group$"
    artifact="$name.akka-http$"
    version="$version$"
}


## PathMatcher Example

@@snip [PathMatcherExample.scala]($root$/src/main/scala/usage/akkahttp/PathMatcherExample.scala) { #example } 

  
## Unmarshaller Example

This derivation was originally added to address a quirk in an Http Api which returned the id of a
generated resource as plain text. Hence, the response entity needed to be unmarshalled directly as in
_Unmarshal(response.entity).to[A]_.  
As long as you receive a properly formatted message, use a derivation for the employed messaging protocol and library
(e.g. your favorite JSON library). 

@@snip [UnmarshallerExample.scala]($root$/src/main/scala/usage/akkahttp/UnmarshallerExample.scala) { #example }
