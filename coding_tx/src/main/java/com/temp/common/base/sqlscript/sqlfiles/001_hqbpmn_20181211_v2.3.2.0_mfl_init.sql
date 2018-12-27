/*初始化工作流表结构*/
declare i integer;
         v integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_GE_PROPERTY';
 if i = 0 then
 execute immediate '
   create table ACT_GE_PROPERTY
(
  name_  NVARCHAR2(64) not null,
  value_ NVARCHAR2(300),
  rev_   NUMBER(38)
)';
 end if;

 

  select count(*) into i from user_tables t where t.table_name = 'ACT_HI_ACTINST';
 if i = 0 then
  execute immediate '
create table ACT_HI_ACTINST
(
  id_                NVARCHAR2(64) not null,
  proc_def_id_       NVARCHAR2(64) not null,
  proc_inst_id_      NVARCHAR2(64) not null,
  execution_id_      NVARCHAR2(64) not null,
  act_id_            NVARCHAR2(255) not null,
  task_id_           NVARCHAR2(64),
  call_proc_inst_id_ NVARCHAR2(64),
  act_name_          NVARCHAR2(255),
  act_type_          NVARCHAR2(255) not null,
  assignee_          NVARCHAR2(255),
  start_time_        TIMESTAMP(6) not null,
  end_time_          TIMESTAMP(6),
  duration_          NUMBER(19)
)';
 execute immediate 'create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST (END_TIME_)';
 execute immediate 'create index ACT_IDX_HI_ACT_INST_EXEC on ACT_HI_ACTINST (EXECUTION_ID_, ACT_ID_)';
 execute immediate 'create index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST (PROC_INST_ID_, ACT_ID_)';
 execute immediate 'create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST (START_TIME_)';
 execute immediate 'alter table ACT_HI_ACTINST add primary key (ID_)';
 end if;


 
 select count(*) into i from user_tables t where t.table_name = 'ACT_HI_ATTACHMENT';
 if i = 0 then
 execute immediate '
  create table ACT_HI_ATTACHMENT
(
  id_           NVARCHAR2(64) not null,
  rev_          INTEGER,
  user_id_      NVARCHAR2(255),
  name_         NVARCHAR2(255),
  description_  NVARCHAR2(2000),
  type_         NVARCHAR2(255),
  task_id_      NVARCHAR2(64),
  proc_inst_id_ NVARCHAR2(64),
  url_          NVARCHAR2(2000),
  content_id_   NVARCHAR2(64)
)';
execute immediate 'alter table ACT_HI_ATTACHMENT
  add primary key (ID_)';
 end if;

  
 
 select count(*) into i from user_tables t where t.table_name = 'ACT_HI_DETAIL';
 if i = 0 then
 execute immediate '
   create table ACT_HI_DETAIL
(
  id_           NVARCHAR2(64) not null,
  type_         NVARCHAR2(255) not null,
  proc_inst_id_ NVARCHAR2(64),
  execution_id_ NVARCHAR2(64),
  task_id_      NVARCHAR2(64),
  act_inst_id_  NVARCHAR2(64),
  name_         NVARCHAR2(255) not null,
  var_type_     NVARCHAR2(64),
  rev_          INTEGER,
  time_         TIMESTAMP(6) not null,
  bytearray_id_ NVARCHAR2(64),
  double_       NUMBER(*,10),
  long_         NUMBER(19),
  text_         NVARCHAR2(2000),
  text2_        NVARCHAR2(2000)
)';
  execute immediate 'create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL (ACT_INST_ID_)';
  execute immediate 'create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL (NAME_)';
  execute immediate 'create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL (PROC_INST_ID_)';
  execute immediate 'create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL (TASK_ID_)';
  execute immediate 'create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL (TIME_)';
  execute immediate 'alter table ACT_HI_DETAIL
  add primary key (ID_)';
 end if;
 
 


 
 select count(*) into i from user_tables t where t.table_name = 'ACT_HI_IDENTITYLINK';
 if i = 0 then
 execute immediate '
    create table ACT_HI_IDENTITYLINK
(
  id_           NVARCHAR2(64) not null,
  group_id_     NVARCHAR2(255),
  type_         NVARCHAR2(255),
  user_id_      NVARCHAR2(255),
  task_id_      NVARCHAR2(64),
  proc_inst_id_ NVARCHAR2(64)
)';
execute immediate 'create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK (PROC_INST_ID_)';
  execute immediate 'create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK (TASK_ID_)';
  execute immediate 'create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK (USER_ID_)';
  execute immediate 'alter table ACT_HI_IDENTITYLINK
  add primary key (ID_)';
 end if;
 
  
  
  select count(*) into i from user_tables t where t.table_name = 'ACT_HI_PROCINST';
 if i = 0 then
  execute immediate '
   create table ACT_HI_PROCINST
(
  id_                        NVARCHAR2(64) not null,
  proc_inst_id_              NVARCHAR2(64) not null,
  business_key_              NVARCHAR2(255),
  proc_def_id_               NVARCHAR2(64) not null,
  start_time_                TIMESTAMP(6) not null,
  end_time_                  TIMESTAMP(6),
  duration_                  NUMBER(19),
  start_user_id_             NVARCHAR2(255),
  start_act_id_              NVARCHAR2(255),
  end_act_id_                NVARCHAR2(255),
  super_process_instance_id_ NVARCHAR2(64),
  delete_reason_             NVARCHAR2(2000)
)';
execute immediate 'create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST (END_TIME_)';
execute immediate 'create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST (BUSINESS_KEY_)';
execute immediate 'alter table ACT_HI_PROCINST add primary key (ID_)';
execute immediate 'alter table ACT_HI_PROCINST add unique (PROC_INST_ID_)';
 end if;

  select count(*) into i from user_tables t where t.table_name = 'ACT_HI_TASKINST';
 if i = 0 then
 execute immediate '
    create table ACT_HI_TASKINST
(
  id_             NVARCHAR2(64) not null,
  proc_def_id_    NVARCHAR2(64),
  task_def_key_   NVARCHAR2(255),
  proc_inst_id_   NVARCHAR2(64),
  execution_id_   NVARCHAR2(64),
  parent_task_id_ NVARCHAR2(64),
  name_           NVARCHAR2(255),
  description_    NVARCHAR2(2000),
  owner_          NVARCHAR2(255),
  assignee_       NVARCHAR2(255),
  start_time_     TIMESTAMP(6) not null,
  claim_time_     TIMESTAMP(6),
  end_time_       TIMESTAMP(6),
  duration_       NUMBER(19),
  delete_reason_  NVARCHAR2(2000),
  priority_       INTEGER,
  due_date_       TIMESTAMP(6),
  form_key_       NVARCHAR2(255)
)';
execute immediate 'alter table ACT_HI_TASKINST add primary key (ID_)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HI_VARINST';
 if i = 0 then
  execute immediate '
   create table ACT_HI_VARINST
(
  id_           NVARCHAR2(64) not null,
  proc_inst_id_ NVARCHAR2(64),
  execution_id_ NVARCHAR2(64),
  task_id_      NVARCHAR2(64),
  name_         NVARCHAR2(255) not null,
  var_type_     NVARCHAR2(100),
  rev_          INTEGER,
  bytearray_id_ NVARCHAR2(64),
  double_       NUMBER(*,10),
  long_         NUMBER(19),
  text_         NVARCHAR2(2000),
  text2_        NVARCHAR2(2000)
)';
execute immediate 'create index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST (NAME_, VAR_TYPE_)';
execute immediate 'create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST (PROC_INST_ID_)';
execute immediate 'alter table ACT_HI_VARINST add primary key (ID_)';
 end if;

  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_CODE';
 if i = 0 then
 execute immediate '
  create table ACT_HQ_CODE
(
  id        VARCHAR2(32) not null,
  code_key  VARCHAR2(64),
  code_name VARCHAR2(200),
  super_id  VARCHAR2(32),
  code_type VARCHAR2(64),
  order_id  NUMBER(10)
)';
  execute immediate 'alter table ACT_HQ_CODE add primary key (ID)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_CUSTOM_BUTTON';
 if i = 0 then
execute immediate '
   create table ACT_HQ_CUSTOM_BUTTON
(
  id           VARCHAR2(32) not null,
  button_name  VARCHAR2(32),
  button_key   VARCHAR2(32),
  button_type  NUMBER(10),
  button_order NUMBER(10),
  js_function  VARCHAR2(200)
)';
execute immediate 'alter table ACT_HQ_CUSTOM_BUTTON add primary key (ID)';
 end if;
 
   select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_DELEGATE';
 if i = 0 then
 execute immediate '
   create table ACT_HQ_DELEGATE
(
  id         VARCHAR2(32) not null,
  user_id    VARCHAR2(255),
  to_user_id VARCHAR2(255),
  start_time TIMESTAMP(6),
  end_time   TIMESTAMP(6),
  state      NUMBER,
  bpmn_type  VARCHAR2(1000)
)';
 execute immediate 'alter table ACT_HQ_DELEGATE
  add constraint PK_ACT_HQ_BPMNDELEGATE primary key (ID)';
 end if;
 
   select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_FIELD';
   select count(*) into v from user_views t where t.VIEW_NAME = 'ACT_HQ_FIELD';

 if i = 0 and v = 0 then
 execute immediate '
    create table ACT_HQ_FIELD
