
对结果计算求和  iif  想到与case   web
SUM(IIF(决策结果='0000',1,0))/COUNT(事件决策数据唯一id)


#case  when  sum  用法
select sum(case province when '北京市' then 1 else 0  end) from ods_ops_dash_map_test





