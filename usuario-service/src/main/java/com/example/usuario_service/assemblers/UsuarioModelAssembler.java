package com.example.usuario_service.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.usuario_service.controller.UsuarioControllerV2;
import com.example.usuario_service.model.Usuario;

@Component
public class UsuarioModelAssembler {

    public EntityModel<Usuario> toModel(Usuario usuario) {
        EntityModel<Usuario> model = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getUsuarioId()))
                        .withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios())
                        .withRel("usuarios"));

        model.add(Link.of("http://localhost:9090/direcciones/usuario/" + usuario.getUsuarioId())
                .withRel("direcciones"));

        model.add(Link.of("http://localhost:9090/pedidos/usuario/" + usuario.getUsuarioId())
                .withRel("pedidos"));

        return model;
    }

}
