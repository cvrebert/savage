name := "savage"

version := "1.0"

scalaVersion := "2.11.8"

mainClass := Some("com.getbootstrap.savage.server.Boot")

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

resolvers += "Eclipse Foundation Releases" at "https://repo.eclipse.org/content/repositories/releases/"

resolvers += "Eclipse Foundation Snapshots" at "https://repo.eclipse.org/content/repositories/snapshots/"

libraryDependencies += "org.eclipse.mylyn.github" % "org.eclipse.egit.github.core" % "4.6.0.201612231935-r"

// egit-github needs Gson, but doesn't explicitly require it
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.9"

// For reading PEM ("-----BEGIN PUBLIC KEY-----"), which Travis's API uses for its public key.
libraryDependencies += "org.bouncycastle" % "bcpkix-jdk15on" % "1.56"

libraryDependencies ++= {
  val akkaV = "2.3.16"
  val sprayV = "1.3.4"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV   % "test",
    "io.spray"            %%  "spray-json"    % "1.3.3",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-slf4j"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV    % "test",
    "org.specs2"          %%  "specs2"        % "2.3.12" % "test"
  )
}

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "–Xlint", "-encoding", "utf8")

scalacOptions in Test ++= Seq("-Yrangepos")

// parallelExecution in Test := false

Revolver.settings
