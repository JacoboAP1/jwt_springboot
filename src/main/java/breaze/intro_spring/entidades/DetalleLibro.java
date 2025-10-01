package breaze.intro_spring.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_libro")
public class DetalleLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "num_paginas")
    private Integer numPaginas;

    @Column(name = "idioma")
    private String idioma;

    @OneToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
}