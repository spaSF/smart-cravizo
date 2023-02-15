prompt ======================================================;
prompt m:n vazba requestLog<->Avizo AV_REQAVIZO;
prompt ======================================================;
create table AV_REQAVIZO
(
    ID          number(19) not null enable,
    AVIZO_ID    number(19) not null enable,
    REQID       number(19) not null enable,
    RESPKOD     number(10),
    RESPMSG     varchar2 (512 char)
)
nocache
logging;

alter table av_reqavizo 
  add constraint AV_REQAVIZO_PK
  primary key (id) 
  validate;

alter table av_reqavizo
  add constraint AV_REQAVIZO_RQ_FK
  foreign key (reqid)
  references crrequest(id)
  validate;

alter table av_reqavizo
  add constraint AV_REQAVIZO_AV_FK
  foreign key (avizo_id)
  references cravizo(id)
  validate;

create sequence AV_REQAVIZO_SEQ start with 1 increment by 1;