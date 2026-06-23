package com.pizzeria.direccion_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.pizzeria.direccion_service.controller.DireccionControllerV2;
import com.pizzeria.direccion_service.model.Direccion;

@Component
public class DireccionModelAssembler {

    public EntityModel<Direccion> toModel(Direccion direccion) {
        EntityModel<Direccion> model = EntityModel.of(direccion,
                linkTo(methodOn(DireccionControllerV2.class).getDireccionById(direccion.getDireccionId()))
                        .withSelfRel(),
                linkTo(methodOn(DireccionControllerV2.class).getAllDirecciones())
                        .withRel("direcciones"),
                linkTo(methodOn(DireccionControllerV2.class).getDireccionByUsuarioId(direccion.getUsuarioId()))
                        .withRel("direcciones-por-usuario"));

        model.add(Link.of("http://localhost:8080/usuarios/" + direccion.getUsuarioId())
                .withRel("usuarios"));

        return model;
    }

}
