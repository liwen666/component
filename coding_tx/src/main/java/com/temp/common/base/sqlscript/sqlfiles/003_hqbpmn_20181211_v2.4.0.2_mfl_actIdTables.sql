/**

 */
declare i integer;
         v integer;
begin
 select count(*) into i from user_tables t where t.table_name = 'ACT_ID_USER';
 select count(*) into v from user_views t where t.VIEW_NAME = 'ACT_ID_USER';
 if i = 0 and v = 0 then
  execute immediate 'create table ACT_ID_USER
(
  ID_         NVARCHAR2(64) not null,
  REV_        INTEGER,
  FIRST_      NVARCHAR2(255),
  LAST_       NVARCHAR2(255),
  EMAIL_      NVARCHAR2(255),
  PWD_        NVARCHAR2(255),
  PICTURE_ID_ NVARCHAR2(64),
  STATUS_     VARCHAR2(10) default 1,
  STATE_      VARCHAR2(10) default 1,
  PROVINCE    VARCHAR2(32)
)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''a'', 2, ''The A'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''b'', 2, ''B'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''c'', 2, ''C'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''k'', 2, ''K'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''gonzo'', 2, ''Gonzo'', ''The Great'', ''gonzo@activiti.org'', ''gonzo'', ''18'', ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''kermit'', 2, ''Kermit'', ''The Frog'', ''kermit@activiti.org'', ''kermit'', ''7'', ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''fozzie'', 2, ''Fozzie'', ''Bear'', ''fozzie@activiti.org'', ''fozzie'', ''22'', ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''d'', 2, ''D'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''e'', 2, ''E'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''f'', 2, ''F'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''g'', 2, ''G'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''h'', 2, ''H'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''i'', 2, ''I'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''j'', 2, ''J'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''l'', 2, ''L'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''m'', 2, ''M'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''n'', 2, ''N'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''o'', 2, ''O'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''p'', 2, ''P'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''q'', 2, ''Q'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''r'', 2, ''R'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''s'', 2, ''S'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''t'', 2, ''T'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''u'', 2, ''U'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''v'', 2, ''V'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''w'', 2, ''W'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''y'', 2, ''Y'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''z'', 2, ''Z'', null, null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''wl'', 2, ''WL'', ''The Wang'', null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''wll'', 2, ''WLL'', ''The Li'', null, null, null, ''1'', ''1'', null)';

execute immediate 'insert into act_id_user (ID_, REV_, FIRST_, LAST_, EMAIL_, PWD_, PICTURE_ID_, STATUS_, STATE_, PROVINCE)
values (''mfl'', 2, ''mfl'', null, null, null, null, ''1'', ''1'', null)';
 end if;
 select count(*) into i from user_tables t where t.table_name = 'ACT_ID_GROUP';
  select count(*) into v from user_views t where t.VIEW_NAME = 'ACT_ID_GROUP';
 if i = 0 and v = 0 then
  execute immediate 'create table ACT_ID_GROUP
(
  ID_       NVARCHAR2(64) not null,
  REV_      INTEGER,
  NAME_     NVARCHAR2(255),
  TYPE_     NVARCHAR2(255),
  PID_      NVARCHAR2(64),
  CATEGORY_ NVARCHAR2(64),
  ORDERCODE NUMBER(18),
  ORGCODE   VARCHAR2(200),
  PROVINCE  VARCHAR2(32)
)';
execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''user'', 1, ''User'', ''2'', null, null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''test'', 1, ''Test'', ''2'', null, null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''role'', 1, ''Role'', ''2'', null, null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''dep1'', 1, ''Dep1'', ''1'', ''0'', null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''dep2'', 1, ''Dep2'', ''1'', ''dep1'', null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''dep3'', 1, ''Dep3'', ''1'', ''dep1'', null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''manager'', 1, ''Manager'', ''2'', null, null, null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''111'', 1, ''111'', ''3'', null, ''qwer'', null, null, null)';

execute immediate 'insert into act_id_group (ID_, REV_, NAME_, TYPE_, PID_, CATEGORY_, ORDERCODE, ORGCODE, PROVINCE)
values (''222'', 1, ''222'', ''3'', null, ''qwer'', null, null, null)';

end if;
 select count(*) into i from user_tables t where t.table_name = 'ACT_ID_MEMBERSHIP';
 select count(*) into v from user_views t where t.VIEW_NAME = 'ACT_ID_GROUP';

 if i = 0 and v = 0 then
  execute immediate 'create table ACT_ID_MEMBERSHIP
(
  USER_ID_  NVARCHAR2(64) not null,
  GROUP_ID_ NVARCHAR2(64) not null
)';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''a'', ''dep2'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''a'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''b'', ''dep2'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''b'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''c'', ''dep2'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''c'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''d'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''d'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''e'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''e'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''f'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''f'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''fozzie'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''fozzie'', ''user'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''g'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''g'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''gonzo'', ''dep1'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''gonzo'', ''user'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''h'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''h'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''i'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''i'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''j'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''j'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''k'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''k'', ''role'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''kermit'', ''dep2'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''kermit'', ''user'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''l'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''l'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''m'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''m'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''n'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''n'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''o'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''o'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''p'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''p'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''q'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''q'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''r'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''r'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''s'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''s'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''t'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''t'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''u'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''u'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''v'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''v'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''w'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''w'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''y'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''y'', ''manager'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''y'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''z'', ''dep3'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''z'', ''manager'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''z'', ''test'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''wl'', ''dep1'')';

execute immediate 'insert into act_id_membership (USER_ID_, GROUP_ID_)
values (''wll'', ''dep1'')';

 end if;
commit;
end;

