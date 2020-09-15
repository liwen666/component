

-- 解决脏数据1、 parent_id为空的数据
update  meta_category set parent_id=0 where parent_id is null;

-- 系统初始化数据源 code 设置为默认的数据源  code
update `meta_data_source_info`  set source_code = 'System_GP_DataSource'  where db_type='GREENPLUM';

-- 解决脏数据2、data_source_id 为null  设置为系统初始化数据源的Id
update meta_data_object_info set data_source_id=(SELECT data_source_id FROM `meta_data_source_info` where db_type='GREENPLUM') where data_source_id=1 or data_source_id is null;
--

-- 解决脏数据3、    resource_object_id = 1 的脏数据

--  有问题，系统字段和 有些字段设置resource_object_id 有问题。
-- UPDATE meta_object_field c JOIN (SELECT a.resource_object_id,b.resource_id from  meta_object_field a
-- JOIN (SELECT resource_id,strategy_id FROM res_strategy WHERE strategy_id in (SELECT resource_object_version_id FROM meta_object_field WHERE field_code like 'NODE%')) b
-- on a.field_code like 'NODE%' and
-- a.resource_object_version_id = b.strategy_id) d on c.resource_object_id = d.resource_object_id set c.resource_object_id = d.resource_id;

UPDATE meta_object_field sd JOIN res_strategy sy   on sd.resource_object_version_id = sy.strategy_id
 set sd.resource_object_id = sy.resource_id where sd.resource_object_id = '1' and sd.field_type != 'SYS_FIELD' and sd.field_code like 'NODE%' ;

-- 解决脏数据4、   更新object_type
update meta_object_field set object_type = 'NODE_FEATURE' WHERE field_code like 'NODE%';

-- 解决脏数据5、  resource_object_id = 1 的问题，该 resource_object_version_id 不存在，需要重新给该resource_object_versio_id 赋值。

update meta_object_field sd join res_strategy_node sn on locate(
        CONCAT( sd.object_field_id, '",' ),
        sn.node_content
    ) != 0
        set sd.resource_object_version_id = sn.strategy_id where sd.resource_object_id = 1
        AND sd.field_type != 'SYS_FIELD'
        AND sd.field_code LIKE 'NODE%'  and sd.object_field_id != '7276';


-- 重新执行步骤三，因为这些resource_object_version_id  已经修复，需要重新设置 resource_object_id

UPDATE meta_object_field sd JOIN res_strategy sy   on sd.resource_object_version_id = sy.strategy_id
 set sd.resource_object_id = sy.resource_id where sd.resource_object_id = '1' and sd.field_type != 'SYS_FIELD' and sd.field_code like 'NODE%' ;





-- 删除字段的code 重复数据的，需要手工验证删除。

-- 生产环境 清理脏数据
-- 删除资源管理设置
DELETE FROM res_resource_set_item WHERE project_id = 4645 and resource_type = 'STAT_OBJECT' and resource_id = 2133;

-- 生产环境 清理脏数据
-- 更新数据集版本的字段field_ids
UPDATE meta_data_object a JOIN
(select GROUP_CONCAT(object_field_id separator ',') field_id from meta_object_field WHERE resource_object_id = 1281 GROUP BY resource_object_id) b
on a.resource_id = 1281 set a.field_ids = b.field_id;


-- 查询字段 重复数

        -- 查找策略字段是否有重复的code字段。

		SELECT
			count( 1 ) num,
			field_code,
			resource_object_id,
			max( object_field_id ) object_field_id
		FROM
			meta_object_field sd
		WHERE
			1 = 1
			AND object_type = 'NODE_FEATURE'
			AND EXISTS (
			SELECT
				1
			FROM
				res_strategy_node aa
				JOIN res_strategy sy ON aa.strategy_id = sy.strategy_id
			WHERE
				locate( sd.object_field_id, aa.node_content ) != 0
				AND sy.publish_mode = 'ONLINE'
			)
		GROUP BY
			field_code,
			resource_object_id;


        -- 查询策略的
        -- 注意locate 问题。
        select * from res_strategy_node sd join res_strategy rs on sd.strategy_id = rs.strategy_id  where locate('6787',sd.node_content) != 0 and rs.publish_mode = 'ONLINE';


delete from meta_object_field  where object_field_id = '6786';
delete from meta_object_field  where object_field_id = '6787';

-- 更新字段
UPDATE meta_object_field c
JOIN (
	SELECT
		*
	FROM
		res_strategy s
	WHERE
		 s.publish_mode = 'ONLINE'
	) a  on 		c.resource_object_id = a.resource_id
	SET c.resource_object_version_id = a.strategy_id
WHERE
	c.object_field_id IN (
	SELECT
		object_field_id
	FROM
		(
		SELECT
			count( 1 ) num,
			field_code,
			resource_object_id,
			max( object_field_id ) object_field_id
		FROM
			meta_object_field sd
		WHERE
			1 = 1
			AND object_type = 'NODE_FEATURE'
			AND EXISTS (
			SELECT
				1
			FROM
				res_strategy_node aa
				JOIN res_strategy sy ON aa.strategy_id = sy.strategy_id
			WHERE
				locate( sd.object_field_id, aa.node_content ) != 0
				AND sy.publish_mode = 'ONLINE'
			)
		GROUP BY
			field_code,
			resource_object_id
		) k
	WHERE
	k.num < 2
	);

delete from meta_function_info where resource_id in (select resource_id from meta_function where function_type='AGGREGATE_FUNCTION');
delete from meta_function where function_type='AGGREGATE_FUNCTION';


DELETE FROM meta_object_field WHERE object_field_id = 17549;


-- 解决策略分组 code重复的问题，暂时改code，存在新建的bug，解决完bug，考虑把重复的分组删除。

update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_1' where resource_id = '10228';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_2' where resource_id = '27111';
update meta_topic_object_info set code = 'DAY_900_tradeTime_7782_linealfamilyMobile_1' where resource_id = '27284';
update meta_topic_object_info set code = 'DAY_900_tradeTime_7782_noLinealfamilyMobile_1' where resource_id = '27243';
update meta_topic_object_info set code = 'DAY_12_icEffectiveDate_39468_icNumber_1' where resource_id = '40811';

-- 策略变更
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_1' where resource_id = '7679';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_2' where resource_id = '7688';
update meta_topic_object_info set code = 'DAY_tradeTime_7782_noLinealfamilyMobile_1' where resource_id = '27247';
update meta_topic_object_info set code = 'DAY_tradeTime_7782_noLinealfamilyMobile_2' where resource_id = '27265';
update meta_topic_object_info set code = 'DAY_tradeTime_7782_noLinealfamilyMobile_3' where resource_id = '27271';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_3' where resource_id = '7688';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_4' where resource_id = '27111';
-- 解决策略分组字段 的分类Id  存的是策略的resource_id 的 问题。需要慧琴把这个bug先解决掉。
update meta_object_field set resource_object_category_id = null  where object_type='STRATEGY_FIELD';

-- 解决 实体、统计、策略分组的归属datasource_id  为系统Greenplum的dataSource_id

update meta_topic_object_info set data_source_id=(SELECT data_source_id FROM `meta_data_source_info` where db_type='GREENPLUM') ;

update meta_object_field set  resource_object_id=5699 , resource_object_version_id=39529 where object_field_id=7276;
--   下面的字段可能有问题
-- update meta_object_field set resource_object_version_id=38926 where object_field_id=9043
-- update meta_object_field set resource_object_version_id=42538 where object_field_id=9043

delete from meta_object_field where object_field_id=17549;
