CREATE TABLE `merchant_agent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商家ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '商户上级ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 0-无效 1-有效',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号,乐观锁使用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8 COMMENT='商家层级结构表';


DELIMITER $$
DROP FUNCTION IF EXISTS `getMerchantChild`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `getMerchantChild`(rootId INT) RETURNS VARCHAR(1000) CHARSET utf8
BEGIN
        DECLARE ptemp VARCHAR(1000);
        DECLARE ctemp VARCHAR(1000);
               SET ptemp = '#';
               SET ctemp =CAST(rootId AS CHAR);
               WHILE ctemp IS NOT NULL DO
                 SET ptemp = CONCAT(ptemp,',',ctemp);
                SELECT GROUP_CONCAT(merchant_id) INTO ctemp FROM merchant_agent
                WHERE FIND_IN_SET(parent_id,ctemp)>0;
               END WHILE;
               RETURN ptemp;
    END$$
DELIMITER ;

SELECT * FROM merchant_agent WHERE FIND_IN_SET(merchant_id, getMerchantChild(1));