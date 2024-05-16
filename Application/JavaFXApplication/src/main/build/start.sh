#!/bin/bash

# Set Java home to the JDK folder next to this script
JAVA_HOME="$(dirname "$0")/jdk"

# Set the path to the Java executable
JAVA_EXE="$JAVA_HOME/bin/java"

# Start the jar file
"$JAVA_EXE" --module-path /libs:/libs/third-party -jar "$(dirname "$0")/libs/javafx-application-0.9.0-SNAPSHOT.jar"