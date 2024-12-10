package sk.ai.net.wot.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.*

@Suppress("unused") // Created reflectively by Gradle.
class WotPlugin : KotlinCompilerPluginSupportPlugin {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

    override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
        artifactId = BuildConfig.KOTLIN_PLUGIN_NAME,
        version = BuildConfig.KOTLIN_PLUGIN_VERSION,
    )

    override fun apply(target: Project) {
        super.apply(target)

        // kotlin("multiplatform")
        target.plugins.withType<KotlinMultiplatformPluginWrapper> {
            target.configure<KotlinMultiplatformExtension> {
                /*
            sourceSets {
                commonTest {
                    dependencies {
                        implementation("sk.ai.net.wot:wot:${BuildConfig.wotVersion}")
                    }
                }

                }
                 */
            }
        }

        // kotlin("jvm")
        target.plugins.withType<KotlinPluginWrapper> {
            target.dependencies {
                add("testImplementation", "sk.ai.net.wot:wot:${BuildConfig.wotVersion}")
            }
        }

        // kotlin("android")
        target.plugins.withType<KotlinAndroidPluginWrapper> {
            target.dependencies {
                add("testImplementation", "sk.ai.net.wot:wot:${BuildConfig.wotVersion}")
                add("androidTestImplementation", "sk.ai.net.wot:wot:${BuildConfig.wotVersion}")
            }
        }
    }

    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>,
    ): Provider<List<SubpluginOption>> {
        return kotlinCompilation.target.project.provider {
            listOf() // No options.
        }
    }
}