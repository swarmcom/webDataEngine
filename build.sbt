name := """webDataEngine"""

version := "1.0-SNAPSHOT"

lazy val api = project

lazy val mongo = project

lazy val webDataEngine = Project("webDataEngine", file(".")).
  enablePlugins(PlayJava).
  aggregate(api,mongo).
  dependsOn(api,mongo)

scalaVersion := "2.11.6"

resolvers += "Spring Snapshots" at "http://maven.springframework.org/snapshot"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false