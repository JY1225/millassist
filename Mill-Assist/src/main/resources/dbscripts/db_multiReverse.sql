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

ALTER TABLE WORKAREA DROP CONSTRAINT WORKAREA_NAME;
ALTER TABLE WORKAREA DROP CONSTRAINT UNIQUE_PRIO_CLONE;    
ALTER TABLE WORKAREA DROP COLUMN PRIO_IF_CLONED;
ALTER TABLE WORKAREA DROP COLUMN NAME;
ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING DROP COLUMN ACTIVE_FL;
ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING ADD COLUMN DEFAULT_FL BOOLEAN;
ALTER TABLE DEVICEACTIONSETTINGS DROP CONSTRAINT DEVICEACTIONSETTINGS_WORKAREA; 
ALTER TABLE DEVICEACTIONSETTINGS ADD CONSTRAINT DEVICEACTIONSETTINGS_WORKAREA FOREIGN KEY (WORKAREA) REFERENCES SIMPLE_WORKAREA (ID);

ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING ADD COLUMN WORKAREA INTEGER;
ALTER TABLE DEVICESETTINGS_WORKAREA_CLAMPING ADD CONSTRAINT DEVICESETTINGS_WA_CLAMPING_WA FOREIGN KEY (WORKAREA) REFERENCES SIMPLE_WORKAREA (ID);

CREATE TABLE
    ALLOWED_APPROACHTYPE_DEVICE
    (
        ID INTEGER NOT NULL,
        APPROACHTYPE INTEGER NOT NULL,
        IS_ALLOWED BOOLEAN NOT NULL,
        CONSTRAINT APPROACHTYPE_DEVICE_PK PRIMARY KEY (ID, APPROACHTYPE),
        CONSTRAINT APPROACHTYPE_DEVICE FOREIGN KEY (ID) REFERENCES DEVICE (ID)
    );
    
ALTER TABLE REVERSALUNIT ADD COLUMN EXTRA_X DOUBLE DEFAULT 0 NOT NULL;
ALTER TABLE REVERSALUNITSETTINGS ADD COLUMN SHIFTED_ORIGIN BOOLEAN DEFAULT FALSE NOT NULL;