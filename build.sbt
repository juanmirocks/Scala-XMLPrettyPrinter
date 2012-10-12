/**
 * Project
 *
 * sbt documentation: https://github.com/harrah/xsbt/wiki
 */
name := "XMLPrettyPrinter"

version := "0.1"

organization := "com.jmcejuela.scala.xml"

//scalaVersion := "2.9.1" //define the exact scala version

crossPaths := false //disable using the Scala version in output paths and artifacts


/** Configuration */
//sbteclipse: include resources in classpath
EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

//sbteclipse: download dependency packages' sources if available
EclipseKeys.withSource := true


/** Publish */
// publish to local maven
publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/.m2/repository")))


/** Repositories */
// use local maven
resolvers += "Local Maven Repository" at "file://" + (Path.userHome / ".m2" / "repository").absolutePath


/** Dependencies */
libraryDependencies ++= Seq(
    /* Test */
    "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
    /* Main */
    //"com.weiglewilczek.slf4s" %% "slf4s" % "1.0.7",
    //"ch.qos.logback" % "logback-classic" % "1.0.6"
)
