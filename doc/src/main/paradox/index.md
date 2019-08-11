# structible

Standardize and ease conversions between different, environment-based representations of the same type.

@@@ index

* [Changelog](changelog/index.md)
* [Licenses](licenses/index.md)
* [Usage](usage/index.md)

@@@

## Motivation
Applications nowadays typically integrate with a variety of environments (and thus their type systems) such as messaging formats, configuration (files) or databases.  

While these environments usually support _common types_ (e.g. primitive types, regular classes) out of the box, support for more _refined types_ (e.g. timestamps or domain specific value classes) varies greatly or is lacking completely.  

As a consequence, you end up writing lots of one-to-one integration code mapping between the two.

## Overview
Structible addresses this issue in two steps: 

**1)**  
By way of its _core_ library providing three type classes to standardize the aforementioned conversion:

* _Constructible_: _common type_ -> _refined type_ (think read)
* _Destructible_: _refined type_ -> _common type_ (think write)
* _Structible_: Combination of _Constructible_ and _Destructible_

**2)**  
By way of a variety of satellite libraries each providing one liners to create the conversion(s) for a specific environment (usually type classes as well). 
