# Hello Java Mesh
This is the simplest example app using the java version of the RightMesh 
library. It is meant to operate on Linux / Windows / Raspberry PI etc,
which support java. This app can interoperate with the Android version:
https://github.com/RightMesh/HelloMesh

## Adding the RightMesh library
Update the [gradle.build](build.gradle) file with the following:
```applicationDefaultJvmArgs = ["-noverify"]
buildscript {
    ext {
        artifactory_app_username = "insertyourusername"
        artifactory_app_password = "insertyourpasswordhash"
        artifactory_app_key = "insertyourkey"
    }
    repositories {
        maven {
            url "http://research.rightmesh.io/artifactory/libs-local"
            credentials {
                username artifactory_app_username
                password artifactory_app_password
            }
        }
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath 'io.left.rightmesh:rightmesh-plugin:1.3'
    }
}
apply plugin: 'io.left.rightmesh.rightmesh-plugin'
build.dependsOn("rightmesh")

repositories {
    mavenCentral()
    jcenter()
    maven {
        url "http://research.rightmesh.io/artifactory/libs-local"
        credentials {
            username artifactory_app_username
            password artifactory_app_password
        }
    }
}

dependencies {
    compile ('io.left.rightmesh:rightmesh-java-library:0.4.0')
}
```

## Building
`./gradlew installDist`

## Running
`./gradlew run` or `./build/install/HelloJavaMesh/bin/HelloJavaMesh`

## Notes on running
At this time, the java library does not automatically join the RM network
so it is up to the developer to manually join the network with the device
the java code is running on. For testing purposes you should be able to
join any mobile phone network starting with RM-XXXXXX with the password
`m3sht3st`. BT connectivity is not supported at this time. If the device
running java has an Internet connection, it can also discover devices
through the Internet as of version 0.4.0.

