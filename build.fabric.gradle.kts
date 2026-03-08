@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom-remap")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["version"] = prop("mod.version") + "+" + prop("deps.minecraft")
        this["minecraft"] = prop("mod.mc_dep_fabric")
        this["ctFile"] = prop("mod.id") + "+" + prop("deps.minecraft") + ".classtweaker"
        this["extraFabricEntrypoints"] = if (stonecutter.eval(stonecutter.current.version, ">=1.21"))
                ""
            else
                ", \"mm:early_risers\": [\"com.macuguita.obese_crops.fabric.ObeseCropsASM\"]"
        this["extraFabricMixins"] = if (stonecutter.eval(stonecutter.current.version, ">=1.21"))
                ""
            else
                ", \"obese_crops.fabric.mixins.json\""
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }

}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String

loom {
    accessWidenerPath = rootProject.file("src/main/resources/${property("mod.id")}+${property("deps.minecraft")}.classtweaker")
}

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    mavenLocal()
    mavenCentral()
    val exclusiveRepos: List<Triple<String, String, List<String>>> = listOf(
        Triple("macuguita Maven", "https://maven.macuguita.com/releases/", emptyList()),
        Triple("Minecraft Forge", "https://maven.minecraftforge.net", emptyList()),
        Triple("shedaniel (Cloth Config)", "https://maven.shedaniel.me/", listOf("me.shedaniel")),
        Triple("Xander Maven", "https://maven.isxander.dev/releases/", listOf("dev.isxander")),
        Triple(
            "Terraformers (Mod Menu)",
            "https://maven.terraformersmc.com/releases/",
            listOf("com.terraformersmc", "dev.emi")
        ),
        Triple("Wisp Forest Maven", "https://maven.wispforest.io/releases/", listOf("io.wispforest")),
        Triple("Modrinth", "https://api.modrinth.com/maven", listOf("maven.modrinth")),
        Triple("Parchment Mappings", "https://maven.parchmentmc.org", listOf("org.parchmentmc")),
        Triple("Jitpack", "https://jitpack.io", emptyList()),
    )

    exclusiveRepos.forEach { (name, url, groups) ->
        if (groups.isNotEmpty()) {
            exclusiveContent {
                forRepository {
                    maven {
                        this.name = name
                        setUrl(url)
                    }
                }
                filter {
                    groups.forEach { includeGroupAndSubgroups(it) }
                }
            }
        } else {
            maven {
                this.name = name
                setUrl(url)
            }
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    mappings(loom.layered {
        officialMojangMappings()
        if (hasProperty("deps.parchment"))
            parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
    compileOnly("org.jspecify:jspecify:1.0.0")

    if (stonecutter.eval(stonecutter.current.version, ">=1.21")) {
        modImplementation("com.macuguita:macu_lib-fabric:${property("deps.macu_lib")}+${property("deps.minecraft")}") {
            exclude(group = "net.fabricmc.fabric-api")
        }
    } else {
        modImplementation("maven.modrinth:macu-lib:1.0.6-${property("deps.minecraft")}-fabric") {
            exclude(group = "net.fabricmc.fabric-api")
        }

        implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
        include("folk.sisby:kaleido-config:${property("deps.kaleido")}")

        if (hasProperty("deps.fabric_asm")) {
            modImplementation("com.github.Chocohead:Fabric-ASM:${property("deps.fabric_asm")}") {
                exclude(group = "net.fabricmc.fabric-api")
            }
            include("com.github.Chocohead:Fabric-ASM:${property("deps.fabric_asm")}")
        }
    }
    if (hasProperty("deps.modmenu")) {
        modLocalRuntime("maven.modrinth:mcqoy:${property("deps.mcqoy")}")
    }

    if (hasProperty("deps.modmenu")) {
        modLocalRuntime("com.terraformersmc:modmenu:${property("deps.modmenu")}")
    }

    if (hasProperty("deps.yacl")) {
        modLocalRuntime("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
    }

}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")
    }
}

stonecutter {
//    replacements.string {
//        direction = eval(current.version, ">1.21.11")
//        replace("classTweaker v1 named", "classTweaker v1 official")
//    }
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
    replacements.string {
        direction = eval(current.version, ">1.21")
        replace("com.macuguita.lib.platform.registry", "com.macuguita.lib.reg")
        replace("BlockBehaviour.Properties.copy", "BlockBehaviour.Properties.ofFullCopy")
        replace("BootstapContext", "BootstrapContext")
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml", "**/pack.mcmeta", "**/generated*")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

loom.runs.named("server") {
    isIdeConfigGenerated = false
}

fabricApi {
    configureDataGeneration {
        outputDirectory = file("$rootDir/src/main/generated+${stonecutter.current.version}")
        client = true
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=26.1")) {
        JavaVersion.VERSION_25
    } else if (stonecutter.eval(stonecutter.current.version, ">=1.21")) {
        JavaVersion.VERSION_21
    } else {
        JavaVersion.VERSION_17
    }
    sourceCompatibility = javaCompat
    targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file.set(tasks.named<org.gradle.jvm.tasks.Jar>("remapJar").map { it.archiveFile.get() })
    additionalFiles.from(
        tasks.named<net.fabricmc.loom.task.RemapSourcesJarTask>("remapSourcesJar").map { it.archiveFile.get() }
    )

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")
    modLoaders.add("quilt")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(property("deps.minecraft").toString())
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        requires("macu-lib")
        optional("modmenu")
        optional("mcqoy")
        optional("qomc")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        requires("macu-lib")
        optional("modmenu")
        optional("mcqoy")
        optional("qomc")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = property("mod.group") as String
            artifactId = (property("mod.id") as String) + "-fabric"
            version = (property("mod.version") as String) + "+${property("deps.minecraft")}"
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
        maven {
            name = "macuguita"
            url = uri("https://maven.macuguita.com/releases")

            credentials {
                username = env.MAVEN_USERNAME.orNull()
                password = env.MAVEN_KEY.orNull()
            }
        }
    }
}
