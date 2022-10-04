create database health_management;

use health_management;

drop table if exists tb_user;
CREATE TABLE `tb_user` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(20) NOT NULL COMMENT 'user name',
    `password` varchar(20) NOT NULL DEFAULT '123456' COMMENT 'password',
    `first_name` varchar(20) DEFAULT NULL,
    `last_name` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='user table'

drop table if exists tb_record;
CREATE TABLE `tb_record` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id` bigint unsigned NOT NULL COMMENT 'user table id',
    `weight` varchar(20) NOT NULL COMMENT 'weight',
    `temperature` varchar(20) NOT NULL COMMENT 'temperature',
    `blood_pressure` varchar(20) NOT NULL COMMENT 'blood pressure',
    `textual_note` varchar(50) NOT NULL COMMENT 'textual note',
    `create_time` varchar(20) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='record table'