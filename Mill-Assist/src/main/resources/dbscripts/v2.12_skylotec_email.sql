CREATE TABLE RBJ_USER (
	ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	NAME VARCHAR(32) NOT NULL,
	EMAIL VARCHAR(64) NOT NULL,
	IMAGE_URL VARCHAR(128) NOT NULL,
	CONSTRAINT PRKEY_USER PRIMARY KEY (ID)
);

CREATE TABLE USER_EMAIL_SETTINGS (
	USER_ID INTEGER NOT NULL,
	EMAIL_AT_BATCH_END BOOLEAN NOT NULL,
	EMAIL_AT_ERROR BOOLEAN NOT NULL,
	EMAIL_ERROR_DELAY INTEGER NOT NULL,
	CONSTRAINT PRKEY_USER_EMAIL_SETTINGS PRIMARY KEY (USER_ID),
	CONSTRAINT FKEY_EMAIL_SETTINGS_USER FOREIGN KEY (USER_ID) REFERENCES RBJ_USER (ID)
);