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
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/uploads/**", "/", "/about-us", "/contacts", "/faq", "/privacy-policy",
                                "/general-conditions", "/excursions", "/subscribe").permitAll()
                        .requestMatchers("/excursions/my-excursions").hasRole("EMPLOYEE")
                        .requestMatchers("/destinations/add-destination","/destinations/update", "/destinations/update/**",
                                "/excursions/update", "/excursions/update/**", "/excursions/add-excursion",
                                "/excursions/delete-excursion/**").hasRole("MANAGER")
                        .requestMatchers("/employees/login", "/employees/login-error", "/employees/login/forgot-password",
                                "/register", "/excursions/reserve/**", "/excursions/send-inquiry").anonymous()
                        .requestMatchers("/employees/profile", "/employees",
                                "/excursions/reservations", "/employees/profile/update/**").authenticated()
                        .requestMatchers("/destinations/**", "/excursions/**",
                                "/excursions/excursion-details/**").permitAll()
                        .requestMatchers("/candidates", "/candidates/add-employee", "/candidates/delete-candidate",
                                "/employees/promote-employee", "/employees/delete-employee").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/employees/login")
                        .usernameParameter("email")
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
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
