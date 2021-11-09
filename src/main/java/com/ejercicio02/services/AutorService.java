/*

 */
package com.ejercicio02.services;

import com.ejercicio02.entities.Autor;
import com.ejercicio02.repositories.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws Exception {

        validarNombreAutor(nombre.toUpperCase());
        Autor autor = new Autor();

        autor.setNombre(nombre.toUpperCase());
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public Autor buscarAutorPorId(String id) {
        Optional<Autor> autorOptional = autorRepositorio.findById(id);
        return autorOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarAutores() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void modificarAutor(String id, String nombre) throws Exception {
        validarNombreAutor(nombre.toUpperCase());
        autorRepositorio.modificarAutor(id, nombre.toUpperCase());
    }

    @Transactional
    public void eliminarAutor(String id) {
        autorRepositorio.deleteById(id);
    }

    @Transactional
    public void darAltaAutor(String id) {
        autorRepositorio.darAltaAutor(id);
    }

    // VALIDACIONES
    public void validarNombreAutor(String nombre) throws Exception {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("EL NOMBRE DEL AUTOR ES OBLIGATORIO.");
        }

        if (nombre.matches("^-?[0-9]+$")) {
            throw new Exception("EL NOMBRE NO PUEDE CONTENER DÍGITOS.");
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

        if (autorRepositorio.buscarAutorPorNombre(nombre) != null) {
            throw new Exception("YA EXISTE UN AUTOR CON ESE NOMBRE.");
        }

    }

}
