# LedgerLink

LedgerLink is a Java-based banking application with both CLI and GUI interfaces that dynamically interacts with a MySQL database to manage customers, accounts, and transactions.

## Features

- View customers, accounts, and transactions
- Add, update, and delete customers
- Create and manage accounts
- Perform deposits, withdrawals, and transfers
- JDBC integration with MySQL
- Modern JavaFX-based GUI

## Prerequisites

- Java 17 or higher
- Gradle 7.0 or higher
- MySQL Server 8.0 or higher

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd LedgerLink
   ```

2. Set up the database:
   - Create a MySQL database named `ledgerlink`
   - Run the SQL script from `docs/mvp-schema.sql` to create the necessary tables

3. Create a `.env` file in the project root with your database credentials:
   ```
   DB_URL=jdbc:mysql://localhost:3306/ledgerlink
   DB_USER=your_username
   DB_PASSWORD=your_password
   ```

## Build and Run

### Using Gradle (Recommended)

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run
```

### Manual Compilation (Legacy)

```bash
# Compile
javac -cp ./lib/mysql-connector-j-9.4.0.jar:./lib/dotenv-java-2.2.0.jar \
    ./src/ledgerlink/app/*.java \
    ./src/ledgerlink/service/*.java \
    ./src/ledgerlink/dao/*.java \
    ./src/ledgerlink/model/*.java \
    ./src/ledgerlink/util/*.java \
    ./src/ledgerlink/gui/*.java

# Run
java -cp ./lib/mysql-connector-j-9.4.0.jar:./lib/dotenv-java-2.2.0.jar:./src \
    --module-path ./build/javafx-sdk/javafx-sdk-17.0.8/lib \
    --add-modules javafx.controls,javafx.fxml \
    ledgerlink.app.LedgerLinkApp
```

## Project Structure

- `src/ledgerlink/app/` - Main application classes
- `src/ledgerlink/dao/` - Data Access Objects
- `src/ledgerlink/model/` - Data model classes
- `src/ledgerlink/service/` - Business logic
- `src/ledgerlink/gui/` - JavaFX GUI controllers and views
- `src/ledgerlink/util/` - Utility classes
- `docs/` - Documentation and database schemas

## Dependencies

- JavaFX 17.0.8
- MySQL Connector/J 9.4.0
- dotenv-java 2.2.0
- JUnit 5.8.1 (for testing)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
