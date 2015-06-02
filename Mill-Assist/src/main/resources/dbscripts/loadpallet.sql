CREATE TABLE PALLETSETTINGS (
	ID INTEGER NOT NULL,
    RAWWORKPIECE INTEGER NOT NULL,
    FINISHEDWORKPIECE INTEGER NOT NULL,
    LAYERS INTEGER NOT NULL,
    AMOUNT INTEGER NOT NULL,
    GRID_ID INTEGER NOT NULL,
	CONSTRAINT PKEY_PALLETSETTINGS PRIMARY KEY (ID),
    CONSTRAINT PALLETSETTINGS_FINISHEDWORKPIECE FOREIGN KEY (FINISHEDWORKPIECE) REFERENCES
        WORKPIECE (ID) ON
    DELETE
        CASCADE,
    CONSTRAINT PALLETSETTINGS_RAWWORKPIECE FOREIGN KEY (RAWWORKPIECE) REFERENCES
        WORKPIECE (ID) ON
    DELETE
        CASCADE,
    CONSTRAINT PALLETSETTINGS_ID FOREIGN KEY (ID) REFERENCES DEVICESETTINGS (ID)
    ON
    DELETE
        CASCADE
    ON
    UPDATE
        RESTRICT
);

ALTER TABLE PALLET ADD DEFAULT_GRID INTEGER;

UPDATE DEVICETYPE SET NAME='UNLOADPALLET' WHERE ID=8;
UPDATE DEVICE SET NAME='UNLOADPALLET' WHERE ID=8;
INSERT INTO DEVICETYPE VALUES (9,'PALLET');
INSERT INTO DEVICE VALUES (9,'PALLET', 9);
INSERT INTO PALLET VALUES (9, 500, 13, 4);
UPDATE USERFRAME SET NAME='UNLOADPALLET' WHERE ID=8;
INSERT INTO USERFRAME VALUES (9, 6, 20, 20, 'PALLET');
UPDATE CLAMPING SET NAME='UNLOADPALLET' WHERE ID=10;
INSERT INTO CLAMPING VALUES(11, 3, 21, 22, 23, 40, NULL, 'PALLET', 0, NULL, NULL);

UPDATE ZONE SET NAME='UNLOADPALLET Main Zone' WHERE ID=8;
INSERT INTO ZONE VALUES (9, 9, 'PALLET Main Zone', 0);
INSERT INTO WORKAREA VALUES (11, 9, 9);
INSERT INTO WORKAREA_CLAMPING VALUES (11, 11, 11);

UPDATE SIMPLE_WORKAREA SET NAME='UNLOADPALLET' WHERE ID=11;
INSERT INTO SIMPLE_WORKAREA VALUES (12, 11, 1, 'PALLET');