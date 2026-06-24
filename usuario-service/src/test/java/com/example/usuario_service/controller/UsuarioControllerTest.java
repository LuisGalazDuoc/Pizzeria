package com.example.usuario_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.usuario_service.dto.UsuarioDTO;
import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setRut(12345678L);
        usuario.setDvrut("9");
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan.perez@gmail.com");
        usuario.setTelefono("+56912345678");

        usuarioDTO = new UsuarioDTO(
                12345678L, "9", "Juan Pérez",
                "juan.perez@gmail.com", "+56912345678");
    }

    @Test
    void testObtenerTodosLosUsuarios() throws Exception {
 
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(usuario));

 
        mockMvc.perform(get("/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList").isArray());
    }

    @Test
    void testObtenerUsuarioPorId() throws Exception {
 
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

 
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@gmail.com"));
    }

    @Test
    void testObtenerUsuarioPorRut() throws Exception {
 
        when(usuarioService.getUsuarioByRut(12345678L)).thenReturn(usuario);

 
        mockMvc.perform(get("/usuarios/rut/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value(12345678));
    }

    @Test
    void testCrearUsuario() throws Exception {
 
        when(usuarioService.createUsuario(any(UsuarioDTO.class))).thenReturn(usuario);

 
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void testActualizarUsuario() throws Exception {
 
        when(usuarioService.updateUsuario(eq(1L), any(UsuarioDTO.class)))
                .thenReturn(usuario);

 
        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1));
    }

    @Test
    void testEliminarUsuario() throws Exception {

        doNothing().when(usuarioService).deleteUsuario(1L);


        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario Eliminado Exitosamente"));
        verify(usuarioService, times(1)).deleteUsuario(1L);
    }

}
