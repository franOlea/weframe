CREATE TABLE USERS
(
    ID SERIAL,
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    EMAIL VARCHAR(255),
    PASSWORD VARCHAR(255),
    PASSWORD_SALT VARCHAR(255),
    ROLE INT
);

CREATE TABLE ROLES
(
    ID SERIAL,
    NAME VARCHAR(255)
);