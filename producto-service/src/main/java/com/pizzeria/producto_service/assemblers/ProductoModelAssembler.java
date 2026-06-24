package com.pizzeria.producto_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.producto_service.controller.ProductoControllerV2;
import com.pizzeria.producto_service.model.Producto;

@Component
public class ProductoModelAssembler {

    public EntityModel<Producto> toModel(Producto producto) {
        EntityModel<Producto> model = EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getProductoId()))
                        .withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos())
                        .withRel("productos"),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoryId(producto.getCategoriaId()))
                        .withRel("productos-por-categoria"));

        model.add(Link.of("http://localhost:9090/categorias/" + producto.getCategoriaId())
                .withRel("categoria"));

        return model;
    }

}
