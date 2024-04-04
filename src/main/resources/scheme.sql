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
    TRANSACTION_ID BIGINT PRIMARY KEY,
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
    USER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(30) NOT NULL ,
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE VARCHAR(20) NOT NULL,
    FIRSTNAME VARCHAR(30) NOT NULL,
    LASTNAME VARCHAR(30) NOT NULL,
    NATIONAL_CODE VARCHAR(13),
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