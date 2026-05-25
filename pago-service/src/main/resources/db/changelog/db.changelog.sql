--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE pago (
    pago_id      BIGINT          NOT NULL AUTO_INCREMENT,
    pedido_id        BIGINT          NOT NULL,
    monto          DECIMAL(10,2)   NOT NULL,
    metodo          VARCHAR(20)     NOT NULL,
    estado          VARCHAR(20)     NOT NULL DEFAULT 'Pendiente',
    fecha_pago         DATETIME,

    CONSTRAINT pk_pago     PRIMARY KEY (pago_id),
    CONSTRAINT uq_pago_pedido UNIQUE (pedido_id)
);

--changeset pizzeria:002
INSERT INTO pago (pedido_id, monto, metodo, estado, fecha_pago) VALUES
    (1,     16980.00,   'Credito',          'Completado',       '2026-01-10 12:05:00'),
    (2,     8990.00,    'Debito',           'Completado',       '2026-01-11 14:05:00'),
    (3,     13980.00,   'Efectivo',         'Pendiente',        NULL),
    (4,     9990.00,    'Transferencia',    'Pendiente',        NULL),
    (5,     7990.00,    'Credito',          'Rechazado',        '2026-02-10 19:01:00'),
    (6,     11980.00,   'Debito',           'Completado',       '2026-02-15 21:05:00'),
    (7,     17980.00,   'Efectivo',         'Pendiente',        NULL),
    (8,     8490.00,    'Transferencia',    'Pendiente',        NULL),
    (9,     14980.00,   'Credito',          'Completado',       '2026-03-10 20:05:00'),
    (10,    9490.00,    'Debito',           'Pendiente',        NULL);