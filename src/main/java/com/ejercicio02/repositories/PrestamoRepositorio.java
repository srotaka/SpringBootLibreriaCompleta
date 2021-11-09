/*
 */
package com.ejercicio02.repositories;

import com.ejercicio02.entities.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {

    @Query("SELECT p FROM Prestamo p")
    List<Prestamo> mostrarPrestamos();

    @Modifying
    @Query("UPDATE Prestamo p SET p.alta = true WHERE p.id = :id")
    void darAltaPrestamo(@Param("id") String id);
    
    @Modifying
    @Query("UPDATE Prestamo p SET p.alta = false WHERE p.id = :id")
    void darBajaPrestamo(@Param("id") String id);

    @Query("SELECT p FROM Prestamo p WHERE p.id = :id")
    Prestamo buscarPrestamoPorId(@Param("id") String id);

}
