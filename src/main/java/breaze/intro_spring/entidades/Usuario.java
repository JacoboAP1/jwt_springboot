package breaze.intro_spring.entidades;

// Importaciones para JPA, Lombok y Spring Security
import jakarta.persistence.*; // Anotaciones para entidades y relaciones
import lombok.Data; // Genera getters/setters, equals, hashCode, toString
import org.springframework.security.core.GrantedAuthority; // Representa una autoridad (rol)
import org.springframework.security.core.userdetails.UserDetails; // Interfaz para usuarios autenticables

import java.util.Collection; // Colección para roles
import java.util.HashSet; // Implementación de Set
import java.util.List; // Utilidad para listas
import java.util.Set; // Interfaz para roles
import java.util.stream.Collectors; // Utilidad para transformar colecciones

@Entity // Marca la clase como entidad JPA
@Table(name = "usuario") // Define la tabla en la base de datos
@Data // Lombok: genera getters/setters y otros métodos
public class Usuario implements UserDetails { // Implementa UserDetails para integración con Spring Security
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id; // Identificador único del usuario

    @Column(nullable = false, unique = true, length = 100) // Username único y obligatorio
    private String username; // Nombre de usuario

    @Column(nullable = false) // Contraseña obligatoria
    private String password; // Contraseña encriptada

    private Boolean activo = true; // Indica si el usuario está activo

    @ManyToMany(fetch = FetchType.EAGER) // Relación muchos a muchos con roles, carga inmediata
    @JoinTable(
            name = "usuario_rol", // Tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"), // FK usuario
            inverseJoinColumns = @JoinColumn(name = "rol_id") // FK rol
    )
    private Set<Rol> roles = new HashSet<>(); // Conjunto de roles asignados al usuario

    // Devuelve las autoridades (roles) del usuario para Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Transforma cada rol en una autoridad con prefijo "ROLE_"
        return roles.stream()
                .map(r -> (GrantedAuthority) () -> "ROLE_" + r.getNombre())
                .collect(Collectors.toSet());
    }

    // Métodos requeridos por UserDetails para el control de la cuenta
    @Override public boolean isAccountNonExpired() { return true; } // La cuenta nunca expira
    @Override public boolean isAccountNonLocked() { return true; } // La cuenta nunca se bloquea
    @Override public boolean isCredentialsNonExpired() { return true; } // Las credenciales nunca expiran
    @Override public boolean isEnabled() { return activo; } // El usuario está habilitado si activo es true
}