DROP TABLE IF EXISTS t_reward_flow;
CREATE TABLE `t_reward_flow`
(
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`      bigint(20) COMMENT '用户id',
  `sn`           varchar(16) COMMENT 'sn',
  `coin_sum`     bigint(20) NOT NULL COMMENT '领取金币数量',
  `coin_descr`   varchar(50)         DEFAULT NULL COMMENT '领取金币说明',
  `reward_key`   varchar(50)         COMMENT '奖励的key，config表中的key',
  `unit_id`      bigint(20) COMMENT '金币单元',
  `product_line` varchar(16) COMMENT '金币预算承担事业线',
  `identity_id`  varchar(16) COMMENT '预算控制账号唯一标识',
  `endpoint`     int(4)     NOT NULL DEFAULT 0 COMMENT '来源：0-app 1-车机 2-两端 3-其他',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version`      int(10)    NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `key_sn_reward_key_create_time`(`sn`,`reward_key`,`create_time`)
) ENGINE = `InnoDB`
  DEFAULT CHARACTER SET utf8 COMMENT ='金币流水表';

DROP TABLE IF EXISTS t_gas_station_info;
CREATE TABLE `t_gas_station_info`
(
  `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gs_name`      varchar(200) COMMENT '加油站名称',
  `gs_addr`      varchar(500) COMMENT '加油站地址',
  `gs_icon`      varchar(2048) COMMENT '加油站图片链接，是一个列表，用，号隔开',
  `gs_tel`       varchar(128) COMMENT '加油站电话号码',
  `gs_gaode_id`  varchar(16) COMMENT '高德地图id',
  `gs_gaode_type_code`  varchar(16) COMMENT '所属高德类型code',
  `gs_gaode_type_name`  varchar(200) COMMENT '所属高德类型名称',
  `gs_lat`      varchar(16)  COMMENT '纬度',
  `gs_lng`     varchar(16)   COMMENT '经度',
  `geo_hash`   varchar(16) COMMENT '经纬度 geo_hash 后的字符串',
  `province_code`     varchar(8) COMMENT '省的code',
  `province_name`     varchar(32) COMMENT '省的名称',
  `city_code`  varchar(8) COMMENT '市的code',
  `city_name`  varchar(32) COMMENT '市的名称',
  `adcode`     varchar(8) COMMENT '县的code',
  `adname`     varchar(32) COMMENT '县的名称',
  `oil_price`  varchar(100) COMMENT '不同油的油价 是一个json',
  `create_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  timestamp  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version`      int(10)    NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `key_gs_lat_gs_lng`(`gs_lat`,`gs_lng`),
  KEY `key_geo_hash`(`geo_hash`),
  KEY `key_gs_gaode_id`(`gs_gaode_id`)
) ENGINE = `InnoDB`
  DEFAULT CHARACTER SET utf8 COMMENT ='加油站信息';

rename table t_news_share_rank to ylt_share_rank;
alter table ylt_share_rank comment '一路听分享排行';
alter table gs_czb_station_info add `skprice` double(8, 2) default 0 COMMENT '省控油价';
update gs_czb_station_info set icon = 'https://aaa.bbb.com/sunflower/enjoymap/gas_station_info.png';
INSERT INTO `t_news_plate`(`plate_code`, `plate_title`, `plate_content`, `plate_status`, `create_time`, `update_time`,`version`)
VALUES ('casualNews', '随心听', '', 1, '2019-11-27 17:14:08', '2019-11-27 17:14:08', 0);