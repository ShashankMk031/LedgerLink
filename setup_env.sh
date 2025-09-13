#!/bin/bash

# Set Java home - adjust this path to your Java 17 installation
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Set JavaFX path - download JavaFX SDK and extract it, then set this path
export PATH_TO_FX=~/javafx-sdk-17.0.8/lib

# Verify Java version
echo "Java version:"
java -version

# Verify JavaFX modules
echo -e "\nJavaFX modules:"
ls -l $PATH_TO_FX
