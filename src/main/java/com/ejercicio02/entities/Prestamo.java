/*
 */
package com.ejercicio02.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE Prestamo p SET p.alta = false WHERE p.id = ?")
public class Prestamo {
    
    //Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private LocalDate fechaPrestamo;
    @Column(nullable = false)
    private LocalDate fechaDevolucion;
    @Column(nullable = false)
    private Boolean alta;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Libro libro;
    @OneToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;
    
    //Constructores

    public Prestamo() {
    }

    public Prestamo(String id, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Libro libro, Cliente cliente) {
        this.id = id;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.libro = libro;
        this.cliente = cliente;
    }
    
}
