/*

 */
package com.ejercicio02.services;

import com.ejercicio02.entities.Usuario;
import com.ejercicio02.repositories.UsuarioRepositorio;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usarioRepositorio;
    
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void crear(String nombre, String apellido, String correo, String clave) throws Exception {
        if (usarioRepositorio.existsUsuarioByCorreo(correo)) {
            throw new Exception("CORREO YA REGISTRADO.");
        }

        validarNombre(nombre.toUpperCase());
        validarApellido(apellido.toUpperCase());
        validarMail(correo);

        Usuario user = new Usuario();
        user.setNombre(nombre.toUpperCase());
        user.setApellido(apellido.toUpperCase());
        user.setCorreo(correo.toLowerCase());
        user.setClave(encoder.encode(clave));
        user.setAlta(true);
        usarioRepositorio.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usarioRepositorio.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("CORREO NO REGISTRADO."));
        return new User(usuario.getCorreo(), usuario.getClave(), Collections.emptyList());
    }
    
    
    
    
    
    

    // VALIDACIONES
    public void validarMail(String mail) {

        Pattern pat = Pattern.compile("[^@]+@[^@]+\\.[a-zA-Z]{2,}");
        //Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mat = pat.matcher(mail);
        if (mat.find()) {
            System.out.println("CORREO VÁLIDO.");
        } else {
            System.out.println("CORREO INVÁLIDO.");
        }
    }

    public void validarNombre(String nombre) throws Exception {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("EL NOMBRE ES OBLIGATORIO.");
        }

        Boolean validacion = false;
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isAlphabetic((nombre.charAt(i)))) {
                validacion = true;
            }
        }

        if (validacion == false) {
            throw new Exception("EL NOMBRE NO PUEDE CONTENER DÍGITOS.");
        }
    }

    public void validarApellido(String apellido) throws Exception {

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("EL APELLIDO ES OBLIGATORIO.");
        }

        Boolean validacion = false;
        for (int i = 0; i < apellido.length(); i++) {
            if (Character.isAlphabetic((apellido.charAt(i)))) {
                validacion = true;
            }
        }
        if (validacion == false) {
            throw new Exception("EL APELLIDO NO PUEDE CONTENER DÍGITOS.");
        }
    }

}
