#添加表
create table employees  
(  
    employee_id int(11) primary key not null auto_increment,  
    employee_name varchar(50) not null,  
    employee_sex varchar(10) default '男',  
    hire_date datetime not null default current_timestamp,  
    employee_mgr int(11),  
    employee_salary float default 3000,  
    department_id int(11)  
);  
  
  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('David Tian','男',10,7500,1);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Black Xie','男',10,6600,1);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Moses Wang','男',10,4300,1);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Rena Ruan','女',10,5300,1);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Sunshine Ma','女',10,6500,2);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Scott Gao','男',10,9500,2);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Warren Si','男',10,7800,2);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Kaishen Yang','男',10,9500,3);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Simon Song','男',10,5500,3);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Brown Guan','男',10,5000,3);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Eleven Chen','女',10,3500,2);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Cherry Zhou','女',10,5500,4);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Klause He','男',10,4500,5);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Maven Ma','男',10,4500,6);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Stephani Wang','女',10,5500,7);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Jerry Guo','男',10,8500,1);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Gerardo Garza','男',10,25000,8);  
insert into employees(employee_name,employee_sex,employee_mgr,employee_salary,department_id) values ('Derek Wu','男',10,5500,5);  
  
  
select * from employees;  

#创建函数
DELIMITER //  
CREATE FUNCTION GetEmployeeInformationByID(id INT)  
RETURNS VARCHAR(300) 
DETERMINISTIC 
BEGIN  
    RETURN(SELECT CONCAT('employee name:',employee_name,'---','salary: ',employee_salary) FROM employees WHERE employee_id=id);  
END//  
DELIMITER ;  


DELIMITER //  
CREATE FUNCTION rand_num()
RETURNS INT(5)
DETERMINISTIC 
BEGIN
 DECLARE i INT DEFAULT 0;
 SET i = FLOOR(10+RAND()*500);
    #set i = CEIL(RAND() * 9000000000) + 1000000000;
RETURN i;
END//
DELIMITER ;
#创建存取过程


#删除函数
drop procedure if exists test;
drop function if exists GetEmployeeInformationByID;





#解决方法：碰见mysql开启binlog无法创建函数问题

解决办法也有两种，
第一种是在创建子程序(存储过程、函数、触发器)时，声明为DETERMINISTIC或NO SQL与READS SQL DATA中的一个，
例如:
CREATE DEFINER = CURRENT_USER PROCEDURE `NewProc`()
    DETERMINISTIC
BEGIN
 #Routine body goes here...
END;;

第二种是信任子程序的创建者，禁止创建、修改子程序时对SUPER权限的要求，设置log_bin_trust_routine_creators全局系统变量为1。设置方法有三种:
1.在客户端上执行SET GLOBAL log_bin_trust_function_creators = 1;
2.MySQL启动时，加上--log-bin-trust-function-creators选贤，参数设置为1
3.在MySQL配置文件my.ini或my.cnf中的[mysqld]段上加log-bin-trust-function-creators=1

网络上的其他方案.也是一样的


CREATE TABLE `sequence` (
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '序列的名字',
  `current_value` int(11) NOT NULL COMMENT '序列的当前值',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '序列的自增值',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
#获取当前值
DROP FUNCTION IF EXISTS currval; 
DELIMITER $ 
CREATE FUNCTION currval (seq_name VARCHAR(50)) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     DECLARE value INTEGER; 
     SET value = 0; 
			 SELECT current_value INTO value 
          FROM sequence
          WHERE name = seq_name; 
			if value=0 then 
			insert into sequence VALUES (seq_name,1,1);
			end if;
     RETURN value; 
END
$ 
DELIMITER ; 

#获取下一个值

DROP FUNCTION IF EXISTS nextval; 
DELIMITER $ 
CREATE FUNCTION nextval (seq_name VARCHAR(50)) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     UPDATE sequence
          SET current_value = current_value + increment 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
$ 
DELIMITER ; 