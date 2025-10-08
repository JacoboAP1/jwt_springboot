package breaze.intro_spring.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CrearLibroInDTO {

    //Campos propios del libro
    private String titulo;
    private Integer anioPublicacion;
    private Long autor;
    //detalle libro
    private String isbn;
    private Integer numPaginas;
    private String idioma;
    //Categorias con toda su información
    private Set<LibroCategoriaDTO> categorias;
}
