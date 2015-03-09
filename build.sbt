name := "XMLPrettyPrinter"

version := "0.4.0" //Remember: also change the version in XMLPrettyPrinter.scala

scalaVersion := "2.11.6"

organization := "com.jmcejuela.scala.xml"

crossPaths := false //disable using the Scala version in output paths and artifacts


/** Configuration */
//sbteclipse: include resources in classpath
EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

//sbteclipse: download dependency packages' sources if available
EclipseKeys.withSource := true

scalacOptions ++= Seq("-deprecation")

/** Publish */
publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/.m2/repository")))


/** Repositories */
resolvers += "Local Maven Repository" at "file://" + (Path.userHome / ".m2" / "repository").absolutePath


/** Dependencies */
libraryDependencies ++= Seq(
  /* Test */
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  /* Main */
)
