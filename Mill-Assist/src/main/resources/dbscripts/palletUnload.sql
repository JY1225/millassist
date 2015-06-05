CREATE TABLE PALLETLAYOUT (
    ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    LENGTH DOUBLE NOT NULL,
    WIDTH DOUBLE NOT NULL,
    HEIGHT DOUBLE NOT NULL,
    OFFSET_X DOUBLE NOT NULL,
    OFFSET_Y DOUBLE NOT NULL,
    BORDER DOUBLE NOT NULL,
    MIN_INTERFERENCE DOUBLE NOT NULL,
    HORIZONTAL_R DOUBLE NOT NULL,
    VERTICAL_R DOUBLE NOT NULL,
    NAME VARCHAR(100) NOT NULL,

    CONSTRAINT PKEY_PALLETLAYOUT_ID PRIMARY KEY (ID)

);

CREATE TABLE UNLOADPALLETSETTINGS (
	ID INTEGER NOT NULL,
    FINISHEDWORKPIECE INTEGER NOT NULL,
    LAYOUT_TYPE INTEGER NOT NULL,
    LAYERS_CARDBOARD INTEGER NOT NULL,
    PALLET_LAYOUT INTEGER NOT NULL,
    CARDBOARD_THICKNESS DOUBLE NOT NULL,
	CONSTRAINT PKEY_UNLOADPALLETSETTINGS PRIMARY KEY (ID),
    CONSTRAINT UNLOADPALLETSETTINGS_FINISHEDWORKPIECE FOREIGN KEY (FINISHEDWORKPIECE) REFERENCES
        WORKPIECE (ID) ON
    DELETE
        CASCADE,
    CONSTRAINT UNLOADPALLETSETTINGS_ID FOREIGN KEY (ID) REFERENCES DEVICESETTINGS (ID)
    ON
    DELETE
        CASCADE
    ON
    UPDATE
        RESTRICT
);

CREATE TABLE PALLET (
    ID INTEGER NOT NULL,
    MAX_HEIGHT DOUBLE NOT NULL,
    DEFAULT_LAYOUT INTEGER NOT NULL,
    CONSTRAINT PKEY_PALLET PRIMARY KEY (ID),
    CONSTRAINT PALLET_DEVICE FOREIGN KEY (ID) REFERENCES DEVICE (ID)
);

INSERT INTO 
PALLETLAYOUT (LENGTH, WIDTH, HEIGHT, OFFSET_X, OFFSET_Y, BORDER, MIN_INTERFERENCE, HORIZONTAL_R, VERTICAL_R, NAME) 
VALUES (1200, 800, 144, 20, 20, 50, 20, 0, -90, 'EUR');
INSERT INTO 
PALLETLAYOUT (LENGTH, WIDTH, HEIGHT, OFFSET_X, OFFSET_Y, BORDER, MIN_INTERFERENCE, HORIZONTAL_R, VERTICAL_R, NAME) 
VALUES (600, 800, 144, 20, 20, 20, 20, 180, 90, 'EUR6');
INSERT INTO 
PALLETLAYOUT (LENGTH, WIDTH, HEIGHT, OFFSET_X, OFFSET_Y, BORDER, MIN_INTERFERENCE, HORIZONTAL_R, VERTICAL_R, NAME) 
VALUES (1000, 1200, 144, 20, 20, 20, 50, 0, -90, 'EUR2');
INSERT INTO 
PALLETLAYOUT (LENGTH, WIDTH, HEIGHT, OFFSET_X, OFFSET_Y, BORDER, MIN_INTERFERENCE, HORIZONTAL_R, VERTICAL_R, NAME) 
VALUES (800, 1200, 144, 30, 30, 50, 50, 0, -90, 'EUR CUSTOM');