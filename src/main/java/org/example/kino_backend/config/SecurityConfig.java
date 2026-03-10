package org.example.kino_backend.config;

import org.example.kino_backend.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration //This contains Spring beans class
@EnableWebSecurity //This tells Spring to use Spring Security
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //csrf protection disabled since we don't use sessions
                .csrf(csrf -> csrf.disable())
                //Never create HTTP sessions, all request must bring their own token
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //There are the security rules we set
                        .requestMatchers("/api/auth/**").permitAll() //Login endpoint, anyone can access
                        .requestMatchers("/api/admin/**").authenticated() //Must be authenticated with valid JWT token
                        .anyRequest().permitAll() //All public so everyone can access
                )
                //We add the filter before Spring sec's own default filter so we can set auth in SecurityContextHolder
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();
    }
}
