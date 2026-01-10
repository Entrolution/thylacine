import sbt.*

object DependencyVersions {
  val scala2p13Version = "2.13.16"

  val bengalStmVersion           = "0.9.5"
  val bigMathVersion             = "2.3.2"
  val catsEffectVersion          = "3.4.8"
  val catsEffectTestingVersion   = "1.4.0"
  val commonMathVersion          = "3.6.1"
  val ejmlVersion                = "0.44.0"
  val parallelCollectionsVersion = "1.0.4"
  val smileVersion               = "3.1.1"
}

object Dependencies {
  import DependencyVersions.*

  private val bengalStm: ModuleID =
    "ai.entrolution" %% "bengal-stm" % bengalStmVersion

  private val bigMath: ModuleID =
    "ch.obermuhlner" % "big-math" % bigMathVersion

  private val commonMath: ModuleID =
    "org.apache.commons" % "commons-math3" % commonMathVersion

  private val catsEffect: ModuleID =
    "org.typelevel" %% "cats-effect" % catsEffectVersion

  private val catsEffectTesting: ModuleID =
    "org.typelevel" %% "cats-effect-testing-scalatest" % catsEffectTestingVersion % "test"

  private val ejml: ModuleID =
    "org.ejml" % "ejml-all" % ejmlVersion

  private val parallelCollections: ModuleID =
    "org.scala-lang.modules" %% "scala-parallel-collections" % parallelCollectionsVersion

  private val smile: ModuleID =
    "com.github.haifengl" %% "smile-scala" % smileVersion

  val thylacine: Seq[ModuleID] =
    Seq(
      bengalStm,
      bigMath,
      catsEffect,
      catsEffectTesting,
      commonMath,
      ejml,
      parallelCollections,
      smile
    )
}
