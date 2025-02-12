package org.travelagency.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/", "/about-us", "/contacts", "/faq", "/privacy-policy",
                                "/general-conditions", "/destinations/**", "/excursions/**",
                                "/excursions/excursion-details/**", "/excursions/reserve/**").permitAll()
                        .requestMatchers("/employees/login", "/register").anonymous()
                        .requestMatchers("/employees/profile", "/employees", "/excursions").authenticated()
                        .requestMatchers("/candidates", "/candidates/add-employee", "/candidates/delete-candidate",
                                "/employees/promote-employee", "/employees/delete-employee",
                                "/destinations/add-destination", "/excursions/add-excursion").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/employees/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/employees/profile", true)
                        .failureUrl("/employees/login-error")
                )
                .logout(logout -> logout
                        .logoutUrl("/employees/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
