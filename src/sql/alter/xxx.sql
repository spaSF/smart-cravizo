SET DEFINE OFF;
prompt -- ALTER TABLE CRAVIZO POPIS --;
ALTER TABLE CRAVIZO 
 MODIFY (
  POPIS VARCHAR2 (2000 CHAR)
 )
/
MERGE INTO SC_DSFIELD A USING
 (SELECT
  1102 as ID,
  4 as VERSION,
  NULL as AUTO_GENERATED,
  0 as CAN_EDIT,
  1 as CAN_FILTER,
  1 as CAN_FOCUS,
  1 as CAN_SAVE,
  NULL as CHARACTER_CASING,
  'unknown' as CREATED_BY,
  223 as XDATASOURCE,
  TO_TIMESTAMP('5. 11. 2015 17:21:19,803000','fmDDfm. fmMMfm. YYYY fmHH24fm:MI:SS,FF') as DATE_CREATED,
  NULL as DECIMAL_PAD,
  NULL as DECIMAL_PRECISION,
  NULL as DETAIL,
  'popis' as DISPLAY_FIELD,
  NULL as EDIT_REQUIRES_ROLE,
  NULL as EDITOR_TYPE,
  4 as FIELD_POSITION,
  NULL as FOREIGN_DISPLAY_FIELD,
  NULL as FOREIGN_KEY,
  NULL as FORM_ITEM_PROPS,
  0 as HIDDEN,
  NULL as INCLUDE_FROM,
  NULL as INCLUDE_VIA,
  TO_TIMESTAMP('18. 11. 2015 9:23:08,016000','fmDDfm. fmMMfm. YYYY fmHH24fm:MI:SS,FF') as LAST_UPDATED,
  NULL as LENGTH,
  NULL as MASK,
  NULL as MULTIPLE,
  NULL as MULTIPLE_STORAGE,
  'paramnm' as NAME,
  'RequestParamCis' as OPTION_DATA_SOURCE,
  NULL as PRIMARY_KEY,
  NULL as PROMPT,
  NULL as PROPERTIES_ONLY,
  NULL as READ_ONLY_EDITOR_TYPE,
  1 as REQUIRED,
  NULL as ROOT_VALUE,
  'isc.i18nMessages["requestParam.paramnm.label"]' as TITLE,
  'select' as TYPE,
  NULL as UPDATE_REQUIRES_ROLE,
  'admin' as UPDATED_BY,
  NULL as VALID_OPERATORS,
  NULL as VALIDATORS,
  'id' as VALUE_FIELD,
  NULL as VALUE_MAP,
  'paramnm/id' as VALUEXPATH,
  NULL as VIEW_REQUIRES_ROLE
  FROM DUAL) B
ON (A.ID = B.ID)
WHEN NOT MATCHED THEN 
INSERT (
  ID, VERSION, AUTO_GENERATED, CAN_EDIT, CAN_FILTER, 
  CAN_FOCUS, CAN_SAVE, CHARACTER_CASING, CREATED_BY, XDATASOURCE, 
  DATE_CREATED, DECIMAL_PAD, DECIMAL_PRECISION, DETAIL, DISPLAY_FIELD, 
  EDIT_REQUIRES_ROLE, EDITOR_TYPE, FIELD_POSITION, FOREIGN_DISPLAY_FIELD, FOREIGN_KEY, 
  FORM_ITEM_PROPS, HIDDEN, INCLUDE_FROM, INCLUDE_VIA, LAST_UPDATED, 
  LENGTH, MASK, MULTIPLE, MULTIPLE_STORAGE, NAME, 
  OPTION_DATA_SOURCE, PRIMARY_KEY, PROMPT, PROPERTIES_ONLY, READ_ONLY_EDITOR_TYPE, 
  REQUIRED, ROOT_VALUE, TITLE, TYPE, UPDATE_REQUIRES_ROLE, 
  UPDATED_BY, VALID_OPERATORS, VALIDATORS, VALUE_FIELD, VALUE_MAP, 
  VALUEXPATH, VIEW_REQUIRES_ROLE)
