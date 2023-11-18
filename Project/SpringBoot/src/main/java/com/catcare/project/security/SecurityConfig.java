package com.catcare.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration // Esta clase crea beans
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
            csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests( requests -> requests
                .requestMatchers("/h2/**").permitAll()
                /* .requestMatchers("/administrador/find").authenticated()
                .requestMatchers("/administrador/details").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/veterinario/details").hasAuthority("VETERINARIO")
                .requestMatchers("/cliente/details").hasAuthority("CLIENTE")
                */

                .requestMatchers("/catcare/clientes/details").hasAuthority("CLIENTE")
                
                .requestMatchers("/catcare/clientes/find/**").hasAuthority("CLIENTE")
                .requestMatchers("/catcare/clientes/mascotas/**").hasAuthority("CLIENTE")

                .requestMatchers("/catcare/veterinarios/details").hasAuthority("VETERINARIO")
                .requestMatchers("/catcare/pacientes/all").hasAnyAuthority("CLIENTE","VETERINARIO","ADMINISTRADOR")
                .requestMatchers("/catcare/clientes/all").hasAnyAuthority("VETERINARIO", "ADMINISTRADOR","CLIENTE")

                .requestMatchers("/catcare/administradores/details").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/delete/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/update/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/add").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/all").hasAnyAuthority("ADMINISTRADOR", "VETERINARIO")

              /*  .requestMatchers("/catcare/veterinarios/details").hasAuthority("VETERINARIO")
                .requestMatchers("/catcare/veterinarios/all").hasAuthority("VETERINARIO")
                .requestMatchers("/catcare/veterinarios/find/**").hasAuthority("VETERINARIO")
                .requestMatchers("/catcare/veterinarios/agregar").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/delete/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/update/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/catcare/veterinarios/veterinariosActivos").hasAuthority("VETERINARIO")
                .requestMatchers("/catcare/veterinarios/veterinariosInactivos").hasAuthority("VETERINARIO") */ 
                .anyRequest().permitAll()
            )
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));
            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

}