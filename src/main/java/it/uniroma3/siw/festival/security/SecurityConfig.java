package it.uniroma3.siw.festival.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

// Questa classe contiene la configurazione
// principale di Spring Security.
@Configuration
public class SecurityConfig {


    // Questo Bean dice a Spring Security
    // quali pagine possono essere viste
    // e da quali utenti.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

        .authorizeHttpRequests(auth -> auth

        		// =========================
        		// OPERAZIONI SOLO ADMIN
        		// =========================

        		// Proteggiamo le pagine che mostrano
        		// i form di creazione e modifica.
        		.requestMatchers(
        		        "/films/new",
        		        "/film/edit/**",

        		        "/festival/new",
        		        "/festival/edit/**",

        		        "/registi/new",
        		        "/regista/edit/**",

        		        "/sala/new",
        		        "/sala/edit/**",

        		        "/proiezione/new",
        		        "/proiezione/edit/**",
        		        "/proiezione/delete/**"

        		        
        		).hasRole("ADMIN")

        		// Proteggiamo anche le POST.
        		//
        		// Questo è fondamentale:
        		// nascondere il pulsante nell'HTML NON basta.
        		// Anche se uno USER costruisse manualmente
        		// una richiesta POST, Spring Security la blocca.
        		.requestMatchers(
        		        HttpMethod.POST,
        		        "/films",
        		        "/festival",
        		        "/registi",
        		        "/sala",
        		        "/proiezione"
        		).hasRole("ADMIN")
        	    
	        	 // =========================
	        	 // RECENSIONI - UTENTE LOGGATO
	        	 // =========================
	        	 // Per creare, modificare o eliminare una Recensione
	        	 // bisogna essere autenticati come USER oppure ADMIN.
        		.requestMatchers(
        		        "/recensione/new",
        		        "/recensione/edit/**",
        		        "/recensione/delete/**",
        		        "/recensione"
        		).hasAnyRole("USER", "ADMIN")

	        	// =========================
	        	// PAGINE PUBBLICHE
	        	// =========================
	        	// Possono essere viste anche senza login.
	        	.requestMatchers(
	        	        "/films",
	        	        "/film/**",

	        	        "/festivals",
	        	        "/festival/**",

	        	        "/proiezioni",
	        	        "/proiezione/**",

	        	        "/registi",
	        	        "/regista/**",

	        	        "/sale",
	        	        "/sala/**",

	        	        // Le Recensioni possono essere lette
	        	        // anche senza aver effettuato il login.
	        	        "/recensioni",
	        	        "/recensione/*"

	        	).permitAll()

	        	// Le API REST di lettura sono pubbliche.
	        	.requestMatchers(HttpMethod.GET, "/api/**").permitAll()

	        	// Eventuali API di modifica richiedono il ruolo ADMIN.
	        	// Per ora non abbiamo endpoint REST di modifica.
	        	.requestMatchers("/api/**").hasRole("ADMIN")
        	    // Tutto ciò che non rientra nelle regole precedenti
        	    // richiede almeno di essere autenticati.
        	    .anyRequest().authenticated()

        	)


            // Usiamo temporaneamente
            // il form di login standard di Spring.
            .formLogin(form -> form
            		// Dopo un login riuscito,
            	    // manda sempre l'utente alla lista dei film.
            	    .defaultSuccessUrl("/films", true)
            	    .permitAll()
            )


            // Permettiamo anche il logout.
            .logout(logout -> logout
                .permitAll()
            );


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        // BCrypt trasforma la password in un hash sicuro.
        // La password reale non viene salvata nel database.
        return new BCryptPasswordEncoder();
    }
}