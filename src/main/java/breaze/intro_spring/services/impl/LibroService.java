package breaze.intro_spring.services.impl;

import breaze.intro_spring.dto.CrearLibroInDTO;
import breaze.intro_spring.dto.CrearLibroOutDTO;
import breaze.intro_spring.dto.LibroCategoriaDTO;
import breaze.intro_spring.entidades.Autor;
import breaze.intro_spring.entidades.Categoria;
import breaze.intro_spring.entidades.Libro;
import breaze.intro_spring.entidades.LibroCategoria;
import breaze.intro_spring.repositorios.LibroRepository;
import breaze.intro_spring.services.IAutorService;
import breaze.intro_spring.services.ICategoriaService;
import breaze.intro_spring.services.ILibroCategoriaService;
import breaze.intro_spring.services.ILibroService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final IAutorService autorService;
    private final ICategoriaService categoriaService;
    private final ILibroCategoriaService libroCategoriaService;
    /**
     * Constructor que inyecta el repositorio de libros.
     * @param libroRepository repositorio de libros
     */
    public LibroService(LibroRepository libroRepository, IAutorService autorService, ICategoriaService categoriaService, ILibroCategoriaService libroCategoriaService) {
        this.libroCategoriaService = libroCategoriaService;
        this.categoriaService = categoriaService;
        this.autorService = autorService;
        this.libroRepository = libroRepository;
    }

    /**
     * Crea un nuevo libro.
     * @param crearLibroInDTO datos del libro a crear
     * @return libro creado
     */
    @Override
    public CrearLibroOutDTO crearLibro(CrearLibroInDTO crearLibroInDTO) {
        CrearLibroOutDTO crearLibroOutDTO = new CrearLibroOutDTO();
        if(!this.autorService.existeAutor(crearLibroInDTO.getAutor())) {
            crearLibroOutDTO.setMensajeError("No existe el autor ingresado");
            return crearLibroOutDTO;
        }
        if (!validarExistenciCategorias(crearLibroInDTO.getCategorias())) {
            crearLibroOutDTO.setMensajeError("Alguna de las categorias ingresadas no existe");
            return crearLibroOutDTO;
        }
        Libro libro = new Libro();
        libro.setTitulo(crearLibroInDTO.getTitulo());
        libro.setAnioPublicacion(crearLibroInDTO.getAnioPublicacion());
        Autor autor = new Autor();
        autor.setId(crearLibroInDTO.getAutor());
        libro.setAutor(autor);
        Libro libroCreado = this.libroRepository.save(libro);
        if (libroCreado.getId() != null) {
            Set<LibroCategoria> libroCategorias = crearLibroInDTO.getCategorias().stream().map(libroCategoriaDTO -> {
                return libroCategoriaDTOToEntity(libroCategoriaDTO, libroCreado.getId());
            }).collect(Collectors.toSet());
            libroCreado.setLibroCategorias(libroCategorias);
            this.libroCategoriaService.crearLibroCategoriaBatch(libroCategorias);
            crearLibroOutDTO.setExitoso(true);
        }
        return crearLibroOutDTO;
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

    private LibroCategoria libroCategoriaDTOToEntity(LibroCategoriaDTO dto, Long idLibro) {
        if (dto == null) return null;
        LibroCategoria libroCategoria = new LibroCategoria();
        Libro libro = new Libro();
        libro.setId(idLibro);
        libroCategoria.setLibro(libro);
        Categoria categoria = new Categoria();
        categoria.setId(dto.getCategoria());
        libroCategoria.setCategoria(categoria);
        libroCategoria.setPrioridad(dto.getPrioridad());
        libroCategoria.setAddedAt(LocalDateTime.now());
        libroCategoria.setComentario(dto.getComentario());
        return libroCategoria;
    }

    private boolean validarExistenciCategorias(Set<LibroCategoriaDTO> categorias) {
        int noExistentes = 0;
        for (LibroCategoriaDTO categoria : categorias) {
            if (!this.categoriaService.validarExistenciaCategoria(categoria.getCategoria())) {
                noExistentes++;
            }
        }
        return noExistentes == 0;
    }
}
