/*

 */
package com.ejercicio02.services;

import com.ejercicio02.entities.Libro;
import com.ejercicio02.entities.Prestamo;
import com.ejercicio02.repositories.ClienteRepositorio;
import com.ejercicio02.repositories.LibroRepositorio;
import com.ejercicio02.repositories.PrestamoRepositorio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private LibroRepositorio libroRespositorio;

    @Transactional
    public void crearPrestamo(String idLibro, String idCliente) throws Exception {

        Libro libro = libroRespositorio.findById(idLibro).orElse(null);

        if (libro.getEjemplaresRestantes() < 1) {
            throw new Exception("SIN EJEMPLARES DISPONIBLES");
        }

        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
        if (libro.getEjemplaresRestantes() < 1) {
            libro.setAlta(false);
        }
        libroRespositorio.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setFechaDevolucion(prestamo.getFechaPrestamo().plusWeeks(1));
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setCliente(clienteRepositorio.findById(idCliente).orElse(null));
        prestamo.setLibro(libro);
        prestamo.setAlta(true);

        prestamoRepositorio.save(prestamo);

    }

    @Transactional(readOnly = true)
    public List<Prestamo> buscarTodos() {
        return prestamoRepositorio.findAll();
    }

}
