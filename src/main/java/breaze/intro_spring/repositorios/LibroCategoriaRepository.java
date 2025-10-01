package breaze.intro_spring.repositorios;

import breaze.intro_spring.entidades.LibroCategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroCategoriaRepository extends JpaRepository<LibroCategoria, Long> {
}
