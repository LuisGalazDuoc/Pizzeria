package com.pizzeria.categoria_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.categoria_service.controller.CategoriaControllerV2;
import com.pizzeria.categoria_service.model.Categoria;

@Component
public class CategoriaModelAssembler {

    public EntityModel<Categoria> toModel(Categoria categoria) {
        EntityModel<Categoria> model = EntityModel.of(categoria,
                linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(categoria.getCategoriaId()))
                        .withSelfRel(),
                linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias())
                        .withRel("categorias"));

        model.add(Link.of("http://localhost:9090/productos/categoria/" + categoria.getCategoriaId())
                .withRel("productos"));

        return model;
    }

}
