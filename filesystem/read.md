创建数据库  dcpay_filesystem


CREATE TABLE `file_media` (
  `uid` bigint(32) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `remote_filename` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `file_ext_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8


#对于fastdfs首次访问失败，可以对方法进行改造，设置一个boolean变量对其进行重试一次。
解决上传失败的可能性。

