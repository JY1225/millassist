INSERT INTO CNC_OPTION_TYPE (ID, NAME)
VALUES (3, 'WORKNUMBER_SEARCH');

INSERT INTO CNC_OPTION (CNC_ID, OPTION_ID, OPTION_VALUE) 
VALUES ((SELECT ID FROM CNCMILLINGMACHINE),3,false);

ALTER TABLE DEVICEACTIONSETTINGS ADD COLUMN WORKNUMBER_SEARCH INTEGER DEFAULT -1 NOT NULL;