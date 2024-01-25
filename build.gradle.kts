plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.21"
  id("org.jetbrains.intellij") version "1.16.1"
}

group = "sk.araed.intellij.plugins.stringtools"
version = "4.1"

repositories {
  mavenCentral()
}

intellij {
  version.set("2023.1.5")
  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
  }

  patchPluginXml {
    sinceBuild.set("203")
    untilBuild.set("241.*")
  }

}
