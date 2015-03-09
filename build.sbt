name := "XMLPrettyPrinter"

version := "1.0.0" //Remember: also change the version in XMLPrettyPrinter.scala

organization := "com.jmcejuela.scala.xml"

//-------------------------------------------------------------------------

scalaVersion := "2.11.6"

crossPaths := false //disable using the Scala version in output paths and artifacts

scalacOptions ++= Seq("-deprecation")

publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/.m2/repository")))

resolvers += "Local Maven Repository" at "file://" + (Path.userHome / ".m2" / "repository").absolutePath

libraryDependencies ++= Seq(
  /* Test */
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  /* Main */
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3"
)

//-------------------------------------------------------------------------

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource //sbteclipse: include resources in classpath

EclipseKeys.withSource := true //sbteclipse: download dependency packages' sources if available
