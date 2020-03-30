
#  查询数据库表字段
select column_name as fieldCode  from information_schema.columns where table_schema !='information_schema' and table_schema = 'public' and TABLE_NAME = 'stat_dev_test'  order by table_schema,table_name,ordinal_position


-- 处理日期类型的字段转换问题
create cast (varchar as timestamp) with inout as ASSIGNMENT;

create cast (varchar as timestamptz) with inout as ASSIGNMENT;

create cast (varchar as date) with inout as ASSIGNMENT;

select sum(case province when '北京市' then 1.0 else 0  end)/COUNT(id) from ods_ops_dash_map_test




运算表达式

数学操作符
编号	操作符	描述	栗子	结果
001	+	加	select 32+90	122
002	-	减	select 100-23	77
003	*	乘	select 5*3	15
004	/	除(整数除法会截断结果)	select 9/3     select 9/4	3   2
005	%	模(求余)	select 9%4	1
006	^	冥(指数运算)	select 3^2	9
007	|/	平方根	select |/ 36	6
008	||/	立方根	select ||/ 27	3
009	！	阶乘	select 6 !	720
010	!!	阶乘（前缀操作符）	select  !! 6	720
011	@	绝对值	select @(-2)	2
012	&	二进制 AND	select 91 & 15	11
013	|	二进制 OR	select 32 | 3	35
014	#	二进制 XOR	select 32 # 3	35
015	~	二进制 NOT	select ~1	-2
016	<<	二进制 左移	select 1<<4	16
017	>>	二进制右移	select 8 >> 2	2