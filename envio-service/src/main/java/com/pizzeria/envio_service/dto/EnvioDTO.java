package com.pizzeria.envio_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {

    @NotNull(message = "El ID de la orden es obligatorio")
    @Positive(message = "El ID de la orden debe ser positivo")
    private Long pedidoId;
    /*
    private String nombreRepartidor;  )---> Trabajar para evaluacion 3   
    private String numeroRepartidor;  )  

    private DeliveryStatus status;   --->    Trabajar con enum en atributos de Envio
    */

}
