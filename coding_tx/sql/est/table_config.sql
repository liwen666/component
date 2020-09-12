
-- ----------------------------
-- Table structure for table_code_relation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `table_code_relation`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `filter_handle_bean` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `primary_code_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `primary_table_china_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `primary_table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `slave_code_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `slave_table_china_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `slave_table_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;



CREATE TABLE IF NOT EXISTS `table_mark_init`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `table_china_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;



CREATE TABLE IF NOT EXISTS  `table_param_config`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `key_column` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_id_column` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_code_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version_column` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;



CREATE TABLE IF NOT EXISTS  `table_history_data`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `history_data_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `primary_key_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `primary_table_china_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


CREATE TABLE  IF NOT EXISTS  `table_import_sort`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `flag` int(11) NULL DEFAULT NULL,
  `order_id` int(11) NULL DEFAULT NULL,
  `table_code_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;



CREATE TABLE  IF NOT EXISTS `table_code_config`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `columns` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `handle_bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ignore_column_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ignore_column_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_code_china_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_code_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `where_sql_columns` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


CREATE TABLE IF NOT EXISTS `table_conversion_key`  (
  `id` int(11) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `used` bit(1) NULL DEFAULT NULL,
  `conversion_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `handle_bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_json_object` bit(1) NULL DEFAULT NULL,
  `is_multiple_relation` bit(1) NULL DEFAULT NULL,
  `table_code_china_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_code_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;



TRUNCATE table_import_sort;

TRUNCATE table_conversion_key;

TRUNCATE table_code_config;

TRUNCATE table_code_relation;

TRUNCATE table_param_config;



-- ----------------------------
-- Records of table_code_relation
-- ----------------------------
INSERT INTO `table_code_relation` VALUES (3361, NULL, b'1', 'defaultTableDataHandler', 'category_id', '规则信息表', 'res_rule_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3362, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '规则信息表', 'res_rule_info', 'resource_id', '规则版本表', 'res_rule');
INSERT INTO `table_code_relation` VALUES (3363, NULL, b'1', 'defaultTableDataHandler', 'category_id', '规则集信息表', 'res_rule_set_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3364, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '规则集信息表', 'res_rule_set_info', 'resource_id', '规则集版本表', 'res_rule_set');
INSERT INTO `table_code_relation` VALUES (3365, NULL, b'1', 'defaultTableDataHandler', 'category_id', '规则树信息表', 'res_rule_tree_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3366, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '规则树信息表', 'res_rule_tree_info', 'resource_id', '规则树版本表', 'res_rule_tree');
INSERT INTO `table_code_relation` VALUES (3367, NULL, b'1', 'defaultTableDataHandler', 'category_id', '评分卡信息表', 'res_score_card_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3368, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '评分卡信息表', 'res_score_card_info', 'resource_id', '评分卡版本表', 'res_score_card');
INSERT INTO `table_code_relation` VALUES (3369, NULL, b'1', 'defaultTableDataHandler', 'category_id', '脚本信息表', 'res_script_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3370, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '脚本信息表', 'res_script_info', 'resource_id', '脚本版本表', 'res_script');
INSERT INTO `table_code_relation` VALUES (3371, NULL, b'1', 'defaultTableDataHandler', 'category_id', '策略信息表', 'res_strategy_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3372, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '策略信息表', 'res_strategy_info', 'resource_id', '策略版本表', 'res_strategy');
INSERT INTO `table_code_relation` VALUES (3373, NULL, b'1', 'defaultTableDataHandler', 'strategy_id', '策略信息表', 'res_strategy', 'resource_object_version_id', '字段表', 'meta_object_field');
INSERT INTO `table_code_relation` VALUES (3374, NULL, b'1', 'defaultTableDataHandler', 'strategy_id', '策略版本表', 'res_strategy', 'strategy_id', '策略节点表', 'res_strategy_node');
INSERT INTO `table_code_relation` VALUES (3375, NULL, b'1', 'defaultTableDataHandler', 'strategy_id', '策略版本表', 'res_strategy', 'strategy_id', '节点连线表', 'res_strategy_node_link');
INSERT INTO `table_code_relation` VALUES (3376, NULL, b'1', 'defaultTableDataHandler', 'category_id', '矩阵信息表', 'res_matrix_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3377, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '规则集信息表', 'res_matrix_info', 'resource_id', '矩阵版本表', 'res_matrix');
INSERT INTO `table_code_relation` VALUES (3378, NULL, b'1', 'defaultTableDataHandler', 'resource_object_category_id', '字段表', 'meta_object_field', 'category_id', '数据集版本表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3379, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '数据集信息表', 'meta_data_object_info', 'resource_id', '数据集版本表', 'meta_data_object');
INSERT INTO `table_code_relation` VALUES (3380, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '数据集信息表', 'meta_data_object_info', 'resource_object_id', '字段表', 'meta_object_field');
INSERT INTO `table_code_relation` VALUES (3381, NULL, b'1', 'defaultTableDataHandler', 'category_id', '数据集信息表', 'meta_data_object_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3382, NULL, b'1', 'defaultTableDataHandler', 'data_source_id', '数据集信息表', 'meta_data_object_info', 'data_source_id', '数据源表', 'meta_data_source_info');
INSERT INTO `table_code_relation` VALUES (3383, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '事件对象信息表', 'meta_model_object_info', 'resource_id', '事件对象版本表', 'meta_model_object');
INSERT INTO `table_code_relation` VALUES (3384, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '事件对象信息表', 'meta_model_object_info', 'resource_object_id', '字段表', 'meta_object_field');
INSERT INTO `table_code_relation` VALUES (3385, NULL, b'1', 'defaultTableDataHandler', 'category_id', '事件对象信息表', 'meta_model_object_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3386, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '统计模型信息表', 'meta_topic_object_info', 'resource_id', '统计模型版本表', 'meta_topic_object');
INSERT INTO `table_code_relation` VALUES (3387, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '统计模型信息表', 'meta_topic_object_info', 'resource_object_id', '字段表', 'meta_object_field');
INSERT INTO `table_code_relation` VALUES (3388, NULL, b'1', 'defaultTableDataHandler', 'category_id', '统计模型信息表', 'meta_topic_object_info', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3389, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '统计模型信息表', 'meta_topic_object_info', 'topic_object_info_id', '统计模型关系表', 'meta_topic_object_relation');
INSERT INTO `table_code_relation` VALUES (3390, NULL, b'1', 'defaultTableDataHandler', 'data_source_id', '统计模型信息表', 'meta_topic_object_info', 'data_source_id', '数据源信息表', 'meta_data_source_info');
INSERT INTO `table_code_relation` VALUES (3391, NULL, b'1', 'defaultTableDataHandler', 'primary_resource_id', '统计模型信息表', 'meta_topic_object_info', 'resource_id', '数据集信息表', 'meta_data_object_info');
INSERT INTO `table_code_relation` VALUES (3392, NULL, b'1', 'defaultTableDataHandler', 'category_id', '统计模型版本表', 'meta_topic_object', 'category_id', '分类表', 'meta_category');
INSERT INTO `table_code_relation` VALUES (3393, NULL, b'1', 'defaultTableDataHandler', 'primary_resource_id', '统计模型关系表', 'meta_topic_object_relation', 'resource_id', '对象信息表', 'meta_data_object_info');
INSERT INTO `table_code_relation` VALUES (3394, NULL, b'1', 'defaultTableDataHandler', 'relation_object_id', '统计模型关系表', 'meta_topic_object_relation', 'resource_id', '对象信息表', 'meta_data_object_info');
INSERT INTO `table_code_relation` VALUES (3395, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '函数信息表', 'meta_function_info', 'resource_id', '对象信息表', 'meta_function');
INSERT INTO `table_code_relation` VALUES (3396, NULL, b'1', 'defaultTableDataHandler', 'node_content.ruleItemList.resourceId', '函数信息表', 'res_strategy_node', 'resource_id', '对象信息表', 'res_rule_info');
INSERT INTO `table_code_relation` VALUES (3397, NULL, b'1', 'defaultTableDataHandler', 'resource_id', '策略信息表', 'res_strategy_info', 'category_id', '策略版本表', 'meta_topic_object_info');




-- ----------------------------
-- Records of table_code_config
-- ----------------------------
INSERT INTO `table_code_config` VALUES (2729, '2020-08-27 15:07:55', b'1', 'category_type,name,parent_id', 'defaultTableDataHandler', NULL, NULL, '分类表', 'meta_category', 'content_code');
INSERT INTO `table_code_config` VALUES (2730, '2020-08-27 15:07:55', b'1', 'source_code', 'defaultTableDataHandler', NULL, NULL, '数据源表', 'meta_data_source_info', NULL);
INSERT INTO `table_code_config` VALUES (2731, '2020-08-27 15:07:55', b'1', 'code,data_source_id', 'defaultTableDataHandler', NULL, NULL, '数据集信息表', 'meta_data_object_info', NULL);
INSERT INTO `table_code_config` VALUES (2732, '2020-08-27 15:07:55', b'1', 'resource_id', 'defaultTableDataHandler', NULL, NULL, '数据集信息表', 'meta_data_object', 'resource_id');
INSERT INTO `table_code_config` VALUES (2733, '2020-08-27 15:07:55', b'1', 'code', 'defaultTableDataHandler', NULL, NULL, '事件和模型对象信息表', 'meta_model_object_info', NULL);
INSERT INTO `table_code_config` VALUES (2734, '2020-08-27 15:07:55', b'1', 'resource_id', 'defaultTableDataHandler', NULL, NULL, '事件和模型对象信息表', 'meta_model_object', 'resource_id');
INSERT INTO `table_code_config` VALUES (2735, '2020-08-27 15:07:55', b'1', 'code', 'defaultTableDataHandler', '', '', '统计模型信息表', 'meta_topic_object_info', NULL);
INSERT INTO `table_code_config` VALUES (2736, '2020-08-27 15:07:55', b'1', 'resource_id', 'defaultTableDataHandler', '', '', '统计模型信息表', 'meta_topic_object', 'resource_id');
INSERT INTO `table_code_config` VALUES (2737, '2020-08-27 15:07:55', b'1', 'strategy_key', 'defaultTableDataHandler', NULL, NULL, '策略信息表', 'res_strategy_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2738, '2020-08-27 15:07:55', b'1', 'field_code,resource_object_id', 'defaultTableDataHandler', NULL, NULL, '字段表', 'meta_object_field', NULL);
INSERT INTO `table_code_config` VALUES (2739, '2020-08-27 15:07:55', b'1', 'resource_id', 'defaultTableDataHandler', NULL, NULL, '资源管理表', 'res_resource_set_item', 'project_id');
INSERT INTO `table_code_config` VALUES (2740, '2020-08-27 15:07:55', b'1', 'primary_resource_id,relation_object_id,topic_object_info_id', 'defaultTableDataHandler', NULL, NULL, '资源管理表', 'meta_topic_object_relation', NULL);
INSERT INTO `table_code_config` VALUES (2741, '2020-08-27 15:07:55', b'1', 'name', 'defaultTableDataHandler', NULL, NULL, '规则信息表', 'res_rule_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2742, '2020-08-27 15:07:55', b'1', 'name', 'defaultTableDataHandler', NULL, NULL, '规则集信息表', 'res_rule_set_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2743, '2020-08-27 15:07:55', b'1', 'name', 'defaultTableDataHandler', NULL, NULL, '规则树信息表', 'res_rule_tree_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2744, '2020-08-27 15:07:55', b'1', 'name', 'defaultTableDataHandler', NULL, NULL, '评分卡信息表', 'res_score_card_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2745, '2020-08-27 15:07:55', b'1', 'name', 'defaultTableDataHandler', NULL, NULL, '脚本信息表', 'res_script_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2746, '2020-08-27 15:07:55', b'1', 'name', 'defaultTableDataHandler', NULL, NULL, '决策矩阵信息表', 'res_matrix_info', 'project_id');
INSERT INTO `table_code_config` VALUES (2747, '2020-08-27 15:07:55', b'1', 'code', 'defaultTableDataHandler', NULL, NULL, '函数信息表', 'meta_function_info', NULL);
INSERT INTO `table_code_config` VALUES (2748, '2020-08-27 15:07:55', b'1', 'resource_id', 'defaultTableDataHandler', NULL, NULL, '函数信息表', 'meta_function', NULL);




-- ----------------------------
-- Records of table_conversion_key
-- ----------------------------


INSERT INTO `table_conversion_key` VALUES (-328, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-327, '2020-09-02 14:23:27', b'1', 'res_rule_info|meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.resourceId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-326, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.expFields', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-325, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-324, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-323, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.condition.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-322, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-321, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-320, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.condition.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-319, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-318, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-317, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.condition.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-316, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-315, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.condition.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-314, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.condition.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-313, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.missOutputList.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-312, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.missOutputList.outField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-311, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.missOutputList.expField', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-310, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.missOutputList.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-309, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@node_content.ruleItemList.versionInfo.hitOutputList.outField.referFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-308, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-307, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.referFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-306, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.referFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-305, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-304, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-303, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.expField', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-302, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-301, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-300, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.hitOutputList.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-299, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-298, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-297, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-296, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-295, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-294, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-293, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-292, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-291, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-290, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-289, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-288, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-287, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-286, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-285, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.ruleItemList.versionInfo.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-284, '2020-09-02 14:23:27', b'1', 'meta_data_object_info@node_content.listInfoId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-283, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputFields.valueConditions.conditionGroup.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-282, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-281, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-280, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputFields.valueConditions.conditionGroup.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-279, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-278, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-277, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputFields.valueConditions.conditionGroup.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-276, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-275, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-274, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputFields.valueConditions.conditionGroup.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-273, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-272, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.conditionGroup.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-271, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@conditions.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-270, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-269, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-268, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@conditions.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-267, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-266, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-265, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@conditions.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-264, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-263, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-262, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@conditions.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-261, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-260, '2020-09-02 14:23:27', b'1', 'meta_object_field@conditions.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (-259, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columnFields.valueConditions.conditionGroup.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-258, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-257, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-256, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columnFields.valueConditions.conditionGroup.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-255, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-254, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-253, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columnFields.valueConditions.conditionGroup.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-252, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-251, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-250, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columnFields.valueConditions.conditionGroup.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-249, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-248, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.conditionGroup.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-247, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@derive_content.valueConditions.conditionGroup.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-246, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-245, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-244, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@derive_content.valueConditions.conditionGroup.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-243, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-242, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-241, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@derive_content.valueConditions.conditionGroup.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-240, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-239, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-238, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@derive_content.valueConditions.conditionGroup.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-237, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-236, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.conditionGroup.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-235, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.valueConditions.conditionGroup.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-234, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-233, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-232, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.valueConditions.conditionGroup.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-231, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-230, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-229, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.valueConditions.conditionGroup.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-228, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-227, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-226, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.valueConditions.conditionGroup.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-225, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-224, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.conditionGroup.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-223, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.condition.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-222, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-221, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-220, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.condition.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-219, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-218, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-217, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.condition.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-216, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-215, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-214, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.condition.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-213, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-212, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.condition.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-211, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-210, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-209, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-208, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-207, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-206, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-205, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-204, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-203, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-202, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-201, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.fieldBidValue', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-200, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.conditionGroup.children.fieldBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-149, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columnFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-148, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-147, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-146, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columnFields.referFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-145, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.referFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-144, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-143, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.referFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-142, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columnFields.valueConditions.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-137, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.rows.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-136, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.rows.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-135, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.rows.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-134, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.columns.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-133, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columns.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-132, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.columns.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-131, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputList.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-130, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputList.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-129, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputList.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-124, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-123, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.referFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-122, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.valueConditions.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-121, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.referFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-120, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputFields.referFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-119, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-118, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.outputFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-117, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.outputFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-116, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-115, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-111, '2020-09-02 14:23:27', b'1', 'meta_object_field@refer_field_ids', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-110, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-109, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-108, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.successLoad.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-107, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.failLoad.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-106, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-105, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-104, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-103, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (-102, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (-101, '2020-09-02 14:23:27', b'1', 'meta_object_field@nodes.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (-100, '2020-09-02 11:39:39', b'1', 'meta_object_field@derive_content.valueConditions.expressionUnits.sort', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3615, '2020-09-02 11:39:39', b'1', 'meta_object_field@derive_content.valueConditions.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3702, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '规则信息表', 'res_rule_info');
INSERT INTO `table_conversion_key` VALUES (3703, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '资源管理表', 'res_resource_set_item');
INSERT INTO `table_conversion_key` VALUES (3704, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '资源管理表', 'res_resource_set_item');
INSERT INTO `table_conversion_key` VALUES (3705, '2020-09-02 14:23:27', b'1', 'res_rule_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (3706, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (3707, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '规则集信息表', 'res_rule_set_info');
INSERT INTO `table_conversion_key` VALUES (3708, '2020-09-02 14:23:27', b'1', 'res_rule_set_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则集版本表', 'res_rule_set');
INSERT INTO `table_conversion_key` VALUES (3709, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_set');
INSERT INTO `table_conversion_key` VALUES (3710, '2020-09-02 14:23:27', b'1', 'res_rule_info@rule_items.resourceId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_set');
INSERT INTO `table_conversion_key` VALUES (3711, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '规则集信息表', 'res_rule_tree_info');
INSERT INTO `table_conversion_key` VALUES (3712, '2020-09-02 14:23:27', b'1', 'res_rule_set_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则集版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (3713, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (3714, '2020-09-02 14:23:27', b'1', 'meta_object_field@nodes.field.bid', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (3715, '2020-09-02 14:23:27', b'1', 'meta_object_field@nodes.field.fieldId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (3716, '2020-09-02 14:23:27', b'1', 'meta_object_field@nodes.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (3717, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@nodes.field.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_rule_tree');
INSERT INTO `table_conversion_key` VALUES (3718, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '规则集信息表', 'res_score_card_info');
INSERT INTO `table_conversion_key` VALUES (3719, '2020-09-02 14:23:27', b'1', 'res_score_card_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则集版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3720, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3721, '2020-09-02 14:23:27', b'1', 'meta_object_field@items.conditionField.bid', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3722, '2020-09-02 14:23:27', b'1', 'meta_object_field@items.conditionField.fieldId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3723, '2020-09-02 14:23:27', b'1', 'meta_object_field@items.conditionField.referFields.bid', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3724, '2020-09-02 14:23:27', b'1', 'meta_object_field@items.conditionField.referFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3725, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@items.conditionField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_score_card');
INSERT INTO `table_conversion_key` VALUES (3726, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '规则集信息表', 'res_script_info');
INSERT INTO `table_conversion_key` VALUES (3727, '2020-09-02 14:23:27', b'1', 'res_script_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则集版本表', 'res_script');
INSERT INTO `table_conversion_key` VALUES (3728, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_script');
INSERT INTO `table_conversion_key` VALUES (3729, '2020-09-02 14:23:27', b'1', 'meta_object_field@in_param_fields.mapField.bid', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_script');
INSERT INTO `table_conversion_key` VALUES (3730, '2020-09-02 14:23:27', b'1', 'meta_object_field@in_param_fields.mapField.fieldId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_script');
INSERT INTO `table_conversion_key` VALUES (3731, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@in_param_fields.mapField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_script');
INSERT INTO `table_conversion_key` VALUES (3732, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '策略信息表', 'res_strategy_info');
INSERT INTO `table_conversion_key` VALUES (3733, '2020-09-02 14:23:27', b'1', 'meta_category@model_category_id', 'defaultTableDataHandler', b'0', b'0', '策略信息表', 'res_strategy_info');
INSERT INTO `table_conversion_key` VALUES (3734, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@model_resource_id', 'defaultTableDataHandler', b'0', b'0', '策略信息表', 'res_strategy_info');
INSERT INTO `table_conversion_key` VALUES (3735, '2020-09-02 14:23:27', b'1', 'res_strategy_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则集版本表', 'res_strategy');
INSERT INTO `table_conversion_key` VALUES (3736, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_strategy');
INSERT INTO `table_conversion_key` VALUES (3737, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '规则集信息表', 'res_matrix_info');
INSERT INTO `table_conversion_key` VALUES (3738, '2020-09-02 14:23:27', b'1', 'res_matrix_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '规则集版本表', 'res_matrix');
INSERT INTO `table_conversion_key` VALUES (3739, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_matrix');
INSERT INTO `table_conversion_key` VALUES (3740, '2020-09-02 14:23:27', b'1', 'meta_object_field@output_field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_matrix');
INSERT INTO `table_conversion_key` VALUES (3741, '2020-09-02 14:23:27', b'1', 'meta_object_field@row_field_ids', 'defaultTableDataHandler', b'0', b'0', '规则版本表', 'res_matrix');
INSERT INTO `table_conversion_key` VALUES (3742, '2020-09-02 14:23:27', b'1', 'meta_data_object_info@primary_resource_id', 'defaultTableDataHandler', b'0', b'0', '统计模型关联关系表', 'meta_topic_object_relation');
INSERT INTO `table_conversion_key` VALUES (3743, '2020-09-02 14:23:27', b'1', 'meta_object_field@primary_field_ids', 'defaultTableDataHandler', b'0', b'0', '统计模型关联表', 'meta_topic_object_relation');
INSERT INTO `table_conversion_key` VALUES (3744, '2020-09-02 14:23:27', b'1', 'meta_object_field@relation_field_ids', 'defaultTableDataHandler', b'0', b'0', '统计模型关联表', 'meta_topic_object_relation');
INSERT INTO `table_conversion_key` VALUES (3745, '2020-09-02 14:23:27', b'1', 'meta_data_object_info@relation_object_id', 'defaultTableDataHandler', b'0', b'0', '统计模型关联表', 'meta_topic_object_relation');
INSERT INTO `table_conversion_key` VALUES (3746, '2020-09-02 14:23:27', b'1', 'meta_topic_object_info@topic_object_info_id', 'defaultTableDataHandler', b'0', b'0', '统计模型关联表', 'meta_topic_object_relation');
INSERT INTO `table_conversion_key` VALUES (3747, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3748, '2020-09-02 14:23:27', b'1', 'meta_data_source_info@data_source_id', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3749, '2020-09-02 14:23:27', b'1', 'meta_object_field@primary_dimension_id', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3750, '2020-09-02 14:23:27', b'1', 'meta_object_field@primary_field_id', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3751, '2020-09-02 14:23:27', b'1', 'meta_object_field@primary_key_field_ids', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3752, '2020-09-02 14:23:27', b'1', 'meta_object_field@second_dimension_ids', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3753, '2020-09-02 14:23:27', b'1', 'meta_object_field@stat_time_field_id', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3754, '2020-09-02 14:23:27', b'1', 'meta_data_object_info@primary_resource_id', 'defaultTableDataHandler', b'0', b'0', '统计模型信息表', 'meta_topic_object_info');
INSERT INTO `table_conversion_key` VALUES (3755, '2020-09-02 14:23:27', b'1', 'meta_topic_object_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '统计模型表', 'meta_topic_object');
INSERT INTO `table_conversion_key` VALUES (3756, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_ids', 'defaultTableDataHandler', b'0', b'0', '统计模型表', 'meta_topic_object');
INSERT INTO `table_conversion_key` VALUES (3757, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.statisticFieldBid', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3758, '2020-09-02 14:23:27', b'1', 'meta_object_field@refer_fieldIds', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3759, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@resource_object_id', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3760, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.originField.bid', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3761, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.originField.fieldId', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3763, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.valueConditions.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3764, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3765, '2020-09-02 14:23:27', b'1', 'meta_object_field@derive_content.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3766, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@derive_content.tableId', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3767, '2020-09-02 14:23:27', b'1', 'meta_category@resource_object_category_id', 'defaultTableDataHandler', b'0', b'0', '字段表', 'meta_object_field');
INSERT INTO `table_conversion_key` VALUES (3768, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '模型信息表', 'meta_model_object_info');
INSERT INTO `table_conversion_key` VALUES (3769, '2020-09-02 14:23:27', b'1', 'meta_model_object_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '模型版本表', 'meta_model_object');
INSERT INTO `table_conversion_key` VALUES (3770, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '模型版本表', 'meta_model_object');
INSERT INTO `table_conversion_key` VALUES (3771, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '函数信息表', 'meta_function_info');
INSERT INTO `table_conversion_key` VALUES (3772, '2020-09-02 14:23:27', b'1', 'meta_function_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '函数表', 'meta_function');
INSERT INTO `table_conversion_key` VALUES (3773, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '数据源信息表', 'meta_data_source_info');
INSERT INTO `table_conversion_key` VALUES (3774, '2020-09-02 14:23:27', b'1', 'meta_category@category_id', 'defaultTableDataHandler', b'0', b'0', '数据集信息表', 'meta_data_object_info');
INSERT INTO `table_conversion_key` VALUES (3775, '2020-09-02 14:23:27', b'1', 'meta_data_source_info@data_source_id', 'defaultTableDataHandler', b'0', b'0', '数据集信息表', 'meta_data_object_info');
INSERT INTO `table_conversion_key` VALUES (3776, '2020-09-02 14:23:27', b'1', 'meta_data_object_info@resource_id', 'defaultTableDataHandler', b'0', b'0', '数据集版本表', 'meta_data_object');
INSERT INTO `table_conversion_key` VALUES (3777, '2020-09-02 14:23:27', b'1', 'meta_object_field@field_ids', 'defaultTableDataHandler', b'0', b'0', '数据集版本表', 'meta_data_object');
INSERT INTO `table_conversion_key` VALUES (3778, '2020-09-02 14:23:27', b'1', 'meta_category@parent_id', 'defaultTableDataHandler', b'0', b'0', '数据集版本表', 'meta_category');
INSERT INTO `table_conversion_key` VALUES (3779, '2020-09-02 14:23:27', b'1', 'meta_object_field@hit_output.expField', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (3780, '2020-09-02 14:23:27', b'1', 'meta_object_field@hit_output.outField.bid', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (3781, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@hit_output.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '规则版本', 'res_rule');
INSERT INTO `table_conversion_key` VALUES (3783, '2020-09-02 14:23:27', b'1', 'meta_category@node_content.ruleItemList.category.categoryId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3784, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.eventStatObjectInfoId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3785, '2020-09-02 14:23:27', b'1', 'res_rule_info@node_content.ruleItemList.resourceId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3786, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.statTimeFieldMapperBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3787, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.statParams.paramBids', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3788, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.statParams.outBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3789, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.primaryFieldMapperBid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3790, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3791, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.items.conditionField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3792, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.items.conditionField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3793, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.items.conditionField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3794, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.scoreField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3795, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.scoreField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3796, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.scoreField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3797, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.missOutputList.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3798, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.hitOutputList.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3799, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.missOutputList.expField', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3800, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.expFields', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3802, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3803, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3804, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.expField', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3805, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.missOutputList.outField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3806, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.missOutputList.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3807, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.queryParams.valueField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3808, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.queryParams.valueField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3809, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.queryParams.valueField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3810, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.dataObjectInfoId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3811, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3812, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3813, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3815, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.valueConditions.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3817, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3818, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3819, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3820, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.referFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3821, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.referFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3823, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.hitOutputList.outField.valueConditions.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3824, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.queryParams.paramField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3825, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.queryParams.paramField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3826, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@node_content.queryParams.paramField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3827, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@node_content.hitOutputList.outField.referFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3828, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@node_content.eventModel.fields.referFields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3829, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@node_content.eventModel.fields.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3830, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info@node_content.eventModel.resourceId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3831, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3832, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3833, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.referFieldBids', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3834, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.referFields.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3835, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.referFields.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3836, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.eventModel.fields.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3837, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.failLoad.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3838, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.failLoad.outField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3839, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.successLoad.expressionUnits.code', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3840, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.successLoad.outField.bid', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3841, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.successLoad.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3842, '2020-09-02 14:23:27', b'1', 'meta_data_object_info|meta_model_object_info|meta_topic_object_info|res_strategy_info@node_content.failLoad.outField.resourceObjectId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3843, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.failLoad.expField', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3844, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.successLoad.expField', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3845, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.successLoad.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');
INSERT INTO `table_conversion_key` VALUES (3846, '2020-09-02 14:23:27', b'1', 'meta_object_field@node_content.failLoad.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');





-- INSERT INTO `table_conversion_key` VALUES (4000, '2020-09-02 14:23:27', b'1', 'meta_object_field@hit_output.outField.fieldId', 'defaultTableDataHandler', b'0', b'0', '节点表', 'res_strategy_node');





-- ----------------------------
-- Records of table_import_sort
-- ----------------------------
INSERT INTO `table_import_sort` VALUES (2600, NULL, b'1', NULL, 1, 'meta_category');
INSERT INTO `table_import_sort` VALUES (2601, NULL, b'1', NULL, 2, 'meta_data_source_info');
INSERT INTO `table_import_sort` VALUES (2602, NULL, b'1', NULL, 3, 'meta_data_object_info');
INSERT INTO `table_import_sort` VALUES (2603, NULL, b'1', NULL, 4, 'meta_model_object_info');
INSERT INTO `table_import_sort` VALUES (2604, NULL, b'1', NULL, 5, 'meta_function_info');
INSERT INTO `table_import_sort` VALUES (2605, NULL, b'1', NULL, 6, 'res_strategy_info');
INSERT INTO `table_import_sort` VALUES (2606, NULL, b'1', NULL, 7, 'res_strategy');
INSERT INTO `table_import_sort` VALUES (2607, NULL, b'1', 0, 8, 'meta_object_field');
INSERT INTO `table_import_sort` VALUES (2608, NULL, b'1', NULL, 9, 'meta_topic_object_info');
INSERT INTO `table_import_sort` VALUES (2609, NULL, b'1', 1, 10, 'meta_object_field');
INSERT INTO `table_import_sort` VALUES (2610, NULL, b'1', NULL, 11, 'meta_data_object');
INSERT INTO `table_import_sort` VALUES (2611, NULL, b'1', NULL, 12, 'meta_model_object');
INSERT INTO `table_import_sort` VALUES (2612, NULL, b'1', NULL, 13, 'meta_function');
INSERT INTO `table_import_sort` VALUES (2613, NULL, b'1', NULL, 14, 'meta_topic_object');
INSERT INTO `table_import_sort` VALUES (2614, NULL, b'1', NULL, 15, 'meta_topic_object_relation');
INSERT INTO `table_import_sort` VALUES (2615, NULL, b'1', NULL, 16, 'res_resource_set_item');
INSERT INTO `table_import_sort` VALUES (2616, NULL, b'1', NULL, 17, 'res_rule_info');
INSERT INTO `table_import_sort` VALUES (2617, NULL, b'1', NULL, 18, 'res_rule');
INSERT INTO `table_import_sort` VALUES (2618, NULL, b'1', NULL, 19, 'res_rule_set_info');
INSERT INTO `table_import_sort` VALUES (2619, NULL, b'1', NULL, 20, 'res_rule_set');
INSERT INTO `table_import_sort` VALUES (2620, NULL, b'1', NULL, 21, 'res_rule_tree_info');
INSERT INTO `table_import_sort` VALUES (2621, NULL, b'1', NULL, 22, 'res_rule_tree');
INSERT INTO `table_import_sort` VALUES (2622, NULL, b'1', NULL, 23, 'res_score_card_info');
INSERT INTO `table_import_sort` VALUES (2623, NULL, b'1', NULL, 24, 'res_score_card');
INSERT INTO `table_import_sort` VALUES (2624, NULL, b'1', NULL, 25, 'res_script_info');
INSERT INTO `table_import_sort` VALUES (2625, NULL, b'1', NULL, 26, 'res_script');
INSERT INTO `table_import_sort` VALUES (2626, NULL, b'1', NULL, 27, 'res_strategy_node');
INSERT INTO `table_import_sort` VALUES (2627, NULL, b'1', NULL, 28, 'res_strategy_node_link');
INSERT INTO `table_import_sort` VALUES (2628, NULL, b'1', NULL, 29, 'res_strategy_business_goal');
INSERT INTO `table_import_sort` VALUES (2629, NULL, b'1', NULL, 30, 'res_strategy_group');
INSERT INTO `table_import_sort` VALUES (2630, NULL, b'1', NULL, 31, 'res_matrix_info');
INSERT INTO `table_import_sort` VALUES (2631, NULL, b'1', NULL, 32, 'res_matrix');

INSERT INTO `table_param_config` VALUES (1621, NULL, b'1', 'next_val', NULL, 'key_sequence', 'hibernate_sequence', NULL);
INSERT INTO `table_param_config` VALUES (1622, NULL, b'1', NULL, 'resource_id', 'res_rule', '', 'version');
INSERT INTO `table_param_config` VALUES (1623, NULL, b'1', NULL, 'resource_id', 'res_rule_set', '', 'version');
INSERT INTO `table_param_config` VALUES (1624, NULL, b'1', NULL, 'resource_id', 'res_rule_tree', '', 'version');
INSERT INTO `table_param_config` VALUES (1625, NULL, b'1', NULL, 'resource_id', 'res_score_card', '', 'version');
INSERT INTO `table_param_config` VALUES (1626, NULL, b'1', NULL, 'resource_id', 'res_script', '', 'version');
INSERT INTO `table_param_config` VALUES (1627, NULL, b'1', NULL, 'resource_id', 'res_strategy', '', 'version');
INSERT INTO `table_param_config` VALUES (1628, NULL, b'1', NULL, 'resource_id', 'res_matrix', '', 'version');
INSERT INTO `table_param_config` VALUES (1629, NULL, b'1', NULL, 'resource_id', 'meta_model_object', '', 'version');
INSERT INTO `table_param_config` VALUES (1630, NULL, b'1', NULL, 'resource_id', 'meta_topic_object', '', 'version');
INSERT INTO `table_param_config` VALUES (1631, NULL, b'1', NULL, 'resource_id', 'meta_data_object', '', 'version');








-- 解决脏数据1、 parent_id为空的数据
update  meta_category set parent_id=0 where parent_id is null;

-- 系统初始化数据源 code 设置为默认的数据源  code
update `meta_data_source_info`  set source_code = 'System_GP_DataSource'  where db_type='GREENPLUM';

-- 解决脏数据2、data_source_id 为null  设置为系统初始化数据源的Id
update meta_data_object_info set data_source_id=(SELECT data_source_id FROM `meta_data_source_info` where db_type='GREENPLUM') where data_source_id=1 or data_source_id is null;
--

-- 解决脏数据3、    resource_object_id = 1 的脏数据

--  有问题，系统字段和 有些字段设置resource_object_id 有问题。
-- UPDATE meta_object_field c JOIN (SELECT a.resource_object_id,b.resource_id from  meta_object_field a
-- JOIN (SELECT resource_id,strategy_id FROM res_strategy WHERE strategy_id in (SELECT resource_object_version_id FROM meta_object_field WHERE field_code like 'NODE%')) b
-- on a.field_code like 'NODE%' and
-- a.resource_object_version_id = b.strategy_id) d on c.resource_object_id = d.resource_object_id set c.resource_object_id = d.resource_id;

UPDATE meta_object_field sd JOIN res_strategy sy   on sd.resource_object_version_id = sy.strategy_id
 set sd.resource_object_id = sy.resource_id where sd.resource_object_id = '1' and sd.field_type != 'SYS_FIELD' and sd.field_code like 'NODE%' ;

-- 解决脏数据4、   更新object_type
update meta_object_field set object_type = 'NODE_FEATURE' WHERE field_code like 'NODE%';

-- 解决脏数据5、  resource_object_id = 1 的问题，该 resource_object_version_id 不存在，需要重新给该resource_object_versio_id 赋值。

update meta_object_field sd join res_strategy_node sn on locate(
        CONCAT( sd.object_field_id, '",' ),
        sn.node_content
    ) != 0
        set sd.resource_object_version_id = sn.strategy_id where sd.resource_object_id = 1
        AND sd.field_type != 'SYS_FIELD'
        AND sd.field_code LIKE 'NODE%'  and sd.object_field_id != '7276';


-- 重新执行步骤三，因为这些resource_object_version_id  已经修复，需要重新设置 resource_object_id

UPDATE meta_object_field sd JOIN res_strategy sy   on sd.resource_object_version_id = sy.strategy_id
 set sd.resource_object_id = sy.resource_id where sd.resource_object_id = '1' and sd.field_type != 'SYS_FIELD' and sd.field_code like 'NODE%' ;





-- 删除字段的code 重复数据的，需要手工验证删除。

-- 生产环境 清理脏数据
-- 删除资源管理设置
DELETE FROM res_resource_set_item WHERE project_id = 4645 and resource_type = 'STAT_OBJECT' and resource_id = 2133;

-- 生产环境 清理脏数据
-- 更新数据集版本的字段field_ids
UPDATE meta_data_object a JOIN
(select GROUP_CONCAT(object_field_id separator ',') field_id from meta_object_field WHERE resource_object_id = 1281 GROUP BY resource_object_id) b
on a.resource_id = 1281 set a.field_ids = b.field_id;


-- 查询字段 重复数

        -- 查找策略字段是否有重复的code字段。

-- 		SELECT
-- 			count( 1 ) num,
-- 			field_code,
-- 			resource_object_id,
-- 			max( object_field_id ) object_field_id
-- 		FROM
-- 			meta_object_field sd
-- 		WHERE
-- 			1 = 1
-- 			AND object_type = 'NODE_FEATURE'
-- 			AND EXISTS (
-- 			SELECT
-- 				1
-- 			FROM
-- 				res_strategy_node aa
-- 				JOIN res_strategy sy ON aa.strategy_id = sy.strategy_id
-- 			WHERE
-- 				locate( sd.object_field_id, aa.node_content ) != 0
-- 				AND sy.publish_mode = 'ONLINE'
-- 			)
-- 		GROUP BY
-- 			field_code,
-- 			resource_object_id;


        -- 查询策略的
        -- 注意locate 问题。
        select * from res_strategy_node sd join res_strategy rs on sd.strategy_id = rs.strategy_id  where locate('6787',sd.node_content) != 0 and rs.publish_mode = 'ONLINE';


delete from meta_object_field  where object_field_id = '6786';
delete from meta_object_field  where object_field_id = '6787';


-- 更新字段
UPDATE meta_object_field c
JOIN (
	SELECT
		*
	FROM
		res_strategy s
	WHERE
		 s.publish_mode = 'ONLINE'
	) a  on 		c.resource_object_id = a.resource_id
	SET c.resource_object_version_id = a.strategy_id
WHERE
	c.object_field_id IN (
	SELECT
		object_field_id
	FROM
		(
		SELECT
			count( 1 ) num,
			field_code,
			resource_object_id,
			max( object_field_id ) object_field_id
		FROM
			meta_object_field sd
		WHERE
			1 = 1
			AND object_type = 'NODE_FEATURE'
			AND EXISTS (
			SELECT
				1
			FROM
				res_strategy_node aa
				JOIN res_strategy sy ON aa.strategy_id = sy.strategy_id
			WHERE
				locate( sd.object_field_id, aa.node_content ) != 0
				AND sy.publish_mode = 'ONLINE'
			)
		GROUP BY
			field_code,
			resource_object_id
		) k
	WHERE
	k.num < 2
	);

delete from meta_function_info where resource_id in (select resource_id from meta_function where function_type='AGGREGATE_FUNCTION');
delete from meta_function where function_type='AGGREGATE_FUNCTION';


DELETE FROM meta_object_field WHERE object_field_id = 17549;


-- 解决策略分组 code重复的问题，暂时改code，存在新建的bug，解决完bug，考虑把重复的分组删除。

update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_1' where resource_id = '10228';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_2' where resource_id = '27111';
update meta_topic_object_info set code = 'DAY_900_tradeTime_7782_linealfamilyMobile_1' where resource_id = '27284';
update meta_topic_object_info set code = 'DAY_900_tradeTime_7782_noLinealfamilyMobile_1' where resource_id = '27243';
update meta_topic_object_info set code = 'DAY_12_icEffectiveDate_39468_icNumber_1' where resource_id = '40811';

-- 策略变更
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_1' where resource_id = '7679';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_2' where resource_id = '7688';
update meta_topic_object_info set code = 'DAY_tradeTime_7782_noLinealfamilyMobile_1' where resource_id = '27247';
update meta_topic_object_info set code = 'DAY_tradeTime_7782_noLinealfamilyMobile_2' where resource_id = '27265';
update meta_topic_object_info set code = 'DAY_tradeTime_7782_noLinealfamilyMobile_3' where resource_id = '27271';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_3' where resource_id = '7688';
update meta_topic_object_info set code = 'HOUR_24_tradeTime_7782_icNumber_4' where resource_id = '27111';
-- 解决策略分组字段 的分类Id  存的是策略的resource_id 的 问题。需要慧琴把这个bug先解决掉。
update meta_object_field set resource_object_category_id = null  where object_type='STRATEGY_FIELD';

-- 解决 实体、统计、策略分组的归属datasource_id  为系统Greenplum的dataSource_id

update meta_topic_object_info set data_source_id=(SELECT data_source_id FROM `meta_data_source_info` where db_type='GREENPLUM') ;

--   下面的字段可能有问题
-- update meta_object_field set resource_object_version_id=38926 where object_field_id=9043
-- update meta_object_field set resource_object_version_id=42538 where object_field_id=9043


delete from meta_object_field where object_field_id=17549;

--
-- 单独维护否则不能执行成功
-- alter table meta_object_field add priority longtext;
--
ALTER TABLE meta_data_object_info ADD data_sync_method NVARCHAR (50) NULL;
--
