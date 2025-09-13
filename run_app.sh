#!/bin/bash

# Set the main class
MAIN_CLASS=ledgerlink.gui.MainWindow

# Build the classpath with all JAR files in the lib directory
CLASSPATH="build/classes/java/main"
for jar in lib/*.jar; do
    CLASSPATH="$CLASSPATH:$jar"
done

# Run the application
echo "Starting LedgerLink..."
echo "Classpath: $CLASSPATH"

export _JAVA_OPTIONS="--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED \
--add-exports=javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
--add-exports=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
--add-opens=javafx.controls/javafx.scene.control=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
--add-opens=javafx.base/com.sun.javafx.runtime=ALL-UNNAMED \
--add-opens=javafx.base/com.sun.javafx.collections=ALL-UNNAMED \
--add-opens=javafx.base/com.sun.javafx=ALL-UNNAMED \
--add-opens=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED \
--add-opens=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.glass.ui=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.glass.events=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.geom=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.scene.traversal=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.geom.transform=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.sg.prism.web=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.scene.text=ALL-UNNAMED \
--add-opens=javafx.graphics/com.sun.javafx.text=ALL-UNNAMED"

java \
  --module-path "lib" \
  --add-modules=javafx.controls,javafx.fxml,javafx.graphics \
  -cp "$CLASSPATH" \
  -Dfile.encoding=UTF-8 \
  $MAIN_CLASS
