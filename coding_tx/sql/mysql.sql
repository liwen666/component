create table `nodelist` (
    `id` int (11),
    `nodecontent` varchar (300),
    `pid` int (11)
);
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('1','a',NULL);
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('2','b','1');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('3','c','1');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('4','d','2');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('5','e','3');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('6','f','3');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('7','g','5');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('8','h','7');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('9','i','8');
insert into `nodelist` (`id`, `nodecontent`, `pid`) values('10','j','8');



-----------------------------------2
DELIMITER $$
DROP FUNCTION IF EXISTS `getChild`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `getChild`(rootId INT) RETURNS VARCHAR(1000) CHARSET utf8
BEGIN
        DECLARE ptemp VARCHAR(1000);
        DECLARE ctemp VARCHAR(1000);
               SET ptemp = '#';
               SET ctemp =CAST(rootId AS CHAR);
               WHILE ctemp IS NOT NULL DO
                 SET ptemp = CONCAT(ptemp,',',ctemp);
                SELECT GROUP_CONCAT(id) INTO ctemp FROM nodelist
                WHERE FIND_IN_SET(pid,ctemp)>0;
               END WHILE;
               RETURN ptemp;
    END$$
DELIMITER ;