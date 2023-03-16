create table event
(
    id                    bigint auto_increment primary key,
    request_limit_user    bigint     not null,
    is_full_request_limit tinyint(1) not null,
    limit_user            bigint     not null,
    current_user_cnt      bigint     not null,
    created_dt            timestamp  not null default current_timestamp,
    updated_dt            timestamp  not null default current_timestamp on update current_timestamp
);