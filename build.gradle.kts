plugins {
    id("java")
}

group = "com.lafis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.commons:commons-csv:1.14.0")
    implementation("org.knowm.xchart:xchart:3.8.8")
}

tasks.test {
    useJUnitPlatform()
}