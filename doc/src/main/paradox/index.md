# structible

Standardize and ease conversions between different, environment-based representations of the same type.

@@@ index

* [Usage](usage/index.md)
* [Changelog](changelog/index.md)
* [Licenses](licenses/index.md)

@@@

## Motivation
Applications nowadays typically integrate with a variety of environments (and thus their type systems) such as messaging formats, configuration (files) or databases.  

While these environments usually support _common_ types (e.g. primitive types, regular classes) out of the box, support for more _refined_ types (e.g. timestamps or domain specific value classes) varies greatly or is lacking completely.  

As a consequence, you end up writing lots of one-to-one integration code mapping between the two.

## Overview
_structible_ attempts to address this issue as follows:

* Instead of each library providing its own way of integrating _refined types_, provide a common way to specify type conversions and provide a small derivation module for each library
* Provide a DSL for common constraints to be checked when mapping a _common_ type to a _refined_ one (e.g. _c >= 0, c matches \<regex>_)
* Generate error messages to avoid hand-crafted (and thus) error-prone ones (typos, refactoring, ...) 
