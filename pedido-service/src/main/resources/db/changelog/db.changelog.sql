--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE pedido (
    pedido_id    BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT          NOT NULL,
    direccion_id  BIGINT          NOT NULL,
    monto_total       DECIMAL(10,2)   NOT NULL,
    fecha_creacion  DATETIME        NOT NULL,
    fecha_actualizacion  DATETIME,

    CONSTRAINT pk_pedido PRIMARY KEY (pedido_id)
);

--changeset pizzeria:002
INSERT INTO pedido (usuario_id, direccion_id, monto_total, fecha_creacion, fecha_actualizacion) VALUES
    (1,  1,  16980.00, '2026-01-10 12:00:00', '2026-01-10 13:00:00'),
    (2,  2,  8990.00, '2026-01-11 14:00:00', '2026-01-11 15:00:00'),
    (3,  3,  13980.00, '2026-02-01 18:30:00', NULL),
    (4,  4,  9990.00, '2026-02-05 20:00:00', NULL),
    (5,  5,  7990.00, '2026-02-10 19:00:00', '2026-02-10 19:05:00'),
    (1,  1,  11980.00, '2026-02-15 21:00:00', '2026-02-15 22:00:00'),
    (6,  6,  17980.00, '2026-03-01 13:00:00', NULL),
    (7,  7,  8490.00, '2026-03-05 17:00:00', NULL),
    (8,  8,  14980.00, '2026-03-10 20:00:00', '2026-03-10 21:00:00'),
    (9,  9,  9490.00, '2026-03-15 22:00:00', NULL);