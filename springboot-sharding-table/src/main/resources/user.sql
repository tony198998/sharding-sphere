CREATE
DATABASE `ds_2` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

CREATE TABLE `user_0`
(
    id            bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    city          varchar(20) not null,
    name          varchar(20) not null,
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，录入时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_1`
(
    id            bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    city          varchar(20) not null,
    name          varchar(20) not null,
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，录入时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_2`
(
    id            bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    city          varchar(20) not null,
    name          varchar(20) not null,
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，录入时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_3`
(
    id            bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    city          varchar(20) not null,
    name          varchar(20) not null,
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，录入时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
