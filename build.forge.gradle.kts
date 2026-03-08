plugins {
    id("net.neoforged.moddev.legacyforge")
    id ("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["version"] = prop("mod.version") + "+" + prop("deps.minecraft")
        this["minecraft"] = prop("mod.mc_dep_forgelike")
        this["atFile"] = "META-INF/accesstransformer+" + prop("deps.minecraft") + ".cfg"
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

version = "${property("mod.version")}+${property("deps.minecraft")}-forge"
base.archivesName = property("mod.id") as String

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
        Triple("Terraformers (Mod Menu)", "https://maven.terraformersmc.com/releases/", listOf("com.terraformersmc", "dev.emi")),
        Triple("Wisp Forest Maven", "https://maven.wispforest.io/releases/", listOf("io.wispforest")),
        Triple("Modrinth", "https://api.modrinth.com/maven", listOf("maven.modrinth")),
        Triple("Sisby Maven", "https://repo.sleeping.town/", listOf("folk.sisby")),
        Triple("Parchment Mappings", "https://maven.parchmentmc.org", listOf("org.parchmentmc")),
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

val localRuntime by configurations.creating

configurations {
    runtimeClasspath {
        extendsFrom(localRuntime)
    }
}

legacyForge {
    accessTransformers.from(rootProject.file("src/main/resources/META-INF/accesstransformer+${property("deps.minecraft")}.cfg"))

    version = property("deps.forge_loader") as String
    validateAccessTransformers = true

    if (hasProperty("deps.parchment")) parchment {
        val (mc, ver) = (property("deps.parchment") as String).split(':')
        mappingsVersion = ver
        minecraftVersion = mc
    }

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
        register("server") {
            gameDirectory = file("run/")
            server()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
    sourceSets["main"].resources.srcDir("src/main/generated")
}

dependencies {
    implementation("maven.modrinth:macu-lib:1.0.6-${property("deps.minecraft")}-forge")
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.3")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")
    implementation("io.github.llamalad7:mixinextras-forge:0.5.3")
    jarJar("io.github.llamalad7:mixinextras-forge:0.5.3")

    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    jarJar("folk.sisby:kaleido-config:${property("deps.kaleido")}")

    compileOnly("org.jspecify:jspecify:1.0.0")

//    if (hasProperty("deps.mcqoy")) {
//        localRuntime("maven.modrinth:mcqoy:${property("deps.mcqoy")}")
//    }
//
//    // YACL  - required by McQoy
//    if (hasProperty("deps.yacl")) {
//        localRuntime("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-forge")
//    }
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
        exclude("**/fabric.mod.json", "**/*.classtweaker", "**/neoforge.mods.toml", "**/generated*")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

sourceSets {
    main {
        resources.srcDir("$rootDir/src/main/generated+${stonecutter.current.version}")
        resources.exclude(".cache")
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
    file = tasks.jar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })

    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Forge"
    version = "${property("mod.version")}+${property("deps.minecraft")}-forge"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("forge")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("macu-lib")
        optional("mcqoy")
        optional("qomc")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("macu-lib")
        optional("mcqoy")
        optional("qomc")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = property("mod.group") as String
            artifactId = (property("mod.id") as String) + "-forge"
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
