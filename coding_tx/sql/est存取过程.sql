


DELIMITER $$
DROP PROCEDURE IF EXISTS `est_data_init`$$
CREATE  PROCEDURE `est_data_init`(IN content_code INTEGER)
BEGIN
    DECLARE num INTEGER ;
   
    SELECT COUNT(1) INTO num  FROM meta_dictionary_field;
    IF num=0 THEN
    INSERT  INTO `meta_dictionary_field`(`dict_id`,`dict_name`,`dict_type`,`dict_value`,`field_id`) VALUES 

(6,'通过',NULL,'0000',5),

(7,'拒绝',NULL,'0100',5),

(8,'告警',NULL,'0200',5);
END IF;
    SELECT COUNT(1) INTO num  FROM meta_model_object  ;
    IF num=0 THEN
    
INSERT  INTO `meta_model_object`(`model_object_id`,`content_code`,`create_time`,`field_ids`,`resource_id`,`resource_type`,`update_person`,`update_time`,`version`,`version_code`,`version_state`,`parent_object_id`,`used`) VALUES 

(2,'0','2020-06-12 20:49:20',NULL,1,'SYSTEM_OBJECT',NULL,NULL,1,'sys_default','ONLINE',NULL,'\0');

END IF;

  SELECT COUNT(1) INTO num  FROM hibernate_sequence  ;
    IF num=0 THEN
insert  into `hibernate_sequence`(`next_val`) values

(85);


END IF;


 SELECT COUNT(1) INTO num  FROM table_entity_conversion_rule  ;
    IF num=0 THEN

insert  into `table_entity_conversion_rule`(`id`,`content_code`,`create_person`,`create_time`,`update_person`,`update_time`,`data_conversion_model`,`entity_key`,`entity_value`,`table_code`,`table_name`) values

