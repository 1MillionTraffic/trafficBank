create table bank_user
(
    id         bigint auto_increment primary key,
    seq        char(36)    not null,
    name       varchar(40) not null,
    state      char(10)    not null,
    created_dt timestamp   not null default current_timestamp,
    updated_dt timestamp   not null default current_timestamp on update current_timestamp
);

create index idx_user_seq
    on bank_user (seq);

# ------------------------------------

create table bank_account
(
    id           bigint auto_increment primary key,
    user_id      bigint       not null,
    bank_type    char(4)      not null,
    account_seq  char(13)     not null,
    account_name varchar(100) not null,
    balance      bigint       not null,
    created_dt   timestamp    not null default current_timestamp,
    updated_dt   timestamp    not null default current_timestamp on update current_timestamp,
    foreign key (user_id) references bank_user (id)
);

create index idx_account_seq
    on bank_user (seq);

# ------------------------------------

create table transaction_history
(
    id               bigint auto_increment primary key,
    transaction_seq  char(36)  not null,
    money            bigint    not null,
    balance          bigint    not null,
    from_account_id  bigint    not null,
    to_account_id    bigint    null,
    transaction_type char(10)  not null,
    created_dt       timestamp not null default current_timestamp,
    foreign key (from_account_id) references bank_account (id),
    foreign key (to_account_id) references bank_account (id)
);

create index idx_transaction_created_dt
    on transaction_history (created_dt);
