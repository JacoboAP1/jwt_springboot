package breaze.intro_spring.services;

import breaze.intro_spring.entidades.LibroCategoria;

import java.util.Set;

public interface ILibroCategoriaService {
    public void crearLibroCategoriaBatch(Set<LibroCategoria> categoriasLibro);
}
