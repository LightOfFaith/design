create database if not exists `db` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE = utf8mb4_general_ci;

use `db`;

create table if not exists `user`
(
    `id`          int          not null auto_increment primary key comment '主键ID',
    `name`        varchar(32)  not null default '' comment '姓名',
    `age`    tinyint(1) unsigned not null default '0' comment '年龄',
    `create_time` datetime     not null default current_timestamp comment '创建时间',
    `modify_time` datetime     not null default current_timestamp on update current_timestamp comment '修改时间',
    `is_deleted` tinyint(1) unsigned not null default '0' comment '逻辑删除，1 表示删除，0 表示未删除。',
    key `idx_name` (`name`)
) engine InnoDB
  default character set utf8mb4
  collate = utf8mb4_general_ci comment '用户表';