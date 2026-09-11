package it.uniroma3.siw.festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.festival.model.Utente;


// Repository che gestisce gli Utenti nel database.
public interface UtenteRepository
        extends CrudRepository<Utente, Long> {

    // Controlla se esiste già un Utente con questo username.
    boolean existsByUsername(String username);
	 // Cerca un Utente tramite username.
	 // Ci servirà quando Spring Security deve
	 // controllare chi sta provando a fare login.
	 Utente findByUsername(String username);
}