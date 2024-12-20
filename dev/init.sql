create table socks
(
    id                uuid,
    color             varchar(255),
    amount            integer default 0,
    cotton_percentage integer default 0
);