create table coupon_history
(
    id         bigint auto_increment primary key,
    coupon_id  bigint    not null,
    user_id    bigint    not null,
    created_dt timestamp not null default current_timestamp,
    updated_dt timestamp not null default current_timestamp on update current_timestamp
);

