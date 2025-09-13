#!/bin/bash

# Set JavaFX SDK path (update this to your JavaFX SDK path)
JAVAFX_SDK=~/javafx-sdk-17.0.8/lib

# Set the main class
MAIN_CLASS=ledgerlink.app.LedgerLinkApp

# Run the application with JavaFX modules
java --module-path $JAVAFX_SDK \
     --add-modules=javafx.controls,javafx.fxml \
     --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED \
     --add-exports=javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
     -cp "build/classes/java/main:lib/*" \
     $MAIN_CLASS
