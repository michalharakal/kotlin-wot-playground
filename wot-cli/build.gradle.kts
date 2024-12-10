import org.jetbrains.kotlin.gradle.plugin.NATIVE_COMPILER_PLUGIN_CLASSPATH_CONFIGURATION_NAME
import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.1.0"
    application
}

group = "sk.ai.net.wot"
version = "2.3.0-SNAPSHOT"

dependencies {
    add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, projects.wotKotlinPlugin)
    add(NATIVE_COMPILER_PLUGIN_CLASSPATH_CONFIGURATION_NAME, projects.wotKotlinPlugin)

}

dependencies {
    implementation(project(":wot"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3") // Use the latest version


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}


application {
    mainClass.set("wot.CliKt")
}