prompt ====================================================;
prompt upgrade DB to version 1.1;
prompt ====================================================;
prompt
prompt Alter AV_REQAVIZO modify respmsg to 4000;
alter table AV_REQAVIZO modify(respmsg varchar2(4000));
prompt ==============NOTIFIKACIE=======================;
Prompt Table SC_NOTIF;
CREATE TABLE SC_NOTIF
(
  ID            NUMBER(19)                      NOT NULL,
  VERSION       NUMBER(19)                      NOT NULL,
  BODY          VARCHAR2(4000 CHAR)             NOT NULL,
  CREATED_BY    VARCHAR2(255 CHAR)              NOT NULL,
  DATE_CREATED  TIMESTAMP(6)                    NOT NULL,
  ERRM          VARCHAR2(1000 CHAR),
  EVENT_ID      VARCHAR2(50 CHAR),
  LAST_UPDATED  TIMESTAMP(6)                    NOT NULL,
  NOTIF_SENDED  TIMESTAMP(6),
  RECIPIENTS    VARCHAR2(1000 CHAR),
  REQ_AVIZO_ID  NUMBER(19)                      NOT NULL,
  SENDER        VARCHAR2(100 CHAR)              NOT NULL,
  STATUS        NUMBER(10)                      NOT NULL,
  SUBJECT       VARCHAR2(1000 CHAR)             NOT NULL,
  UPDATED_BY    VARCHAR2(255 CHAR)              NOT NULL
)

;


Prompt Table SC_NOTIFEV;
CREATE TABLE SC_NOTIFEV
(
  ID            VARCHAR2(50 CHAR)               NOT NULL,
  VERSION       NUMBER(19)                      NOT NULL,
  CREATED_BY    VARCHAR2(255 CHAR)              NOT NULL,
  DATE_CREATED  TIMESTAMP(6)                    NOT NULL,
  DESCRIPTION   VARCHAR2(255 CHAR)              NOT NULL,
  LAST_UPDATED  TIMESTAMP(6)                    NOT NULL,
  UPDATED_BY    VARCHAR2(255 CHAR)              NOT NULL
)

;


Prompt Table SC_NOTIFTMP;
CREATE TABLE SC_NOTIFTMP
(
  ID             NUMBER(19)                     NOT NULL,
  VERSION        NUMBER(19)                     NOT NULL,
  CLAZZ          VARCHAR2(255 CHAR),
  CREATED_BY     VARCHAR2(255 CHAR)             NOT NULL,
  DATE_CREATED   TIMESTAMP(6)                   NOT NULL,
  EVENT_ID       VARCHAR2(50 CHAR),
  IS_BODY        NUMBER(1),
  LAST_UPDATED   TIMESTAMP(6)                   NOT NULL,
  OBJECT_PROPS   VARCHAR2(2000 CHAR),
  TEMPLATE_TEXT  VARCHAR2(2000 CHAR),
  UPDATED_BY     VARCHAR2(255 CHAR)             NOT NULL,
  TEMPLATE_PART  NUMBER(10)                     NOT NULL
)

;


--  There is no statement for index CRFO.SYS_C0030432.
--  The object is created when the parent object is created.

--  There is no statement for index CRFO.SYS_C0030439.
--  The object is created when the parent object is created.

--  There is no statement for index CRFO.SYS_C0030543.
--  The object is created when the parent object is created.

Prompt Index UK_ORS5MVFJQSH7AERVFXF25J3N6;
CREATE UNIQUE INDEX UK_ORS5MVFJQSH7AERVFXF25J3N6 ON SC_NOTIF
(REQ_AVIZO_ID);

Prompt Non-Foreign Key Constraints on Table SC_NOTIF;
ALTER TABLE SC_NOTIF ADD (
  PRIMARY KEY
  (ID)
  ENABLE VALIDATE);

ALTER TABLE SC_NOTIF ADD (
  CONSTRAINT UK_ORS5MVFJQSH7AERVFXF25J3N6
  UNIQUE (REQ_AVIZO_ID)
  USING INDEX UK_ORS5MVFJQSH7AERVFXF25J3N6
  ENABLE VALIDATE);


Prompt Non-Foreign Key Constraints on Table SC_NOTIFEV;
ALTER TABLE SC_NOTIFEV ADD (
  PRIMARY KEY
  (ID)
  ENABLE VALIDATE);


Prompt Non-Foreign Key Constraints on Table SC_NOTIFTMP;
ALTER TABLE SC_NOTIFTMP ADD (
  PRIMARY KEY
  (ID)
  ENABLE VALIDATE);


Prompt Foreign Key Constraints on Table SC_NOTIF;
ALTER TABLE SC_NOTIF ADD (
  CONSTRAINT FK_KE5FO1FNQNAFBRXRT7HG17D4J 
  FOREIGN KEY (EVENT_ID) 
  REFERENCES SC_NOTIFEV (ID)
  ENABLE VALIDATE);

ALTER TABLE SC_NOTIF ADD (
  CONSTRAINT FK_ORS5MVFJQSH7AERVFXF25J3N6 
  FOREIGN KEY (REQ_AVIZO_ID) 
  REFERENCES AV_REQAVIZO (ID)
  ENABLE VALIDATE);


Prompt Foreign Key Constraints on Table SC_NOTIFTMP;
ALTER TABLE SC_NOTIFTMP ADD (
  CONSTRAINT FK_STQMRIATUEOBOUSH29DSX21E3 
  FOREIGN KEY (EVENT_ID) 
  REFERENCES SC_NOTIFEV (ID)
  ENABLE VALIDATE);
