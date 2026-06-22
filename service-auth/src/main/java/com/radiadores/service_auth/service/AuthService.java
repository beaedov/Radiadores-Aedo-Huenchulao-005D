package com.radiadores.service_auth.service;

import com.radiadores.service_auth.model.Rol;
import com.radiadores.service_auth.model.Usuario;
import com.radiadores.service_auth.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTRO: encripta la contraseña y guarda el usuario
    public String registrar(Usuario usuario) {
        // Toma la contraseña en texto plano y la encripta
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        // Guarda el usuario con la contraseña ya encriptada
        usuarioRepository.save(usuario);
        return "Usuario registrado exitosamente";
    }

    // LOGIN: valida usuario y contraseña, y devuelve un token
    public String login(String username, String password) {
        Usuario user = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // Compara la contraseña ingresada con la encriptada en la BD
        if (passwordEncoder.matches(password, user.getContrasena())) {
            // Convierte la lista de Rol a una lista de textos con los nombres
            List<String> roles = user.getRoles().stream()
                    .map(Rol::getNombreRol)
                    .collect(Collectors.toList());

            return jwtService.generarToken(username, roles);
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}
