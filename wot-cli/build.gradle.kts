import org.jetbrains.kotlin.gradle.plugin.NATIVE_COMPILER_PLUGIN_CLASSPATH_CONFIGURATION_NAME
import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    kotlin("jvm")
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
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}


application {
    mainClass.set("MainKt")
}