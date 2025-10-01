package breaze.intro_spring.repositorios;

import breaze.intro_spring.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    public List<Autor> findByNacionalidad(String nacionalidad);
    public List<Autor> findByNombreContainingIgnoreCase(String text);
}
