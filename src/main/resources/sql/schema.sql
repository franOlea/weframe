CREATE TABLE ROLES
(
    ID SERIAL NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT UNIQUE_ROLE_NAME UNIQUE (NAME)
);

CREATE TABLE STATES
(
    ID SERIAL NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT UNIQUE_STATE_NAME UNIQUE (NAME)
);

CREATE TABLE USERS
(
    ID SERIAL NOT NULL AUTO_INCREMENT,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE INT NOT NULL,
    STATE INT NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT USER_ROLE FOREIGN KEY (ROLE) REFERENCES ROLES(ID),
    CONSTRAINT USER_STATE FOREIGN KEY (STATE) REFERENCES STATES(ID),
    CONSTRAINT UNIQUE_USER_EMAIL UNIQUE (EMAIL)
);
