plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.lucianpopescu.viewlanguage"
version = "1.0.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("241.14494.240")  // Official PhpStorm 2024.1
    type.set("IC")                // IntelliJ Community
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    patchPluginXml {
        version.set("1.0.0")
        sinceBuild.set("241")
        untilBuild.set("259.*")  // <-- MUCH higher (to cover 251+)
    }
}
