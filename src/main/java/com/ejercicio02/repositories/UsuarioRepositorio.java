
package com.ejercicio02.repositories;

import com.ejercicio02.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario, String> {
    
    Optional<Usuario> findByCorreo(String correo);
    
    boolean existsUsuarioByCorreo(String correo);
    
}
