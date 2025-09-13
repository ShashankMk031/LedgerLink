-- The schema will be added here . 
CREATE DATABASE IF NOT EXISTS ledgerlink CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci; 

USE ledgerlink; 

CREATE TABLE IF NOT EXISTS customer{ 
    customerId INT AUTO_INCREMENT PRIMAY KEY,
    name VARCHAR(150) NOT NULL, 
    email VARCHAR(200) UNIQUE NOT NULL, 
    phone VARCHAR(50), 
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
} ENGINE = InnoDB; 

CREATE TABLE IF NOT EXISTS account {
    accountId INT AUTO_INCREMENT PRIMARY KEY, 
    customerId INT NOT NULL, 
    branchId INT NULL, 
    currency VARCHAR(3),
    balance DECIMAL(18,2) NOT NULL DEFAULT 0.0,
    status VARCHAR(20) NOT NULL, 
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (customerId) REFERENCES customer(customerId) ON DELETE CASCADE
} ENGINE = InnoDB; 

CREATE TABLE IF NOT EXISTS transaction_ledger { 
    transactionId BIGINT AUTO_INCREMENT PRIMARY KEY,
    accountId INT NOT NULL, 
    type VARCHAR(20) NOT NULL, 
    amount DECIMAL(18,2) NOT NULL, 
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    description VARCHAR(255), 
    relatedAccountId INT NULL, 
    FOREIGN KEY (accountId) REFERENCES account(accountId) ON DELETE CASCADE, 
} ENGINE = InnoDB; 

CREATE TABLE IF NOT EXISTS loan (
  loanId INT AUTO_INCREMENT PRIMARY KEY,
  customerId INT NOT NULL,
  targetAccountId INT NULL,
  principal DECIMAL(18,2) NOT NULL,
  annualRate DECIMAL(9,4) NOT NULL,
  termMonths INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  appliedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  approvedAt TIMESTAMP NULL,
  disbursedAt TIMESTAMP NULL,
  FOREIGN KEY (customerId) REFERENCES customer(customerId) ON DELETE CASCADE,
  FOREIGN KEY (targetAccountId) REFERENCES account(accountId) ON DELETE SET NULL
) ENGINE=InnoDB;