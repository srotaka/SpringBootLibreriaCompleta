    /*

 */
package com.ejercicio02.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE Libro a SET a.alta = false WHERE a.id = ?")
public class Libro {
    
    // Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private Long isbn;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private Integer anio;
    @Column(nullable = false)
    private Integer ejemplares;
    @Column(nullable = false)
    private Integer ejemplaresPrestados;
    @Column(nullable = false)
    private Integer ejemplaresRestantes;
    @Column(nullable = false)
    private Boolean alta;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Autor autor;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Editorial editorial;
    
    // Constructores
    
    //public Libro(String titulo, Integer anio, Autor autor, Editorial editorial) {
    public Libro(String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.autor = autor;
        this.editorial = editorial;
    }

    public Libro() {
    }
    
    
    // toString
    
     public String toString() {
    
        return String.format("%-10d%-40s%-10d%-10d%-10d%-10d%-40s%-40s", isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor.getNombre(), editorial.getNombre());
        
    }
    
    

}
