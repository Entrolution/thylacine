ThisBuild / tlBaseVersion := "0.12"

ThisBuild / organization     := "ai.entrolution"
ThisBuild / organizationName := "Greg von Nessi"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.Apache2)
ThisBuild / developers ++= List(
  tlGitHubDev("gvonness", "Greg von Nessi")
)

// CI configuration - Java 21 required for Smile 3.x
ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("21"))

scalaVersion                   := DependencyVersions.scala2p13Version
ThisBuild / crossScalaVersions := Seq(DependencyVersions.scala2p13Version)

Global / idePackagePrefix := Some("ai.entrolution")
Global / excludeLintKeys += idePackagePrefix

lazy val commonSettings = Seq(
  scalaVersion := DependencyVersions.scala2p13Version,
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-encoding",
    "UTF-8",
    "-Xlint:_",
    "-Ywarn-unused:-implicits",
    "-Ywarn-value-discard",
    "-Ywarn-dead-code"
  )
)

lazy val thylacine = (project in file("."))
  .settings(
    commonSettings,
    name := "thylacine",
    libraryDependencies ++= Dependencies.thylacine,
    crossScalaVersions := Seq(
      DependencyVersions.scala2p13Version
    )
  )
