    /*

 */
package com.ejercicio02.services;

import com.ejercicio02.entities.Autor;
import com.ejercicio02.entities.Editorial;
import com.ejercicio02.utilities.GeneradorISBN;
import com.ejercicio02.entities.Libro;
import com.ejercicio02.repositories.LibroRepositorio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;
    private AutorService aService;
    private EditorialService eService;
    private GeneradorISBN isbnRandom;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) throws Exception {

        validarTituloLibro(titulo.toUpperCase());
        validarIsbn(isbn);
        validarAnio(anio);
        validarEjemplares(ejemplares);
        validarEjemplaresPrestados(ejemplaresPrestados, ejemplares);

//        String strIsbn = isbnRandom.isbnRandom();
//        Long isbn = Long.parseLong(strIsbn);
//        
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo.toUpperCase());
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setAlta(true);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) throws Exception {
        
        Libro libro = libroRepositorio.findById(id).get();
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("EL NOMBRE DEL LIBRO ES OBLIGATORIO.");
        }
        validarIsbn(isbn);
        validarAnio(anio);
        validarEjemplares(ejemplares);
        validarEjemplaresPrestados(ejemplaresPrestados, ejemplares);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo.toUpperCase());
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void eliminarLibro(String id) {
        libroRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Libro buscarLibroId(String id) {
        Optional<Libro> libroOptional = libroRepositorio.findById(id);
        return libroOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional
    public void darAltaLibro(String id) {
        libroRepositorio.darAltaLibro(id);

    }
    
    // VALIDACIONES

    public void validarTituloLibro(String titulo) throws Exception {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("EL NOMBRE DEL LIBRO ES OBLIGATORIO.");
        }

        if (libroRepositorio.buscarLibroPorTitulo(titulo) != null) {
            throw new Exception("YA EXISTE UN LIBRO CON ESE NOMBRE.");
        }
    }

    public void validarEjemplares(Integer ejemplares) throws Exception {
        if (ejemplares == null || ejemplares < 0) {
            throw new Exception("LA CANTIDAD DE EJEMPLARES NO PUEDE SER MENOR A 0.");
        }
    }

    public void validarEjemplaresPrestados(Integer ejemplaresPrestados, Integer ejemplares) throws Exception {

        if (ejemplaresPrestados == null || ejemplaresPrestados < 0) {
            throw new Exception("LA CANTIDAD DE EJEMPLARES PRESTADOS NO PUEDE SER MENOR A 0.");
        }
        if (ejemplaresPrestados > ejemplares) {
            throw new Exception("LA CANTIDAD DE LIBROS PRESTADOS NO PUEDE SUPERAR LA CANTIDAD DE EJEMPLARES.");
        }
    }

    public void validarAnio(Integer anio) throws Exception {

        
//      El 11 de mayo del año 868, Wang Jie autorizó la impresión y distribución 
//      de El Sutra del Diamante, el libro impreso más antiguo del que se tiene conocimiento, 
//      el cual se estampó casi 600 años antes que la Biblia de Gutenberg.
        if (anio == null || anio < 867) {
            throw new Exception("EL AÑO NO PUEDE SER MENOR A 868.");
        }
                
        Date date = new Date();
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String anioString = getYearFormat.format(date);
        Integer anioActual = Integer.parseInt(anioString);

        if (anio > anioActual) {
            throw new Exception("EL AÑO NO PUEDE SER MAYOR AL AÑO ACTUAL.");
        }
       
//        
//        if (!String.valueOf(anio).matches("\\d{4}")) {
//            throw new ExcepcionSpring("El año de publicación debe estar compuesto de 4 dígitos");
//        }

    }

    public void validarIsbn(Long isbn) throws Exception {

        int digitos = 0;
        while (isbn != 0) {
            isbn = isbn / 10;
            digitos++;
        }
        if (isbn == null || digitos != 13) {
            throw new Exception("EL ISBN DEBE TENER 13 DÍGITOS.");
        }

        if (libroRepositorio.buscarLibroPorIsbn(isbn) != null) {
            throw new Exception("YA EXISTE UN LIBRO CON ESE ISBN.");
        }

    }

}
