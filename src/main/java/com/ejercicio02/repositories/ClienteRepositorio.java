/*

 */
package com.ejercicio02.repositories;

import com.ejercicio02.entities.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
    @Modifying
    @Query("UPDATE Cliente c SET c.dni = :dni, c.nombre = :nombre, c.apellido = :apellido, c.telefono = :telefono WHERE c.id = :id")
    void modificarCliente(@Param("id") String id, @Param("dni") Long dni, @Param("nombre") String nombre, @Param("apellido") String apellido, @Param("telefono") String telefono);

    @Query("SELECT c FROM Cliente c")
    List<Cliente> mostrarClientes();

    @Modifying
    @Query("UPDATE Cliente c SET c.alta = true WHERE c.id = :id")
    void darAltaCliente(@Param("id") String id);
    
    @Query("SELECT c FROM Cliente c WHERE c.dni = :dni")
    Cliente buscarClientePorDni(@Param("dni") Long dni);
    
        
    @Query("SELECT c FROM Cliente c WHERE c.id = :id")
    Cliente buscarClientePorId(@Param("id") String id);
        
}
