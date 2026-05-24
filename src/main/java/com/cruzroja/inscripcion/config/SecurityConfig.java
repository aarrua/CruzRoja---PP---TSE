package com.cruzroja.inscripcion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF temporalmente para simplificar el envío del formulario con JavaScript
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Solo dejamos públicos los recursos estáticos (imágenes, estilos)
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // TODA la aplicación (incluida la pantalla principal y la API) requiere la contraseña general
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                // Habilita el formulario de login automático de Spring para todos
                .permitAll()
            );
            
        return http.build();
    }
}