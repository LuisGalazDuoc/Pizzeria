package com.example.usuario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long rut;
    private String dvrut;
    private String nombre;
    private String email;
    private String telefono;

}
