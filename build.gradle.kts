plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.21"
  id("org.jetbrains.intellij") version "1.16.1"
}

group = "sk.araed.intellij.plugins.stringtools"
version = "4.21"

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.google.code.gson:gson:2.8.7");
  implementation("com.paypal.sdk:rest-api-sdk:1.14.0")
}

intellij {
  version.set("2023.3.3")
  type.set("IU")
}

tasks {
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }
  patchPluginXml {
    sinceBuild.set("203")
  }
}
