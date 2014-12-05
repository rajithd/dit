name := """dit-admin"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "2.12.4",
  "com.dit" % "dit-service" % "1.0-SNAPSHOT",
  "com.google.code.gson" % "gson" % "2.3.1"
)
