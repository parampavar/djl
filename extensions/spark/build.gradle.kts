plugins {
    ai.djl.javaProject
    ai.djl.publish apply false
    scala
}

group = "ai.djl.spark"

if (JavaVersion.current() < JavaVersion.VERSION_21) apply(plugin = "ai.djl.publish")

dependencies {
    api(project(":api"))
    api(project(":extensions:tokenizers"))
    api(project(":extensions:audio"))
    api(libs.spark.core)
    api(libs.spark.sql)
    api(libs.spark.mllib)

    testImplementation(project(":testing"))
    testRuntimeOnly(libs.apache.log4j.slf4j)

    testRuntimeOnly(project(":engines:pytorch:pytorch-model-zoo"))
}

tasks {
    compileScala {
        scalaCompileOptions.setAdditionalParameters(listOf("-target:jvm-1.8"))
    }

    register<Exec>("formatPython") {
        workingDir = project.projectDir
        commandLine("bash", "-c", "find . -name '*.py' -print0 | xargs -0 yapf --in-place")
    }

    clean {
        doFirst {
            val initFile = projectDir / "setup/djl_spark/__init__.py"
            initFile.text = initFile.text.replace(Regex("\\n*__version__.*"), "\n")

            delete("setup/build/")
            delete("setup/dist/")
            delete("setup/__pycache__/")
            delete("setup/djl_spark.egg-info/")
            delete("setup/djl_spark/__pycache__/")
        }
    }
}

// need to fall back to this, probably the conditionality on the plugin apply breaks the accessor generation
extensions.getByName<PublishingExtension>("publishing").apply {
    publications {
        named<MavenPublication>("maven") {
            groupId = "ai.djl.spark"
            artifactId = "spark_2.12"
            pom {
                name = "Apache Spark integration for DJL"
                description = "Apache Spark integration for DJL"
                url = "http://www.djl.ai/extensions/${project.name}"
            }
        }
    }
}
