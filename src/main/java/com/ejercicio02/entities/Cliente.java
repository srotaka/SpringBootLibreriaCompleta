/*

 */
package com.ejercicio02.entities;

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
@SQLDelete(sql = "UPDATE Cliente c SET c.alta = false WHERE c.id = ?")
public class Cliente {
    
    // Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private Long dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private Boolean alta;
    
    // Constructores
    public Cliente() {
    }

    public Cliente(Long dni, String nombre, String apellido, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    
    
}