(
  id          VARCHAR2(32) not null,
  name        VARCHAR2(64),
  dbname      VARCHAR2(32),
  description VARCHAR2(255),
  tableid     VARCHAR2(32)
)';
 execute immediate 'alter table ACT_HQ_FIELD
  add primary key (ID)';
 end if;
 
    select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_FIELD_PRIVILEGE';
 if i = 0 then
  execute immediate '
 create table ACT_HQ_FIELD_PRIVILEGE
(
  id                    VARCHAR2(32) not null,
  process_definition_id VARCHAR2(32),
  task_def_key          VARCHAR2(255),
  runtime_bpmn_role     VARCHAR2(32),
  table_id              VARCHAR2(32),
  table_dbname          VARCHAR2(32),
  field_id              VARCHAR2(32),
  field_dbname          VARCHAR2(32),
  can_not_visble        INTEGER,
  can_not_modify        INTEGER,
  must_fill_in          INTEGER
)';
execute immediate 'alter table ACT_HQ_FIELD_PRIVILEGE add primary key (ID)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_HI_READING';
 if i = 0 then
  execute immediate '
  create table ACT_HQ_HI_READING
(
  id           VARCHAR2(32) not null,
  business_key VARCHAR2(32),
  task_id      VARCHAR2(32),
  bpmn_type    VARCHAR2(32),
  create_time  TIMESTAMP(6),
  title        VARCHAR2(200),
  assignee     VARCHAR2(32),
  comment_     VARCHAR2(2000),
  proc_inst_id VARCHAR2(32),
  proc_def_id  VARCHAR2(32),
  task_name    VARCHAR2(200),
  task_key     VARCHAR2(200),
  end_time     TIMESTAMP(6),
  type_        NUMBER,
  launch_user  VARCHAR2(64)
)';
  execute immediate 'alter table ACT_HQ_HI_READING
  add constraint PK_ACT_HQ_HI_READING primary key (ID)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_HI_TASK';
 if i = 0 then
 execute immediate '
   create table ACT_HQ_HI_TASK
(
  id_             NVARCHAR2(64) not null,
  proc_def_id_    NVARCHAR2(64),
  task_def_key_   NVARCHAR2(255),
  proc_inst_id_   NVARCHAR2(64),
  execution_id_   NVARCHAR2(64),
  parent_task_id_ NVARCHAR2(64),
  name_           NVARCHAR2(255),
  description_    NVARCHAR2(2000),
  owner_          NVARCHAR2(255),
  assignee_       NVARCHAR2(255),
  start_time_     TIMESTAMP(6) not null,
  claim_time_     TIMESTAMP(6),
  end_time_       TIMESTAMP(6),
  duration_       NUMBER(19),
  delete_reason_  NVARCHAR2(2000),
  priority_       INTEGER,
  due_date_       TIMESTAMP(6),
  form_key_       NVARCHAR2(255)
)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_PERSONNEL_ALLOCATION';
 if i = 0 then
 execute immediate '
 create table ACT_HQ_PERSONNEL_ALLOCATION
(
  id_                   NVARCHAR2(64) not null,
  typeid_               NVARCHAR2(255),
  typename_             NVARCHAR2(255),
  prev_step_            NVARCHAR2(2000),
  prev_step_expression_ NVARCHAR2(2000),
  next_step_            NVARCHAR2(2000),
  next_step_expression_ NVARCHAR2(2000),
  order_id              NUMBER(10)
)';
 execute immediate 'alter table ACT_HQ_PERSONNEL_ALLOCATION add primary key (ID_)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_RU_READING';
 if i = 0 then
 execute immediate '
  create table ACT_HQ_RU_READING
(
  id           VARCHAR2(32) not null,
  business_key VARCHAR2(32),
  task_id      VARCHAR2(32),
  bpmn_type    VARCHAR2(32),
  create_time  TIMESTAMP(6),
  title        VARCHAR2(200),
  assignee     VARCHAR2(32),
  comment_     VARCHAR2(2000),
  proc_inst_id VARCHAR2(32),
  proc_def_id  VARCHAR2(32),
  task_name    VARCHAR2(200),
  task_key     VARCHAR2(200),
  type_        NUMBER,
  launch_user  VARCHAR2(64)
)';
 execute immediate 'alter table ACT_HQ_RU_READING add constraint PK_ACT_HQ_RU_READING primary key (ID)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_SEQUENCE_EXTEND';
 if i = 0 then
  execute immediate '
  create table ACT_HQ_SEQUENCE_EXTEND
(
  id                     VARCHAR2(32) not null,
  process_definition_id  VARCHAR2(255),
  sequence_key           VARCHAR2(255),
  is_need_comment        NUMBER,
  open_panel             VARCHAR2(64),
  is_candidate_users     INTEGER,
  is_candidate_groups    INTEGER,
  bath_complete_variable VARCHAR2(64),
  sequence_type          VARCHAR2(32),
  sequence_sort_number   VARCHAR2(32)
)';
execute immediate 'alter table ACT_HQ_SEQUENCE_EXTEND add primary key (ID)';
 end if;

     select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_SEQUENCE_IDENTITYLINK';
 if i = 0 then
  execute immediate '
  create table ACT_HQ_SEQUENCE_IDENTITYLINK
(
  id           VARCHAR2(64) not null,
  sequence_key VARCHAR2(255),
  group_id     VARCHAR2(255),
  proc_def_id  VARCHAR2(64),
  user_id      VARCHAR2(255)
)';
 execute immediate 'alter table ACT_HQ_SEQUENCE_IDENTITYLINK add primary key (ID)';
 end if;

     select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_SUBTASK';
 if i = 0 then
 execute immediate '
 create table ACT_HQ_SUBTASK
(
  id          VARCHAR2(32) not null,
  subtask_id_ NVARCHAR2(64),
  isproxy     VARCHAR2(2)
)';
execute immediate 'alter table ACT_HQ_SUBTASK add primary key (ID)';
 end if;
 
      select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TABLE';
      select count(*) into v from user_views t where t.VIEW_NAME = 'ACT_HQ_TABLE';
 if i = 0 and v = 0 then
 execute immediate '
   create table ACT_HQ_TABLE
(
  id        VARCHAR2(32) not null,
  name      VARCHAR2(100),
  dbname    VARCHAR2(30),
  parentid  VARCHAR2(32),
  bpmn_type VARCHAR2(200)
)';
execute immediate 'alter table ACT_HQ_TABLE add primary key (ID)';
 end if;

       select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TABLE_PRIVILEGE';
 if i = 0 then
execute immediate '
   create table ACT_HQ_TABLE_PRIVILEGE
(
  id                    VARCHAR2(32) not null,
  process_definition_id VARCHAR2(32),
  task_def_key          VARCHAR2(255),
  runtime_bpmn_role     VARCHAR2(32),
  table_id              VARCHAR2(32),
  table_dbname          VARCHAR2(32),
  can_not_add           INTEGER,
  can_not_delete        INTEGER,
  can_not_visble        INTEGER,
  can_not_modify        INTEGER
)';
execute immediate 'alter table ACT_HQ_TABLE_PRIVILEGE add primary key (ID)';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TASK_BUTTON';
 if i = 0 then
  execute immediate '
  create table ACT_HQ_TASK_BUTTON
(
  id                    VARCHAR2(32) not null,
  process_definition_id VARCHAR2(32),
  task_key              VARCHAR2(1000),
  button_key            VARCHAR2(32),
  button_name           VARCHAR2(100),
  processor_expression  VARCHAR2(1000),
  button_order          NUMBER,
  lunch_type            NUMBER
)';
  execute immediate 'alter table ACT_HQ_TASK_BUTTON add constraint PK_ACT_HQ_TASK_BUTTON primary key (ID)';
 end if;

   select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TASK_EXTEND';
 if i = 0 then
  execute immediate '
 create table ACT_HQ_TASK_EXTEND
(
  id                    VARCHAR2(32) not null,
  process_definition_id VARCHAR2(255),
  task_key              VARCHAR2(255),
  is_create_sub_task    VARCHAR2(64),
  custom_button         VARCHAR2(1000),
  is_to_be_received     NUMBER(10),
  is_to_be_revoked      NUMBER(10),
  is_to_be_returned     NUMBER(10),
  task_type             VARCHAR2(32),
  processor             VARCHAR2(1000),
  processor_expression  VARCHAR2(500),
  show_type             VARCHAR2(32),
  is_to_all_user        NUMBER(10)
)';
 execute immediate 'alter table ACT_HQ_TASK_EXTEND add primary key (ID)';
 end if;

   select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TASK_READING';
 if i = 0 then
  execute immediate '
 create table ACT_HQ_TASK_READING
(
  id           VARCHAR2(32) not null,
  business_key VARCHAR2(32),
  task_id      VARCHAR2(32),
  bpmn_type    VARCHAR2(32),
  create_time  VARCHAR2(20),
  title        VARCHAR2(200),
  is_read      NUMBER,
  assignee     VARCHAR2(20),
  comment_     VARCHAR2(2000),
  proc_inst_id VARCHAR2(32),
  proc_def_id  VARCHAR2(32),
  task_name    VARCHAR2(200),
  task_key     VARCHAR2(200)
)';
execute immediate 'alter table ACT_HQ_TASK_READING
  add constraint PK_ACT_HQ_TASK_READING primary key (ID)';
 end if;
  
 
    select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TEM_CATEGORY';

    select count(*) into v from user_views t where t.VIEW_NAME = 'ACT_HQ_TEM_CATEGORY';

 if i = 0 and v = 0 then
  execute immediate '
 create table ACT_HQ_TEM_CATEGORY
