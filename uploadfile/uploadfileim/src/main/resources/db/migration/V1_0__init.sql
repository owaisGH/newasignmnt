CREATE TABLE FILEMETA (
    ID VARCHAR(10) PRIMARY KEY NOT NULL,
    NAME VARCHAR(64) NOT NULL,
    FULL_NAME VARCHAR(256) NOT NULL,
    JOB_TITLE VARCHAR(128),
    EMAIL VARCHAR(256),
    MOBILE VARCHAR(12),
    SKYPE_ID VARCHAR(32)
);