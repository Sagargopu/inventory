package com.example.inventory.Security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer->customizer.disable())
                .authorizeHttpRequests(request->
                        request.requestMatchers("/users/register")
                                .permitAll()
                                .requestMatchers("/addresses").permitAll()
                                .requestMatchers("/categories").permitAll()
                                .requestMatchers("/deliveries").permitAll()
                                .requestMatchers("/drivers").permitAll()
                                .requestMatchers("/inventory").permitAll()
                                .requestMatchers("/orders").permitAll()
                                .requestMatchers("/products").permitAll()
                                .requestMatchers("/stockarrivals").permitAll()
                                .requestMatchers("/stockitems").permitAll()
                                .requestMatchers("/users").permitAll()
                                .requestMatchers("/vehicles").permitAll()
                                .requestMatchers("/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }


    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);  // Strength parameter, can be adjusted
    }
}
