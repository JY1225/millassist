DROP TABLE ZONE_BOUNDARIES;

CREATE TABLE
    WORKAREA_BOUNDARIES
    (
        WORKAREA_ID INTEGER NOT NULL,
        BOTTOMCOORD INTEGER NOT NULL,
        TOPCOORD INTEGER NOT NULL,
        CONSTRAINT PK_BOUND_ID PRIMARY KEY (WORKAREA_ID),
        CONSTRAINT FK_WA_ID FOREIGN KEY (WORKAREA_ID) REFERENCES WORKAREA (ID) ON DELETE CASCADE,
        CONSTRAINT FK_BOTTOMCOORD_BOUND FOREIGN KEY (BOTTOMCOORD) REFERENCES COORDINATES (ID) ON
    DELETE
        CASCADE,
        CONSTRAINT FK_TOPCOORD_BOUND FOREIGN KEY (TOPCOORD) REFERENCES COORDINATES (ID)
    ON
    DELETE
        CASCADE
    );