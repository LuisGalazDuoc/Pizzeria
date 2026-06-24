package com.example.usuario_service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.usuario_service.dto.UsuarioDTO;
import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.repository.UsuarioRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, 12345678L, "9", "Juan Pérez",
                "juan.perez@gmail.com", "+56912345678");

        usuarioDTO = new UsuarioDTO(12345678L, "9", "Juan Pérez",
                "juan.perez@gmail.com", "+56912345678");
    }

    @Test
    void testListarUsuarios() {
        // Given
        List<Usuario> usuarios = List.of(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // When
        List<Usuario> resultado = usuarioService.getAllUsuarios();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan Pérez");
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testReturnUsuarioSiIdExiste() {
 
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

 
        Usuario resultado = usuarioService.getUsuarioById(1L);

 
        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsuarioId()).isEqualTo(1L);
        assertThat(resultado.getEmail()).isEqualTo("juan.perez@gmail.com");
    }

    @Test
    void testCreateUsuario() {
 
        when(usuarioRepository.existsByRut(12345678L)).thenReturn(false);
        when(usuarioRepository.existsByEmail("juan.perez@gmail.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

 
        Usuario resultado = usuarioService.createUsuario(usuarioDTO);

 
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRut()).isEqualTo(12345678L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario() {
 
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

 
        usuarioService.deleteUsuario(1L);

 
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    //VALIDACIONES ESPECIFICAS

    private boolean ValidadorRut(Long rut, String dv) {
        if (rut == null || dv == null) return false;

        int suma = 0;
        int multiplicador = 2;
        long rutTemp = rut;

        while (rutTemp > 0) {
            suma += (int) (rutTemp % 10) * multiplicador;
            rutTemp /= 10;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }

        int resto = 11 - (suma % 11);
        String dvCalculado = switch (resto) {
            case 11 -> "0";
            case 10 -> "K";
            default -> String.valueOf(resto);
        };

        return dvCalculado.equalsIgnoreCase(dv);
    }

    @Test
    void validarRutConDvNumerico() {
        Long rut = 12345678L;
        String dv = "5";

        boolean result = ValidadorRut(rut, dv);

        assertThat(result).isTrue();
    }

    @Test
    void validarRutConDvLetra() {
        Long rut = 11223344L;

        boolean result = ValidadorRut(rut, "K");

        assertThat(result).isTrue();
    }

    @Test
    void crearUsuarioSiRutEsValido() {
        UsuarioDTO dto = new UsuarioDTO(
                12345678L, "5", "Juan Pérez",
                "juan@gmail.com", "+56912345678");

        Usuario u = new Usuario();
        u.setUsuarioId(1L);
        u.setRut(dto.getRut());
        u.setDvrut(dto.getDvrut());

        when(usuarioRepository.existsByRut(any())).thenReturn(false);
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(usuarioRepository.save(any())).thenReturn(u);

        Usuario resultado = usuarioService.createUsuario(dto);

        assertThat(ValidadorRut(resultado.getRut(), resultado.getDvrut())).isTrue();
        verify(usuarioRepository, times(1)).save(any());
    }

}
