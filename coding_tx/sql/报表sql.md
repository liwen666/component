#报表sql日志

SELECT COUNT(*),sp.belong_uid FROM schedule_plan  sp GROUP BY sp.belong_uid

##查询计划的执行次数，并分组
SELECT sp.plan_name ,plan.* FROM schedule_plan sp ,(SELECT COUNT(*) plan_execution_count ,spe.`plan_id`  FROM schedule_plan_execution spe GROUP BY spe.plan_id
) plan WHERE sp.plan_id= plan.plan_id