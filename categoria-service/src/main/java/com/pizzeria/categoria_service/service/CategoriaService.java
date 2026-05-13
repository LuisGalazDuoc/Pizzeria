package com.pizzeria.categoria_service.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.pizzeria.categoria_service.dto.CategoriaDTO;
import com.pizzeria.categoria_service.model.Categoria;
import com.pizzeria.categoria_service.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Categoría no encontrada con ID: " + id
                ));
    }

    public Categoria getCategoriaByNombre(String name) {
        return categoriaRepository.findByNombre(name)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Categoría no encontrada con nombre: " + name
                ));
    }

    public Categoria createCategoria(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre()))
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + dto.getNombre());

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());

        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Categoría no encontrada con ID: " + id
                ));

        if (!categoria.getNombre().equals(dto.getNombre()) &&
                categoriaRepository.existsByNombre(dto.getNombre()))
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + dto.getNombre());

        categoria.setNombre(dto.getNombre());

        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new IllegalArgumentException("Categoría no encontrada con ID: " + id);
        categoriaRepository.deleteById(id);
    }

}
