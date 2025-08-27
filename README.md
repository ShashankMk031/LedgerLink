# LedgerLink

LedgerLink is a simple Java-based banking CLI application that dynamically interacts with a MySQL database to manage customers, accounts, and transactions.

## Features

- View customers, accounts, and transactions
- Add customers, create accounts
- Perform deposits, withdrawals, and transfers
- JDBC integration with MySQL


## Run 
Compile 
javac -cp ./lib/mysql-connector-j-9.4.0.jar:./lib/dotenv-java-2.2.0.jar ./src/ledgerlink/app/*.java ./src/ledgerlink/service/*.java ./src/ledgerlink/dao/*.java ./src/ledgerlink/model/*.java ./src/ledgerlink/util/*.java


Run the application 
java -cp ./lib/mysql-connector-j-9.4.0.jar:./lib/dotenv-java-2.2.0.jar:./src ledgerlink.app.LedgerLinkApp
