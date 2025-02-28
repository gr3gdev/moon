plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "fenrir"
include("fenrir-api", "fenrir-http", "fenrir-thymeleaf", "fenrir-jpa", "fenrir-security",
    "fenrir-file", "fenrir-json", "fenrir-hal", "fenrir-websocket")
include("fenrir-gradle-plugin")
include("fenrir-test")
include("fenrir-thymeleaf-validator")
