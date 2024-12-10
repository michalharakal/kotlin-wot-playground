rootProject.name = "wot-root"

include(":wot")
include(":wot-sample")
include(":wot-gradle-plugin")
include(":wot-kotlin-plugin")
include(":wot-kotlin-plugin-tests")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("wot-cli")
