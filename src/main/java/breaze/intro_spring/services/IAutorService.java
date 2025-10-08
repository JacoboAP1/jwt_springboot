package breaze.intro_spring.services;

import breaze.intro_spring.entidades.Autor;
import java.util.List;
import java.util.Optional;

public interface IAutorService {
    public List<Autor> obtenerAutores();
    public Optional<Autor> obtenerAutor(Long id);
    public  Autor crearAutor(Autor autor);
    public boolean eliminarAutor(Long id);
    public Optional<Autor> actualizarAutor(Long id, Autor autor);
    public Optional<Autor> actualizacionParcial(Long id, Autor autor);
    public Integer contarAutoresPorNacionalidad(String nacionalidad);
    public List<Autor> buscarAutoresPorTexto(String text);
    public boolean existeAutor(Long id);
}
