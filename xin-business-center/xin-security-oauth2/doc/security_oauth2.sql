https://andaily.com/spring-oauth-server/db_table_description.html


DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(256) NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密匙',
  `scope` varchar(256) DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: read,write',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: authorization_code,password',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(256) DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒) ',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒) ',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '用户是否自动Approval操作',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth客户端信息';

DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `token_id` varchar(256) DEFAULT NULL COMMENT 'MD5加密的access_token的值',
  `token` blob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  `authentication_id` varchar(256) NOT NULL COMMENT 'MD5加密过的username,client_id,scope  ',
  `user_name` varchar(256) DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(256) DEFAULT NULL COMMENT '客户端ID',
  `authentication` blob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  `refresh_token` varchar(256) DEFAULT NULL COMMENT 'MD5加密果的refresh_token的值',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth访问令牌';


DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` varchar(256) DEFAULT NULL COMMENT '登录的用户名',
  `clientId` varchar(256) DEFAULT NULL COMMENT '客户端ID',
  `scope` varchar(256) DEFAULT NULL COMMENT '申请的权限 ',
  `status` varchar(10) DEFAULT NULL COMMENT '状态（Approve或Deny）',
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth审批信息';

DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
`id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `token_id` varchar(256) DEFAULT NULL COMMENT 'MD5加密的access_token值',
  `token` blob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  `authentication_id` varchar(256) NOT NULL COMMENT 'MD5加密过的username,client_id,scope',
  `user_name` varchar(256) DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(256) DEFAULT NULL COMMENT '客户端ID',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth客户端令牌';

DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
   `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(256) DEFAULT NULL COMMENT '授权码(未加密)',
  `authentication` blob COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth编码';

DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `token_id` varchar(256) DEFAULT NULL COMMENT 'MD5加密过的refresh_token的值',
  `token` blob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据',
  `authentication` blob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth刷新令牌';


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '随机盐',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `deptId` int(11) DEFAULT NULL COMMENT '部门ID',
  `state` int(2) DEFAULT '1' COMMENT '状态（1=正常，0=删除）',
  `author` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_idx1_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户信息';


INSERT INTO `oauth_client_details` VALUES (1,'1', null, 'c81e728d9d4c2f636f067f89cc14862c', 'app,openid', 'password,authorization_code', 'https://www.baidu.com', null, null, null, null, '','2020-08-01 13:22:22','2020-08-01 13:22:22');
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', 'admin', '2019-03-26 09:31:52', null);
