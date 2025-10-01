package breaze.intro_spring.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    // One-to-many to the join entity
    @OneToMany(mappedBy = "categoria")
    private Set<LibroCategoria> libroCategorias = new HashSet<>();
}