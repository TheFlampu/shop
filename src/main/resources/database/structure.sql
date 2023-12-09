create table role
(
    id   bigserial
        constraint role_pk
            primary key,
    name varchar(16) not null
);

alter table role
    owner to postgres;

create table "user"
(
    id         bigserial
        constraint user_pk
            primary key,
    first_name varchar(32)  not null,
    last_name  varchar(32)  not null,
    email      varchar(128) not null,
    password   varchar(256) not null
);

alter table "user"
    owner to postgres;

create unique index user_email_uindex
    on "user" (email);

create table user_role
(
    user_id bigint not null
        constraint user_role_user_id_fk
            references "user",
    role_id bigint not null
        constraint user_role_role_id_fk
            references role,
    constraint user_role_pk
        primary key (user_id, role_id)
);

alter table user_role
    owner to postgres;

create table category
(
    id                 bigserial
        constraint category_pk
            primary key,
    name               varchar(32) not null,
    parent_category_id bigint
        constraint category_category_id_fk
            references category
);

alter table category
    owner to postgres;

create table item
(
    id              bigserial
        constraint item_pk
            primary key,
    name            varchar(255)      not null,
    description     text              not null,
    image           varchar(256),
    price           integer           not null,
    sale_price      integer,
    available_count integer default 0 not null
);

alter table item
    owner to postgres;

create table item_category
(
    item_id     bigint not null
        constraint item_category_item_id_fk
            references item,
    category_id bigint not null
        constraint item_category_category_id_fk
            references category,
    constraint item_category_pk
        primary key (item_id, category_id)
);

alter table item_category
    owner to postgres;

create table user_item
(
    user_id bigint            not null
        constraint user_item_user_id_fk
            references "user",
    item_id bigint            not null
        constraint user_item_item_id_fk
            references item
            on delete cascade,
    count   integer default 1 not null,
    constraint cart_item_pk
        primary key (user_id, item_id)
);

alter table user_item
    owner to postgres;

