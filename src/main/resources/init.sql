DROP TABLE IF EXISTS sausageShop.product;
DROP TABLE IF EXISTS sausageShop.category;
DROP TABLE IF EXISTS sausageShop.roles cascade ;
DROP TABLE IF EXISTS sausageShop.users cascade ;
DROP TABLE IF EXISTS sausageShop.users_roles;
DROP SCHEMA IF EXISTS sausageShop;

CREATE SCHEMA IF NOT EXISTS sausageShop;

CREATE TABLE IF NOT EXISTS sausageShop.category (
    id serial PRIMARY KEY ,
     title VARCHAR NOT NULL
                                                );

CREATE TABLE IF NOT EXISTS sausageShop.roles(
    id serial PRIMARY KEY ,
    name VARCHAR NOT NULL
                                            );

CREATE TABLE IF NOT EXISTS sausageShop.users(
    id serial PRIMARY KEY,
     username VARCHAR NOT NULL,
      password VARCHAR NOT NULL
                                            );

CREATE TABLE IF NOT EXISTS sausageShop.users_roles(
    idUser INT,
    idRole INT
);

CREATE TABLE IF NOT EXISTS sausageShop.product (
    id serial PRIMARY KEY ,
     nameProduct VARCHAR(45) NOT NULL,
      price DOUBLE PRECISION NOT NULL,
       composition VARCHAR,
        description VARCHAR,
         rating DOUBLE PRECISION,
          categoryId INTEGER,
           FOREIGN KEY (categoryId) REFERENCES sausageShop.category (id) ON DELETE CASCADE );

INSERT INTO sausageShop.category (title) VALUES ('колбаски');
INSERT INTO sausageShop.category (title) VALUES ('мяско');

INSERT INTO sausageShop.product (nameProduct, price, description, composition, rating, categoryId)
VALUES ('Сосиски', 100, 'Небольшие вкусные штучки', '100% курица', 0 , 1);
INSERT INTO sausageShop.product (nameProduct, price, description, composition, rating, categoryId)
VALUES ('Сервелат', 500, 'Классная копченая колбаска', 'Кто-то умер, чтобы попасть туда', 0 , 1);
INSERT INTO sausageShop.product (nameProduct, price, description, composition, rating, categoryId)
VALUES ('Вяленое мясо', 800, 'Оно вкусное', '200% вяленого мяса', 0 , 2);
INSERT INTO sausageShop.product (nameProduct, price, description, composition, rating, categoryId)
VALUES ('Копченное мясо', 683, 'Оно стоит 683 рубля', 'Его ингридиенты стоили 683 рубля', 0 , 2);