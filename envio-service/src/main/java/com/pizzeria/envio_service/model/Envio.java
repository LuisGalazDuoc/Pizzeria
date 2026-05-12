package com.pizzeria.envio_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envio_id")
    private Long envioId;

    @Column(name = "pedido_id", nullable = false, unique = true)
    private Long pedidoId;
    
    /*  Atributos a implementar con los siguientes microservicios (repartidor-service) (sig. evaluacion)
    @Column(name = "nombre_repartidor", nullable = false, length = 100)
    private String nombreRepartidor;

    @Column(name = "numero_repartidor", nullable = false, length = 15)
    private String numeroRepartidor;
    
        Mismo caso, buscar la implementacion de clases enum
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EnvioEstado estado = EnvioEstado.ASSIGNED;

        Este metodo sera usado cuando el metodo de arriba sea implementado
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    */

    @Column(name = "fecha_envio", nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = LocalDateTime.now();
    }

}
