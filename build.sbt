name := "warmUp"

version := "0.1"

scalaVersion := "2.12.8"

// ScalaTest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

// ScalaCheck
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

//Wartremover
addCompilerPlugin("org.wartremover" %% "wartremover" % "2.4.1")
//wartremoverErrors ++= Warts.all
//wartremoverWarnings ++= Warts.all
wartremoverWarnings ++= Warts.unsafe
scalacOptions ++= Seq("-deprecation", "-Xlint")