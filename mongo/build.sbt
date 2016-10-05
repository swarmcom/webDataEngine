name := """mongo"""

version := "1.0-SNAPSHOT"

lazy val mongo = (project in file(".")).dependsOn(api)

lazy val api = project in file("../api")

scalaVersion := "2.11.6"

resolvers += "Spring Snapshots" at "http://maven.springframework.org/snapshot"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "2.13.1",
  "org.springframework.data" % "spring-data-mongodb" % "1.8.1.RELEASE",
  "org.springframework" % "spring-core" % "4.2.2.RELEASE",
  "org.springframework" % "spring-context" % "4.2.2.RELEASE",
  "org.springframework" % "spring-tx" % "4.2.0.RELEASE",
  "org.springframework.guice" % "spring-guice" % "1.0.0.BUILD-SNAPSHOT"
)