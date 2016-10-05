name := """security"""

version := "1.0-SNAPSHOT"

lazy val security = (project in file(".")).dependsOn(api)

lazy val api = project in file("../api")

scalaVersion := "2.11.6"

resolvers += "Spring Snapshots" at "http://maven.springframework.org/snapshot"

libraryDependencies ++= Seq(
  "org.springframework.guice" % "spring-guice" % "1.0.0.BUILD-SNAPSHOT",
  "javax.servlet" % "javax.servlet-api" % "3.1.0",
  "org.springframework" % "spring-core" % "4.2.2.RELEASE",
  "org.springframework.security" % "spring-security-core" % "4.0.3.RELEASE",
  "org.springframework.security" % "spring-security-config" % "4.0.3.RELEASE",
  "org.springframework.security" % "spring-security-web" % "4.0.3.RELEASE",
  "com.nimbusds" % "oauth2-oidc-sdk" % "5.7",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.16.1",
  "org.pac4j" % "play-pac4j" % "2.3.1",
  "org.pac4j" % "pac4j-core" % "1.9.2",
  "org.pac4j" % "pac4j-oidc" % "1.9.2",
  "org.pac4j" % "pac4j-http" % "1.9.2",
  "org.pac4j" % "spring-security-pac4j" % "2.0.1"
)

dependencyOverrides += "commons-io" % "commons-io" % "2.4"