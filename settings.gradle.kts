plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "kstd-test"
include("kstd-interface")
include("kstd-domain")
include("kstd-infra:kstd-h2")
findProject(":kstd-infra:kstd-h2")?.name = "kstd-h2"
include("kstd-common")
