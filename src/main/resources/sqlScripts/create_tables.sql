CREATE SCHEMA IF NOT EXISTS sausageShop;

CREATE TABLE IF NOT EXISTS sausageShop.category
(
    id    serial PRIMARY KEY ,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS sausageShop.product
(
    id          serial PRIMARY KEY ,
    nameProduct VARCHAR(45)      NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    composition VARCHAR,
    description VARCHAR,
    rating      DOUBLE PRECISION
);

