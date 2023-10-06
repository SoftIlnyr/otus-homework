create sequence phone_SEQ start with 1 increment by 1;

create table phone
(
    id   bigint not null primary key,
    client_id bigint not null references client(id),
    number varchar(50)
);
