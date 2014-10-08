ALTER TABLE IRSCW.CNCMILLINGMACHINE ADD COLUMN NEW_DEV_INT BOOLEAN;
ALTER TABLE IRSCW.ZONE ADD COLUMN ZONE_NR INT NOT NULL DEFAULT 0;

UPDATE IRSCW.ZONE 
   SET ZONE_NR = 1 
 WHERE
       IRSCW.ZONE.DEVICE IN
       ( 
          SELECT ID
            FROM IRSCW.DEVICE
           WHERE TYPE = 1
       )
;

UPDATE IRSCW.CNCMILLINGMACHINE SET NEW_DEV_INT = false;