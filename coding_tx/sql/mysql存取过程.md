#2.1：语法：CREATE PROCEDURE  过程名([[IN|OUT|INOUT] 参数名 数据类型[,[IN|OUT|INOUT] 参数名 数据类型…]]) [特性 ...] 过程体

 
 CREATE TABLE `order_history_count` (
   `id` int(32) NOT NULL AUTO_INCREMENT,
   `borrow_ammount` decimal(19,2) DEFAULT NULL,
   `create_iime` time DEFAULT NULL,
   `id_card` varchar(255) DEFAULT NULL,
   `repay_ment_ammount` decimal(19,2) DEFAULT NULL,
   `update_time` datetime DEFAULT NULL,
   `user_name` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=101822 DEFAULT CHARSET=utf8
 
 
 
 

语法实例：CREATE PROCEDURE `proc_auto_insertdata`(in start int)

BEGIN

DECLARE num INTEGER ;

END;





#生成流水号函数
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


DELIMITER $$

USE `test_data_sync`$$

DROP PROCEDURE IF EXISTS `proc_auto_insertdata`$$

CREATE DEFINER=`root`@`%` PROCEDURE `proc_auto_insertdata`()
BEGIN
    DECLARE num INTEGER ;
    DECLARE order_no  VARCHAR(32);
    DECLARE trans_code  VARCHAR(32);
    DECLARE out_order_no  VARCHAR(32);
-- set autocommit = 0  //把autocommit设置成0，这样可以只提交一次，否则。。。。。
--  CONCAT('out_order_no',DATE_FORMAT(NOW(),'%Y%m%d')
    SET num=1;
    SET order_no=2018062600000;
    SET trans_code=870210;
    SET out_order_no=201806260000000;
    START TRANSACTION;
    WHILE num <=2 DO
        SET order_no=order_no+1;
        SET out_order_no=out_order_no+1;
        INSERT INTO `test_data_sync`.`order_history_count` (`borrow_ammount`, `create_iime`, `id_card`, `repay_ment_ammount`, `update_time`, `user_name`) 
        VALUES (rand_num( ), NOW(), trans_code,rand_num( ),NOW(),'张三');
        SET num =num+1;
    END WHILE;
    COMMIT ;
    select count(1) from `test_data_sync`.`order_history_count`;
END$$

DELIMITER ;