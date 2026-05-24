package com.cruzroja.inscripcion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cruzroja.inscripcion.dto.EstadoAlumnoDTO;
import com.cruzroja.inscripcion.entity.Alumno;
import com.cruzroja.inscripcion.entity.Hospital;
import com.cruzroja.inscripcion.service.InscripcionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
public class InscripcionRestController {

    private final InscripcionService inscripcionService;

    @GetMapping("/disponibles")
    public ResponseEntity<EstadoAlumnoDTO> obtenerHospitalesDisponibles(@RequestParam String dni) {
        try {
            // Ahora solo le pasamos el DNI
            EstadoAlumnoDTO estado = inscripcionService.verificarEstadoAlumno(dni);
            return ResponseEntity.ok(estado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Alumno> registrarAlumno(@RequestBody Alumno alumno) {
        try {
            Alumno alumnoRegistrado = inscripcionService.registrarAlumno(alumno);
            return ResponseEntity.status(HttpStatus.CREATED).body(alumnoRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/inscribir")
    public ResponseEntity<String> realizarInscripcion(
            @RequestParam String dni,
            @RequestParam Long hospitalId,
            @RequestParam Integer nroVuelta) {
        try {
            inscripcionService.realizarInscripcion(dni, hospitalId, nroVuelta);
            return ResponseEntity.ok("Inscripción realizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }
}
