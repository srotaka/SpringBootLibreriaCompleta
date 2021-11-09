/*

 */
package com.ejercicio02.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE Usuario u SET u.alta = false WHERE u.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    // Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false, unique = true)
    private String correo;
    @Column(nullable = false)
    private String clave;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @LastModifiedDate
    private LocalDateTime fechaModificacion;
    private Boolean alta;
    
    //Constructores

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String correo, String clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
    }
    
    
}