(
  id            VARCHAR2(32) not null,
  name          VARCHAR2(64),
  category      VARCHAR2(255),
  parentid      VARCHAR2(32),
  description   VARCHAR2(32),
  proc_def_key_ VARCHAR2(255)
)';
 execute immediate 'alter table ACT_HQ_TEM_CATEGORY add primary key (ID)';
 end if;
 
 
    select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TEM_CODE';
 if i = 0 then
  execute immediate '
 create table ACT_HQ_TEM_CODE
(
  id        VARCHAR2(32) not null,
  code_type VARCHAR2(64),
  type_name VARCHAR2(200),
  super_id  VARCHAR2(32),
  type      NUMBER(10),
  order_id  NUMBER(10)
)';
execute immediate 'alter table ACT_HQ_TEM_CODE  add primary key (ID)';
 end if;

     select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_VARIABLE';
 if i = 0 then
  execute immediate '
 create table ACT_HQ_VARIABLE
(
  id                    VARCHAR2(32) not null,
  category              VARCHAR2(255),
  type                  VARCHAR2(255),
  name                  VARCHAR2(255),
  process_definition_id VARCHAR2(100)
)';
 execute immediate 'alter table ACT_HQ_VARIABLE add primary key (ID)';
 end if;
 

 select count(*) into i from user_tables t where t.table_name = 'ACT_RE_DEPLOYMENT';
 if i = 0 then
  execute immediate '
create table ACT_RE_DEPLOYMENT
(
  id_          NVARCHAR2(64) not null,
  name_        NVARCHAR2(255),
  category_    NVARCHAR2(255),
  deploy_time_ TIMESTAMP(6)
)';
execute immediate 'alter table ACT_RE_DEPLOYMENT add primary key (ID_)';
 end if;

       select count(*) into i from user_tables t where t.table_name = 'ACT_GE_BYTEARRAY';
 if i = 0 then
  execute immediate '
create table ACT_GE_BYTEARRAY
(
  id_            NVARCHAR2(64) not null,
  rev_           INTEGER,
  name_          NVARCHAR2(255),
  deployment_id_ NVARCHAR2(64),
  bytes_         BLOB,
  generated_     NUMBER(1)
)';
 execute immediate 'create index ACT_IDX_BYTEAR_DEPL on ACT_GE_BYTEARRAY (DEPLOYMENT_ID_)';
 execute immediate 'alter table ACT_GE_BYTEARRAY add primary key (ID_)';
 execute immediate 'alter table ACT_GE_BYTEARRAY
  add constraint ACT_FK_BYTEARR_DEPL foreign key (DEPLOYMENT_ID_) references ACT_RE_DEPLOYMENT (ID_)';
 execute immediate 'alter table ACT_GE_BYTEARRAY add check (GENERATED_ IN (1,0))';
 end if;

 select count(*) into i from user_tables t where t.table_name = 'ACT_RE_MODEL';
 if i = 0 then
  execute immediate '
create table ACT_RE_MODEL
(
  id_                           NVARCHAR2(64) not null,
  rev_                          INTEGER,
  name_                         NVARCHAR2(255),
  key_                          NVARCHAR2(255),
  category_                     NVARCHAR2(255),
  create_time_                  TIMESTAMP(6),
  last_update_time_             TIMESTAMP(6),
  version_                      INTEGER,
  meta_info_                    NVARCHAR2(2000),
  deployment_id_                NVARCHAR2(64),
  editor_source_value_id_       NVARCHAR2(64),
  editor_source_extra_value_id_ NVARCHAR2(64)
)';
 execute immediate 'create index ACT_IDX_MODEL_DEPLOYMENT on ACT_RE_MODEL (DEPLOYMENT_ID_)';
 execute immediate 'create index ACT_IDX_MODEL_SOURCE on ACT_RE_MODEL (EDITOR_SOURCE_VALUE_ID_)';
 execute immediate 'create index ACT_IDX_MODEL_SOURCE_EXTRA on ACT_RE_MODEL (EDITOR_SOURCE_EXTRA_VALUE_ID_)';
 execute immediate 'alter table ACT_RE_MODEL add primary key (ID_)';
 execute immediate 'alter table ACT_RE_MODEL
  add constraint ACT_FK_MODEL_DEPLOYMENT foreign key (DEPLOYMENT_ID_)
  references ACT_RE_DEPLOYMENT (ID_)';
 execute immediate 'alter table ACT_RE_MODEL
  add constraint ACT_FK_MODEL_SOURCE foreign key (EDITOR_SOURCE_VALUE_ID_)
  references ACT_GE_BYTEARRAY (ID_)';
 execute immediate 'alter table ACT_RE_MODEL
  add constraint ACT_FK_MODEL_SOURCE_EXTRA foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_)
  references ACT_GE_BYTEARRAY (ID_)';
 end if;

  select count(*) into i from user_tables t where t.table_name = 'ACT_RE_PROCDEF';
 if i = 0 then
  execute immediate '
create table ACT_RE_PROCDEF
(
  id_                 NVARCHAR2(64) not null,
  rev_                INTEGER,
  category_           NVARCHAR2(255),
  name_               NVARCHAR2(255),
  key_                NVARCHAR2(255) not null,
  version_            INTEGER not null,
  deployment_id_      NVARCHAR2(64),
  resource_name_      NVARCHAR2(2000),
  dgrm_resource_name_ VARCHAR2(4000),
  description_        NVARCHAR2(2000),
  has_start_form_key_ NUMBER(1),
  suspension_state_   INTEGER
)';
  execute immediate 'alter table ACT_RE_PROCDEF add primary key (ID_)';
 execute immediate 'alter table ACT_RE_PROCDEF add constraint ACT_UNIQ_PROCDEF unique (KEY_, VERSION_)';
 execute immediate 'alter table ACT_RE_PROCDEF add check (HAS_START_FORM_KEY_ IN (1,0))';
 end if;

 select count(*) into i from user_tables t where t.table_name = 'ACT_RU_EXECUTION';
 if i = 0 then
  execute immediate '
create table ACT_RU_EXECUTION
(
  id_               NVARCHAR2(64) not null,
  rev_              INTEGER,
  proc_inst_id_     NVARCHAR2(64),
  business_key_     NVARCHAR2(255),
  parent_id_        NVARCHAR2(64),
  proc_def_id_      NVARCHAR2(64),
  super_exec_       NVARCHAR2(64),
  act_id_           NVARCHAR2(255),
  is_active_        NUMBER(1),
  is_concurrent_    NUMBER(1),
  is_scope_         NUMBER(1),
  is_event_scope_   NUMBER(1),
  suspension_state_ INTEGER,
  cached_ent_state_ INTEGER
)';
  execute immediate 'create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION (BUSINESS_KEY_)';
  execute immediate 'create index ACT_IDX_EXE_PARENT on ACT_RU_EXECUTION (PARENT_ID_)';
  execute immediate 'create index ACT_IDX_EXE_PROCDEF on ACT_RU_EXECUTION (PROC_DEF_ID_)';
  execute immediate 'create index ACT_IDX_EXE_PROCINST on ACT_RU_EXECUTION (PROC_INST_ID_)';
  execute immediate 'create index ACT_IDX_EXE_SUPER on ACT_RU_EXECUTION (SUPER_EXEC_)';
  execute immediate 'create unique index ACT_UNIQ_RU_BUS_KEY on ACT_RU_EXECUTION (CASE  WHEN BUSINESS_KEY_ IS NULL THEN NULL ELSE PROC_DEF_ID_ END, CASE  WHEN BUSINESS_KEY_ IS NULL THEN NULL ELSE BUSINESS_KEY_ END)';
  execute immediate 'alter table ACT_RU_EXECUTION add primary key (ID_)';
  execute immediate 'alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_PARENT foreign key (PARENT_ID_)
  references ACT_RU_EXECUTION (ID_)';
  execute immediate 'alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_PROCDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_)';
  execute immediate 'alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_)';
  execute immediate 'alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_SUPER foreign key (SUPER_EXEC_) references ACT_RU_EXECUTION (ID_)';
  execute immediate 'alter table ACT_RU_EXECUTION add check (IS_ACTIVE_ IN (1,0))';
  execute immediate 'alter table ACT_RU_EXECUTION add check (IS_CONCURRENT_ IN (1,0))';
  execute immediate 'alter table ACT_RU_EXECUTION add check (IS_SCOPE_ IN (1,0))';
  execute immediate 'alter table ACT_RU_EXECUTION add check (IS_EVENT_SCOPE_ IN (1,0))';
 end if;
 
  select count(*) into i from user_tables t where t.table_name = 'ACT_RU_EVENT_SUBSCR';
 if i = 0 then
 execute immediate '
create table ACT_RU_EVENT_SUBSCR
(
  id_            NVARCHAR2(64) not null,
  rev_           INTEGER,
  event_type_    NVARCHAR2(255) not null,
  event_name_    NVARCHAR2(255),
  execution_id_  NVARCHAR2(64),
  proc_inst_id_  NVARCHAR2(64),
  activity_id_   NVARCHAR2(64),
  configuration_ NVARCHAR2(255),
  created_       TIMESTAMP(6) not null
)';
execute immediate 'create index ACT_IDX_EVENT_SUBSCR on ACT_RU_EVENT_SUBSCR (EXECUTION_ID_)';
   execute immediate 'create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR (CONFIGURATION_)';
   execute immediate 'alter table ACT_RU_EVENT_SUBSCR add primary key (ID_)';
   execute immediate 'alter table ACT_RU_EVENT_SUBSCR
  add constraint ACT_FK_EVENT_EXEC foreign key (EXECUTION_ID_)
  references ACT_RU_EXECUTION (ID_)';
 end if;

   
  select count(*) into i from user_tables t where t.table_name = 'ACT_RU_TASK';
 if i = 0 then
  execute immediate '
