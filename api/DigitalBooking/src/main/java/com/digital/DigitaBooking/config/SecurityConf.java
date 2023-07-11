package com.digital.DigitaBooking.config;


import com.digital.DigitaBooking.jwt.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.digital.DigitaBooking.models.entities.Role.ADMIN;
import static com.digital.DigitaBooking.models.entities.Role.USER;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                                // Rutas para autorizaciones
                                .requestMatchers(HttpMethod.GET, "/welcome/anyone").permitAll()
                                .requestMatchers(HttpMethod.GET, "/welcome/user")
                                .hasAnyAuthority(USER.name(), ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/sign-up").permitAll()
                                .requestMatchers(HttpMethod.GET, "/welcome/admin").hasAuthority(ADMIN.name())
                                // Rutas para tours
                                .requestMatchers(HttpMethod.GET, "/tours").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/byCategory/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/country/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/country/name/{countryName}").permitAll()
                                .requestMatchers(HttpMethod.POST,"tours/filterTourByProximity").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/filterByCountryAndDates/{countryId}/{initialDate}/{finalDate}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/tours").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/tours/{id}").hasAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/tours/{id}").hasAnyAuthority(ADMIN.name())
                                // Rutas para categorías
                                .requestMatchers(HttpMethod.GET, "/category").permitAll()
                                .requestMatchers(HttpMethod.GET, "/category/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/category").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/category/load_image").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/category/{id}").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/category/{id}").hasAuthority(ADMIN.name())
                                // Rutas para usuarios
                                .requestMatchers(HttpMethod.GET, "/users").hasAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/users/{id}")
                                .hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(HttpMethod.GET, "/users/name/{name}")
                                .hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(HttpMethod.POST, "/users/admin").hasAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/users/users").hasAuthority(ADMIN.name())
                                // Rutas para puntuaciones
                                .requestMatchers(HttpMethod.GET, "/scores/fromTour/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/scores/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/scores").hasAnyAuthority(USER.name())
                                .requestMatchers(HttpMethod.PUT, "/scores/update").hasAnyAuthority(USER.name())
                                .requestMatchers(HttpMethod.DELETE, "/scores/delete/{id}").hasAuthority(USER.name())
                                // Rutas para características
                                .requestMatchers(HttpMethod.GET, "/features").permitAll()
                                .requestMatchers(HttpMethod.GET, "/features/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/features").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/features/{id}").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/features/{id}").hasAnyAuthority(ADMIN.name())
                                // Rutas para países
                                .requestMatchers(HttpMethod.GET, "/countries").permitAll()
                                .requestMatchers(HttpMethod.GET, "/countries/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/countries").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"countries/byDistance").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/countries/{id}").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/countries/{id}").hasAnyAuthority(ADMIN.name())
                                // Rutas para reservas
                                .requestMatchers(HttpMethod.GET, "/reservations/byTour/{id}").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(HttpMethod.GET, "/reservations/byUser/{id}").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(HttpMethod.POST, "/reservations").hasAnyAuthority(USER.name())
                                .requestMatchers(HttpMethod.DELETE, "/reservations/{id}").hasAnyAuthority(USER.name())
                                .anyRequest().authenticated())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowedMethods(List.of("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    protected void configure(HttpSecurity http) throws Exception {
        http.logout()
                .logoutUrl("/logout") // URL de cierre de sesión
                .logoutSuccessUrl("/login") // Redireccionar a la página de inicio de sesión después de cerrar sesión
                .invalidateHttpSession(true) // Invalidar la sesión actual
                .deleteCookies("JSESSIONID") // Elimina las cookies específicas (si es necesario)
                .permitAll();
    }

}

