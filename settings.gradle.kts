plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "gvt-practice-coroutine"

include("api")
include("application")
include("db")
include("framework")
