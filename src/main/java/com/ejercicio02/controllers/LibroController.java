/*

 */
package com.ejercicio02.controllers;

import com.ejercicio02.entities.Autor;
import com.ejercicio02.entities.Editorial;
import com.ejercicio02.entities.Libro;
import com.ejercicio02.services.AutorService;
import com.ejercicio02.services.LibroService;
import com.ejercicio02.services.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService lService;
    @Autowired
    private AutorService aService;
    @Autowired
    private EditorialService eService;

    @GetMapping
    public ModelAndView mostrarLibros() {
        ModelAndView mav = new ModelAndView("libros");
        mav.addObject("libros", lService.buscarLibros());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro() {
        ModelAndView mav = new ModelAndView("libros-formulario");
        mav.addObject("libro", new Libro());
        mav.addObject("autores", aService.buscarAutores());
        mav.addObject("editoriales", eService.buscarEditoriales());
        mav.addObject("title", "Crear Libro");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLibro(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("libros-formulario");
        mav.addObject("libro", lService.buscarLibroId(id));
        mav.addObject("autores", aService.buscarAutores());
        mav.addObject("editoriales", eService.buscarEditoriales());
        mav.addObject("title", "Editar Libro");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Autor autor, @RequestParam Editorial editorial, RedirectAttributes attributes) throws Exception {
        try {
            lService.crearLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            attributes.addFlashAttribute("exito", "LIBRO CREADO CON ÉXITO.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/libros/crear");
        }

        return new RedirectView("/libros");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Autor autor, @RequestParam Editorial editorial, RedirectAttributes attributes) throws Exception {
        try {
            lService.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            attributes.addFlashAttribute("exito", "LIBRO MODIFICADO CON ÉXITO.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/libros");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) {
        lService.eliminarLibro(id);
        return new RedirectView("/libros");
    }

    @PostMapping("/darAlta/{id}")
    public RedirectView darAlta(@PathVariable String id) {
        lService.darAltaLibro(id);
        return new RedirectView("/libros");
    }

}
