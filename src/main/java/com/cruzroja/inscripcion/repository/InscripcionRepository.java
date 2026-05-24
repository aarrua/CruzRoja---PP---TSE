package com.cruzroja.inscripcion.repository;

import com.cruzroja.inscripcion.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByAlumnoDni(String dni);
}