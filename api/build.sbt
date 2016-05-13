name := """api"""

version := "1.0-SNAPSHOT"

lazy val api = (project in file("."))

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play_2.11" % "2.4.3",
  "org.springframework" % "spring-core" % "4.2.2.RELEASE",
  "org.springframework" % "spring-context" % "4.2.2.RELEASE",
  "org.springframework.security" % "spring-security-core" % "4.0.3.RELEASE",
  "org.springframework.security" % "spring-security-config" % "4.0.3.RELEASE",
  "commons-io" % "commons-io" % "2.4",
  "junit" % "junit" % "4.12"
)