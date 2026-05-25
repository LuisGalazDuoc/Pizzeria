--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE pedido_detalle (
    pedido_detalle_id   BIGINT          NOT NULL AUTO_INCREMENT,
    pedido_id    BIGINT          NOT NULL,
    producto_id  BIGINT          NOT NULL,
    cantidad    INT             NOT NULL,
    precio_unidad  DECIMAL(10,2)   NOT NULL,
    subtotal    DECIMAL(10,2)   NOT NULL,

    CONSTRAINT pk_pedido_detalle PRIMARY KEY (pedido_detalle_id)
);

--changeset pizzeria:002
INSERT INTO pedido_detalle (pedido_id, producto_id, cantidad, precio_unidad, subtotal) VALUES
    (1,  1, 2, 7990.00, 15980.00),
    (1,  6, 1, 1490.00,  1490.00),
    (2,  2, 1, 8990.00,  8990.00),
    (3,  3, 1, 8490.00,  8490.00),
    (3,  8, 1, 2990.00,  2990.00),
    (4,  4, 1, 9490.00,  9490.00),
    (5,  1, 1, 7990.00,  7990.00),
    (6,  2, 1, 8990.00,  8990.00),
    (6,  9, 1, 4990.00,  4990.00),
    (7,  4, 2, 9490.00, 18980.00);