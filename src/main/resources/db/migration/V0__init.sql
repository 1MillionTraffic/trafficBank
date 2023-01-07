create table bank_user
(
    id         bigint auto_increment primary key,
    seq        char(36)    not null,
    name       varchar(40) not null,
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
    transaction_start_dt timestamp not null,
    transaction_end_dt   timestamp not null,
    created_dt           timestamp not null default current_timestamp
);

create index idx_sync_created_dt
    on sync_history (created_dt);

# ------------------------------------

create table bank_account
(
    id                   bigint auto_increment primary key,
    user_id              bigint       not null,
    account_seq          char(14)     not null,
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
    id              bigint auto_increment primary key,
    transaction_seq char(32)  not null,
    before_money    bigint    not null,
    after_money     bigint    not null,
    from_account_id bigint    null,
    to_account_id   bigint    null,
    created_dt      timestamp not null default current_timestamp,
    foreign key (from_account_id) references bank_account (id),
    foreign key (to_account_id) references bank_account (id)
);

create index idx_transaction_created_dt
    on transaction_history (created_dt);
