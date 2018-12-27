/**
简版工作流脚本
 */
/*==============================================================*/
/* New Table: act_hq_procinst                                   */
/*==============================================================*/
declare i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_PROCINST';
 if i = 0 then
  execute immediate '
  create table act_hq_procinst
(
id_                  NVARCHAR2(64)        not null,
proc_def_category_   NVARCHAR2(255),
business_key_        NVARCHAR2(255),
proc_def_id_         NVARCHAR2(64),
proc_start_user_id_  NVARCHAR2(64),
proc_status_         NUMBER(1),
proc_start_time_     TIMESTAMP(6),
proc_end_time_       TIMESTAMP(6)
)'; 
 execute immediate 'alter table ACT_HQ_PROCINST add primary key (id_)';
 execute immediate 'create index IDX_HQPI_BUSKEY on ACT_HQ_PROCINST(BUSINESS_KEY_)'; 
 end if;
commit;
end;
/|/

/*==============================================================*/
/* New Table: ACT_HQ_TASKINST                                   */
/*==============================================================*/

declare i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TASKINST';
 if i = 0 then
  execute immediate '
  create table ACT_HQ_TASKINST 
(
id_                  NVARCHAR2(64)        not null,
proc_inst_id_        NVARCHAR2(64),
proc_def_id_         NVARCHAR2(64),
proc_def_category_   NVARCHAR2(255),
business_key_        NVARCHAR2(255),
prev_task_id_        NVARCHAR2(255),
task_def_key_        NVARCHAR2(255),
has_task_candidate_  NUMBER(1),
assignee_            NVARCHAR2(255),
task_type_           NUMBER(1),
delete_reason_       NVARCHAR2(32),
create_reason_       NVARCHAR2(32),
start_time_          TIMESTAMP(6),
end_time_            TIMESTAMP(6),
message_             NVARCHAR2(2000),
description_         NVARCHAR2(2000)
)'; 
 execute immediate 'alter table ACT_HQ_TASKINST add primary key (id_)';
 execute immediate 'create index IDX_HQTI_BUSKEY on ACT_HQ_TASKINST(BUSINESS_KEY_)'; 
 execute immediate 'create index IDX_HQTI_piid on ACT_HQ_TASKINST(proc_inst_id_)'; 
 end if;
commit;
end;
/|/


/*==============================================================*/
/* ALTER Table: ACT_RE_PROCDEF                                 */
/*==============================================================*/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_RE_PROCDEF' AND COLUMN_NAME = 'BATCH_TYPE_';
  if i=0
    then 
    execute immediate 'ALTER TABLE act_re_procdef add BATCH_TYPE_  NUMBER(1)';
  end if;
  commit;
end;
/|/

/* 20180316_mfl_*/
declare
  i integer;
begin
  select count(*)
    into i
    from user_tables t
   where t.table_name = 'ACT_HQ_CONFIG';
  if i = 0 then
    execute immediate 'create table ACT_HQ_CONFIG(key_ varchar2(255), value1_ varchar2(255), value2_ number(1) )';
    execute immediate 'alter table ACT_HQ_CONFIG add primary key (key_)';
  end if;
  commit;
end;
/|/

/* 20180316_mfl_*/
declare
  i          integer;
  keyValue   varchar2(255) := 'default_version_mode_';
  valueValue number(1) := 1;--
begin
  select count(*)
    into i
    from ACT_HQ_CONFIG
   where key_ = 'default_version_mode_';
  if i = 0 then
    execute immediate 'insert into ACT_HQ_CONFIG(key_, value2_) values(:2,:3)'
      using keyValue, valueValue;
  end if;
  commit;
end;

