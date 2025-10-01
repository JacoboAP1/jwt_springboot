package breaze.intro_spring.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    @OneToMany(mappedBy = "libro")
    private Set<LibroCategoria> libroCategorias = new HashSet<>();
    @OneToOne(mappedBy = "libro")
    private DetalleLibro detalle;
}
