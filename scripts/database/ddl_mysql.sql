drop table if exists user;
create table user
(
    user_id  bigint auto_increment
        primary key,
    email    varchar(100) not null,
    password varchar(100) null,
    enabled  bit          null

);

drop table if exists user_roles;
create table user_roles
(
    role_id bigint auto_increment
        primary key,
    role    varchar(10) null,
    user_id bigint      null
);

drop table if exists user_fans;
create table user_fans
(
    role_id bigint auto_increment
        primary key,
    fan_user    bigint null,
    user_id bigint      null
);

drop table if exists user_blocks;
create table user_blocks
(
    role_id bigint auto_increment
        primary key,
    block_user    bigint null,
    user_id bigint      null
);