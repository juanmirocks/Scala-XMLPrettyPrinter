organization := "rocks.juanmi"
name := "XMLPrettyPrinter"
version := "2.2.0" //Remember: also change the version in XMLPrettyPrinter.scala

//-------------------------------------------------------------------------

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  //
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
)

//-------------------------------------------------------------------------

val scala_2_13 = "2.13.1"
val scala_2_12 = "2.12.10"
val scala_2_11 = "2.11.12"

scalaVersion in ThisBuild := scala_2_13

crossScalaVersions := Seq(scala_2_11, scala_2_12, scala_2_13)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding",
  "UTF-8",
  "-Ywarn-unused",
  //"-Ywarn-unused-import"
)

scalafmtOnCompile in Compile := true
scalafmtOnCompile in Test := false
scalafmtVersion := "1.3.0"
