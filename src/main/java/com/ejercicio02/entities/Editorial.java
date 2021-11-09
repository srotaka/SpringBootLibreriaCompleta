/*

 */
package com.ejercicio02.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE Editorial a SET a.alta = false WHERE a.id = ?")
public class Editorial {

    // Atributos

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean alta;
//    @OneToMany(mappedBy="editorial")
//    private List<Libro> libros;

    
    
    // Constructores
    public Editorial(String nombre, List<Libro> libros) {
        this.nombre = nombre;
        //this.libros = libros;
    }

      public Editorial() {
    }

    // toString()
    @Override
    public String toString() {
        return String.format("%-40s", nombre);
    }

}
