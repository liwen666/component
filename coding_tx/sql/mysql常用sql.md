
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


#查询分组数大于1的数据id
select object_field_id  from meta_object_field where object_field_id in (select object_field_id from meta_object_field GROUP BY field_code,resource_object_id HAVING count(1)>1)


select *  from meta_object_field a , (select  field_code,resource_object_id from meta_object_field GROUP BY field_code,resource_object_id HAVING count(1)>1) b where a.field_code=b.field_code and a.resource_object_id=b.resource_object_id

#查找json串删除数据

##删除所有未被引用的数据
delete from meta_object_field where field_code like '%NODE%' and object_field_id not in (
select DISTINCT object_field_id from ( select  object_field_id  from meta_object_field a , (select  field_code,resource_object_id from meta_object_field GROUP BY field_code,resource_object_id HAVING count(1)>1) b where a.field_code=b.field_code and a.resource_object_id=b.resource_object_id and a.field_code like '%NODE%') c ,res_strategy_node d where locate(c.object_field_id,d.node_content)>0)
##指定策略删除重复数据
delete from meta_object_field where field_code like '%NODE%' and object_field_id not in (
select DISTINCT object_field_id from ( select  object_field_id  from meta_object_field a , (select  field_code,resource_object_id from meta_object_field GROUP BY field_code,resource_object_id HAVING count(1)>1) b where a.field_code=b.field_code and a.resource_object_id=b.resource_object_id and a.field_code like '%NODE%') c ,res_strategy_node d where locate(c.object_field_id,d.node_content)>0 and d.strategy_id=105635)



#列转行
SELECT  
studyCode 学号,
SUM(CASE WHEN subjectS = '国学' THEN  score ELSE 0 END) 国学,
SUM(CASE WHEN subjectS = '数学' THEN  score ELSE 0 END) 数学,
SUM(CASE WHEN subjectS = '英语' THEN  score ELSE 0 END) 英语
   FROM grade
GROUP BY studyCode;


#分组并对某一列的值做拼接
select a.object_field_id resource_object_id, GROUP_CONCAT(a.object_field_id SEPARATOR ',') 对象 from meta_object_field a  group by a.resource_object_id