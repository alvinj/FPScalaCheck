name := "FPScalaCheck"

version := "1.0"

scalaVersion := "2.12.2"

// all of these imports will only work under 'src/test/scala'
libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",  //scalacheck
    "org.scalactic" %% "scalactic" % "3.0.1" % "test",     //scalatest
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"      //scalatest
)

scalacOptions += "-deprecation"


