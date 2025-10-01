package breaze.intro_spring.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "autor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="nacionalidad")
    private String nacionalidad;
    /*@OneToMany(mappedBy = "autor")
    private Set<Libro> libros = new HashSet<>();*/
}
