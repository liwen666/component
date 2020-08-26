
#查询数据库的所有表的主键

select table_schema, table_name,column_name from  INFORMATION_SCHEMA.KEY_COLUMN_USAGE  t where t.table_schema='anyest3_financial_cloud_15029'


#查询表字段类型
（1）DESC 表名；

（2）DESCRIBE 表名；

（3）SHOW COLUMNS FROM 表名；


1、查找表中多余的重复记录，重复记录是根据单个字段（peopleId）来判断


SELECT
    *
FROM
    meta_object_field
WHERE
    field_code IN (
        SELECT
            field_code
        FROM
            meta_object_field
        GROUP BY
            field_code
        HAVING
            count(field_code) > 1
    )
2、删除表中多余的重复记录，重复记录是根据单个字段（peopleId）来判断，只留有rowid最小的记录
DELETE
FROM
    people
WHERE
    peopleName IN (
        SELECT
            peopleName
        FROM
            people
        GROUP BY
            peopleName
        HAVING
            count(peopleName) > 1
    )
AND peopleId NOT IN (
    SELECT
        min(peopleId)
    FROM
        people
    GROUP BY
        peopleName
    HAVING
        count(peopleName) > 1
)

3、查找表中多余的重复记录（多个字段）








SELECT
    *
FROM
    meta_object_field a
WHERE
    (a.field_code, a.resource_object_id) IN (
        SELECT
            a.field_code, a.resource_object_id
        FROM
            meta_object_field
        GROUP BY
                       a.field_code, a.resource_object_id

        HAVING
            count(*) > 1
    )
    
    
  #查询需要删除的记录
  select a.object_field_id
  FROM
      meta_object_field a
  WHERE
      (a.field_code, a.resource_object_id) IN (
          SELECT
              field_code, resource_object_id
          FROM
              meta_object_field 
          GROUP BY
                         field_code, resource_object_id
  
          HAVING
            
  					count(*) > 1
      )
  		
  AND a.object_field_id NOT IN (
      SELECT
          min(object_field_id)
      FROM
          meta_object_field
      GROUP BY
                                 field_code, resource_object_id
  
      HAVING
          count(*) > 1
  )  
    
 4、删除表中多余的重复记录（多个字段），只留有rowid最小的记录
 
 
create table temp_relation (
select a.object_relation_id
  FROM
      meta_topic_object_relation a
  WHERE
      (a.primary_resource_id,a.relation_object_id,a.topic_object_info_id) IN (
          SELECT
             primary_resource_id,relation_object_id,topic_object_info_id
          FROM
              meta_topic_object_relation 
          GROUP BY
             primary_resource_id,relation_object_id,topic_object_info_id
  
          HAVING
            
  					count(*) > 1
      )
  		
  AND a.object_relation_id NOT IN (
      SELECT
          min(object_relation_id)
      FROM
          meta_topic_object_relation
      GROUP BY
             primary_resource_id,relation_object_id,topic_object_info_id
  
      HAVING
          count(*) > 1
  ) );
	
DELETE from 	meta_topic_object_relation WHERE object_relation_id in (select object_relation_id from temp_relation );
drop table temp_relation;
    
  #定位数据是否包含id
    select * from res_strategy_node a where strategy_id = '23208'  and locate('9036', a.node_content)!=0
    
   # 清理垃圾数据
   
   
   
select resource_object_id from  meta_object_field  where meta_object_field.resource_object_id  not in  (select resource_id  from meta_data_object_info  ) and meta_object_field.resource_object_id not in(select meta_model_object_info.resource_id from meta_model_object_info) and meta_object_field.resource_object_id not in(select meta_topic_object_info.resource_id from meta_topic_object_info)  and meta_object_field.resource_object_id not in(select res_strategy_info.resource_id from res_strategy_info)  ORDER BY resource_object_id desc
    
    
    #创建临时表删除数据
    create table  temp (
    select resource_object_id from  meta_object_field  where meta_object_field.resource_object_id  not in  (select resource_id  from meta_data_object_info  ) and meta_object_field.resource_object_id not in(select meta_model_object_info.resource_id from meta_model_object_info) and meta_object_field.resource_object_id not in(select meta_topic_object_info.resource_id from meta_topic_object_info)  and meta_object_field.resource_object_id not in(select res_strategy_info.resource_id from res_strategy_info) )


#清理垃圾数据


select a.*,b.* from  meta_object_field a   join  
(select  strategy_id,resource_Id from res_strategy  where strategy_id in( select resource_object_version_id from meta_object_field where resource_object_id=1)) b on a.resource_object_version_id= b.strategy_id


update  meta_object_field a,(select  strategy_id,resource_Id from res_strategy  where strategy_id in( select resource_object_version_id from meta_object_field where resource_object_id=1)) b set a.resource_object_id=b.resource_id  where   
  a.resource_object_version_id= b.strategy_id
