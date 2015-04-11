name := "torrent-delolla"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.6"

scalacOptions := Seq("-Xlint", "-Xfatal-warnings", "-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

libraryDependencies ++= {
  val akkaV = "2.3.7"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.scalatest" %% "scalatest" % "2.1.7" % "test"
  )
}

