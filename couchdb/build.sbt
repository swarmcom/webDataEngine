name := """couchdb"""

version := "1.0-SNAPSHOT"

lazy val couchdb = (project in file(".")).dependsOn(api)

lazy val api = project in file("../api")

scalaVersion := "2.11.6"

resolvers += "Spring Snapshots" at "http://maven.springframework.org/snapshot"

libraryDependencies ++= Seq(
  "org.ektorp" % "org.ektorp" % "1.4.2",
  "org.springframework" % "spring-core" % "4.2.0.RELEASE",
  "org.springframework" % "spring-context" % "4.2.0.RELEASE",
  "org.springframework" % "spring-tx" % "4.2.0.RELEASE",
  "org.springframework.guice" % "spring-guice" % "1.0.0.BUILD-SNAPSHOT",
  "com.typesafe.play" % "play_2.11" % "2.4.3"
)