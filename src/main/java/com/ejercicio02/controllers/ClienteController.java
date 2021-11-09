/*

 */
package com.ejercicio02.controllers;

import com.ejercicio02.entities.Cliente;
import com.ejercicio02.services.ClienteService;
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

public class ClienteController {

    @Controller
    @RequestMapping("/clientes")
    public class AutorController {

        @Autowired
        private ClienteService cService;

        @GetMapping
        public ModelAndView mostrarClientes() {
            ModelAndView mav = new ModelAndView("clientes");
            mav.addObject("clientes", cService.buscarClientes());
            return mav;
        }

        @GetMapping("/crear")
        public ModelAndView crearCliente() {
            ModelAndView mav = new ModelAndView("cliente-formulario");
            mav.addObject("cliente", new Cliente());
            mav.addObject("title", "Crear Cliente");
            mav.addObject("action", "guardar");
            return mav;
        }

        @GetMapping("/editar/{id}")
        public ModelAndView editarCliente(@PathVariable String id) {
            ModelAndView mav = new ModelAndView("cliente-formulario");
            mav.addObject("cliente", cService.buscarClientePorId(id));
            mav.addObject("title", "Editar Cliente");
            mav.addObject("action", "modificar");
            return mav;
        }

        @PostMapping("/guardar")
        public RedirectView guardar(@RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, RedirectAttributes attributes) throws Exception {
            try {
                cService.crearCliente(dni, nombre, apellido, telefono);
                attributes.addFlashAttribute("exito", "CLIENTE CREADO CON ÉXITO.");
            } catch (Exception e) {
                attributes.addFlashAttribute("error", e.getMessage());
                return new RedirectView("/clientes/crear");
            }
            return new RedirectView("/clientes");
        }

        @PostMapping("/modificar")
        public RedirectView modificar(@RequestParam String id, @RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, RedirectAttributes attributes) {
            try {
                cService.modificarCliente(id, dni, nombre, apellido, telefono);
                attributes.addFlashAttribute("exito", "CLIENTE MODIFICADO CON ÉXITO.");
            } catch (Exception e) {
                attributes.addFlashAttribute("error", e.getMessage());
            }

            return new RedirectView("/clientes");
        }

        @PostMapping("/eliminar/{id}")
        public RedirectView eliminar(@PathVariable String id) {
            cService.eliminarCliente(id);
            return new RedirectView("/clientes");
        }

        @PostMapping("/darAlta/{id}")
        public RedirectView darAlta(@PathVariable String id) {
            cService.darAltaCliente(id);
            return new RedirectView("/clientes");
        }
    }
}
