package com.pizzeria.direccion_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direccion_id")
    private Long direccionId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "calle", nullable = false, length = 150)
    private String calle;

    @Column(name = "comuna", nullable = false, length = 100)
    private String comuna;

    @Column(name = "direc_default", nullable = false)
    private Boolean esDefault = false;

}
