package com.radiadores.service_auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude= "roles") // Evita bucles infinitos y errores de carga.
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Solo usa los campos marcados para comparar
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name="nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;
    @Column(name= "contrasena", nullable = false)
    private String contrasena;
    @Column(unique = true, nullable = false)
    private String correo;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name ="rol_id")
    )
    private Set<Rol> roles = new HashSet<>();
}