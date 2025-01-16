plugins {
    id("java")
}

group = "org.example"
version = "1.2-1.21"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
