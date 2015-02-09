ALTER TABLE DEVICEACTIONSETTINGS DROP COLUMN WORKPIECETYPE;

CREATE TABLE
    SIMPLE_WORKAREA
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
        WORKAREA INTEGER NOT NULL,
        SEQ_NB INTEGER DEFAULT 1 NOT NULL,
        NAME VARCHAR(100) NOT NULL,
        CONSTRAINT PKEY_SIMPLE_WORKAREA PRIMARY KEY (ID),
        CONSTRAINT FKEY_WORKAREA FOREIGN KEY (WORKAREA) REFERENCES WORKAREA (ID) 
                ON DELETE CASCADE
                ON UPDATE RESTRICT,
        CONSTRAINT SIMPLE_WA_NAME UNIQUE (NAME),
        CONSTRAINT SIMPLE_WA_UNIQUE UNIQUE (WORKAREA, SEQ_NB)
    );
    
INSERT INTO SIMPLE_WORKAREA (WORKAREA, NAME) 
( 
        SELECT ID, NAME FROM WORKAREA
);

ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING ADD COLUMN WORKAREA INTEGER;

update DEVICESETTINGS_WORKAREA_CLAMPING set workarea =
(select workarea_clamping.workarea from workarea_clamping
where workarea_clamping.id = DEVICESETTINGS_WORKAREA_CLAMPING.workarea_clamping);

update DEVICESETTINGS_WORKAREA_CLAMPING set workarea =
( SELECT SIMPLE_WORKAREA.ID 
          FROM SIMPLE_WORKAREA 
          JOIN WORKAREA 
          ON SIMPLE_WORKAREA.NAME = WORKAREA.NAME
          where workarea.id = DEVICESETTINGS_WORKAREA_CLAMPING.workarea);

UPDATE DEVICESETTINGS_WORKAREA_CLAMPING SET WORKAREA_CLAMPING = (
SELECT WORKAREA_CLAMPING.ID FROM WORKAREA_CLAMPING
JOIN WORKAREA 
ON WORKAREA.ID = WORKAREA_CLAMPING.WORKAREA
JOIN USERFRAME
ON USERFRAME.ID = WORKAREA.USERFRAME
WHERE WORKAREA.NAME NOT LIKE '%REV%'
AND USERFRAME.NUMBER = 3 
)
WHERE WORKAREA_CLAMPING IN (
SELECT WORKAREA_CLAMPING.ID FROM WORKAREA_CLAMPING
JOIN WORKAREA 
ON WORKAREA.ID = WORKAREA_CLAMPING.WORKAREA
JOIN USERFRAME
ON USERFRAME.ID = WORKAREA.USERFRAME
WHERE WORKAREA.NAME LIKE '%REV%'
AND USERFRAME.NUMBER = 3 
)
;
ALTER TABLE DEVICEACTIONSETTINGS drop CONSTRAINT DEVICEACTIONSETTINGS_WORKAREA;

update DEVICEACTIONSETTINGS set workarea =
( SELECT SIMPLE_WORKAREA.ID 
          FROM SIMPLE_WORKAREA 
          JOIN WORKAREA 
          ON SIMPLE_WORKAREA.NAME = WORKAREA.NAME
          where workarea.id = DEVICEACTIONSETTINGS.workarea);

ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING ADD COLUMN DEFAULT_FL BOOLEAN;
UPDATE DEVICESETTINGS_WORKAREA_CLAMPING 
SET DEFAULT_FL = ACTIVE_FL;
ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING DROP COLUMN ACTIVE_FL;
ALTER TABLE DEVICEACTIONSETTINGS ADD CONSTRAINT DEVICEACTIONSETTINGS_WORKAREA FOREIGN KEY (WORKAREA) REFERENCES SIMPLE_WORKAREA (ID);
ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING ADD CONSTRAINT DEVICESETTINGS_WA_CLAMPING_WA FOREIGN KEY (WORKAREA) REFERENCES SIMPLE_WORKAREA (ID);


CREATE TABLE
    ALLOWED_APPROACHTYPE_DEVICE
    (
        DEVICE_ID INTEGER NOT NULL,
        APPROACHTYPE INTEGER NOT NULL,
        IS_ALLOWED BOOLEAN NOT NULL,
        CONSTRAINT APPROACHTYPE_DEVICE_PK PRIMARY KEY (DEVICE_ID, APPROACHTYPE),
        CONSTRAINT APPROACHTYPE_DEVICE FOREIGN KEY (DEVICE_ID) REFERENCES DEVICE (ID)
    );
    
ALTER TABLE REVERSALUNIT ADD COLUMN STATION_LENGTH DOUBLE DEFAULT 120 NOT NULL;
ALTER TABLE REVERSALUNIT ADD COLUMN STATION_FIXTURE_WIDTH DOUBLE DEFAULT 2 NOT NULL;
ALTER TABLE REVERSALUNIT ALTER COLUMN STATION_HEIGHT DEFAULT 20;
ALTER TABLE REVERSALUNIT ALTER COLUMN EXTRA_X DEFAULT 75;
ALTER TABLE REVERSALUNITSETTINGS ADD COLUMN SHIFTED_ORIGIN BOOLEAN DEFAULT FALSE NOT NULL;

INSERT INTO ALLOWED_APPROACHTYPE_DEVICE VALUES (7,1, true);
INSERT INTO ALLOWED_APPROACHTYPE_DEVICE VALUES (7,2, true);
INSERT INTO ALLOWED_APPROACHTYPE_DEVICE VALUES (7,3, false);
INSERT INTO ALLOWED_APPROACHTYPE_DEVICE VALUES (7,5, false);

DELETE FROM WORKAREA_CLAMPING WHERE WORKAREA IN (
SELECT WORKAREA.ID FROM WORKAREA WHERE WORKAREA.NAME LIKE '% REV %'
);

DELETE FROM WORKAREA_BOUNDARIES WHERE WORKAREA_ID IN (
SELECT WORKAREA.ID FROM WORKAREA WHERE WORKAREA.NAME LIKE '% REV %'
);
UPDATE SIMPLE_WORKAREA SET SEQ_NB = 2 WHERE NAME LIKE '% REV %';
UPDATE SIMPLE_WORKAREA SET WORKAREA = (
SELECT WORKAREA FROM SIMPLE_WORKAREA
JOIN WORKAREA ON WORKAREA.ID = SIMPLE_WORKAREA.WORKAREA
JOIN USERFRAME ON USERFRAME.ID = WORKAREA.USERFRAME
WHERE USERFRAME.NUMBER = 3 AND SEQ_NB = 1)
WHERE SEQ_NB > 1;

DELETE FROM WORKAREA WHERE WORKAREA.NAME LIKE '% REV %';


ALTER TABLE WORKAREA DROP CONSTRAINT WORKAREA_NAME;
ALTER TABLE WORKAREA DROP COLUMN NAME;