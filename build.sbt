import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayJava

name := """webDataEngine"""

version := "1.0-SNAPSHOT"

lazy val api = project

lazy val mongo = project

lazy val couchdb = project

lazy val security = project

lazy val tenancy = project

lazy val webDataEngine = Project("webDataEngine", file(".")).
  enablePlugins(PlayJava).
  aggregate(api, mongo, couchdb, security, tenancy).
  dependsOn(api, mongo, couchdb, security, tenancy)

scalaVersion := "2.11.6"

resolvers += "Spring Snapshots" at "http://maven.springframework.org/snapshot"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "org.springframework.guice" % "spring-guice" % "1.0.0.BUILD-SNAPSHOT",
  "org.springframework" % "spring-core" % "4.2.2.RELEASE",
  "org.springframework" % "spring-context" % "4.2.2.RELEASE"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

javaOptions in Test ++= Seq("-Dconfig.file=conf/test.application.conf",
  "-Dplay.http.router=test.Routes"
)

fork in run := false