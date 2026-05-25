--liquibase formatted sql

--changeset pizzeria:001
CREATE TABLE direccion (
    direccion_id  BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT          NOT NULL,
    calle      VARCHAR(150)    NOT NULL,
    comuna    VARCHAR(100)    NOT NULL,
    direc_default  BOOLEAN         NOT NULL DEFAULT FALSE,

    CONSTRAINT pk_direccion PRIMARY KEY (direccion_id)
);

--changeset pizzeria:002
INSERT INTO direccion (usuario_id, calle, comuna, direc_default) VALUES
    (1,  'Av. Providencia 1234',   'Providencia',   TRUE),
    (2,  'Calle Las Flores 567',   'Ñuñoa',         TRUE),
    (3,  'Pasaje Los Álamos 890',  'La Florida',    TRUE),
    (4,  'Av. Maipú 321',          'Maipú',         TRUE),
    (5,  'Calle Principal 654',    'Las Condes',    TRUE),
    (6,  'Av. Vitacura 987',       'Vitacura',      TRUE),
    (7,  'Calle Los Pinos 147',    'Pudahuel',      TRUE),
    (8,  'Av. Central 258',        'Peñalolén',     TRUE),
    (9,  'Pasaje El Roble 369',    'Macul',         TRUE),
    (10, 'Av. Grecia 741',         'Ñuñoa',         TRUE);