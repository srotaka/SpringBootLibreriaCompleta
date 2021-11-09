/*

 */
package com.ejercicio02.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE Autor a SET a.alta = false WHERE a.id = ?")
public class Autor {

    // Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean alta;
//    
//    @OneToMany(mappedBy="autor")
//    private List<Libro> libros;
    
    
    // Constructores
   

    public Autor(String nombre, List<Libro> libros) {
        this.nombre = nombre;
        //this.libros = libros;
    }

    public Autor() {
    }

    
    // toString()
    @Override
    public String toString() {
        return String.format("%-40s", nombre);
    }
}
