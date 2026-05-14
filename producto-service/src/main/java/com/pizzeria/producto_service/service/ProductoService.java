package com.pizzeria.producto_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pizzeria.producto_service.dto.ProductoDTO;
import com.pizzeria.producto_service.model.Producto;
import com.pizzeria.producto_service.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final WebClient categoriaWebClient;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado con ID: " + id
                ));
    }

    public List<Producto> getProductosByCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public List<Producto> getProductosDisponibles() {
        return productoRepository.findByDisponibleTrue();
    }

    public Producto createProducto(ProductoDTO dto) {
        validateCategoriaExists(dto.getCategoriaId());

        Producto producto = new Producto();
        producto.setCategoriaId(dto.getCategoriaId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setDisponible(dto.getDisponible());

        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado con ID: " + id
                ));

        validateCategoriaExists(dto.getCategoriaId());

        producto.setCategoriaId(dto.getCategoriaId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setDisponible(dto.getDisponible());

        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id))
            throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
        productoRepository.deleteById(id);
    }

    // Comunicacion con ms categoria
    private void validateCategoriaExists(Long categoriaId) {
        try {
            categoriaWebClient.get()
                    .uri("/categorias/{id}", categoriaId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException(
                    "La categoría con ID " + categoriaId + " no existe en la base de datos"
            );
        }
    }

}