(11,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_data_source_info','数据源表'),

(12,NULL,NULL,NULL,NULL,NULL,'CATEGORY','categoryId',NULL,'meta_data_source_info','数据源表'),

(13,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_category','分类表'),

(14,NULL,NULL,NULL,NULL,NULL,'CATEGORY','parentId',NULL,'meta_category','分类表'),

(15,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_object_field','字段表'),

(16,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.statisticFieldBid',NULL,'meta_object_field','字段表'),

(17,NULL,NULL,NULL,NULL,NULL,'FIELD','referFieldIds',NULL,'meta_object_field','字段表'),

(18,NULL,NULL,NULL,NULL,NULL,'OBJECT','resourceObjectId',NULL,'meta_object_field','字段表'),

(19,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.originField.bid',NULL,'meta_object_field','字段表'),

(20,NULL,NULL,NULL,NULL,NULL,'FIELD','deriveContent.originField.fieldId',NULL,'meta_object_field','字段表'),

(21,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.valueConditions.expressionUnits.code',NULL,'meta_object_field','字段表'),

(22,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.valueConditions.conditionGroup.children.fieldBid',NULL,'meta_object_field','字段表'),

(23,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.valueConditions.referFieldBids',NULL,'meta_object_field','字段表'),

(24,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.expressionUnits.code',NULL,'meta_object_field','字段表'),

(25,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.referFieldBids',NULL,'meta_object_field','字段表'),

(26,NULL,NULL,NULL,NULL,NULL,'FIELD_INDEX','deriveContent.params.value',NULL,'meta_object_field','字段表'),

(27,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_model_object_info','模型表'),

(28,NULL,NULL,NULL,NULL,NULL,'CATEGORY','categoryId',NULL,'meta_model_object_info','模型表'),

(29,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_data_object_info','模型表'),

(30,NULL,NULL,NULL,NULL,NULL,'CATEGORY','categoryId',NULL,'meta_data_object_info','模型表'),

(31,NULL,NULL,NULL,NULL,NULL,'DATASOURCE','dataSourceId',NULL,'meta_data_object_info','模型表'),

(32,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_topic_object_info','模型表'),

(33,NULL,NULL,NULL,NULL,NULL,'CATEGORY','categoryId',NULL,'meta_topic_object_info','模型表'),

(34,NULL,NULL,NULL,NULL,NULL,'DATASOURCE','dataSourceId',NULL,'meta_topic_object_info','模型表'),

(35,NULL,NULL,NULL,NULL,NULL,'OBJECT_VERSION','primaryDataObjectId',NULL,'meta_topic_object_info','模型表'),

(36,NULL,NULL,NULL,NULL,NULL,'FIELD','primaryDimensionId',NULL,'meta_topic_object_info','模型表'),

(37,NULL,NULL,NULL,NULL,NULL,'FIELD','primaryFieldId',NULL,'meta_topic_object_info','模型表'),

(38,NULL,NULL,NULL,NULL,NULL,'FIELD','primaryKeyFieldIds',NULL,'meta_topic_object_info','模型表'),

(39,NULL,NULL,NULL,NULL,NULL,'FIELD','secondDimensionIds',NULL,'meta_topic_object_info','模型表'),

(40,NULL,NULL,NULL,NULL,NULL,'FIELD','statTimeFieldId',NULL,'meta_topic_object_info','模型表'),

(41,NULL,NULL,NULL,NULL,NULL,'OBJECT','primaryResourceId',NULL,'meta_topic_object_info','模型表'),

(42,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_model_object','模型对象表'),

(43,NULL,NULL,NULL,NULL,NULL,'OBJECT','resourceId',NULL,'meta_model_object','模型对象表'),

(44,NULL,NULL,NULL,NULL,NULL,'FIELD','fieldIds',NULL,'meta_model_object','模型对象表'),

(45,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_data_object','模型表'),

(46,NULL,NULL,NULL,NULL,NULL,'OBJECT','resourceId',NULL,'meta_data_object','模型表'),

(47,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_topic_object','模型表'),

(48,NULL,NULL,NULL,NULL,NULL,'OBJECT','resourceId',NULL,'meta_topic_object','模型表'),

(49,NULL,NULL,NULL,NULL,NULL,'FIELD','deriveIds',NULL,'meta_topic_object','模型表'),

(50,NULL,NULL,NULL,NULL,NULL,'ORG','15029','金融云','org_rule','机构编码字典'),

(51,NULL,NULL,NULL,NULL,NULL,'ORG','0','系统机构','org_rule','机构编码字典'),

(52,NULL,NULL,NULL,NULL,NULL,'ORG','132','租户1','org_rule','机构编码字典'),

(53,NULL,NULL,NULL,NULL,NULL,'ORG','149','租户2','org_rule','机构编码字典'),

(54,NULL,NULL,NULL,NULL,NULL,'ORG','2401550','租户3','org_rule','机构编码字典'),

(55,NULL,NULL,NULL,NULL,NULL,'ORG','2754918','租户4','org_rule','机构编码字典'),

(56,NULL,NULL,NULL,NULL,NULL,'ORG','28260','租户5','org_rule','机构编码字典'),

(57,NULL,NULL,NULL,NULL,NULL,'ORG','635','租户6','org_rule','机构编码字典'),

(58,NULL,NULL,NULL,NULL,NULL,'ORG','851479','租户7','org_rule','机构编码字典'),

(59,NULL,NULL,NULL,NULL,NULL,'ORG','2942275','租户8','org_rule','机构编码字典'),

(60,NULL,NULL,NULL,NULL,NULL,'ORG','2820972','租户9','org_rule','机构编码字典'),

(61,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'res_resource_project','项目表'),

(62,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_function_info','函數表'),

(63,NULL,NULL,NULL,NULL,NULL,'CATEGORY','categoryId',NULL,'meta_function_info','函數表'),

(64,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_function','函數版本表'),

(65,NULL,NULL,NULL,NULL,NULL,'FUNCTION','resourceId',NULL,'meta_function','函數版本表'),

(66,NULL,NULL,NULL,NULL,NULL,'ORG','contentCode',NULL,'meta_topic_object_relation','模型关联关系表'),

(67,NULL,NULL,NULL,NULL,NULL,'OBJECT_VERSION','primaryDataObjectId',NULL,'meta_topic_object_relation','模型关联关系表'),

(68,NULL,NULL,NULL,NULL,NULL,'FIELD','primaryFieldIds',NULL,'meta_topic_object_relation','模型关联关系表'),

(69,NULL,NULL,NULL,NULL,NULL,'FIELD','relationFieldIds',NULL,'meta_topic_object_relation','模型关联关系表'),

(70,NULL,NULL,NULL,NULL,NULL,'OBJECT','relationObjectId',NULL,'meta_topic_object_relation','模型关联关系表'),

(71,NULL,NULL,NULL,NULL,NULL,'OBJECT','topicObjectInfoId',NULL,'meta_topic_object_relation','模型关联关系表'),

(77,NULL,NULL,NULL,NULL,NULL,'FIELD','fieldIds',NULL,'meta_data_object','模型表');
END IF;

    SELECT COUNT(1) INTO num  FROM meta_model_object_info   ;
    IF num=0 THEN

insert  into `meta_model_object_info`(`resource_id`,`category_id`,`code`,`content_code`,`create_time`,`description`,`name`,`project_id`,`resource_state`,`resource_type`,`update_person`,`update_time`,`object_type`) values

(1,NULL,'sys_default','0','2020-06-12 20:49:19',NULL,'系统对象',NULL,NULL,'SYSTEM_OBJECT',NULL,NULL,'SYSTEM');

END IF;
    SELECT COUNT(1) INTO num  FROM meta_category where content_code=content_code   ;
    IF num=0 THEN


insert  into `meta_category`(`category_id`,`content_code`,`create_person`,`create_time`,`update_person`,`update_time`,`category_type`,`name`,`parent_id`,`project_id`,`used`) values

(72,content_code,NULL,'2020-06-12 20:49:32','lsw','2020-06-12 20:49:32','DATASOURCE','默认',0,NULL,'\0'),

(73,content_code,NULL,'2020-06-12 20:49:32','lsw','2020-06-12 20:49:32','OBJECT_MODEL','默认',0,NULL,'\0'),

(74,content_code,NULL,'2020-06-12 20:49:32','lsw','2020-06-12 20:49:32','OBJECT_EVENT','默认',0,NULL,'\0'),

(75,content_code,NULL,'2020-06-12 20:49:32','lsw','2020-06-12 20:49:32','COLLECT_DATA','默认',0,NULL,'\0'),

(76,content_code,NULL,'2020-06-12 20:49:32','lsw','2020-06-12 20:49:32','DATASOURCE','系统数据源',0,NULL,'\0'),

(78,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','RULE','默认',0,98,'\0'),

(79,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','RULESET','默认',0,98,'\0'),

(80,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','SCORECARD','默认',0,98,'\0'),

(81,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','SCRIPT','默认',0,98,'\0'),

(82,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','RULETREE','默认',0,98,'\0'),

(83,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','MATRIX','默认',0,98,'\0'),

(84,content_code,NULL,'2020-06-12 20:59:07','lsw','2020-06-12 20:59:07','STRATEGY','默认',0,98,'\0');
END IF;



  SELECT COUNT(1) INTO num  FROM meta_object_field  ;
    IF num=0 THEN

insert  into `meta_object_field`(`object_field_id`,`content_code`,`create_person`,`create_time`,`update_person`,`update_time`,`column_code`,`compute_period`,`data_object_id`,`default_value`,`derive_content`,`description`,`field_code`,`field_format`,`field_length`,`field_name`,`field_state`,`field_type`,`is_dimension`,`is_key`,`is_target`,`list_value_type`,`object_type`,`object_versions`,`refer_field_ids`,`resource_object_category_id`,`resource_object_id`,`resource_object_version_id`,`scale_length`,`source_field_id`,`sql_fragment`,`update_mode`,`value_type`) values

(3,'0',NULL,'2020-06-12 20:49:20','system','2020-06-12 20:49:20','eventNo','NONE',NULL,NULL,NULL,NULL,'eventNo',NULL,NULL,'事件编号','ACTIVE','SYS_FIELD',NULL,'\0',NULL,NULL,'SYSTEM',NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,'STRING'),

(4,'0',NULL,'2020-06-12 20:49:20','system','2020-06-12 20:49:20','uuid','NONE',NULL,NULL,NULL,NULL,'uuid',NULL,NULL,'用户唯一标识','ACTIVE','SYS_FIELD',NULL,'\0',NULL,NULL,'SYSTEM',NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,'STRING'),

(5,'0',NULL,'2020-06-12 20:49:20','system','2020-06-12 20:49:20','strategy_result','NONE',NULL,NULL,NULL,NULL,'strategy_result',NULL,NULL,'决策结果','ACTIVE','SYS_FIELD',NULL,'\0',NULL,NULL,'SYSTEM',NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,'DICTIONARY'),

(9,'0',NULL,'2020-06-12 20:49:20','system','2020-06-12 20:49:20','dateTime_now','NONE',NULL,NULL,NULL,NULL,'dateTime_now',NULL,NULL,'当前时间','ACTIVE','SYS_FIELD',NULL,'\0',NULL,NULL,'SYSTEM',NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,'DATE'),

(10,'0',NULL,'2020-06-12 20:49:20','system','2020-06-12 20:49:20','anyTaskCode','NONE',NULL,NULL,NULL,NULL,'anyTaskCode',NULL,NULL,'anyTask订单标识（用于异步决策）','ACTIVE','SYS_FIELD',NULL,'\0',NULL,NULL,'SYSTEM',NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,'STRING');


END IF;

END$$

DELIMITER ;

