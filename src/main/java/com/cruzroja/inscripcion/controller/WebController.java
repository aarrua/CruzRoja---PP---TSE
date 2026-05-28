package com.cruzroja.inscripcion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Cuando el usuario entra a la ruta raíz ("/") y ya pasó el login, le mostramos el HTML principal
    @GetMapping("/")
    public String mostrarPantallaPrincipal() {
        return "index"; // Esto le dice a Spring que busque el archivo "index.html" en la carpeta templates
    }
}