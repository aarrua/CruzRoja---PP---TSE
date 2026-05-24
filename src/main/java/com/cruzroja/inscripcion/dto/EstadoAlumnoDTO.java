package com.cruzroja.inscripcion.dto;

import com.cruzroja.inscripcion.entity.Alumno;
import com.cruzroja.inscripcion.entity.Hospital;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class EstadoAlumnoDTO {
    private boolean existe;
    private Alumno alumno;
    private String destinoVuelta1;
    private boolean yaInscritoVuelta2;
    private String destinoVuelta2; // <-- NUEVO CAMPO
    private List<Hospital> hospitalesDisponibles;
}