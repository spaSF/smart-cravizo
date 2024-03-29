SET DEFINE OFF;
MERGE INTO SC_NOTIFEV A USING
 (SELECT
  'AVSTATUS' as ID,
  0 as VERSION,
  'adminaviz' as CREATED_BY,
  TO_TIMESTAMP('10/12/2018 15:52:28,272000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as DATE_CREATED,
  'Aktualizácia vybavenosti avíza' as DESCRIPTION,
  TO_TIMESTAMP('10/12/2018 15:52:28,272000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as LAST_UPDATED,
  'adminaviz' as UPDATED_BY
  FROM DUAL) B
ON (A.ID = B.ID)
WHEN NOT MATCHED THEN 
INSERT (
  ID, VERSION, CREATED_BY, DATE_CREATED, DESCRIPTION, 
  LAST_UPDATED, UPDATED_BY)
VALUES (
  B.ID, B.VERSION, B.CREATED_BY, B.DATE_CREATED, B.DESCRIPTION, 
  B.LAST_UPDATED, B.UPDATED_BY)
WHEN MATCHED THEN
UPDATE SET 
  A.VERSION = B.VERSION,
  A.CREATED_BY = B.CREATED_BY,
  A.DATE_CREATED = B.DATE_CREATED,
  A.DESCRIPTION = B.DESCRIPTION,
  A.LAST_UPDATED = B.LAST_UPDATED,
  A.UPDATED_BY = B.UPDATED_BY;

COMMIT;
