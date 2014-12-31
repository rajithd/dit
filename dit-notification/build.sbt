name := """dit-notification"""

version := "1.00-SNAPSHOT"

scalaVersion := "2.11.1"

Revolver.settings

com.github.retronym.SbtOneJar.oneJarSettings

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:postfixOps")

libraryDependencies ++= {
  val akkaVersion = "2.3.4"
  Seq(
    "com.typesafe.akka"       %% "akka-actor"       % akkaVersion,
    "com.typesafe.akka"       %% "akka-slf4j"       % akkaVersion,
    "com.typesafe.akka"       %% "akka-testkit"     % akkaVersion,
    "com.rabbitmq"            %  "amqp-client"      % "3.3.4",
    "org.json4s"              %% "json4s-jackson"   % "3.2.10",
    "ch.qos.logback"          %  "logback-classic"  % "1.1.2",
    "org.json4s"              %% "json4s-jackson"   % "3.2.10",
    "org.antlr"               %  "ST4"              % "4.0.8",
    "javax.mail"              %  "mail"             % "1.5.0-b01",
    "org.scalatest"           %% "scalatest"        % "2.1.6"         % "test"
  )
}
