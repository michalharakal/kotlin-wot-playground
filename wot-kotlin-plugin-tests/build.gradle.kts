plugins {
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

dependencies {
  testImplementation(projects.wotKotlinPlugin)
  testImplementation(projects.wot)
  testImplementation(kotlin("compiler-embeddable"))
  testImplementation(kotlin("test-junit"))
  testImplementation(libs.assertk)
  testImplementation(libs.kotlin.compile.testing)
}
