package it.prodotto.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    //  catena di filtri dal più stretto al più largo
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
            // Permetto l'accesso alle risorse statiche
            .requestMatchers("/css/**", "/login").permitAll()


            // Proteggi tutti gli altri percorsi richiedendo autenticazione.
            // In questo modo, Spring Security forza l'esecuzione del Controller dopo il login.
            .requestMatchers("/**").authenticated() 
        )
        .formLogin(form -> form
            
            .permitAll()
            //Forzo il redirect per attivare l'indexController
            .defaultSuccessUrl("/index", true) 
        )
        .logout(logout -> logout
            .permitAll()
        );

    return http.build();
}

    // aggiunta classe servizio controllo input user login
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // Encoder della password
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // controllo utente e psw di autenticazione del login 
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
    // inseriamo direttamente nel costruttore il nostro userDetailsService;
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
