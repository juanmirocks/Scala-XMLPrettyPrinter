organization := "rocks.juanmi"
name := "XMLPrettyPrinter"
version := "2.1.0" //Remember: also change the version in XMLPrettyPrinter.scala

//-------------------------------------------------------------------------

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  //
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
)

//-------------------------------------------------------------------------

scalaVersion in ThisBuild := "2.11.11"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding",
  "UTF-8",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
)

scalafmtOnCompile in Compile := true
scalafmtOnCompile in Test := false
scalafmtVersion := "1.3.0"
