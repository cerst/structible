addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")

// only affects the build
// used to suppress the following warnings when loading the project
//
// SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
// SLF4J: Defaulting to no-operation (NOP) logger implementation
// SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.26"