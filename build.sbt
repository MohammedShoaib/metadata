import sbt._

import scala.sys.process.Process

name := "kube-secrets-auditor"

val gitCommit =
  sys.env.getOrElse("GIT_COMMIT", Process("git rev-parse HEAD").lines.head)
val buildNumber = sys.env.getOrElse("BUILD_NUMBER", "000")

version := "0.1.0+" + gitCommit.substring(0, 7) + "." + buildNumber // scalastyle:ignore
scalaVersion := "2.12.8"

val akkaVersion     = "2.5.19"
val akkaHttpVersion = "10.1.7"
val json4sVersion   = "3.6.12"

libraryDependencies ++= Seq(
  "ch.qos.logback"             % "logback-classic"    % "1.2.3",
  "com.tesla"                  %% "common-core"       % "1.6.0+f6d04cf.143",
  "com.typesafe"               % "config"             % "1.3.3",
  "com.typesafe.akka"          %% "akka-actor"        % akkaVersion,
  "com.typesafe.akka"          %% "akka-actor-typed"  % akkaVersion,
  "com.typesafe.akka"          %% "akka-http-core"    % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"       % akkaVersion,
  "com.typesafe.akka"          %% "akka-testkit"      % akkaVersion % Test,
  "com.typesafe.scala-logging" %% "scala-logging"     % "3.9.0",
  "commons-codec"              % "commons-codec"      % "1.14",
  "io.skuber"                  %% "skuber"            % "2.0.12",
  "org.json4s"                 %% "json4s-jackson"    % json4sVersion,
  "org.json4s"                 %% "json4s-ext"        % json4sVersion,
  "org.scalatest"              %% "scalatest"         % "3.0.5" % "test"
)

val username       = sys.env.getOrElse("ARTIFACTORY_USERNAME", "")
val password       = sys.env.getOrElse("ARTIFACTORY_PASSWORD", "")
val artifactoryUrl = "artifactory.teslamotors.com"
val repoUrl        = s"https://$artifactoryUrl/artifactory/stst-ivy-virtual/"

credentials += Credentials("Artifactory Realm", artifactoryUrl, username, password)
externalResolvers := Seq(Resolver.defaultLocal, "Tesla Artifacts" at repoUrl)

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion,
      sbtVersion,
      "gitCommit" -> gitCommit
    ),
    buildInfoPackage := "com.tesla.KubeSecretsAuditor"
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint"
)
parallelExecution in Test := true

(scalastyleConfig in Compile) := baseDirectory.value / "project" / "scalastyle-config.xml"
(scalastyleConfig in Test) := baseDirectory.value / "project" / "scalastyle-config.xml"
