--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE categoria (
    categoria_id BIGINT          NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100)    NOT NULL,

    CONSTRAINT pk_categoria PRIMARY KEY (categoria_id),
    CONSTRAINT uq_categoria_nombre UNIQUE (nombre)
);

--changeset pizzeria:002
INSERT INTO categoria (nombre) VALUES
    ('Pizzas'),
    ('Bebidas'),
    ('Entradas'),
    ('Postres'),
    ('Combos'),
    ('Pizzas Veganas'),
    ('Pizzas Sin Gluten'),
    ('Salsas'),
    ('Ensaladas'),
    ('Promociones');