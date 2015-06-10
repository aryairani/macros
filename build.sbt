organization := "net.arya"

macroParadise
monocle

scalacOptions += "-language:experimental.macros"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.4")

enablePlugins(GitVersioning)

enablePlugins(GitBranchPrompt)

//git.useGitDescribe := true

sonatypeProfileName := "net.arya"

pomExtra := {
  <url>http://github.com/refried/macros</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://opensource.org/licenses/MIT</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/refried/macros</connection>
      <developerConnection>scm:git:git@github.com:refried/macros</developerConnection>
      <url>github.com/refried/macros</url>
    </scm>
    <developers>
      <developer>
        <id>arya</id>
        <name>Arya Irani</name>
        <url>https://github.com/refried</url>
      </developer>
    </developers>
}
