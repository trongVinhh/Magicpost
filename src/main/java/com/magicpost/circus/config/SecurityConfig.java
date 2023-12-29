package com.magicpost.circus.config;

import com.magicpost.circus.security.JwtAuthenticationEntryPoint;
import com.magicpost.circus.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                // configure CORS for http request from client
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(request ->
                                {
                                    CorsConfiguration configuration = new CorsConfiguration();
                                    configuration.addAllowedOrigin("*");
                                    configuration.addAllowedMethod(HttpMethod.PUT);
                                    configuration.addAllowedMethod(HttpMethod.GET);
                                    configuration.addAllowedMethod(HttpMethod.POST);
                                    configuration.addAllowedMethod(HttpMethod.DELETE);
                                    configuration.addAllowedHeader("*");
                                    configuration.applyPermitDefaultValues();
                                    return new CorsConfiguration().applyPermitDefaultValues();
                                }
                        )
                )
                // configure authorization for http request from client
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                                                            .requestMatchers("/api/v1/auth/**").permitAll()
                                                            .anyRequest().authenticated())
                                                            .httpBasic(Customizer.withDefaults())
                // configure exception handling for http request from client
                .exceptionHandling((exception) ->
                        exception.authenticationEntryPoint((authenticationEntryPoint)))

                // configure session management for http request from client
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // configure authentication filter for http request from client
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
