create or replace table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) null,
    password varchar(255) null
);

