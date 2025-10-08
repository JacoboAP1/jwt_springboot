package breaze.intro_spring.services.impl;

import breaze.intro_spring.entidades.LibroCategoria;
import breaze.intro_spring.repositorios.LibroCategoriaRepository;
import breaze.intro_spring.services.ILibroCategoriaService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LibroCategoriaService implements ILibroCategoriaService {
    private final LibroCategoriaRepository libroCategoriaRepository;

    public LibroCategoriaService(LibroCategoriaRepository libroCategoriaRepository) {
        this.libroCategoriaRepository = libroCategoriaRepository;
    }
    @Override
    public void crearLibroCategoriaBatch(Set<LibroCategoria> categoriaLibros) {
        this.libroCategoriaRepository.saveAll(categoriaLibros);
    }
}
