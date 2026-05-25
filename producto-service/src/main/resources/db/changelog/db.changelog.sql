--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE producto (
    producto_id  BIGINT          NOT NULL AUTO_INCREMENT,
    categoria_id BIGINT          NOT NULL,
    nombre       VARCHAR(100)    NOT NULL,
    descripcion  TEXT,
    precio       DECIMAL(10,2)   NOT NULL,
    disponible   BOOLEAN         NOT NULL DEFAULT TRUE,

    CONSTRAINT pk_producto PRIMARY KEY (producto_id)
);

--changeset pizzeria:002
INSERT INTO producto (categoria_id, nombre, descripcion, precio, disponible) VALUES
    (1, 'Pizza Margherita',   'Salsa de tomate, mozzarella y albahaca',              7990.00, TRUE),
    (1, 'Pizza Pepperoni',    'Salsa de tomate, mozzarella y pepperoni',             8990.00, TRUE),
    (1, 'Pizza Hawaiana',     'Salsa de tomate, mozzarella, jamón y piña',           8490.00, TRUE),
    (1, 'Pizza Cuatro Quesos','Mozzarella, parmesano, gouda y azul',                 9490.00, TRUE),
    (1, 'Pizza Vegana',       'Salsa de tomate, verduras asadas y queso vegano',     8990.00, TRUE),
    (2, 'Coca-Cola 500ml',    'Bebida gaseosa clásica',                              1490.00, TRUE),
    (2, 'Agua Mineral 500ml', 'Agua mineral sin gas',                                990.00,  TRUE),
    (3, 'Papas Fritas',       'Papas fritas crujientes con sal',                     2990.00, TRUE),
    (3, 'Alitas BBQ',         'Alitas de pollo con salsa BBQ',                       4990.00, TRUE),
    (1, 'Pizza BBQ Chicken',  'Salsa BBQ, pollo grillado y cebolla morada',          9990.00, FALSE);