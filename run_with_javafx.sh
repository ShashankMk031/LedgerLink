#!/bin/bash

# Set JavaFX SDK path
JAVAFX_SDK=~/javafx-sdk-17.0.8/lib

# Run the application with JavaFX modules
java --module-path $JAVAFX_SDK \
     --add-modules=javafx.controls,javafx.fxml \
     --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
     -cp "build/classes/java/main:lib/*" \
     ledgerlink.gui.MainWindow
