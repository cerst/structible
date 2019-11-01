# Usage

In order to the _structible_ library including any derivation provided by its satellite projects, you first need a create a
_structible_ instance. Once you have that check the sub-pages on the left for library-specific derivations.

## Dependency

@@dependency[sbt,Maven,Gradle] {
    group="$group$"
    artifact="$name.core$"
    version="$version$"
}  

## Basic Example
Here's an example for a _UserId_ value class:

@@snip [BasicExample.scala]($root$/src/main/scala/usage/core/BasicExample.scala) { #example }

_Structible.structible_ takes four parameters:

1. a _construct_ function - usually a constructor/ function which may throw - but you can also use functions returning
_Either[String, R]_, _Option[R]_ or _Try[R]_
2. a _destruct_ function - the opposite of _construct_ which is expected to be safe (i.e. not to throw)
3. a _constraint_ to be checked - in this case _unconstrained_ which does not require anything

## Constraints
Structible Constraints are based on the standard operators you'd use in a typical _require_ expression 
(e.g. _>=_, _matches_, _length_) and can be combined using the _||_ and _&&_ operator with _&&_ having a high precedence.  
Let's assume, you want the value of _UserId_ to be a UInt:

@@snip [ConstraintExample.scala]($root$/src/main/scala/usage/core/ConstraintExample.scala) { #example }

Note the additional import _DefaultConstraints.__ which is required because _structible constraints_ are implemented as
type classes. To see the available constraint syntax and instances, check the sources of 
_ConstraintSyntax_ and _DefaultConstraints_ respectively.

## Working without class tags
In some circumstances, you might not be able to get a _ClassTag[R]_ from the compiler. To fix that, all _structible_
methods provide an overload accepting either what is expected to be the companion object of _R_ or a plain _String_.

## Concerning _AnyVal_ and access modifiers ( _private_ )
You may have noticed that the above example uses _AnyVal_ to form a _value class_ and defines both the constructor
as well as the _structible_ member in the companion object as _private_. However, none of that is a requirement.  
Rather, _structible_ is intended to work with any (_value_) class definition style. Likewise, constructor and field are
merely set to _private_ to show that external access isn't needed. Indeed, it may make sense to keep the _structible_
as _public_ and even _implicit_ assuming that you (eventually) want to use self-made or external integration (though
none are known at the time of writing).   

@@@ index

* [akka-http](akka-http/index.md)
* [avro4s](avro4s/index.md)
* [configs](configs/index.md)
* [jsoniterscala](jsoniterscala/index.md)
* [pureconfig](pureconfig/index.md)
* [quill](quill/index.md)

@@@
