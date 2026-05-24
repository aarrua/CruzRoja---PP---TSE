package com.cruzroja.inscripcion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospitales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String turno;

    @Column(nullable = false)
    private Integer cupoMaximo;

    @Column(nullable = false)
    @Builder.Default
    private Integer cupoActual = 0;

    @Column(nullable = false)
    private Integer curso;

    @Column(nullable = false)
    @Builder.Default
    private boolean habilitado = true;
}
