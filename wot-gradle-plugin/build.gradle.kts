import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    id("java-gradle-plugin")
    `kotlin-dsl`
    kotlin("jvm")
    id("com.github.gmazzo.buildconfig")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish.base")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
    implementation(projects.wot)
    implementation(projects.wotKotlinPlugin)
    implementation(libs.kotlin.gradlePlugin)
    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}

buildConfig {
    useKotlinOutput {
        internalVisibility = true
    }

    val compilerPlugin = projects.wotKotlinPlugin
    packageName("sk.ai.net.wot.gradle")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${libs.plugins.wot.kotlin.get()}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${compilerPlugin.group}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${compilerPlugin.name}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${compilerPlugin.version}\"")
    buildConfigField("String", "wotVersion", "\"${project.version}\"")
}

gradlePlugin {
    plugins {
        create("burst") {
            id = "sk.ai.net"
            displayName = "wot"
            description = "Compiler plugin to transform WoT"
            implementationClass = "sk.ai.net.wot.gradle.WotPlugin"
        }
    }
}

tasks {
    test {
        systemProperty("wotVersion", project.version)
        dependsOn(":wot-gradle-plugin:publishAllPublicationsToTestMavenRepository")
        dependsOn(":wot-kotlin-plugin:publishAllPublicationsToTestMavenRepository")

        // Depend on the host platforms exercised by BurstGradlePluginTest.
        dependsOn(":wot:publishJsPublicationToTestMavenRepository")
        dependsOn(":wot:publishJvmPublicationToTestMavenRepository")
        dependsOn(":wot:publishKotlinMultiplatformPublicationToTestMavenRepository")
        dependsOn(":wot:publishLinuxX64PublicationToTestMavenRepository")
        dependsOn(":wot:publishMacosArm64PublicationToTestMavenRepository")
    }
}

configure<MavenPublishBaseExtension> {
    configure(
        GradlePlugin(
            javadocJar = JavadocJar.Empty()
        )
    )
}
