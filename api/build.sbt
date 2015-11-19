name := """api"""

version := "1.0-SNAPSHOT"

lazy val api = (project in file("."))

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play_2.11" % "2.4.3"
)