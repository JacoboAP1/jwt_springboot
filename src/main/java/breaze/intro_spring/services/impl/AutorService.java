package breaze.intro_spring.services.impl;

import breaze.intro_spring.entidades.Autor;
import breaze.intro_spring.repositorios.AutorRepository;
import breaze.intro_spring.services.IAutorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de autores.
 * Proporciona la lógica de negocio para la gestión de autores, incluyendo operaciones CRUD y búsquedas personalizadas.
 * Utiliza AutorRepository para el acceso a datos.
 */
@Service
public class AutorService implements IAutorService {
    /**
     * Repositorio para la gestión de autores en la base de datos.
     */
    private AutorRepository autorRepository;

    /**
     * Constructor que inyecta el repositorio de autores.
     * @param autorRepository repositorio de autores
     */
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * Obtiene la lista de todos los autores registrados.
     * @return lista de autores
     */
    @Override
    public List<Autor> obtenerAutores() {
        return this.autorRepository.findAll();
    }

    /**
     * Busca un autor por su ID.
     * @param id identificador del autor
     * @return Optional con el autor encontrado o vacío si no existe
     */
    @Override
    public Optional<Autor> obtenerAutor(Long id) {
        return this.autorRepository.findById(id);
    }

    /**
     * Crea un nuevo autor.
     * @param autor datos del autor a crear
     * @return autor creado
     */
    public Autor crearAutor(Autor autor) {
        return this.autorRepository.save(autor);
    }

    /**
     * Elimina un autor por su ID.
     * @param id identificador del autor a eliminar
     * @return true si se eliminó, false si no existe
     */
    @Override
    public boolean eliminarAutor(Long id) {
        return this.autorRepository.findById(id).map(autor -> {
            autorRepository.delete(autor);
            return true;
        }).orElse(false);
    }

    /**
     * Actualiza completamente los datos de un autor existente por su ID.
     * @param id identificador del autor a actualizar
     * @param autor datos actualizados del autor
     * @return Optional con el autor actualizado o vacío si no existe
     */
    @Override
    public Optional<Autor> actualizarAutor(Long id, Autor autor) {
         return autorRepository.findById(id)
                 .map(existingAutor -> {
                        existingAutor.setNombre(autor.getNombre());
                        existingAutor.setNacionalidad(autor.getNacionalidad());
                        return autorRepository.save(existingAutor);
                 });
    }

    /**
     * Actualiza parcialmente los datos de un autor existente por su ID.
     * Solo modifica los campos enviados en la petición.
     * @param id identificador del autor a actualizar
     * @param autor datos parciales del autor
     * @return Optional con el autor actualizado o vacío si no existe
     */
    @Override
    public Optional<Autor> actualizacionParcial(Long id, Autor autor) {
        return autorRepository.findById(id)
                .map(existingAutor -> {
                    if (autor.getNombre() != null) {
                        existingAutor.setNombre(autor.getNombre());
                    }
                    if (autor.getNacionalidad() != null) {
                        existingAutor.setNacionalidad(autor.getNacionalidad());
                    }
                    return autorRepository.save(existingAutor);
                });
    }

    /**
     * Cuenta la cantidad de autores por nacionalidad.
     * @param nacionalidad nacionalidad a buscar
     * @return número de autores con esa nacionalidad
     */
    @Override
    public Integer contarAutoresPorNacionalidad(String nacionalidad){
        return autorRepository.findByNacionalidad(nacionalidad).size();
    }

    /**
     * Busca autores cuyo nombre contenga el texto proporcionado (ignorando mayúsculas/minúsculas).
     * @param text texto a buscar
     * @return lista de autores encontrados
     */
    @Override
    public List<Autor> buscarAutoresPorTexto(String text){
        return autorRepository.findByNombreContainingIgnoreCase(text);
    }

    @Override
    public boolean existeAutor(Long id) {
        return this.autorRepository.existsById(id);
    }

}
