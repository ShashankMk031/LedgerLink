#!/bin/bash

# Install OpenJDK 17 if not already installed
sudo apt update
sudo apt install -y openjdk-17-jdk

# Download JavaFX SDK
wget https://download2.gluonhq.com/openjfx/17.0.8/openjfx-17.0.8_linux-x64_bin-sdk.zip

# Extract JavaFX SDK
mkdir -p ~/javafx-sdk-17.0.8
unzip openjfx-17.0.8_linux-x64_bin-sdk.zip -d ~/

echo "JavaFX SDK has been installed to ~/javafx-sdk-17.0.8"
echo "Please add the following to your ~/.bashrc or ~/.zshrc:"
echo ""
echo 'export PATH_TO_FX=~/javafx-sdk-17.0.8/lib'
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64'
echo 'export PATH=$JAVA_HOME/bin:$PATH'
echo ""
echo "After adding these, run: source ~/.bashrc  # or source ~/.zshrc"
