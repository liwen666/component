
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


#创建业务表
CREATE TABLE `test_user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tenant_id` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '租户ID',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户Id',
  `mobile_no` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `trans_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '交易密码',
  `login_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录密码',
  `channel_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道号',
  `reg_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `open_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否开户 1:是,0:否',
  `bind_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否绑卡 1:是0:否',
  `sign_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否签约 1:是,0否',
  `loan_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否借款 1:是,0:否',
  `contract_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否补充信息 1:是,0:否',
  `limit_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '授信状态 0：未发起，3：授信审批中，5：授信初审完成，6:授信复审中，7：授信成功，8:授信失败',
  `cert_no` char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '姓名',
  `nation` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `sexual` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `lock_end_date` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '锁定到期时间',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '1:可用,0:失效',
  `idEffectiveDate` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证开始有效日期(\"yyyyMMdd\")',
  `idExpireDate` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证截止有效日期(\"yyyyMMdd\")',
  `issued_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签证机关',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址信息',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后更新时间',
  `update_by` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新操作',
  `record_version_number` int(11) NULL DEFAULT 1 COMMENT '记录版本号',
  `sub_channel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子渠道',
  `login_salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码盐值',
  `trans_salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易密码盐值',
  `refer_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐人码值',
  `safe_pwd_param` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安全键盘加密参数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user`(`user_id`) USING BTREE,
  INDEX `idx_mobile`(`mobile_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

INSERT INTO `test_user_info`( `tenant_id`, `user_id`, `mobile_no`, `trans_password`, `login_password`, `channel_no`, `reg_date`, `open_flag`, `bind_flag`, `sign_flag`, `loan_flag`, `contract_flag`, `limit_flag`, `cert_no`, `user_name`, `nation`, `sexual`, `lock_end_date`, `state`, `idEffectiveDate`, `idExpireDate`, `issued_by`, `address`, `create_time`, `update_time`, `update_by`, `record_version_number`, `sub_channel`, `login_salt`, `trans_salt`, `refer_code`, `safe_pwd_param`) VALUES ( '101', '41009', '15939320004', '', 'phv/UkSj6sV4xMNK3U4ut4Ze2Dw=', 'channel01', '2020-10-30 17:24:06', '0', '0', '0', '0', '0', '3', '110101198709071097', '赵改改', '01', '1', '', '1', '20110221', '20210221', '濮阳县公安局', '河南省濮阳县鲁河乡安家什八郎村021号', '2020-10-30 17:24:06', '2020-12-25 18:02:47', 'system', 2, NULL, NULL, NULL, NULL, '15939320004');





CREATE DEFINER=`any`@`%` PROCEDURE `test_user_info_proc`(in total int)
BEGIN
    DECLARE num INTEGER ;
    DECLARE order_no  VARCHAR(32);
    DECLARE trans_code  VARCHAR(32);
    DECLARE out_order_no  VARCHAR(32);
    set autocommit = 0 ;
--  CONCAT('out_order_no',DATE_FORMAT(NOW(),'%Y%m%d')
    SET num=1;
    START TRANSACTION;
    WHILE num <=total DO
        SET order_no=order_no+1;
        SET out_order_no=out_order_no+1;
				INSERT INTO `test_user_info`( `tenant_id`, `user_id`, `mobile_no`, `trans_password`, `login_password`, `channel_no`, `reg_date`, `open_flag`, `bind_flag`, `sign_flag`, `loan_flag`, `contract_flag`, `limit_flag`, `cert_no`, `user_name`, `nation`, `sexual`, `lock_end_date`, `state`, `idEffectiveDate`, `idExpireDate`, `issued_by`, `address`, `create_time`, `update_time`, `update_by`, `record_version_number`, `sub_channel`, `login_salt`, `trans_salt`, `refer_code`, `safe_pwd_param`) VALUES ( '101', '41009', '15939320004', '', 'phv/UkSj6sV4xMNK3U4ut4Ze2Dw=', 'channel01', '2020-10-30 17:24:06', '0', '0', '0', '0', '0', '3', '110101198709071097', '赵改改', '01', '1', '', '1', '20110221', '20210221', '濮阳县公安局', '河南省濮阳县鲁河乡安家什八郎村021号', '2020-10-30 17:24:06', '2020-12-25 18:02:47', 'system', 2, NULL, NULL, NULL, NULL, '15939320004');
        SET num =num+1;
    END WHILE;
    COMMIT ;
    select count(1) from `test_user_info`;
END


lock table test_user_info  read ;
添加读锁不允许所有线程写；
允许所有线程读取
INSERT INTO `test_user_info`( `tenant_id`, `user_id`, `mobile_no`, `trans_password`, `login_password`, `channel_no`, `reg_date`, `open_flag`, `bind_flag`, `sign_flag`, `loan_flag`, `contract_flag`, `limit_flag`, `cert_no`, `user_name`, `nation`, `sexual`, `lock_end_date`, `state`, `idEffectiveDate`, `idExpireDate`, `issued_by`, `address`, `create_time`, `update_time`, `update_by`, `record_version_number`, `sub_channel`, `login_salt`, `trans_salt`, `refer_code`, `safe_pwd_param`) VALUES ( '101', '41009', '15939320004', '', 'phv/UkSj6sV4xMNK3U4ut4Ze2Dw=', 'channel01', '2020-10-30 17:24:06', '0', '0', '0', '0', '0', '3', '110101198709071097', '赵改改', '01', '1', '', '1', '20110221', '20210221', '濮阳县公安局', '河南省濮阳县鲁河乡安家什八郎村021号', '2020-10-30 17:24:06', '2020-12-25 18:02:47', 'system', 2, NULL, NULL, NULL, NULL, '15939320004');
 unlock tables;
 
 
 写锁允许当前线程写
 不允许所有线程读
 lock table test_user_info  write ;
 
 
 
 mysql为了提高其性能，部分数据时缓存在内存中，因此要刷新表（清除缓存），就需要使用
 
 FLUSH TABLES;
 当然，如果是需要备份数据库，同时防止备份时候有新数据写入，且备份的是最新的：
  FLUSH TABLES test_user_info , sequence  WITH READ LOCK;

 

 
 
 