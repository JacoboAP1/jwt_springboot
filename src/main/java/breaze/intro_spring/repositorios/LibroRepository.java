package breaze.intro_spring.repositorios;

import breaze.intro_spring.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContaining(String titulo);
    List<Libro> findByAnioPublicacion(Integer anioPublicacion);
    List<Libro> findByAutorId(Long autorId);
}