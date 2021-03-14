DROP TABLE IF EXISTS ADDRESS;
DROP TABLE IF EXISTS EMPLOYEE;

CREATE TABLE ADDRESS (
    ID VARCHAR(36) PRIMARY KEY,
    LINE1 VARCHAR(255) NOT NULL,
    LINE2 VARCHAR(255),
    CITY VARCHAR(50) NOT NULL,
    STATE VARCHAR(50) NOT NULL,
    ZIP_CODE VARCHAR(6) NOT NULL
);

CREATE TABLE EMPLOYEE (
  ID INT PRIMARY KEY,
  FIRST_NAME VARCHAR(255) NOT NULL,
  LAST_NAME VARCHAR(255) NOT NULL,
  DATE_OF_BIRTH DATE NOT NULL,
  ADDRESS_ID VARCHAR(36),
  FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ID)
);