VALUES (
  B.ID, B.VERSION, B.AUTO_GENERATED, B.CAN_EDIT, B.CAN_FILTER, 
  B.CAN_FOCUS, B.CAN_SAVE, B.CHARACTER_CASING, B.CREATED_BY, B.XDATASOURCE, 
  B.DATE_CREATED, B.DECIMAL_PAD, B.DECIMAL_PRECISION, B.DETAIL, B.DISPLAY_FIELD, 
  B.EDIT_REQUIRES_ROLE, B.EDITOR_TYPE, B.FIELD_POSITION, B.FOREIGN_DISPLAY_FIELD, B.FOREIGN_KEY, 
  B.FORM_ITEM_PROPS, B.HIDDEN, B.INCLUDE_FROM, B.INCLUDE_VIA, B.LAST_UPDATED, 
  B.LENGTH, B.MASK, B.MULTIPLE, B.MULTIPLE_STORAGE, B.NAME, 
  B.OPTION_DATA_SOURCE, B.PRIMARY_KEY, B.PROMPT, B.PROPERTIES_ONLY, B.READ_ONLY_EDITOR_TYPE, 
  B.REQUIRED, B.ROOT_VALUE, B.TITLE, B.TYPE, B.UPDATE_REQUIRES_ROLE, 
  B.UPDATED_BY, B.VALID_OPERATORS, B.VALIDATORS, B.VALUE_FIELD, B.VALUE_MAP, 
  B.VALUEXPATH, B.VIEW_REQUIRES_ROLE)
WHEN MATCHED THEN
UPDATE SET 
  A.VERSION = B.VERSION,
  A.AUTO_GENERATED = B.AUTO_GENERATED,
  A.CAN_EDIT = B.CAN_EDIT,
  A.CAN_FILTER = B.CAN_FILTER,
  A.CAN_FOCUS = B.CAN_FOCUS,
  A.CAN_SAVE = B.CAN_SAVE,
  A.CHARACTER_CASING = B.CHARACTER_CASING,
  A.CREATED_BY = B.CREATED_BY,
  A.XDATASOURCE = B.XDATASOURCE,
  A.DATE_CREATED = B.DATE_CREATED,
  A.DECIMAL_PAD = B.DECIMAL_PAD,
  A.DECIMAL_PRECISION = B.DECIMAL_PRECISION,
  A.DETAIL = B.DETAIL,
  A.DISPLAY_FIELD = B.DISPLAY_FIELD,
  A.EDIT_REQUIRES_ROLE = B.EDIT_REQUIRES_ROLE,
  A.EDITOR_TYPE = B.EDITOR_TYPE,
  A.FIELD_POSITION = B.FIELD_POSITION,
  A.FOREIGN_DISPLAY_FIELD = B.FOREIGN_DISPLAY_FIELD,
  A.FOREIGN_KEY = B.FOREIGN_KEY,
  A.FORM_ITEM_PROPS = B.FORM_ITEM_PROPS,
  A.HIDDEN = B.HIDDEN,
  A.INCLUDE_FROM = B.INCLUDE_FROM,
  A.INCLUDE_VIA = B.INCLUDE_VIA,
  A.LAST_UPDATED = B.LAST_UPDATED,
  A.LENGTH = B.LENGTH,
  A.MASK = B.MASK,
  A.MULTIPLE = B.MULTIPLE,
  A.MULTIPLE_STORAGE = B.MULTIPLE_STORAGE,
  A.NAME = B.NAME,
  A.OPTION_DATA_SOURCE = B.OPTION_DATA_SOURCE,
  A.PRIMARY_KEY = B.PRIMARY_KEY,
  A.PROMPT = B.PROMPT,
  A.PROPERTIES_ONLY = B.PROPERTIES_ONLY,
  A.READ_ONLY_EDITOR_TYPE = B.READ_ONLY_EDITOR_TYPE,
  A.REQUIRED = B.REQUIRED,
  A.ROOT_VALUE = B.ROOT_VALUE,
  A.TITLE = B.TITLE,
  A.TYPE = B.TYPE,
  A.UPDATE_REQUIRES_ROLE = B.UPDATE_REQUIRES_ROLE,
  A.UPDATED_BY = B.UPDATED_BY,
  A.VALID_OPERATORS = B.VALID_OPERATORS,
  A.VALIDATORS = B.VALIDATORS,
  A.VALUE_FIELD = B.VALUE_FIELD,
  A.VALUE_MAP = B.VALUE_MAP,
  A.VALUEXPATH = B.VALUEXPATH,
  A.VIEW_REQUIRES_ROLE = B.VIEW_REQUIRES_ROLE;
COMMIT;

