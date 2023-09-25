create sequence address_SEQ start with 1 increment by 1;

create table address
(
    id   bigint not null primary key,
    client_id bigint not null references client(id),
    street varchar(50)
);
