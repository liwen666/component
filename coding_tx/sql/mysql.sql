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


CREATE TABLE `newpay_payment_picture_history` (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `pic_content` blob NOT NULL COMMENT '图片内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `pic_md5` varchar(64) NOT NULL COMMENT '图片MD5加密结果',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8



GRANT ALL on maxwell.* to 'maxwell'@'%' identified by 'maxwell';
 GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';
 FLUSH PRIVILEGES;




CREATE TABLE `mc_team` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modified_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK4gi4cny1fo7vwajm3fsnbdbrc` (`project_id`) USING BTREE,
  CONSTRAINT `FK4gi4cny1fo7vwajm3fsnbdbrc` FOREIGN KEY (`project_id`) REFERENCES `mc_project` (`id`),
  CONSTRAINT `mc_team_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `mc_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci

-- 删除外键

 alter table mc_team drop foreign key  mc_team_ibfk_1;


 增加主键:alter table your_table_name add primary key (your_primary_key_name);//最后边的那个()一点要有;
增加外键:alter table your_table_name add foreign key your_foreign_key_id(your_foerign_key_name) references zhu_jian_table_name(your_foreign_key_name);



