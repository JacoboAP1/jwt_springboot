package breaze.intro_spring.repositorios;

import breaze.intro_spring.entidades.DetalleLibro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroDetalleRepository extends JpaRepository<DetalleLibro, Long> {
}
