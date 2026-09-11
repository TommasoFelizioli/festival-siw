package it.uniroma3.siw.festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.festival.model.Film;
import it.uniroma3.siw.festival.model.Recensione;
import it.uniroma3.siw.festival.model.Utente;


// Repository che gestisce le Recensioni nel database.
public interface RecensioneRepository
        extends CrudRepository<Recensione, Long> {

    // Controlla se esiste già una Recensione
    // scritta da questo Utente per questo Film.
    //
    // Spring costruisce automaticamente la query
    // leggendo il nome del metodo.
	// Esiste già una recensione
	// dove utente = questo utente
	// e film = questo film?
	// true  → sì, esiste già
	// false → no, non esiste
    boolean existsByUtenteAndFilm(Utente utente, Film film);
 // Controlla se esiste un'ALTRA Recensione
 // dello stesso Utente per lo stesso Film,
 // escludendo la Recensione che stiamo modificando.
 boolean existsByUtenteAndFilmAndIdNot(
         Utente utente,
         Film film,
         Long id
 );
 
//Trova tutte le recensioni associate a un determinato Film.
Iterable<Recensione> findByFilm(Film film);

}