-- DROP TABLE TRANSACTIONS;
-- DROP TABLE ACCOUNTS;
CREATE TABLE IF NOT EXISTS ACCOUNTS(
    ACCOUNT_TYPE CHARACTER VARYING(31) NOT NULL,
    ACCOUNT_NUMBER CHARACTER VARYING(255) PRIMARY KEY,
    ACCOUNT_HOLDER_NUMBER CHARACTER VARYING(255) NOT NULL,
    BALANCE DOUBLE PRECISION NOT NULL,
    OVERDRAFT_LIMIT DOUBLE PRECISION,
    MINIMUM_BALANCE DOUBLE PRECISION,
    MONTHLY_INTEREST_RATE DOUBLE PRECISION,
    MINIMUM_BALANCE_IN_MONTH DOUBLE PRECISION,
    IS_ACTIVE BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS TRANSACTIONS(
    TRANSACTION_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TRANSACTION_TYPE NVARCHAR(20),
    AMOUNT  DOUBLE PRECISION,
    TRANSACTION_DATE DATE,
    SOURCE_ACCOUNT_NUMBER VARCHAR(16),
    TARGET_ACCOUNT_NUMBER VARCHAR(16),
    STATUS VARCHAR(10),
    FEE DOUBLE PRECISION,
    FOREIGN KEY (SOURCE_ACCOUNT_NUMBER) REFERENCES ACCOUNTS(ACCOUNT_NUMBER),
    FOREIGN KEY (TARGET_ACCOUNT_NUMBER) REFERENCES ACCOUNTS(ACCOUNT_NUMBER)
);

CREATE TABLE IF NOT EXISTS BANK_USER(
    USERNAME VARCHAR(30) PRIMARY KEY,
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE VARCHAR(20) NOT NULL,
    BANK_ACCOUNT_ID CHARACTER VARYING(255),
    FOREIGN KEY (BANK_ACCOUNT_ID) REFERENCES ACCOUNTS(ACCOUNT_NUMBER)
);

CREATE TABLE IF NOT EXISTS TOKEN(
    TOKEN_ID INTEGER PRIMARY KEY,
    TOKEN CHARACTER VARYING(255) NOT NULL,
    TOKEN_TYPE CHARACTER VARYING(20) NOT NULL,
    REVOKED BOOLEAN NOT NULL,
    EXPIRED BOOLEAN NOT NULL
);

select * from BANK_USER;