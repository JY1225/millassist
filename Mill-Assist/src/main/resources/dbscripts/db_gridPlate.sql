RENAME TABLE GRIDPLATE TO GRIDPLATE_BCKUP;

CREATE TABLE
    GRIDPLATE
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
        NAME VARCHAR(100) NOT NULL,
        LENGTH DOUBLE NOT NULL,
        WIDTH DOUBLE NOT NULL,
        HEIGHT DOUBLE NOT NULL,
        HOLE_LENGTH DOUBLE NOT NULL,
        HOLE_WIDTH DOUBLE NOT NULL,
        OFFSET_X DOUBLE NOT NULL,
        OFFSET_Y DOUBLE NOT NULL,
        CONSTRAINT PKEY_GRIDPLATE_ID PRIMARY KEY (ID),
        CONSTRAINT UNIQUE_PLATENAME UNIQUE (NAME)
    );
    
CREATE TABLE
    GRIDHOLE
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
        PLATE_ID INTEGER NOT NULL,
        ORIENTATION DOUBLE NOT NULL,
        X_BOTTOM DOUBLE NOT NULL,
        Y_BOTTOM DOUBLE NOT NULL,
        CONSTRAINT PKEY_GRIDHOLE PRIMARY KEY (ID),
        CONSTRAINT FKEY_GRIDPLATE FOREIGN KEY (PLATE_ID) REFERENCES GRIDPLATE (ID) ON DELETE
        CASCADE
    ON
    UPDATE
        RESTRICT
    );
        
UPDATE STACKPLATESETTINGS SET ORIENTATION = 0 WHERE ORIENTATION = 1;
UPDATE STACKPLATESETTINGS SET ORIENTATION = 45 WHERE ORIENTATION = 2;
UPDATE STACKPLATESETTINGS SET ORIENTATION = 90 WHERE ORIENTATION = 3;
ALTER TABLE STACKPLATESETTINGS ADD COLUMN ORI DOUBLE NOT NULL default 0;
UPDATE STACKPLATESETTINGS SET ORI = ORIENTATION;
ALTER TABLE STACKPLATESETTINGS DROP COLUMN ORIENTATION;
ALTER TABLE STACKPLATESETTINGS ADD COLUMN ORIENTATION DOUBLE NOT NULL DEFAULT 0;
UPDATE STACKPLATESETTINGS SET ORIENTATION = ORI;
ALTER TABLE STACKPLATESETTINGS DROP COLUMN ORI;

ALTER TABLE STACKPLATE ADD COLUMN EXTRA_R_MIN_90 DOUBLE NOT NULL DEFAULT 90;
ALTER TABLE STACKPLATE ADD COLUMN EXTRA_R_PLUS_90 DOUBLE NOT NULL DEFAULT -90;


ALTER TABLE STACKPLATESETTINGS DROP CONSTRAINT GRIDPLATE_ID;
ALTER TABLE STACKPLATESETTINGS ADD CONSTRAINT GRIDPLATE_ID FOREIGN KEY (GRID_ID) REFERENCES GRIDPLATE (ID);
