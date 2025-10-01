package breaze.intro_spring.services;



import breaze.intro_spring.entidades.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroService {
    public Libro crearLibro(Libro libro);
    public List<Libro> obtenerLibros();
    public Optional<Libro> actualizarParcial(Long id, Libro libro);
}
