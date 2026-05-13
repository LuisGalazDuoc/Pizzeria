package com.pizzeria.envio_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {

    private Long pedidoId;
    /*
    private String nombreRepartidor;  )---> Trabajar para evaluacion 3   
    private String numeroRepartidor;  )  

    private DeliveryStatus status;   --->    Trabajar con enum en atributos de Envio
    */

}
