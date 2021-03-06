# Changelog

## 0.6.4

* Upgrade jsoniter-scala to 1.2.0

## 0.6.3

* Patch upgrade for dependencies

## 0.6.2

* Upgrade to sbt 1.3.3

## 0.6.1

* Upgrade build

## 0.6.0
**Breaking Changes**

* Integrate the _factories_ library into _structible_ to specify construction constraints - see the updated documentation
* Use a value class for handling manually passed-in names of the refined type _R_ and slightly improve its ergonomics

**Other Changes**

* add _structible-core_ support for _LocalDateTime_
* (re-) add _structible-jsoniter-scala_ support for 
    * _Byte_
    * _Instant_
    * _LocalDateTime_

## 0.5.2

* Downgrade to sbt 1.2.8 to make ghpages work again

## 0.5.1
Did **not** get released properly due a build-related problem.

* fix build related problem which prevent releasing
* add missing Avro4s and Pureconfig projects to root for them to be released

## 0.5.0
Did **not** get released properly due a build-related problem.

* add support for Avro4s
* add support for Pureconfig
* update dependencies

## 0.4.0

**Breaking Changes**

* replace all _construct_, _destruct_ and _instance_ methods having suffixes _safe_ and _unsafe_ with such better
representing the involved types (e.g. _Either_ or _Try_)
* extend the external and internal API to support exceptions, _Either_, _Try_, and _Option_  

**Other Changes**

* update Dependencies (see _Licenses_ in the documentation)

## 0.3.0

* cross-build for Scala 2.12 and 2.13 whenever possible
* update build
* update dependencies (see _Licenses_ in the documentation)



## 0.2.0

* extend _structible-jsoniter-scala_ to support the following types
    * _BigDecimal_
    * _BigInt_
    * _java.time.Duration_
    * _java.time.Instant_
    * _java.time.OffsetDateTime_
    * _java.time.ZonedDateTime_
* update Dependencies (see _Licenses_ in the documentation)



## 0.1.2

* Add missing _index.html_ for documentation entry site



## 0.1.1

* Dependency Updates
    * Akka: 2.5.21 -> 2.5.22
    * Akka-Http: 10.1.7 -> 10.1.8
    * Jsoniter-Scala: 0.40.0 -> 0.46.0
    * Quill: 3.0.1 -> 3.1.0
    * UTest: 0.6.6 -> 0.6.7
* Push documentation into Github-pages branch rather than a repo (the one for 0.1.0 is thus lost) 



## 0.1.0

* Release first version including derivations for
    * akka-http
    * configs
    * jsoniter-scala
    * quill
