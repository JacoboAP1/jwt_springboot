package breaze.intro_spring.controllers;

import breaze.intro_spring.dto.CrearLibroInDTO;
import breaze.intro_spring.dto.CrearLibroOutDTO;
import breaze.intro_spring.entidades.Libro;
import breaze.intro_spring.services.ILibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de libros.
 * Proporciona endpoints para crear, consultar y actualizar libros de forma parcial.
 * Utiliza un servicio para la lógica de negocio y acceso a datos.
 */
@RestController
@RequestMapping("/libro")
public class LibroController {
    /**
     * Servicio para operaciones relacionadas con libros.
     */
    private ILibroService libroService;

    /**
     * Constructor que inyecta el servicio de libros.
     * @param libroService servicio de libros
     */
    public LibroController(ILibroService libroService) {
        this.libroService = libroService;
    }

    /**
     * Crea un nuevo libro.
     * @param  crearLibroInDTO del libro a crear
     * @return ResponseEntity con el libro creado
     */
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CrearLibroOutDTO> crearLibro(@RequestBody CrearLibroInDTO crearLibroInDTO) {
        return ResponseEntity.ok(this.libroService.crearLibro(crearLibroInDTO));
    }

    /**
     * Consulta todos los libros registrados.
     * @return ResponseEntity con la lista de libros
     */
    @GetMapping("/consultar")
    public ResponseEntity<List<Libro>> consultarTodos() {
        List<Libro> libros = libroService.obtenerLibros();
        return ResponseEntity.ok(libros);
    }

    /**
     * Actualiza parcialmente los datos de un libro existente por su ID.
     * Permite modificar solo los campos enviados en la petición.
     * @param id identificador del libro a actualizar
     * @param libroDetails datos parciales del libro
     * @return ResponseEntity con el libro actualizado o 404 si no existe
     */
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<Libro> actualizarLibroParcial(@PathVariable Long id, @RequestBody Libro libroDetails) {
        return libroService.actualizarParcial(id, libroDetails)
                .map(updatedLibro -> ResponseEntity.ok().body(updatedLibro))
                .orElse(ResponseEntity.notFound().build());
    }
}
