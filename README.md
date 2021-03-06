# Hello Java Mesh
This is the simplest example app using the java version of the RightMesh
library. It is meant to operate on Linux / Windows / Raspberry PI etc,
or any other platform supporting Java.
This app can interoperate with the Android version:
https://github.com/RightMesh/HelloMesh

## Adding the RightMesh library
Refer to [build.gradle](build.gradle) for an example of integrating
RightMesh into your own application.

## Using this app
`./gradlew installDist`
`./gradlew run` or `./build/install/HelloJavaMesh/bin/HelloJavaMesh`

## Notes on running
If you have an Internet connection, HelloJavaMesh will connect to a RightMesh
Superpeer and discover peers around the world who are also connected
to a superpeer.

At this time, the Java library does not automatically join the local mesh
network so it is up to the developer to manually join the network with the
device the Java code is running on. For testing purposes you should be able to
join any WiFi network being broadcast by a phone running RightMesh with an
SSID following the pattern RM-XXXXXX using the password
`m3sht3st`. BT connectivity is not supported at this time.

