--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE usuario (
    usuario_id BIGINT NOT NULL AUTO_INCREMENT,
    rut BIGINT NOT NULL,
    dvrut VARCHAR(1) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),

    CONSTRAINT pk_usuario PRIMARY KEY (usuario_id),
    CONSTRAINT uq_usuario_rut UNIQUE (rut),
    CONSTRAINT uq_usuario_email UNIQUE (email)
);

--changeset pizzeria:002
INSERT INTO usuario (rut, dvrut, nombre, email, telefono) VALUES
    (12345678, '9', 'Juan Pérez',       'juan.perez@gmail.com',       '+56912345678'),
    (23456789, '0', 'María González',   'maria.gonzalez@gmail.com',   '+56923456789'),
    (34567890, '1', 'Carlos Rodríguez', 'carlos.rodriguez@gmail.com', '+56934567890'),
    (45678901, '2', 'Ana Martínez',     'ana.martinez@gmail.com',     '+56945678901'),
    (56789012, '3', 'Luis Sánchez',     'luis.sanchez@gmail.com',     '+56956789012'),
    (67890123, '4', 'Sofía López',      'sofia.lopez@gmail.com',      '+56967890123'),
    (78901234, '5', 'Diego Torres',     'diego.torres@gmail.com',     '+56978901234'),
    (89012345, '6', 'Valentina Flores', 'valentina.flores@gmail.com', '+56989012345'),
    (90123456, '7', 'Matías Herrera',   'matias.herrera@gmail.com',   '+56990123456'),
    (11223344, 'K', 'Camila Vargas',    'camila.vargas@gmail.com',    '+56911223344');