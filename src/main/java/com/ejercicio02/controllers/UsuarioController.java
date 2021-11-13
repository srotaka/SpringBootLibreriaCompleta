/*
 */
package com.ejercicio02.controllers;

import com.ejercicio02.services.UsuarioService;
import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService uService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {

        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("error", "CORREO O CONTRASEÑA INCORRECTO");
        }
        if (logout != null) {
            mav.addObject("logout", "Sesión Finalizada");
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("signup");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("nombre", flashMap.get("nombre"));
            mav.addObject("apellido", flashMap.get("apellido"));
            mav.addObject("correo", flashMap.get("correo"));
            mav.addObject("clave", flashMap.get("clave"));
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @PostMapping("/registro")
    public RedirectView signup(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String correo, @RequestParam String clave, RedirectAttributes attributes, HttpServletRequest request) {
        RedirectView redirectView = new RedirectView("/login");

        try {
            uService.crear(nombre, apellido, correo, clave);
            attributes.addFlashAttribute("exito", "SE HA REGISTRADO CON ÉXITO.");
            request.login(correo, clave);
            redirectView.setUrl("/index");

        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("apellido", apellido);
            attributes.addFlashAttribute("correo", correo);
            attributes.addFlashAttribute("clave", clave);
            redirectView.setUrl("/signup");
        }
        return redirectView;
    }
    
    
}