create table ACT_RU_TASK
(
  id_               NVARCHAR2(64) not null,
  rev_              INTEGER,
  execution_id_     NVARCHAR2(64),
  proc_inst_id_     NVARCHAR2(64),
  proc_def_id_      NVARCHAR2(64),
  name_             NVARCHAR2(255),
  parent_task_id_   NVARCHAR2(64),
  description_      NVARCHAR2(2000),
  task_def_key_     NVARCHAR2(255),
  owner_            NVARCHAR2(255),
  assignee_         NVARCHAR2(255),
  delegation_       NVARCHAR2(64),
  priority_         INTEGER,
  create_time_      TIMESTAMP(6),
  due_date_         TIMESTAMP(6),
  suspension_state_ INTEGER
)';
execute immediate 'create index ACT_IDX_TASK_CREATE on ACT_RU_TASK (CREATE_TIME_)';
   execute immediate 'create index ACT_IDX_TASK_EXEC on ACT_RU_TASK (EXECUTION_ID_)';
   execute immediate 'create index ACT_IDX_TASK_PROCDEF on ACT_RU_TASK (PROC_DEF_ID_)';
   execute immediate 'create index ACT_IDX_TASK_PROCINST on ACT_RU_TASK (PROC_INST_ID_)';
   execute immediate 'alter table ACT_RU_TASK add primary key (ID_)';
   execute immediate 'alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_EXE foreign key (EXECUTION_ID_)
  references ACT_RU_EXECUTION (ID_)';
   execute immediate 'alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_)';
   execute immediate 'alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_)';
 end if;

   select count(*) into i from user_tables t where t.table_name = 'ACT_RU_IDENTITYLINK';
 if i = 0 then
 execute immediate '
create table ACT_RU_IDENTITYLINK
(
  id_           NVARCHAR2(64) not null,
  rev_          INTEGER,
  group_id_     NVARCHAR2(255),
  type_         NVARCHAR2(255),
  user_id_      NVARCHAR2(255),
  task_id_      NVARCHAR2(64),
  proc_inst_id_ NVARCHAR2(64),
  proc_def_id_  NVARCHAR2(64)
)';
execute immediate 'create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK (PROC_DEF_ID_)';
  execute immediate 'create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK (GROUP_ID_)';
  execute immediate 'create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK (USER_ID_)';
  execute immediate 'create index ACT_IDX_IDL_PROCINST on ACT_RU_IDENTITYLINK (PROC_INST_ID_)';
  execute immediate 'create index ACT_IDX_TSKASS_TASK on ACT_RU_IDENTITYLINK (TASK_ID_)';
  execute immediate 'alter table ACT_RU_IDENTITYLINK add primary key (ID_)';
  execute immediate 'alter table ACT_RU_IDENTITYLINK
  add constraint ACT_FK_ATHRZ_PROCEDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_)';
  execute immediate 'alter table ACT_RU_IDENTITYLINK
  add constraint ACT_FK_IDL_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_)';
  execute immediate 'alter table ACT_RU_IDENTITYLINK
  add constraint ACT_FK_TSKASS_TASK foreign key (TASK_ID_)
  references ACT_RU_TASK (ID_)';
 end if;


    select count(*) into i from user_tables t where t.table_name = 'ACT_RU_JOB';
 if i = 0 then
  execute immediate '
create table ACT_RU_JOB
(
  id_                  NVARCHAR2(64) not null,
  rev_                 INTEGER,
  type_                NVARCHAR2(255) not null,
  lock_exp_time_       TIMESTAMP(6),
  lock_owner_          NVARCHAR2(255),
  exclusive_           NUMBER(1),
  execution_id_        NVARCHAR2(64),
  process_instance_id_ NVARCHAR2(64),
  proc_def_id_         NVARCHAR2(64),
  retries_             INTEGER,
  exception_stack_id_  NVARCHAR2(64),
  exception_msg_       NVARCHAR2(2000),
  duedate_             TIMESTAMP(6),
  repeat_              NVARCHAR2(255),
  handler_type_        NVARCHAR2(255),
  handler_cfg_         NVARCHAR2(2000)
)';
  execute immediate 'create index ACT_IDX_JOB_EXCEPTION on ACT_RU_JOB (EXCEPTION_STACK_ID_)';
  execute immediate 'alter table ACT_RU_JOB add primary key (ID_)';
  execute immediate 'alter table ACT_RU_JOB
  add constraint ACT_FK_JOB_EXCEPTION foreign key (EXCEPTION_STACK_ID_)
  references ACT_GE_BYTEARRAY (ID_)';
  execute immediate 'alter table ACT_RU_JOB add check (EXCLUSIVE_ IN (1,0))';
 end if;

    select count(*) into i from user_tables t where t.table_name = 'ACT_RU_VARIABLE';
 if i = 0 then
 execute immediate '
create table ACT_RU_VARIABLE
(
  id_           NVARCHAR2(64) not null,
  rev_          INTEGER,
  type_         NVARCHAR2(255) not null,
  name_         NVARCHAR2(255) not null,
  execution_id_ NVARCHAR2(64),
  proc_inst_id_ NVARCHAR2(64),
  task_id_      NVARCHAR2(64),
  bytearray_id_ NVARCHAR2(64),
  double_       NUMBER(*,10),
  long_         NUMBER(19),
  text_         NVARCHAR2(2000),
  text2_        NVARCHAR2(2000)
)';
 execute immediate 'create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE (TASK_ID_)';
 execute immediate 'create index ACT_IDX_VAR_BYTEARRAY on ACT_RU_VARIABLE (BYTEARRAY_ID_)';
 execute immediate 'create index ACT_IDX_VAR_EXE on ACT_RU_VARIABLE (EXECUTION_ID_)';
 execute immediate 'create index ACT_IDX_VAR_PROCINST on ACT_RU_VARIABLE (PROC_INST_ID_)';
 execute immediate 'alter table ACT_RU_VARIABLE add primary key (ID_)';
 execute immediate 'alter table ACT_RU_VARIABLE
  add constraint ACT_FK_VAR_BYTEARRAY foreign key (BYTEARRAY_ID_)
  references ACT_GE_BYTEARRAY (ID_)';
 execute immediate 'alter table ACT_RU_VARIABLE
  add constraint ACT_FK_VAR_EXE foreign key (EXECUTION_ID_)
  references ACT_RU_EXECUTION (ID_)';
 execute immediate 'alter table ACT_RU_VARIABLE
  add constraint ACT_FK_VAR_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_)';
 end if;

     select count(*) into i from user_tables t where t.table_name = 'ACT_HI_COMMENT';
 if i = 0 then
  execute immediate '
create table ACT_HI_COMMENT
(
  id_           NVARCHAR2(64) not null,
  type_         NVARCHAR2(255),
  time_         TIMESTAMP(6) not null,
  user_id_      NVARCHAR2(255),
  task_id_      NVARCHAR2(64),
  proc_inst_id_ NVARCHAR2(64),
  action_       NVARCHAR2(255),
  message_      NVARCHAR2(2000),
  full_msg_     BLOB
)';
 execute immediate 'alter table ACT_HI_COMMENT add primary key (ID_)';
 end if;
 

    select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TEM_DEF';
 if i = 0 then
 execute immediate '
create table ACT_HQ_TEM_DEF
(
  id            VARCHAR2(32) not null,
  name          VARCHAR2(64),
  category      VARCHAR2(255),
  version       INTEGER,
  create_by     VARCHAR2(64),
  modify_by     VARCHAR2(64),
  create_time   TIMESTAMP(6),
  modify_time   TIMESTAMP(6),
  deploy_state  INTEGER,
  version_state INTEGER,
  deployment_id VARCHAR2(255),
  content_bytes BLOB,
  init_num      INTEGER,
  canvaswidth   VARCHAR2(32),
  canvasheight  VARCHAR2(32),
  table_ids     VARCHAR2(255),
  table_names   VARCHAR2(255)
)';
execute immediate 'alter table ACT_HQ_TEM_DEF add primary key (ID)';
 end if;

 select count(*) into i from user_tables t where t.table_name = 'ACT_ID_INFO';
 if i = 0 then
  execute immediate '
create table ACT_ID_INFO
(
  id_        NVARCHAR2(64) not null,
  rev_       INTEGER,
  user_id_   NVARCHAR2(64),
  type_      NVARCHAR2(64),
  key_       NVARCHAR2(255),
  value_     NVARCHAR2(255),
  password_  BLOB,
  parent_id_ NVARCHAR2(255)
)';
 execute immediate 'alter table ACT_ID_INFO add primary key (ID_)';
 end if;
  commit;
end;
/|/

----对工作流表进行维护
 --20150803-00001-mfl  add/modify
 alter table ACT_HQ_CUSTOM_BUTTON modify button_name varchar2(255)
/|/
 --20150803-00002-mfl  add/modify
 alter table ACT_HQ_CUSTOM_BUTTON modify button_key varchar2(64)
/|/
 --20150803-00003-mfl  add/modify
 alter table ACT_HQ_HI_READING modify business_key varchar2(255)
/|/
 --20150803-00004-mfl  add/modify
 alter table ACT_HQ_HI_READING modify bpmn_type varchar2(255)
/|/
 --20150803-00005-mfl  add/modify
 alter table ACT_HQ_RU_READING modify business_key varchar2(255)
/|/
 --20150803-00006-mfl  add/modify
 alter table ACT_HQ_RU_READING modify bpmn_type varchar2(255)
