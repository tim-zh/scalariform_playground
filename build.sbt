name := "scalariform_playground"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalariform" %% "scalariform" % "0.1.7"
)

assemblyOutputPath in assembly := file("./jar.jar")