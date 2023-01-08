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

create table sync_history
(
    id                   bigint auto_increment primary key,
    account_id           bigint    not null,
    sync_money           bigint    not null,
    transaction_state    char(10)  not null,
    transaction_start_dt timestamp not null,
    transaction_end_dt   timestamp not null,
    created_dt           timestamp not null default current_timestamp
);

create index idx_sync_created_dt
    on sync_history (created_dt);

create index idx_sync_account_state
    on sync_history (account_id, transaction_state);

# ------------------------------------

create table bank_account
(
    id                   bigint auto_increment primary key,
    user_id              bigint       not null,
    bank_type            char(4)      not null,
    account_seq          char(13)     not null,
    account_name         varchar(100) not null,
    last_sync_history_id bigint       null,
    created_dt           timestamp    not null default current_timestamp,
    updated_dt           timestamp    not null default current_timestamp on update current_timestamp,
    foreign key (user_id) references bank_user (id),
    foreign key (last_sync_history_id) references sync_history (id)
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
