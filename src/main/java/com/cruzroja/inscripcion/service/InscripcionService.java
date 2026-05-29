package com.cruzroja.inscripcion.service;

import com.cruzroja.inscripcion.entity.Alumno;
import com.cruzroja.inscripcion.entity.Hospital;
import com.cruzroja.inscripcion.entity.Inscripcion;
import com.cruzroja.inscripcion.repository.AlumnoRepository;
import com.cruzroja.inscripcion.repository.HospitalRepository;
import com.cruzroja.inscripcion.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruzroja.inscripcion.dto.EstadoAlumnoDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final AlumnoRepository alumnoRepository;
    private final HospitalRepository hospitalRepository;
    private final InscripcionRepository inscripcionRepository;

    public List<Hospital> obtenerHospitalesDisponibles(String dni, Integer curso) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findByDni(dni);

        if (alumnoOpt.isEmpty()) {
            // Si no existe, trae todos los que tengan cupo para su curso
            return hospitalRepository.findHospitalesDisponiblesPorCurso(curso);
        } else {
            // Si existe, le pasamos la variable 'dni' (String) a la consulta
            return hospitalRepository.findHospitalesDisponiblesForAlumno(curso, dni);
        }
    }

    public Alumno registrarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Transactional
    public void realizarInscripcion(String dni, Long hospitalId, Integer nroVuelta) {
        // Buscar el alumno
        Alumno alumno = alumnoRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Alumno con DNI " + dni + " no encontrado"));

        // 🔥 NUEVA VALIDACIÓN DE SEGURIDAD: Evitar duplicados para la misma vuelta
        List<Inscripcion> historial = inscripcionRepository.findByAlumnoDni(dni);
        boolean yaInscrito = historial.stream()
                .anyMatch(i -> i.getNroVuelta().equals(nroVuelta));

        if (yaInscrito) {
            throw new RuntimeException("¡Atención! Ya registraste un destino para la vuelta " + nroVuelta);
        }

        // Buscar el hospital
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital con ID " + hospitalId + " no encontrado"));

        // Verificar que hay cupo disponible
        if (hospital.getCupoActual() >= hospital.getCupoMaximo()) {
            throw new RuntimeException("No hay cupo disponible en el hospital " + hospital.getNombre());
        }

        // Incrementar el cupo actual del hospital
        hospital.setCupoActual(hospital.getCupoActual() + 1);
        hospitalRepository.save(hospital);

        // Crear y guardar la inscripción
        Inscripcion inscripcion = Inscripcion.builder()
                .alumno(alumno)
                .hospital(hospital)
                .fechaHora(LocalDateTime.now())
                .nroVuelta(nroVuelta)
                .build();

        inscripcionRepository.save(inscripcion);
    }

    // Le sacamos el parámetro 'curso'
    public EstadoAlumnoDTO verificarEstadoAlumno(String dni) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findByDni(dni);

        if (alumnoOpt.isEmpty()) {
            return EstadoAlumnoDTO.builder()
                    .existe(false)
                    .hospitalesDisponibles(List.of()) // Va vacío porque aún no sabemos de qué año es
                    .build();
        }

        Alumno alumno = alumnoOpt.get();
        Integer cursoDelAlumno = alumno.getCurso(); // ¡Lo sacamos de la base de datos!

        List<Inscripcion> historial = inscripcionRepository.findByAlumnoDni(dni);

        String destinoVuelta1 = "No realizó pasantía en la 1ra vuelta";
        String destinoVuelta2 = "";
        boolean yaInscritoVuelta2 = false;

        for (Inscripcion ins : historial) {
            if (ins.getNroVuelta() == 1) {
                destinoVuelta1 = ins.getHospital().getNombre() + " - " + ins.getHospital().getTurno();
            } else if (ins.getNroVuelta() == 2) {
                yaInscritoVuelta2 = true;
                destinoVuelta2 = ins.getHospital().getNombre() + " - " + ins.getHospital().getTurno();
            }
        }

        // Usamos el curso exacto que tiene guardado en su perfil
        List<Hospital> disponibles = hospitalRepository.findHospitalesDisponiblesForAlumno(cursoDelAlumno, dni);

        return EstadoAlumnoDTO.builder()
                .existe(true)
                .alumno(alumno)
                .destinoVuelta1(destinoVuelta1)
                .yaInscritoVuelta2(yaInscritoVuelta2)
                .destinoVuelta2(destinoVuelta2)
                .hospitalesDisponibles(disponibles)
                .build();
    }
}