/|/
 --20150803-00007-mfl  add/modify
 alter table ACT_HQ_TASK_READING modify business_key varchar2(255)
/|/
 --20150803-00008-mfl  add/modify
 alter table ACT_HQ_TASK_READING modify bpmn_type varchar2(255)
/|/
 --20150803-00009-mfl  add/modify
 alter table ACT_HQ_TASK_READING modify assignee varchar2(64)
/|/
  --20151211-00010-dh  add/modify
 alter table ACT_HQ_TASK_EXTEND modify PROCESSOR VARCHAR2(4000)

 -------------------20150803 以上脚本已发送给吴付灿让其同步------------------
 
 -------------------20150805 以上脚本已发送给赵国辉让其同步------------------
/|/
 --20150827-00011-dh  add/modify
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_CATEGORY' AND COLUMN_NAME = 'ORDER_ID';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TEM_CATEGORY  add ORDER_ID  NUMBER(10)';
  end if;
  commit;
end;
/|/
 
 --20150826-000012-dh  add/modify   
 --reason:等保三级
 declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_CUSTOM_BUTTON' AND COLUMN_NAME = 'DEPT_ID';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_CUSTOM_BUTTON  add DEPT_ID  VARCHAR(32)';
  end if;
  commit;
end;
/|/

declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_CATEGORY' AND COLUMN_NAME = 'DEPT_ID';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TEM_CATEGORY  add DEPT_ID  VARCHAR(32)';
  end if;
  commit;
end;
/|/

declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_CODE' AND COLUMN_NAME = 'DEPT_ID';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_CODE  add DEPT_ID  VARCHAR(32)';
  end if;
  commit;
end;
/|/


 --20150828-000013-dh  add/modify   
 --reason:字段太小，不满足需求

declare
  i integer;
begin
  select count(*)
    into i
    from User_Tab_Columns t
   where t.column_name = upper('PREV_STEP_')
     and t.TABLE_NAME = upper(trim('ACT_HQ_PERSONNEL_ALLOCATION'))
     and t.DATA_TYPE = 'CLOB';
  if i = 0 then
    execute immediate 'alter table act_hq_personnel_allocation add(tmp_col clob)';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = PREV_STEP_';
    execute immediate 'update act_hq_personnel_allocation set PREV_STEP_ = null';
    execute immediate 'alter table act_hq_personnel_allocation modify PREV_STEP_ long';
    execute immediate 'alter table act_hq_personnel_allocation modify PREV_STEP_ clob';
    execute immediate 'update act_hq_personnel_allocation set PREV_STEP_ = tmp_col';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = PREV_STEP_';
    execute immediate 'alter table act_hq_personnel_allocation drop column tmp_col';
  end if;
  commit;
end;
/|/

declare
  i integer;
begin
  select count(*)
    into i
    from User_Tab_Columns t
   where t.column_name = upper('PREV_STEP_EXPRESSION_')
     and t.TABLE_NAME = upper(trim('ACT_HQ_PERSONNEL_ALLOCATION'))
     and t.DATA_TYPE = 'CLOB';
  if i = 0 then
    execute immediate 'alter table act_hq_personnel_allocation add(tmp_col clob)';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = PREV_STEP_EXPRESSION_';
    execute immediate 'update act_hq_personnel_allocation set PREV_STEP_EXPRESSION_ = null';
    execute immediate 'alter table act_hq_personnel_allocation modify PREV_STEP_EXPRESSION_ long';
    execute immediate 'alter table act_hq_personnel_allocation modify PREV_STEP_EXPRESSION_ clob';
    execute immediate 'update act_hq_personnel_allocation set PREV_STEP_EXPRESSION_ = tmp_col';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = PREV_STEP_EXPRESSION_';
    execute immediate 'alter table act_hq_personnel_allocation drop column tmp_col';
  end if;
  commit;
end;
/|/

declare
  i integer;
begin
  select count(*)
    into i
    from User_Tab_Columns t
   where t.column_name = upper('NEXT_STEP_')
     and t.TABLE_NAME = upper(trim('ACT_HQ_PERSONNEL_ALLOCATION'))
     and t.DATA_TYPE = 'CLOB';
  if i = 0 then
    execute immediate 'alter table act_hq_personnel_allocation add(tmp_col clob)';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = NEXT_STEP_';
    execute immediate 'update act_hq_personnel_allocation set NEXT_STEP_ = null';
    execute immediate 'alter table act_hq_personnel_allocation modify NEXT_STEP_ long';
    execute immediate 'alter table act_hq_personnel_allocation modify NEXT_STEP_ clob';
    execute immediate 'update act_hq_personnel_allocation set NEXT_STEP_ = tmp_col';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = NEXT_STEP_';
    execute immediate 'alter table act_hq_personnel_allocation drop column tmp_col';
  end if;
  commit;
end;
/|/

declare
  i integer;
begin
  select count(*)
    into i
    from User_Tab_Columns t
   where t.column_name = upper('NEXT_STEP_EXPRESSION_')
     and t.TABLE_NAME = upper(trim('ACT_HQ_PERSONNEL_ALLOCATION'))
     and t.DATA_TYPE = 'CLOB';
  if i = 0 then
    execute immediate 'alter table act_hq_personnel_allocation add(tmp_col clob)';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = NEXT_STEP_EXPRESSION_';
    execute immediate 'update act_hq_personnel_allocation set NEXT_STEP_EXPRESSION_ = null';
    execute immediate 'alter table act_hq_personnel_allocation modify NEXT_STEP_EXPRESSION_ long';
    execute immediate 'alter table act_hq_personnel_allocation modify NEXT_STEP_EXPRESSION_ clob';
    execute immediate 'update act_hq_personnel_allocation set NEXT_STEP_EXPRESSION_ = tmp_col';
    execute immediate 'update act_hq_personnel_allocation set tmp_col = NEXT_STEP_EXPRESSION_';
    execute immediate 'alter table act_hq_personnel_allocation drop column tmp_col';
  end if;
  commit;
end;
/|/


---20151126-000014-mc  add/modify  
--reason:单条委托跟踪表
declare i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_DELEGATE_DETAIL';
 if i = 0 then
  execute immediate '
create table ACT_HQ_DELEGATE_DETAIL
(
  id           VARCHAR2(32) not null,
  business_key VARCHAR2(32),
  task_id      VARCHAR2(32),
  bpmn_type    VARCHAR2(32),
  create_time  TIMESTAMP(6),
  user_id      VARCHAR2(32),
  to_user_id   VARCHAR2(32),
  proc_inst_id VARCHAR2(64),
  proc_def_id  VARCHAR2(64),
  task_name    VARCHAR2(200),
  task_key     VARCHAR2(200),
  end_time     TIMESTAMP(6),
  status        NUMBER
)'; 
 execute immediate 'alter table ACT_HQ_DELEGATE_DETAIL add primary key (ID)';
 execute immediate 'alter table ACT_HQ_DELEGATE_DETAIL modify proc_inst_id VARCHAR2(64)';
 execute immediate 'alter table ACT_HQ_DELEGATE_DETAIL modify proc_def_id VARCHAR2(64)';
 end if;
commit;
end;
/|/
 --20151126-000015-wangyz  add/modify  
 alter table ACT_HQ_DELEGATE modify to_user_id VARCHAR2(4000)
 /|/
 alter table ACT_HQ_DELEGATE modify bpmn_type VARCHAR2(2000)
/|/
 
 --20151130-000016-mc  add/modify  
--reason:为支持混合流程添加的字段
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TASK_EXTEND' AND COLUMN_NAME = 'IS_TO_BE_FREEDOM_NODE';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TASK_EXTEND  add IS_TO_BE_FREEDOM_NODE  NUMBER(10)';
  end if;
  commit;
end;
/|/
--201501209-000017-dh  add/modify   
 --reason:内部会签
 declare 
i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_RU_COUNTERSIGN';
 if i = 0 then
  execute immediate '
create table ACT_HQ_RU_COUNTERSIGN(
    ID_            NVARCHAR2(64),
    REV_           INTEGER,
    TASK_ID_       NVARCHAR2(64),
    PID_           NVARCHAR2(64),
    ASSIGNEE_      NVARCHAR2(255),
    START_USER_ID_  NVARCHAR2(255),
    START_TIME_    TIMESTAMP(6) not null,
    END_TIME_      TIMESTAMP(6),
    COMMENT_       NVARCHAR2(2000),
    BACTCH_NO_     NVARCHAR2(64)
)';
execute immediate 'alter table ACT_HQ_RU_COUNTERSIGN add primary key (ID_)';
 end if; 
 commit;
end;
/|/

 declare 
i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_HI_COUNTERSIGN';
 if i = 0 then
  execute immediate '
create table ACT_HQ_HI_COUNTERSIGN(
    ID_            NVARCHAR2(64),
    TASK_ID_       NVARCHAR2(64),
    PID_           NVARCHAR2(64),
    ASSIGNEE_      NVARCHAR2(255),
    START_USER_ID_ NVARCHAR2(255),
    START_TIME_    TIMESTAMP(6) not null,
    END_TIME_      TIMESTAMP(6),
    COMMENT_       NVARCHAR2(2000),
    BACTCH_NO_     NVARCHAR2(64)
)';
execute immediate 'alter table ACT_HQ_HI_COUNTERSIGN add primary key (ID_)';
 end if;
  commit;
end;
/|/


--传阅表title长度增加   add by wangyz 2015-11-30
alter table ACT_HQ_RU_READING modify title VARCHAR2(1000)
/|/
alter table ACT_HQ_HI_READING modify title VARCHAR2(1000)
/|/

