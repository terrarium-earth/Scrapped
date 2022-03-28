import net.minecraftforge.gradle.userdev.UserDevExtension

val modVendor: String by project
val modName: String by project
val modid: String by project
val modGroup: String by project
val modVersion: String by project
val mappingsVersion: String by project
val minecraftVersion: String by project
val forgeVersion: String by project
val jeiVersion: String by project

plugins {
    java
    idea

    id("net.minecraftforge.gradle") version("5.1.+")
    id("org.spongepowered.mixin") version("0.7-SNAPSHOT")
    id("org.parchmentmc.librarian.forgegradle") version("1.+")
}

version = modVersion
group = "${modGroup}:${modid}"
base.archivesName.set(modid)

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

mixin {
    add(sourceSets["main"], "minefactoryrenewed.refmap.json")
    config("minefactoryrenewed.mixins.json")
}

configure<UserDevExtension> {
    mappings("parchment", mappingsVersion)

    runs {
        create("client") {
            workingDirectory(file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${projectDir}/build/createSrgToMcp/output.srg")

            mods {
                create(modid) {
                    source(sourceSets["main"])
                }
            }
        }

        create("server") {
            workingDirectory(file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create(modid) {
                    source(sourceSets["main"])
                }
            }
        }

        create("data") {
            workingDirectory(file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            args(listOf(
                "--mod", modid, "--all", "--output",
                file("src/generated/resources/"),
                "--existing", file("src/main/resources/")
            ))

            mods {
                create(modid) {
                    source(sourceSets["main"])
                }
            }
        }
    }
}

repositories {
    maven { url = uri("https://dvs1.progwml6.com/files/maven/") }
}

dependencies {
    add("minecraft", "net.minecraftforge:forge:$minecraftVersion-$forgeVersion")
    implementation(fg.deobf("mezz.jei:jei-$minecraftVersion:$jeiVersion"))
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources")
        }
    }
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "Specification-Title" to modName,
            "Specification-Vendor" to modVendor,
            "Specification-Version" to "1",
            "Implementation-Title" to modName,
            "Implementation-Version" to modVersion,
            "Implementation-Vendor" to modVendor,
            "MixinConfigs" to "minefactoryrenewed.mixins.json"
        )
    }

    finalizedBy("reobfJar")
}