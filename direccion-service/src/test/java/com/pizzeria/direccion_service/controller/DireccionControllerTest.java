package com.pizzeria.direccion_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzeria.direccion_service.dto.DireccionDTO;
import com.pizzeria.direccion_service.model.Direccion;
import com.pizzeria.direccion_service.service.DireccionService;

@WebMvcTest(DireccionController.class)
public class DireccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DireccionService direccionService;

    private Direccion direccion;
    private DireccionDTO direccionDTO;

    @BeforeEach
    void setUp() {
        direccion = new Direccion();
        direccion.setDireccionId(1L);
        direccion.setUsuarioId(1L);
        direccion.setCalle("Av. Providencia 1234");
        direccion.setComuna("Providencia");
        direccion.setEsDefault(true);

        direccionDTO = new DireccionDTO(1L, "Av. Providencia 1234",
                "Providencia", true);
    }

    @Test
    void testListarDirecciones() throws Exception {
        
        when(direccionService.getAllDirecciones()).thenReturn(List.of(direccion));

        
        mockMvc.perform(get("/direcciones"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerDireccionPorId() throws Exception {

        when(direccionService.getDireccionById(1L)).thenReturn(direccion);


        mockMvc.perform(get("/direcciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.DireccionId").value(1))
                .andExpect(jsonPath("$.Calle").value("Av. Providencia 1234"))
                .andExpect(jsonPath("$.Comuna").value("Providencia"));
    }

    @Test
    void testObtenerDireccionesPorUsuario() throws Exception {

        when(direccionService.getDireccionByUsuarioId(1L)).thenReturn(List.of(direccion));

        mockMvc.perform(get("/direcciones/usuario/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerDireccionDefaultByUsuarioId() throws Exception {
        
        when(direccionService.getDireccionDefaultByUsuarioId(1L)).thenReturn(direccion);


        mockMvc.perform(get("/direcciones/default/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.esDefault").value(true));
    }

    @Test
    void testCrearDireccion() throws Exception {

        when(direccionService.createDireccion(any(DireccionDTO.class))).thenReturn(direccion);


        mockMvc.perform(post("/direcciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(direccionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.direccionId").value(1));
    }

    @Test
    void testActualizarDireccion() throws Exception {

        when(direccionService.updateDireccion(eq(1L), any(DireccionDTO.class))).thenReturn(direccion);


        mockMvc.perform(put("/direcciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(direccionDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarDireccion() throws Exception {

        doNothing().when(direccionService).deleteDireccion(1L);

        mockMvc.perform(delete("/direcciones/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Direccion Eliminada Exitosamente"));
        verify(direccionService, times(1)).deleteDireccion(1L);
    }

}
