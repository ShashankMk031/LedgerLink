# LedgerLink Development Setup

## Prerequisites
- Java 17 or higher
- Gradle 7.0 or higher
- JavaFX 17.0.8 SDK
- MySQL 8.0 or higher

## Setup Instructions

### 1. Install Java 17
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### 2. Install Gradle
```bash
sudo apt install gradle
```

### 3. Download and Install JavaFX SDK
1. Download JavaFX 17.0.8 SDK from: https://gluonhq.com/products/javafx/
2. Extract the downloaded archive to your home directory:
   ```bash
   mkdir -p ~/javafx-sdk-17.0.8
   unzip ~/Downloads/javafx-sdk-17.0.8_linux-x64_bin.zip -d ~/
   ```

### 4. Set Up Environment Variables
Run the setup script:
```bash
chmod +x setup_env.sh
. ./setup_env.sh
```

### 5. Build and Run the Application
```bash
# Make run script executable
chmod +x run.sh

# Run the application
./run.sh
```

## Project Structure
- `src/ledgerlink/` - Main source code
  - `app/` - Application entry points
  - `gui/` - JavaFX controllers and views
  - `model/` - Data models
  - `dao/` - Data Access Objects
  - `service/` - Business logic
  - `util/` - Utility classes

## Troubleshooting

### JavaFX Module Errors
If you encounter JavaFX module errors, ensure:
1. JavaFX SDK is properly installed and `PATH_TO_FX` is set correctly
2. You're using Java 17 or higher
3. The project is built with the correct module path

### Database Connection Issues
1. Verify MySQL is running
2. Check the database credentials in your `.env` file
3. Ensure the database schema exists and is up to date
