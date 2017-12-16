#!bin/sh

# Prerequisites:
#	. Java
#	. Gradle

# Compile with Gradle
./gradlew build

# Run the application
java -jar build/libs/rs-springwebhandling-0.1.jar
