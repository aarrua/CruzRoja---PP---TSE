package com.cruzroja.inscripcion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alumnos", uniqueConstraints = {@UniqueConstraint(columnNames = "dni")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, length = 255)
    private String nombreApellido;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private Integer curso;

    @Column(nullable = false)
    private String division;
}
