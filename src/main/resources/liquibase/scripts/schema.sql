-- liquibase formatted sql
-- changeset antonL:1
CREATE TABLE socks
(
    id          SERIAL PRIMARY KEY ,
    color       varchar NOT NULL,
    cotton_Part INTEGER NOT NULL,
    quantity    INTEGER NOT NULL
);