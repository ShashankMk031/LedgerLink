# LedgerLink

![LedgerLink Logo](https://via.placeholder.com/150x50?text=LedgerLink)

LedgerLink is a comprehensive Java-based banking application featuring a modern JavaFX GUI for managing customers, accounts, transactions, and loans. The application provides a user-friendly interface for financial operations while maintaining a robust backend with MySQL database integration.

## ✨ Features

- **Customer Management**: Add, view, update, and delete customer information
- **Account Operations**: Create and manage bank accounts with various account types
- **Transaction Processing**: Handle deposits, withdrawals, and fund transfers
- **Loan Management**: Process and track different types of loans
- **Modern UI**: Intuitive JavaFX-based graphical user interface
- **Secure**: Environment-based configuration for database credentials
- **Modular Design**: Clean architecture with separation of concerns

## Quick Start

### Prerequisites

- Java 17 or higher (OpenJDK recommended)
- MySQL Server 8.0 or higher
- Git (for version control)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ShashankMk031/LedgerLink.git
   cd LedgerLink
   ```

2. **Set up the database**:
   ```sql
   CREATE DATABASE ledgerlink;
   USE ledgerlink;
   -- Run the schema script
   SOURCE docs/mvp-schema.sql;
   ```

3. **Configure environment variables**:
   Create a `.env` file in the project root:
   ```env
   DB_URL=jdbc:mysql://localhost:3306/ledgerlink
   DB_USER=your_username
   DB_PASSWORD=your_secure_password
   ```

## Build & Run

### Option 1: Using Provided Scripts (Recommended)

1. **Set up JavaFX** (first time only):
   ```bash
   chmod +x setup_javafx_ubuntu.sh
   ./setup_javafx_ubuntu.sh
   ```

2. **Run the application**:
   ```bash
   ./run_app.sh
   ```

### Option 2: Manual Build

1. **Build the project**:
   ```bash
   ./build.sh
   ```

2. **Run the application**:
   ```bash
   ./run_with_javafx.sh
   ```

## Project Structure

```
LedgerLink/
├── src/ledgerlink/
│   ├── analytics/         # Data analysis and reporting
│   ├── api/               # API endpoints
│   ├── app/               # Main application class
│   ├── dao/               # Data Access Objects
│   ├── gui/               # JavaFX controllers and FXML views
│   ├── model/             # Data models (Customer, Account, etc.)
│   ├── notifications/     # Email and SMS notifications
│   ├── scheduler/         # Background jobs and scheduling
│   ├── security/          # Authentication and authorization
│   ├── service/           # Business logic services
│   └── util/              # Utility classes
├── lib/                   # External dependencies (JDBC, JavaFX, etc.)
├── docs/                  # Documentation and database schemas
├── .gitignore             # Git ignore rules
├── build.gradle           # Gradle build configuration
├── sources.txt            # List of Java source files for compilation
├── README.md              # This file

# Build and Run Scripts
├── build.sh               # Compile the application
├── run.sh                 # Run without JavaFX
├── run_app.sh             # Main run script with JavaFX
├── run_javafx.sh          # Alternative JavaFX launcher
├── run_with_javafx.sh     # Another JavaFX launcher
├── setup_env.sh           # Environment setup
└── setup_javafx_ubuntu.sh # JavaFX setup for Ubuntu/Debian
```

## Dependencies

- **JavaFX 17.0.8** - For the graphical user interface
- **MySQL Connector/J 9.4.0** - Database connectivity
- **dotenv-java 2.2.0** - Environment variable management
- **JUnit 5.8.1** - For unit testing

## Available Scripts

- `build.sh` - Compiles the Java application
- `run.sh` - Runs the application without JavaFX
- `run_app.sh` - Main script to run with JavaFX (recommended)
- `run_javafx.sh` - Alternative JavaFX launcher
- `run_with_javafx.sh` - Additional JavaFX launcher
- `setup_javafx_ubuntu.sh` - Sets up JavaFX on Ubuntu/Debian
- `setup_env.sh` - Configures the development environment

## sources.txt

This file contains a list of all Java source files in the project and is used by some build scripts for compilation. It helps in:
- Explicitly defining which files to compile
- Maintaining a consistent build process
- Ensuring all necessary files are included in the build

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

- **Shashank M K** - [@ShashankMk031](https://github.com/ShashankMk031)
- **Project Link**: [https://github.com/ShashankMk031/LedgerLink](https://github.com/ShashankMk031/LedgerLink)
