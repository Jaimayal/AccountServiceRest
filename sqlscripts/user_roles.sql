create or replace table user_roles
(
    user_id bigint not null,
    roles   int    null,
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id) references users (id)
);

