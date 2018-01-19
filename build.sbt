name := "scala-minesweeper-tui"
organization := "de.htwg.scala-minesweeper"
version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.4.16"
libraryDependencies += "com.typesafe.akka" % "akka-remote_2.12" % "2.4.16"

libraryDependencies += "de.htwg.scala-minesweeper" %% "scala-minesweeper-api" % "0.1-SNAPSHOT"