package breaze.intro_spring.auth;

// Importaciones de dependencias de seguridad y configuración
import lombok.RequiredArgsConstructor; // Genera constructor con los campos requeridos
import org.springframework.context.annotation.Bean; // Permite definir beans en el contexto de Spring
import org.springframework.context.annotation.Configuration; // Marca la clase como configuración de Spring
import org.springframework.security.authentication.AuthenticationManager; // Administra la autenticación
import org.springframework.security.authentication.AuthenticationProvider; // Proveedor de autenticación
import org.springframework.security.authentication.ProviderManager; // Implementación de AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Proveedor basado en DAO
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Habilita seguridad por métodos
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Configura seguridad HTTP
import org.springframework.security.config.http.SessionCreationPolicy; // Políticas de sesión
import org.springframework.security.core.userdetails.UserDetailsService; // Servicio para cargar usuarios
import org.springframework.security.crypto.password.PasswordEncoder; // Codificador de contraseñas
import org.springframework.security.web.SecurityFilterChain; // Cadena de filtros de seguridad
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Filtro de autenticación por usuario
import org.springframework.web.cors.CorsConfiguration; // Configuración CORS
import org.springframework.web.cors.CorsConfigurationSource; // Fuente de configuración CORS
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Fuente CORS basada en URL
import java.util.List; // Utilidad para listas

/**
 * Configuración principal de seguridad para la aplicación.
 * Define la cadena de filtros, el proveedor de autenticación, el manager de autenticación y la configuración CORS.
 * La autorización por roles se gestiona mediante anotaciones @PreAuthorize en los controladores.
 */
@Configuration // Indica que esta clase es de configuración de Spring
@EnableMethodSecurity // Habilita @PreAuthorize y otras anotaciones de seguridad
@RequiredArgsConstructor // Genera constructor con los campos finales
public class SecurityConfig {
    /**
     * Filtro JWT para validar tokens en cada petición.
     */
    private final JwtAuthFilter jwtFilter;
    /**
     * Servicio para cargar detalles de usuario desde la base de datos.
     */
    private final UserDetailsService uds;
    /**
     * Codificador de contraseñas (BCrypt).
     */
    private final PasswordEncoder encoder;

    /**
     * Define la cadena de filtros de seguridad y las reglas de acceso.
     * Endpoints de login y registro son públicos, el resto requiere autenticación.
     * La autorización por roles se gestiona con @PreAuthorize en los controladores.
     * @param http objeto de configuración de seguridad HTTP
     * @return SecurityFilterChain configurada
     * @throws Exception si ocurre un error en la configuración
     */
    @Bean // Define un bean gestionado por Spring
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configura la seguridad HTTP
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (no necesario para APIs REST)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones, API stateless
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sin autenticación requerida)
                        .requestMatchers("/auth/login", "/auth/register").permitAll() // Permite login y registro
                        .requestMatchers("/micro/auth/login", "/micro/auth/register").permitAll() // Permite login y registro alternativo
                        // Todos los demás endpoints requieren autenticación
                        // La autorización por roles se maneja con @PreAuthorize en los controladores
                        .anyRequest().authenticated() // Requiere autenticación para el resto
                )
                .authenticationProvider(authenticationProvider()) // Usa el proveedor de autenticación definido
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT antes del filtro de usuario
                .build(); // Construye la cadena de filtros
    }

    /**
     * Define el proveedor de autenticación usando el servicio de usuarios y el codificador de contraseñas.
     * @return AuthenticationProvider configurado
     */
    @Bean // Define un bean gestionado por Spring
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Proveedor basado en DAO
        provider.setUserDetailsService(uds); // Usa el servicio de usuarios
        provider.setPasswordEncoder(encoder); // Usa el codificador de contraseñas
        return provider; // Retorna el proveedor configurado
    }

    /**
     * Define el manager de autenticación que utiliza el proveedor configurado.
     * @return AuthenticationManager configurado
     */
    @Bean // Define un bean gestionado por Spring
    AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider()); // Usa el proveedor de autenticación
    }

    /**
     * Configuración de CORS para permitir peticiones desde el frontend.
     * Ajusta los orígenes, métodos y cabeceras permitidas.
     * @return CorsConfigurationSource configurada
     */
    @Bean // Define un bean gestionado por Spring
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration(); // Crea la configuración CORS
        cfg.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:4200", "http://127.0.0.1:3000")); // Orígenes permitidos
        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS")); // Métodos permitidos
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","Accept")); // Cabeceras permitidas
        cfg.setExposedHeaders(List.of("Authorization")); // Cabeceras expuestas
        cfg.setAllowCredentials(true); // Permite credenciales
        cfg.setMaxAge(3600L); // Tiempo de cacheo de CORS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Fuente basada en URL
        source.registerCorsConfiguration("/**", cfg); // Aplica la configuración a todas las rutas
        return source; // Retorna la fuente de configuración
    }
}