// Class from SMok Client by SleepyFish
plugins {
    id("java")
    id("com.github.weave-mc.weave") version "8b70bcc707"
}

group = "club.maxstats.weaveyoutube"
version = "v1"

repositories {
    maven("https://jitpack.io")
    maven("https://repo.spongepowered.org/maven/")
    mavenCentral()
}

dependencies {
    compileOnly("com.github.weave-mc:weave-loader:70bd82faa6")
    compileOnly("org.spongepowered:mixin:0.8.5")
}

minecraft.version("1.8.9")