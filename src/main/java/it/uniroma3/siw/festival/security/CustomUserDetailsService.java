package it.uniroma3.siw.festival.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.festival.model.Utente;
import it.uniroma3.siw.festival.repository.UtenteRepository;


// Questa classe dice a Spring Security
// come recuperare un nostro Utente dal database.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UtenteRepository utenteRepository;


    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    // Questo metodo viene chiamato automaticamente
    // da Spring Security quando qualcuno prova a fare login.
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Cerchiamo il nostro Utente nel database.
        Utente utente = utenteRepository.findByUsername(username);


        // Se non esiste, il login deve fallire.
        if (utente == null) {

            throw new UsernameNotFoundException(
                    "Utente non trovato"
            );
        }


        // Convertiamo il nostro Utente
        // in un oggetto che Spring Security conosce.
        return User
                .withUsername(utente.getUsername())
                .password(utente.getPassword())
                .roles(utente.getRuolo())
                .build();
    }
}