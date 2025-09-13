#!/bin/bash

# Clean and build the project
./gradlew clean build

# Run the application with JavaFX modules
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64  # Adjust this path to your Java 17 installation
export PATH=$JAVA_HOME/bin:$PATH

# Run the application
java --module-path $PATH_TO_FX \
     --add-modules javafx.controls,javafx.fxml \
     --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
     -cp "build/classes/java/main:lib/*:build/libs/*" \
     ledgerlink.app.LedgerLinkApp
