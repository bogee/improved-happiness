
name := """hello-slick"""

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// LOGBACK
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.8"

// POSTGRESQL
libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1212"

// FLYWAYDB
libraryDependencies += "org.flywaydb" % "flyway-core" % "4.0.3"

// SLICK
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.1"

// PLAY
libraryDependencies += "com.typesafe.play" %% "play" % "2.5.10"

// PLAY SLICK
libraryDependencies += "com.typesafe.play" %% "play-slick" % "2.0.0"

// SPEC2
libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.5" % Test,
  "org.specs2" %% "specs2-mock" % "3.8.5" % Test
)

// PLAY TEST
libraryDependencies += "com.typesafe.play" %% "play-test" % "2.5.10" % Test

fork in run := true
