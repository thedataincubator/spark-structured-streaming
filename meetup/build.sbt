name := "MeetupStreaming"

version := "1.0"

scalaVersion := "2.11.8"

scalaSource in Compile := baseDirectory.value / "src"

// | @see https://github.com/jrudolph/sbt-dependency-graph
net.virtualvoid.sbt.graph.Plugin.graphSettings

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.0.0" % "provided"
libraryDependencies += "net.liftweb" %% "lift-json" % "2.6.2" 

