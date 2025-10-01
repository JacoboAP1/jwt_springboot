package breaze.intro_spring.services.impl;

import breaze.intro_spring.entidades.Libro;
import breaze.intro_spring.repositorios.LibroRepository;
import breaze.intro_spring.services.ILibroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de libros.
 * Proporciona la lógica de negocio para la gestión de libros, incluyendo operaciones de creación, consulta y actualización parcial.
 * Utiliza LibroRepository para el acceso a datos.
 */
@Service
public class LibroService implements ILibroService {
    /**
     * Repositorio para la gestión de libros en la base de datos.
     */
    private final LibroRepository libroRepository;

    /**
     * Constructor que inyecta el repositorio de libros.
     * @param libroRepository repositorio de libros
     */
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * Crea un nuevo libro.
     * @param libro datos del libro a crear
     * @return libro creado
     */
    @Override
    public Libro crearLibro(Libro libro) {
        return this.libroRepository.save(libro);
    }

    /**
     * Obtiene la lista de todos los libros registrados.
     * @return lista de libros
     */
    @Override
    public List<Libro> obtenerLibros() {
        return this.libroRepository.findAll();
    }

    /**
     * Actualiza parcialmente los datos de un libro existente por su ID.
     * Solo modifica los campos enviados en la petición.
     * @param id identificador del libro a actualizar
     * @param libro datos parciales del libro
     * @return Optional con el libro actualizado o vacío si no existe
     */
    @Override
    public Optional<Libro> actualizarParcial(Long id, Libro libro) {
        return libroRepository.findById(id)
                .map(existingLibro -> {
                    if (libro.getTitulo() != null) {
                        existingLibro.setTitulo(libro.getTitulo());
                    }
                    if (libro.getAnioPublicacion() != null) {
                        existingLibro.setAnioPublicacion(libro.getAnioPublicacion());
                    }
                    if(libro.getAutor() != null) {
                        existingLibro.setAutor(libro.getAutor());
                    }
                    return libroRepository.save(existingLibro);
                });
    }
}
