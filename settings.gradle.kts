pluginManagement {
    repositories {
        maven { url = uri("https://maven.minecraftforge.net") }
        maven { url = uri("https://maven.parchmentmc.org") }
        maven { url = uri("https://repo.spongepowered.org/repository/maven-public") }
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "net.minecraftforge.gradle")
                useModule("net.minecraftforge.gradle:ForgeGradle:${requested.version}")

            if (requested.id.id == "org.spongepowered.mixin")
                useModule("org.spongepowered:mixingradle:${requested.version}")

            if (requested.id.id == "org.parchmentmc.librarian.forgegradle")
                useModule("org.parchmentmc:librarian:${requested.version}")
        }
    }
}

rootProject.name = "minefactoryrenewed"