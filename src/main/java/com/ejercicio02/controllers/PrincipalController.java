/*
 */
package com.ejercicio02.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {

    @GetMapping("/index")
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }
}
