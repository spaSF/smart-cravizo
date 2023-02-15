prompt ---------------------------;
prompt -- CREATE TABLE crusrlog --;
prompt ---------------------------;
CREATE TABLE crusrlog
    (id                             NUMBER(19,0) NOT NULL,
    version                        NUMBER(19,0) NOT NULL,
    cas                            TIMESTAMP (6) NOT NULL,
    osoba_id                       NUMBER(19,0),
    user_id                        NUMBER(19,0) NOT NULL)
  NOPARALLEL
  LOGGING
  MONITORING
/
ALTER TABLE crusrlog
ADD PRIMARY KEY (id)
USING INDEX
/
ALTER TABLE crusrlog
ADD CONSTRAINT fk_914c9hbl62wxkhqiaa0g73oh5 FOREIGN KEY (osoba_id)
REFERENCES crosoba (id)
/
ALTER TABLE crusrlog
ADD CONSTRAINT fk_thlyf1d6ejostcdywkk5s6w1b FOREIGN KEY (user_id)
REFERENCES sc_user (id)
/
create sequence CRZUSRLOG_SEQ
/
CREATE INDEX CRFO.CRUSRLOG_OSOBA_IDX ON CRUSRLOG
   (  OSOBA_ID ASC ,  USER_ID ASC ,  CAS ASC  ) 
/
CREATE INDEX CRFO.CRUSRLOG_CAS_IDX ON CRUSRLOG
   (  CAS ASC  ) 
/
CREATE INDEX CRFO.CRUSRLOG_USER_IDX ON CRUSRLOG
   (  USER_ID ASC  ) 
/
prompt ---------------------------;
prompt -- CREATE view wcrusrlog --;
prompt ---------------------------;
create or replace view wcrusrlog as
select a.*, b.ifo, b.dtnar, b.priezcele, b.menocele, b.rodprcele, c.surname, c.name from crusrlog a, crosoba b, sc_user c 
where b.id = a.osoba_id and c.id = a.user_id
/