--其他项目组在act_id_user_视图新增state_（1启用2停用）和status_（1正常 2删除）字段，不需要的话默认都是1就行。

--20160414-添加控制权限的字段IS_ENABLED_ALL_USER-dh  add/modify   
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TASK_EXTEND' AND COLUMN_NAME = 'IS_ENABLED_ALL_USER';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TASK_EXTEND  add IS_ENABLED_ALL_USER  NUMBER(10)';
  end if;
  commit;
end;
/|/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_SEQUENCE_EXTEND' AND COLUMN_NAME = 'IS_ENABLED_ALL_USER';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_SEQUENCE_EXTEND  add IS_ENABLED_ALL_USER  INTEGER';
  end if;
  commit;
end;
/|/

--20160419-修改字段的长度(杭州投审)-dh  add/modify
 alter table ACT_HQ_FIELD_PRIVILEGE modify ID varchar2(64)
 /|/
 alter table ACT_HQ_FIELD_PRIVILEGE modify PROCESS_DEFINITION_ID varchar2(64)
 /|/
 alter table ACT_HQ_FIELD_PRIVILEGE modify RUNTIME_BPMN_ROLE varchar2(64)
 /|/
 alter table ACT_HQ_FIELD_PRIVILEGE modify TABLE_ID varchar2(64)
 /|/
 alter table ACT_HQ_FIELD_PRIVILEGE modify TABLE_DBNAME varchar2(64)
 /|/
 alter table ACT_HQ_FIELD_PRIVILEGE modify FIELD_ID varchar2(64)
 /|/
 alter table ACT_HQ_FIELD_PRIVILEGE modify FIELD_DBNAME varchar2(64)
 /|/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_FIELD_PRIVILEGE' AND COLUMN_NAME = 'DBVERSION';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_FIELD_PRIVILEGE  add DBVERSION  TIMESTAMP(6) default SYSDATE';
  end if;
  commit;
end;
/|/

 alter table ACT_HQ_TABLE_PRIVILEGE modify ID varchar2(64)
 /|/
 alter table ACT_HQ_TABLE_PRIVILEGE modify PROCESS_DEFINITION_ID varchar2(64)
 /|/
 alter table ACT_HQ_TABLE_PRIVILEGE modify RUNTIME_BPMN_ROLE varchar2(64)
 /|/
 alter table ACT_HQ_TABLE_PRIVILEGE modify TABLE_ID varchar2(64)
 /|/
 alter table ACT_HQ_TABLE_PRIVILEGE modify TABLE_DBNAME varchar2(64)
/|/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TABLE_PRIVILEGE' AND COLUMN_NAME = 'DBVERSION';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TABLE_PRIVILEGE  add DBVERSION  TIMESTAMP(6) default SYSDATE';
  end if;
  commit;
end;
/|/

alter table ACT_HQ_TASK_BUTTON modify PROCESS_DEFINITION_ID varchar2(200)
/|/

declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_CATEGORY' AND COLUMN_NAME = 'DESCRIPTION';
  if i=0
    then execute
      immediate 'alter table ACT_HQ_TEM_CATEGORY modify DESCRIPTION varchar2(4000)';
  end if;
  commit;
end;
 /|/


--20160516-给act_ru_identitylink添加机构字段-dh  add/modify

declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_RU_IDENTITYLINK' AND COLUMN_NAME = 'ORG_ID_';
  if i=0
    then execute 
      immediate 'alter table ACT_RU_IDENTITYLINK  add ORG_ID_ NVARCHAR2(255)';
  end if;
  commit;
end;
/|/

--20160524-给act_hq_task_extend添加供会签节点使用的处理组字段-dh  add/modify
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TASK_EXTEND' AND COLUMN_NAME = 'CANDIDATE_GROUP_';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TASK_EXTEND  add CANDIDATE_GROUP_ NVARCHAR2(2000)';
  end if;
commit;
end;
/|/


--工作流表初始化数据



declare         i    integer;
       name_value    varchar2(64) := 'schema.version';
    content_value    varchar2(300):= '5.14';
        rev_value    varchar2(38) := '1';
begin
   select count(*) into i from ACT_GE_PROPERTY where name_='schema.version';
  if i=0
    then execute immediate 'insert into ACT_GE_PROPERTY(NAME_, VALUE_, REV_) values(:2,:3,:4)'
      using name_value,content_value,rev_value;
  end if;
  commit;
end;
/|/

declare         i    integer;
       name_value    varchar2(64) := 'schema.history';
    content_value    varchar2(300):= 'create(5.14)';
        rev_value    varchar2(38) := '1';
begin
   select count(*) into i from ACT_GE_PROPERTY where name_='schema.history';
  if i=0
    then execute immediate 'insert into ACT_GE_PROPERTY(NAME_, VALUE_, REV_) values(:2,:3,:4)'
      using name_value,content_value,rev_value;
  end if;
  commit;
end;
/|/

declare         i    integer;
       name_value    varchar2(64) := 'next.dbid';
    content_value    varchar2(300):= '1';
        rev_value    varchar2(38) := '1';
begin
   select count(*) into i from ACT_GE_PROPERTY where name_='next.dbid';
  if i=0
    then execute immediate 'insert into ACT_GE_PROPERTY(NAME_, VALUE_, REV_) values(:2,:3,:4)'
      using name_value,content_value,rev_value;
  end if;
  commit;
end;
/|/

declare         i    integer;
         id_value    varchar2(32) := '1';
       name_value    varchar2(64) := '工作流类型';
   category_value    varchar2(255):= 'bpmn';
     parent_value    varchar2(32) := '#';
begin
   select count(*) into i from act_hq_tem_category where category='bpmn';
  if i=0
    then execute immediate 'insert into act_hq_tem_category(ID, NAME, CATEGORY, PARENTID) values(:2,:3,:4,:5)'
      using id_value,name_value,category_value,parent_value;
  end if;
  commit;
end;
/|/


declare         i    integer;
         id_value    varchar2(32) := '1';
         code_key    varchar2(64) := '0';
        code_name    varchar2(200):= '未部署';
         super_id    varchar2(32) := '#';
        code_type    varchar2(64) := 'deploystate';
        order_id_    integer      := 0;
         dept_id_    varchar2(32) := 'all';
begin
   select count(*) into i from act_hq_code where code_type='deploystate' and code_key = '0';
  if i=0
    then execute immediate 'insert into act_hq_code (id, code_key, code_name, super_id, code_type, order_id,dept_id) values (:2,:3,:4,:5,:6,:7,:8)'
      using id_value,code_key,code_name,super_id,code_type,order_id_,dept_id_;
  end if;
  commit;
end;
/|/

declare         i    integer;
         id_value    varchar2(32) := '2';
         code_key    varchar2(64) := '1';
        code_name    varchar2(200):= '已部署';
         super_id    varchar2(32) := '#';
        code_type    varchar2(64) := 'deploystate';
        order_id_    integer      := 1;
         dept_id_    varchar2(32) := 'all';
begin
   select count(*) into i from act_hq_code where code_type='deploystate' and code_key = '1';
  if i=0
    then execute immediate 'insert into act_hq_code (id, code_key, code_name, super_id, code_type, order_id,dept_id) values (:2,:3,:4,:5,:6,:7,:8)'
      using id_value,code_key,code_name,super_id,code_type,order_id_,dept_id_;
  end if;
  commit;
end;
/|/
declare         i    integer;
         id_value    varchar2(32) := '3';
         code_key    varchar2(64) := '0';
        code_name    varchar2(200):= '停用';
         super_id    varchar2(32) := '#';
        code_type    varchar2(64) := 'versionstate';
        order_id_    integer      := 0;
         dept_id_    varchar2(32) := 'all';
begin
   select count(*) into i from act_hq_code where code_type='versionstate' and code_key = '0';
  if i=0
    then execute immediate 'insert into act_hq_code (id, code_key, code_name, super_id, code_type, order_id,dept_id) values (:2,:3,:4,:5,:6,:7,:8)'
      using id_value,code_key,code_name,super_id,code_type,order_id_,dept_id_;
  end if;
  commit;
end;
/|/

declare         i    integer;
         id_value    varchar2(32) := '4';
         code_key    varchar2(64) := '1';
        code_name    varchar2(200):= '启用';
         super_id    varchar2(32) := '#';
        code_type    varchar2(64) := 'versionstate';
        order_id_    integer      := 1;
         dept_id_    varchar2(32) := 'all';
begin
   select count(*) into i from act_hq_code where code_type='versionstate' and code_key = '1';
  if i=0
    then execute immediate 'insert into act_hq_code (id, code_key, code_name, super_id, code_type, order_id,dept_id) values (:2,:3,:4,:5,:6,:7,:8)'
      using id_value,code_key,code_name,super_id,code_type,order_id_,dept_id_;
  end if;
  commit;
end;
/|/

declare         i    integer;
         id_value    varchar2(32) := '5';
         code_key    varchar2(64) := '2';
        code_name    varchar2(200):= '禁用';
         super_id    varchar2(32) := '#';
        code_type    varchar2(64) := 'versionstate';
        order_id_    integer      := 2;
         dept_id_    varchar2(32) := 'all';
begin
   select count(*) into i from act_hq_code where code_type='versionstate' and code_key = '2';
  if i=0
    then execute immediate 'insert into act_hq_code (id, code_key, code_name, super_id, code_type, order_id,dept_id) values (:2,:3,:4,:5,:6,:7,:8)'
      using id_value,code_key,code_name,super_id,code_type,order_id_,dept_id_;
  end if;
  commit;
