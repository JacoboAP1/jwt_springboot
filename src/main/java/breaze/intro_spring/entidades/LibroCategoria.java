package breaze.intro_spring.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "libro_categoria")
public class LibroCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "comentario")
    private String comentario;
}