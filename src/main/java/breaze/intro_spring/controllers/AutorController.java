package breaze.intro_spring.controllers;

import breaze.intro_spring.entidades.Autor;
import breaze.intro_spring.services.IAutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de autores.
 * Proporciona endpoints para listar, buscar, crear, actualizar, eliminar y buscar autores por texto.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/autores")
public class AutorController {

    /**
     * Servicio para operaciones relacionadas con autores.
     */
    private final IAutorService autorService;

    /**
     * Constructor que inyecta el servicio de autores.
     * @param autorService servicio de autores
     */
    public AutorController(IAutorService autorService) {
        this.autorService = autorService;
    }

    /**
     * Obtiene la lista de todos los autores registrados.
     * Solo usuarios con el rol USER pueden acceder.
     * @return ResponseEntity con la lista de autores
     */
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> getAllAutores() {
        List<Autor> autores = autorService.obtenerAutores();
        return ResponseEntity.ok(autores);
    }

    /**
     * Busca un autor por su ID.
     * Solo usuarios con el rol USER pueden acceder.
     * @param id identificador del autor
     * @return ResponseEntity con el autor encontrado o 404 si no existe
     */
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
        return autorService.obtenerAutor(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo autor.
     * Solo usuarios con el rol ADMIN pueden acceder.
     * @param autor datos del autor a crear
     * @return ResponseEntity con el autor creado
     */
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede crear
    @PostMapping("/crear")
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) {
        return ResponseEntity.ok().body(autorService.crearAutor(autor));
    }

    /**
     * Actualiza completamente un autor existente por su ID.
     * Solo usuarios con el rol ADMIN pueden acceder.
     * @param id identificador del autor a actualizar
     * @param autorDetails datos actualizados del autor
     * @return ResponseEntity con el autor actualizado o 404 si no existe
     */
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @RequestBody Autor autorDetails) {
        return autorService.actualizarAutor(id, autorDetails)
                .map(updatedAutor -> ResponseEntity.ok().body(updatedAutor))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un autor por su ID.
     * Solo usuarios con el rol ADMIN pueden acceder.
     * @param id identificador del autor a eliminar
     * @return ResponseEntity vacío si se elimina, o 404 si no existe
     */
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        return autorService.eliminarAutor(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    /**
     * Obtiene el perfil de un autor por su nombre de usuario.
     * Solo el propio usuario o un ADMIN pueden acceder.
     * @param username nombre de usuario del autor
     * @return ResponseEntity con información del perfil
     */
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #username")
    @GetMapping("/perfil/{username}")
    public ResponseEntity<String> getPerfilAutor(@PathVariable String username) {
        return ResponseEntity.ok("Perfil del autor: " + username);
    }

    /**
     * Actualiza parcialmente los datos de un autor.
     * Permite modificar solo los campos enviados en la petición.
     * @param id identificador del autor a actualizar
     * @param autorDetails datos parciales del autor
     * @return ResponseEntity con el autor actualizado o 404 si no existe
     */
    @PatchMapping("/actualizacion_parcial/{id}")
    public ResponseEntity<Autor> actualizacionParcialAutor(@PathVariable Long id, @RequestBody Autor autorDetails) {
        return autorService.actualizacionParcial(id, autorDetails)
                .map(updateAutor-> ResponseEntity.ok().body(updateAutor))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca autores cuyo nombre o nacionalidad contenga el texto proporcionado.
     * @param texto texto a buscar
     * @return ResponseEntity con la lista de autores encontrados
     */
    @GetMapping("/buscar_por_texto")
    public ResponseEntity<List<Autor>> buscarAutoresPorTexto(@RequestParam String texto) {
        return ResponseEntity.ok(autorService.buscarAutoresPorTexto(texto));
    }
}