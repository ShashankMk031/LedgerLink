#!/bin/bash

# Clean up previous build
rm -rf build
rm -f sources.txt

# Create necessary directories
mkdir -p build/classes/java/main
mkdir -p lib

# Function to download file with retry
download_with_retry() {
    local url=$1
    local output=$2
    local max_retries=3
    local retry_count=0

    while [ $retry_count -lt $max_retries ]; do
        if curl -L -s -f -o "$output" "$url"; then
            # Verify the file is not empty and is a valid zip file
            if [ -s "$output" ] && unzip -t "$output" >/dev/null 2>&1; then
                return 0
            fi
            echo "Warning: Downloaded file is corrupt, retrying..."
            rm -f "$output"
        fi
        retry_count=$((retry_count + 1))
        echo "Download attempt $retry_count failed, retrying..."
        sleep 2
    done
    
    echo "Error: Failed to download $url after $max_retries attempts"
    return 1
}

# Download dependencies
echo "Downloading dependencies..."

# Use system MySQL connector
SYSTEM_MYSQL_CONNECTOR="/usr/share/java/mysql-connector-java-9.4.0.jar"

echo "Using system MySQL connector at $SYSTEM_MYSQL_CONNECTOR"
mkdir -p lib
cp "$SYSTEM_MYSQL_CONNECTOR" "lib/mysql-connector-java.jar"

# Dotenv
download_with_retry \
    "https://repo1.maven.org/maven2/io/github/cdimascio/dotenv-java/2.2.0/dotenv-java-2.2.0.jar" \
    "lib/dotenv-java-2.2.0.jar"

# JavaFX modules
download_with_retry \
    "https://repo1.maven.org/maven2/org/openjfx/javafx-base/17.0.8/javafx-base-17.0.8-linux.jar" \
    "lib/javafx-base-17.0.8-linux.jar"

download_with_retry \
    "https://repo1.maven.org/maven2/org/openjfx/javafx-controls/17.0.8/javafx-controls-17.0.8-linux.jar" \
    "lib/javafx-controls-17.0.8-linux.jar"

download_with_retry \
    "https://repo1.maven.org/maven2/org/openjfx/javafx-fxml/17.0.8/javafx-fxml-17.0.8-linux.jar" \
    "lib/javafx-fxml-17.0.8-linux.jar"

download_with_retry \
    "https://repo1.maven.org/maven2/org/openjfx/javafx-graphics/17.0.8/javafx-graphics-17.0.8-linux.jar" \
    "lib/javafx-graphics-17.0.8-linux.jar"

# Compile Java sources
echo "Compiling Java sources..."
find src -name "*.java" > sources.txt

# Build classpath with all dependencies
CLASSPATH=""
for jar in lib/*.jar; do
    if [ -s "$jar" ]; then  # Only add non-empty JAR files
        CLASSPATH="$CLASSPATH:$jar"
    fi
done
CLASSPATH="${CLASSPATH:1}"  # Remove leading ':'

echo "Classpath: $CLASSPATH"

javac -d build/classes/java/main \
    -cp "$CLASSPATH" \
    @sources.txt

# Copy resources
echo "Copying resources..."

# Create target directory structure
mkdir -p build/classes/java/main/ledgerlink/gui

# Print source and destination for debugging
echo "Copying FXML files from: $(pwd)/src/ledgerlink/gui/"
echo "Copying to: $(pwd)/build/classes/java/main/ledgerlink/gui/"

# List source files for debugging
ls -la src/ledgerlink/gui/*.fxml || echo "No FXML files found in source directory"

# Copy FXML files
cp -v src/ledgerlink/gui/*.fxml build/classes/java/main/ledgerlink/gui/ 2>/dev/null || echo "No FXML files to copy"

# Copy other resources
find src -type f \( -name "*.css" -o -name "*.properties" \) -exec echo "Copying {}" \; -exec cp --parents {} build/classes/java/main/ \; 2>/dev/null || echo "No CSS or properties files to copy"

echo "Build complete. Run ./run_app.sh to start the application."
