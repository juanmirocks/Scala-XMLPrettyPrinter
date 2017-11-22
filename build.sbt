organization := "rocks.juanmi"
name := "XMLPrettyPrinter"
version := "2.0.0" //Remember: also change the version in XMLPrettyPrinter.scala

//-------------------------------------------------------------------------

scalaVersion in ThisBuild := "2.11.11"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
)

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.4" % Test,
  //
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3"
)
