plugins {
    application
    kotlin("jvm")
    id("com.palantir.graal") version "0.7.2"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":stub-lite"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")

    runtimeOnly("io.grpc:grpc-okhttp:${rootProject.ext["grpcVersion"]}")
}

application {
    mainClass.set("io.grpc.examples.helloworld.HelloWorldClientKt")
}

// todo: add graalvm-config-create task
/* Generate GraalVM Configs - In examples dir:
 ./gradlew :native-server:run

 ./gradlew :native-client:extractGraalTooling
 ./gradlew :native-client:install
 JAVA_HOME=~/.gradle/caches/com.palantir.graal/20.3.0/11/graalvm-ce-java11-20.3.0 JAVA_OPTS=-agentlib:native-image-agent=config-output-dir=native-client/src/graal native-client/build/install/native-client/bin/native-client
 */

graal {
    graalVersion("20.3.0")
    javaVersion("11")
    mainClass(application.mainClass.get())
    outputName("hello-world-client")
    option("--verbose")
    option("--no-server")
    option("--no-fallback")
    option("-H:+ReportExceptionStackTraces")
    option("-H:+PrintClassInitialization")
    option("-H:ConfigurationFileDirectories=src/graal")
}
