package com.pizzeria.producto_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzeria.producto_service.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByDisponibleTrue();
    boolean existsByCategoriaId(Long categoriaId);

}
