package breaze.intro_spring.services.impl;

import breaze.intro_spring.repositorios.CategoriaRepository;
import breaze.intro_spring.services.ICategoriaService;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public boolean validarExistenciaCategoria(Long categoria) {
        return categoriaRepository.existsById(categoria);
    }
}
