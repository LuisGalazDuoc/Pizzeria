package com.pizzeria.categoria_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

import com.pizzeria.categoria_service.dto.CategoriaDTO;
import com.pizzeria.categoria_service.model.Categoria;
import com.pizzeria.categoria_service.service.CategoriaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaService categoriaService;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setCategoriaId(1L);
        categoria.setNombre("Pizzas");

        categoriaDTO = new CategoriaDTO("Pizzas");
    }

    @Test
    void testObtenerTodasLasCategorias() throws Exception {

        when(categoriaService.getAllCategorias()).thenReturn(List.of(categoria));


        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testObtenerCategoriaPorId() throws Exception {

        when(categoriaService.getCategoriaById(1L)).thenReturn(categoria);


        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoriaId").value(1))
                .andExpect(jsonPath("$.nombre").value("Pizzas"));
    }

    @Test
    void testObtenerCategoriaPorNombre() throws Exception {

        when(categoriaService.getCategoriaByNombre("Pizzas")).thenReturn(categoria);


        mockMvc.perform(get("/categorias/nombre/Pizzas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pizzas"));
    }

    @Test
    void testCrearCategoria() throws Exception {

        when(categoriaService.createCategoria(any(CategoriaDTO.class))).thenReturn(categoria);


        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pizzas"));
    }

    @Test
    void testActualizarCategoria() throws Exception {

        when(categoriaService.updateCategoria(eq(1L), any(CategoriaDTO.class))).thenReturn(categoria);


        mockMvc.perform(put("/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaDTO)))
                .andExpect(status().isOk());
    }
    
    @Test
    void testEliminarCategoria() throws Exception {

        doNothing().when(categoriaService).deleteCategoria(1L);


        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Categoria Eliminada Exitosamente"));
        verify(categoriaService, times(1)).deleteCategoria(1L);
    }
}