end;
/|/

declare         i    integer;
         id_value    varchar2(32) := '1';
        code_type    varchar2(64) := 'deploystate';
        type_name    varchar2(200):= '部署状态';
         super_id    varchar2(32) := '#';
         type_value  integer      := 1;
         order_id_   integer      := 0;
begin
   select count(*) into i from ACT_HQ_TEM_CODE where code_type='deploystate';
  if i=0
    then execute immediate 'insert into ACT_HQ_TEM_CODE (id, code_type, type_name, super_id, type, order_id) values (:2,:3,:4,:5,:6,:7)'
      using id_value,code_type,type_name,super_id,type_value,order_id_;
  end if;
  commit;
end;
/|/
declare         i    integer;
         id_value    varchar2(32) := '2';
        code_type    varchar2(64) := 'versionstate';
        type_name    varchar2(200):= '版本状态';
         super_id    varchar2(32) := '#';
         type_value  integer      := 1;
         order_id_   integer      := 1;
begin
   select count(*) into i from ACT_HQ_TEM_CODE where code_type='versionstate';
  if i=0
    then execute immediate 'insert into ACT_HQ_TEM_CODE (id, code_type, type_name, super_id, type, order_id) values (:2,:3,:4,:5,:6,:7)'
      using id_value,code_type,type_name,super_id,type_value,order_id_;
  end if;
  commit;
end;
/|/
declare         i    integer;
         id_value    varchar2(32) := '3';
        code_type    varchar2(64) := 'taskDescription';
        type_name    varchar2(200):= '节点描述';
         super_id    varchar2(32) := '#';
         type_value  integer      := 1;
         order_id_   integer      := 2;
begin
   select count(*) into i from ACT_HQ_TEM_CODE where code_type='taskDescription';
  if i=0
    then execute immediate 'insert into ACT_HQ_TEM_CODE (id, code_type, type_name, super_id, type, order_id) values (:2,:3,:4,:5,:6,:7)'
      using id_value,code_type,type_name,super_id,type_value,order_id_;
  end if;
  commit;
end;
/|/
declare         i    integer;
         id_value    varchar2(32) := '4';
        code_type    varchar2(64) := 'taskListenerClass';
        type_name    varchar2(200):= '节点监听器';
         super_id    varchar2(32) := '#';
         type_value  integer      := 0;
         order_id_   integer      := 4;
begin
   select count(*) into i from ACT_HQ_TEM_CODE where code_type='taskListenerClass';
  if i=0
    then execute immediate 'insert into ACT_HQ_TEM_CODE (id, code_type, type_name, super_id, type, order_id) values (:2,:3,:4,:5,:6,:7)'
      using id_value,code_type,type_name,super_id,type_value,order_id_;
  end if;
  commit;
end;
/|/
declare         i    integer;
         id_value    varchar2(32) := '5';
        code_type    varchar2(64) := 'executionListenerClass';
        type_name    varchar2(200):= '执行监听器';
         super_id    varchar2(32) := '#';
         type_value  integer      := 1;
         order_id_   integer      := 5;
begin
   select count(*) into i from ACT_HQ_TEM_CODE where code_type='executionListenerClass';
  if i=0
    then execute immediate 'insert into ACT_HQ_TEM_CODE (id, code_type, type_name, super_id, type, order_id) values (:2,:3,:4,:5,:6,:7)'
      using id_value,code_type,type_name,super_id,type_value,order_id_;
  end if;
  commit;
end;
/|/
declare         i    integer;
         id_value    varchar2(32) := '6';
        code_type    varchar2(64) := 'personnelAllocation';
        type_name    varchar2(200):= '人员关系';
         super_id    varchar2(32) := '#';
         type_value  integer      := 0;
         order_id_   integer      := 1;
begin
   select count(*) into i from ACT_HQ_TEM_CODE where code_type='personnelAllocation';
  if i=0
    then execute immediate 'insert into ACT_HQ_TEM_CODE (id, code_type, type_name, super_id, type, order_id) values (:2,:3,:4,:5,:6,:7)'
      using id_value,code_type,type_name,super_id,type_value,order_id_;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '123456789';
          button_name    varchar2(255) := '转办';
           button_key    varchar2(64)  := 'hqbpmn_tranform';
          button_type    integer       := 0;
         button_order    integer       := 10;
          js_function    varchar2(200) := 'hqbpmn_tranform';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_tranform';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '12345678910';
          button_name    varchar2(255) := '阅办';
           button_key    varchar2(64)  := 'hqbpmn_reading';
          button_type    integer       := 0;
         button_order    integer       := 11;
          js_function    varchar2(200) := 'hqbpmn_reading';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_reading';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '1345678911';
          button_name    varchar2(255) := '传阅';
           button_key    varchar2(64)  := 'hqbpmn_tranformReading';
          button_type    integer       := 0;
         button_order    integer       := 12;
          js_function    varchar2(200) := 'hqbpmn_tranformReading';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_tranformReading';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/
declare             i    integer;
             id_temp    varchar2(32)  := '1345678912';
          button_name    varchar2(255) := '退回';
           button_key    varchar2(64)  := 'hqbpmn_toBeReturned';
          button_type    integer       := 0;
         button_order    integer       := null;
          js_function    varchar2(200) := 'hqbpmn_toBeReturned';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_toBeReturned';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '1324657895';
          button_name    varchar2(255) := '补正';
           button_key    varchar2(64)  := 'hqbpmn_toBeSupplement';
          button_type    integer       := 0;
         button_order    integer       := null;
          js_function    varchar2(200) := 'hqbpmn_toBeSupplement';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_toBeSupplement';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/
declare             i    integer;
             id_temp    varchar2(32)  := '132467896';
          button_name    varchar2(255) := '删除';
           button_key    varchar2(64)  := 'hqbpmn_toBeDelete';
          button_type    integer       := 0;
         button_order    integer       := null;
          js_function    varchar2(200) := 'hqbpmn_toBeDelete';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_toBeDelete';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '1234657897';
          button_name    varchar2(255) := '流程跟踪';
           button_key    varchar2(64)  := 'hqbpmn_processTrack';
          button_type    integer       := 0;
         button_order    integer       := null;
          js_function    varchar2(200) := 'hqbpmn_processTrack';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_processTrack';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/


declare             i    integer;
             id_temp    varchar2(32)  := '134657898';
          button_name    varchar2(255) := '撤回';
           button_key    varchar2(64)  := 'hqbpmn_toBeRecall';
          button_type    integer       := 0;
         button_order    integer       := null;
          js_function    varchar2(200) := 'hqbpmn_toBeRecall';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_toBeRecall';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '1234657899';
          button_name    varchar2(255) := '提交';
           button_key    varchar2(64)  := 'hqbpmn_submit';
          button_type    integer       := 0;
         button_order    integer       := null;
          js_function    varchar2(200) := 'hqbpmn_submit';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_submit';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/


declare             i    integer;
             id_temp    varchar2(32)  := '134657891110';
          button_name    varchar2(255) := '办结';
           button_key    varchar2(64)  := 'hqbpmn_handleEnd';
          button_type    number       := 0;
         button_order    number       := null;
          js_function    varchar2(200) := 'hqbpmn_handleEnd';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_handleEnd';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/
declare             i    integer;
             id_temp    varchar2(32)  := '1234657891011';
          button_name    varchar2(255) := '内部会签';
           button_key    varchar2(64)  := 'hqbpmn_innerCountersign';
          button_type    number       := 0;
         button_order    number       := null;
          js_function    varchar2(200) := 'hqbpmn_innerCountersign';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_innerCountersign';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/

declare             i    integer;
             id_temp    varchar2(32)  := '1234567897712';
          button_name    varchar2(255) := '内部会签转办';
           button_key    varchar2(64)  := 'hqbpmn_innerTranform';
          button_type    number       := 0;
         button_order    number       := 14;
          js_function    varchar2(200) := 'hqbpmn_innerTranform';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_innerTranform';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/


declare             i    integer;
             id_temp    varchar2(32)  := '1234567899113';
          button_name    varchar2(255) := '取消伪办结';
           button_key    varchar2(64)  := 'hqbpmn_cancelPretendFinishPd';
          button_type    number       := 0;
         button_order    number       := 14;
          js_function    varchar2(200) := 'hqbpmn_cancelPretendFinishPd';
              dept_id    varchar2(32)  := 'all';
begin
   select count(*) into i from ACT_HQ_CUSTOM_BUTTON where button_key='hqbpmn_cancelPretendFinishPd';
  if i=0
    then execute immediate 'insert into ACT_HQ_CUSTOM_BUTTON(id, button_name, button_key, button_type, button_order, js_function,dept_id) values(:2,:3,:4,:5,:6,:7,:8)'
      using id_temp,button_name,button_key,button_type,button_order,js_function,dept_id;
  end if;
  commit;
end;
/|/


declare i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_RU_SUBTASK_EXTEND';
 if i = 0 then
 execute immediate '
   create table act_hq_ru_subtask_extend
