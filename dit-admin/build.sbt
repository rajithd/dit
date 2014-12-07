name := """dit-admin"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  cache,
  "org.mongodb" % "mongo-java-driver" % "2.12.4",
  "com.dit" % "dit-domain" % "1.0-SNAPSHOT",
  "com.google.code.gson" % "gson" % "2.3.1",
  "org.apache.httpcomponents" % "httpclient" % "4.3.2",
  "org.apache.httpcomponents" % "httpcore" % "4.3.2",
  "commons-io" % "commons-io" % "2.4"
)
