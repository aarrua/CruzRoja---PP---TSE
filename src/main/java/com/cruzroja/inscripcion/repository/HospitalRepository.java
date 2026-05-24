package com.cruzroja.inscripcion.repository;

import com.cruzroja.inscripcion.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("""
            SELECT DISTINCT h FROM Hospital h 
            WHERE h.curso = :curso 
            AND h.habilitado = true 
            AND h.cupoActual < h.cupoMaximo 
            AND h.nombre NOT IN (
                SELECT h2.nombre FROM Hospital h2 
                INNER JOIN Inscripcion i ON i.hospital.id = h2.id 
                INNER JOIN Alumno a ON i.alumno.id = a.id 
                WHERE a.dni = :dni
            )
            """)
    List<Hospital> findHospitalesDisponiblesForAlumno(@Param("curso") Integer curso, @Param("dni") String dni);

    @Query("SELECT h FROM Hospital h WHERE h.curso = :curso AND h.habilitado = true AND h.cupoActual < h.cupoMaximo")
    List<Hospital> findHospitalesDisponiblesPorCurso(@Param("curso") Integer curso);
}