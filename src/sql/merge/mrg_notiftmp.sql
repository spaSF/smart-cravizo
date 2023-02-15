SET DEFINE OFF;
MERGE INTO SC_NOTIFTMP A USING
 (SELECT
  1 as ID,
  3 as VERSION,
  'com.sfs.crfosp.aviza.Avizo' as CLAZZ,
  'adminaviz' as CREATED_BY,
  TO_TIMESTAMP('11/12/2018 16:19:53,718000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as DATE_CREATED,
  'AVSTATUS' as EVENT_ID,
  0 as IS_BODY,
  TO_TIMESTAMP('19/12/2018 12:24:46,318000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as LAST_UPDATED,
  'id,avosoba.rc,avosoba.priezCele,avosoba.menoCele' as OBJECT_PROPS,
  'Aktualiz�cia av�za ��slo {0} osoby R� {1}, {2} {3}' as TEMPLATE_TEXT,
  'avadm' as UPDATED_BY,
  0 as TEMPLATE_PART
  FROM DUAL) B
ON (A.ID = B.ID)
WHEN NOT MATCHED THEN 
INSERT (
  ID, VERSION, CLAZZ, CREATED_BY, DATE_CREATED, 
  EVENT_ID, IS_BODY, LAST_UPDATED, OBJECT_PROPS, TEMPLATE_TEXT, 
  UPDATED_BY, TEMPLATE_PART)
VALUES (
  B.ID, B.VERSION, B.CLAZZ, B.CREATED_BY, B.DATE_CREATED, 
  B.EVENT_ID, B.IS_BODY, B.LAST_UPDATED, B.OBJECT_PROPS, B.TEMPLATE_TEXT, 
  B.UPDATED_BY, B.TEMPLATE_PART)
WHEN MATCHED THEN
UPDATE SET 
  A.VERSION = B.VERSION,
  A.CLAZZ = B.CLAZZ,
  A.CREATED_BY = B.CREATED_BY,
  A.DATE_CREATED = B.DATE_CREATED,
  A.EVENT_ID = B.EVENT_ID,
  A.IS_BODY = B.IS_BODY,
  A.LAST_UPDATED = B.LAST_UPDATED,
  A.OBJECT_PROPS = B.OBJECT_PROPS,
  A.TEMPLATE_TEXT = B.TEMPLATE_TEXT,
  A.UPDATED_BY = B.UPDATED_BY,
  A.TEMPLATE_PART = B.TEMPLATE_PART;

MERGE INTO SC_NOTIFTMP A USING
 (SELECT
  2 as ID,
  8 as VERSION,
  'com.sfs.crfosp.aviza.Avizo' as CLAZZ,
  'adminaviz' as CREATED_BY,
  TO_TIMESTAMP('11/12/2018 16:23:07,040000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as DATE_CREATED,
  'AVSTATUS' as EVENT_ID,
  1 as IS_BODY,
  TO_TIMESTAMP('20/12/2018 14:34:34,671000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as LAST_UPDATED,
  'id,avosoba.rc,avosoba.priezCele,avosoba.menoCele,popis,status,dateCreated,datumVybav' as OBJECT_PROPS,
  'Na av�ze ��slo <b>{0}</b> osoby R� <b>{1}</b>, {2} {3} zaslanom z d�vodom <b>{4}</b>, do�lo k aktualiz�cii stavu vybavenosti. Celkov� stav av�za je <b>{5}</b>.<br>D�tum vytvorenia av�za: {6}<br>D�tum vybavenia av�za: {7}<br>Stav vybavenia jednotliv�ch atrib�tov:<br>
' as TEMPLATE_TEXT,
  'adminaviz' as UPDATED_BY,
  0 as TEMPLATE_PART
  FROM DUAL) B
ON (A.ID = B.ID)
WHEN NOT MATCHED THEN 
INSERT (
  ID, VERSION, CLAZZ, CREATED_BY, DATE_CREATED, 
  EVENT_ID, IS_BODY, LAST_UPDATED, OBJECT_PROPS, TEMPLATE_TEXT, 
  UPDATED_BY, TEMPLATE_PART)
VALUES (
  B.ID, B.VERSION, B.CLAZZ, B.CREATED_BY, B.DATE_CREATED, 
  B.EVENT_ID, B.IS_BODY, B.LAST_UPDATED, B.OBJECT_PROPS, B.TEMPLATE_TEXT, 
  B.UPDATED_BY, B.TEMPLATE_PART)
WHEN MATCHED THEN
UPDATE SET 
  A.VERSION = B.VERSION,
  A.CLAZZ = B.CLAZZ,
  A.CREATED_BY = B.CREATED_BY,
  A.DATE_CREATED = B.DATE_CREATED,
  A.EVENT_ID = B.EVENT_ID,
  A.IS_BODY = B.IS_BODY,
  A.LAST_UPDATED = B.LAST_UPDATED,
  A.OBJECT_PROPS = B.OBJECT_PROPS,
  A.TEMPLATE_TEXT = B.TEMPLATE_TEXT,
  A.UPDATED_BY = B.UPDATED_BY,
  A.TEMPLATE_PART = B.TEMPLATE_PART;

MERGE INTO SC_NOTIFTMP A USING
 (SELECT
  3 as ID,
  5 as VERSION,
  'com.sfs.crfosp.aviza.AvizoAtributy' as CLAZZ,
  'adminaviz' as CREATED_BY,
  TO_TIMESTAMP('11/12/2018 16:26:26,174000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as DATE_CREATED,
  'AVSTATUS' as EVENT_ID,
  1 as IS_BODY,
  TO_TIMESTAMP('12/12/2018 10:53:45,059000','fmDDfm/fmMMfm/YYYY fmHH24fm:MI:SS,FF') as LAST_UPDATED,
  'polozka.popis,status' as OBJECT_PROPS,
  '<li>Atrib�t <b>{0}</b> - aktu�lny stav vybavenia je <b>{1}</b><br></li>
' as TEMPLATE_TEXT,
  'adminaviz' as UPDATED_BY,
  1 as TEMPLATE_PART
  FROM DUAL) B
ON (A.ID = B.ID)
WHEN NOT MATCHED THEN 
INSERT (
  ID, VERSION, CLAZZ, CREATED_BY, DATE_CREATED, 
  EVENT_ID, IS_BODY, LAST_UPDATED, OBJECT_PROPS, TEMPLATE_TEXT, 
  UPDATED_BY, TEMPLATE_PART)
VALUES (
  B.ID, B.VERSION, B.CLAZZ, B.CREATED_BY, B.DATE_CREATED, 
  B.EVENT_ID, B.IS_BODY, B.LAST_UPDATED, B.OBJECT_PROPS, B.TEMPLATE_TEXT, 
  B.UPDATED_BY, B.TEMPLATE_PART)
WHEN MATCHED THEN
UPDATE SET 
  A.VERSION = B.VERSION,
  A.CLAZZ = B.CLAZZ,
  A.CREATED_BY = B.CREATED_BY,
  A.DATE_CREATED = B.DATE_CREATED,
  A.EVENT_ID = B.EVENT_ID,
  A.IS_BODY = B.IS_BODY,
  A.LAST_UPDATED = B.LAST_UPDATED,
  A.OBJECT_PROPS = B.OBJECT_PROPS,
  A.TEMPLATE_TEXT = B.TEMPLATE_TEXT,
  A.UPDATED_BY = B.UPDATED_BY,
  A.TEMPLATE_PART = B.TEMPLATE_PART;

COMMIT;