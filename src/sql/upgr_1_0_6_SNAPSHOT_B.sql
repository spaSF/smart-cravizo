ALTER TABLE SC_CONFIG 
RENAME COLUMN PROP_KEY TO KEY
/

CREATE TABLE sc_user_setting
    (id                             NUMBER(19,0) NOT NULL,
    version                        NUMBER(19,0) NOT NULL,
    app_case                       VARCHAR2(255) NOT NULL,
    app_object                     VARCHAR2(255) NOT NULL,
    field_name                     VARCHAR2(255),
    setting_object                 VARCHAR2(255),
    use_default                    NUMBER(1,0),
    user_id                        NUMBER(19,0) NOT NULL)
  NOPARALLEL
  LOGGING
/

create sequence SC_SETTING_SEQ
/

-- Constraints for SC_USER_SETTING

ALTER TABLE sc_user_setting
ADD PRIMARY KEY (id)
USING INDEX
/

-- End of DDL Script for Table ZIUPT.SC_USER_SETTING

-- Foreign Key
ALTER TABLE sc_user_setting
ADD CONSTRAINT fk1961cb4b20310cd3 FOREIGN KEY (user_id)
REFERENCES sc_user (id)
/
-- End of DDL script for Foreign Key(s)