(
  id_                    NVARCHAR2(64),
  proc_def_id_           NVARCHAR2(64),
  proc_inst_id_          NVARCHAR2(64),
  execution_id_          NVARCHAR2(64),
  parent_task_id_        NVARCHAR2(64),
  task_def_key_          NVARCHAR2(255),
  name_                  NVARCHAR2(255),
  initiator_             NVARCHAR2(255),
  assignee_              NVARCHAR2(255),
  description_           NVARCHAR2(2000),
  create_time_           TIMESTAMP(6),
  return_state_          INTEGER,
  create_subtask_count_  INTEGER,
  category_              NVARCHAR2(255),
  business_key_          NVARCHAR2(255),
  nrt_sub_task_ids_      CLOB,
  nrt_main_task_id_      NVARCHAR2(64),
  task_type_             NVARCHAR2(64),
  level_                 INTEGER
)';
 execute immediate 'comment on column act_hq_ru_subtask_extend.id_  is ''子任务id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.proc_def_id_  is ''流程定义Id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.proc_inst_id_   is ''流程实例Id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.execution_id_  is ''执行实例Id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.parent_task_id_  is ''父任务Id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.task_def_key_  is ''任务节点Id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.name_  is ''任务名称''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.initiator_  is ''任务发起者（即：子任务的创建人）''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.assignee_   is ''任务处理人''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.description_  is ''任务描述字段''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.create_time_  is ''任务创建时间''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.return_state_   is ''返回状态''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.create_subtask_count_  is ''子任务数量''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.category_  is ''流程类型''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.business_key_  is ''工单号''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.nrt_sub_task_ids_  is ''不返回时，拼接的子任务Id字符串，多个时以逗号分隔''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.nrt_main_task_id_  is ''不返回时，主任务Id''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.task_type_  is ''任务类型''';
 execute immediate 'comment on column act_hq_ru_subtask_extend.level_  is ''任务的等级''';
 end if;
  commit;
end;
/|/



declare i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_HI_SUBTASK_EXTEND';
 if i = 0 then
 execute immediate '
   create table act_hq_hi_subtask_extend
(
  id_                    NVARCHAR2(64),
  proc_def_id_           NVARCHAR2(64),
  proc_inst_id_          NVARCHAR2(64),
  execution_id_          NVARCHAR2(64),
  parent_task_id_        NVARCHAR2(64),
  task_def_key_          NVARCHAR2(255),
  name_                  NVARCHAR2(255),
  initiator_             NVARCHAR2(255),
  assignee_              NVARCHAR2(255),
  description_           NVARCHAR2(2000),
  create_time_           TIMESTAMP(6),
  category_              NVARCHAR2(255),
  business_key_          NVARCHAR2(255),
  nrt_sub_task_ids_      CLOB,
  nrt_main_task_id_      NVARCHAR2(64),
  comment_               CLOB,
  delete_reason_         NVARCHAR2(64),
  end_time_              TIMESTAMP(6),
  return_state_          INTEGER,
  level_                  INTEGER
)';
 execute immediate 'comment on column act_hq_hi_subtask_extend.id_  is ''子任务id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.proc_def_id_  is ''流程定义Id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.proc_inst_id_   is ''流程实例Id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.execution_id_  is ''执行实例Id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.parent_task_id_  is ''父任务Id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.task_def_key_  is ''任务节点Id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.name_  is ''任务名称''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.initiator_  is ''任务发起者（即：子任务的创建人）''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.assignee_   is ''任务处理人''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.description_  is ''任务描述字段''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.create_time_  is ''任务创建时间''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.category_  is ''流程类型''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.business_key_  is ''工单号''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.nrt_sub_task_ids_  is ''不返回时，拼接的子任务Id字符串，多个时以逗号分隔''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.nrt_main_task_id_  is ''不返回时，主任务Id''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.comment_  is '' 评论意见''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.delete_reason_  is ''完成原因''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.end_time_  is ''结束时间''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.return_state_  is ''返回状态''';
 execute immediate 'comment on column act_hq_hi_subtask_extend.level_  is ''任务的等级''';
 end if;
  commit;
end;
/|/

/* 20170111-001_mfl_添加线上扩展定义的报送分送字段　*/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_SEQUENCE_EXTEND' AND COLUMN_NAME = 'IS_DISTRIBUTE_OR_SUBMISSION';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_SEQUENCE_EXTEND  add IS_DISTRIBUTE_OR_SUBMISSION  nvarchar2(32)';
  end if;
  commit;
end;
/|/


/* 20170328_tgx_流程模板类型添加字段财年   */
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_CATEGORY' AND COLUMN_NAME = 'YEAR';
  if i=1
    then 
    execute immediate 'alter table ACT_HQ_TEM_CATEGORY  add YEAR  VARCHAR2(32)';
  end if;
  commit;
end;
/|/
/* 20170328_tgx_流程模板类型添加字段财政   */
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_CATEGORY' AND COLUMN_NAME = 'PROVINCE';
  if i=1
    then 
    execute immediate 'alter table ACT_HQ_TEM_CATEGORY  add PROVINCE  VARCHAR2(32)';
  end if;
  commit;
end;
/|/
/* 20170328_mfl_创建事务级临时表（针对贾总已办sql过长解决方法）*/
declare i integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_HITASKS';
 if i = 0 then
 execute immediate '
   create global temporary table ACT_HQ_HITASKS
(
 business_key_ NVARCHAR2(255),
 name_ NVARCHAR2(255),
 task_def_key_ NVARCHAR2(255),
 description_ NVARCHAR2(2000)
)
On Commit Delete Rows
';
 end if;
 commit;
end;
/|/

/* 20170329_mfl_2.3.2.6L02_创建索引优化批量更新操作性能*/
declare i integer;
begin
 select count(*) into i from user_indexes where index_name='ACT_IDX_HI_ACT_INST_TASKID' ;
 if i = 0 then 
  execute immediate 'create index ACT_IDX_HI_ACT_INST_TASKID on act_hi_actinst(task_id_)'; 
 end if;
commit;
end;
/|/

/* 20170421_dh_2.3.2.7XTL01_修改协同办公产品字段值过小实际值为36*/
declare i integer;
begin
select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TEM_CATEGORY';
 if i = 1 then
  execute immediate 'alter table ACT_HQ_TEM_CATEGORY modify PARENTID VARCHAR2(255)';
 end if;
commit;
end;


/|/
/* 20170512_mfl_2.3.2.7L04_创建索引优化曾经办sql性能*/
declare i integer;
begin
 select count(*) into i from user_indexes where index_name='ACT_IDX_HI_PROINST_BUSKEYPIID' ;
 if i = 0 then 
  execute immediate 'create index ACT_IDX_HI_PROINST_BUSKEYPIID on ACT_HI_PROCINST (BUSINESS_KEY_, PROC_INST_ID_)'; 
 end if;
commit;
end;
/|/

/* 20170920_lxq_创建索引优化流程日志查询comments sql性能*/
declare i integer;
begin
 select count(*) into i from user_indexes where index_name='HQ_ACT_HI_COMMENT_TASKIDTYPE' ;
 if i = 0 then 
  execute immediate 'create index HQ_ACT_HI_COMMENT_TASKIDTYPE on ACT_HI_COMMENT (TASK_ID_)'; 
 end if;
commit;
end;
/|/

/* 20171202_dh_节点默认人功能扩展字段*/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TASK_EXTEND' AND COLUMN_NAME = 'IS_DEFAULT_USER';
  if i=0
    then execute 
      immediate 'alter table ACT_HQ_TASK_EXTEND  add IS_DEFAULT_USER  NUMBER(10)';
  end if;
  commit;
end;
/|/





-------------------------------------------------20181010-------------------------------------------------
/* 2018.8.29 LW 独立部署工作流模板扩展字段*/
/*添加系统标识*/
  /*添加系统标识APP_ID_ 业务系统模板部署标识DEPLOYMENT_STATE_ 部署ID字段*/
declare i integer;
begin
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_DEF' AND COLUMN_NAME = 'APP_ID_';
  if i=0
    then execute immediate 'ALTER TABLE ACT_HQ_TEM_DEF  ADD (APP_ID_ varchar(32))';
  end if;
  SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_DEF' AND COLUMN_NAME = 'DEPLOYMENT_ID_';
  if i=0
    then execute immediate 'ALTER TABLE ACT_HQ_TEM_DEF  ADD (DEPLOYMENT_ID_  varchar(255))';
  end if;
   SELECT COUNT(*) INTO i FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'ACT_HQ_TEM_DEF' AND COLUMN_NAME = 'DEPLOYMENT_STATE_';
  if i=0
    then execute immediate 'ALTER TABLE ACT_HQ_TEM_DEF  ADD (DEPLOYMENT_STATE_ INTEGER)';
  end if;
  commit;
end;
/|/

declare i integer;
begin
select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_PIPDDEPLYANALY';
 if i = 0 then
  execute immediate '
create table act_hq_pipddeplyanaly
(
   proc_inst_id_      NVARCHAR2(64),
   proc_def_Id_       NVARCHAR2(64),
   deployment_id_     NVARCHAR2(64),
   proc_inst_status_  NUMBER(1),
   app_id_            NVARCHAR2(64),
   year_              char(4)
)';
 end if;
 
 end;
 
/|/

 
declare i integer;
begin
select count(*) into i from user_tables t where t.table_name = 'ACT_HQ_TASKANALY';
 if i = 0 then
  execute immediate '
create table act_hq_taskanaly
(
   proc_inst_id_      NVARCHAR2(64),
   proc_def_id_       NVARCHAR2(64),
   deployment_id_      NVARCHAR2(64),
   assignee_          NVARCHAR2(64),
   act_id_            NVARCHAR2(255),
   act_name_          NVARCHAR2(255),
   task_type_         NUMBER(1),
   start_time_        TIMESTAMP(6),
   end_time_          TIMESTAMP(6),
   duration_          NUMBER(20),
   app_id_            NVARCHAR2(64),
   year_              CHAR(4)
)';
 end if;
 
 end;
 



