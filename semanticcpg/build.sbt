name := "semanticcpg"

dependsOn(Projects.codepropertygraph)

libraryDependencies ++= Seq(
  "org.apache.commons"  % "commons-lang3"    % "3.8",
  "org.json4s" %% "json4s-native" % "3.6.7",
  "com.massisframework" % "j-text-utils" % "0.3.4",
  "org.scalatest"       %% "scalatest"       % "3.0.8" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg % Test exclude("io.shiftleft", "codepropertygraph") exclude("io.shiftleft", "codepropertygraph-protos")
)

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-title",
  "semanticcpg apidocs",
  "-doc-version",
  version.value
)

compile / javacOptions ++= Seq("-g") //debug symbols

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
